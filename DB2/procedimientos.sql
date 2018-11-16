use control_piezas_2;

DELIMITER //
CREATE PROCEDURE agregar_pedido(
IN desc_orden_compra	VARCHAR(50),
IN desc_cliente			VARCHAR(20),
IN desc_contacto		VARCHAR(50),
IN fecha_entrega		DATETIME,
INOUT resultado			VARCHAR(150),
INOUT r_id_pedido		INT	
)
BEGIN
	DECLARE id_contacto INT;
    DECLARE id_pedido INT;
    DECLARE id_estado INT;
    IF NOT EXISTS (SELECT * FROM pedidos WHERE no_orden_compra = desc_orden_compra)
    THEN
		SELECT @id_contacto := contactos.id_contacto FROM contactos JOIN clientes ON contactos.id_cliente = clientes.id_cliente
        WHERE clientes.nombre_cliente = desc_cliente AND contactos.desc_contacto = desc_contacto;
        
        IF @id_contacto IS NOT NULL THEN
			
            SELECT @id_estado := todos_los_estados.id_estado FROM todos_los_estados WHERE desc_tipo_estado = 'PEDIDOS' AND desc_estados = 'ABIERTO';
            
        
			INSERT INTO pedidos(fecha_recepcion,no_orden_compra,id_contacto,id_estado,fecha_entrega) 
            VALUES (now(),desc_orden_compra,@id_contacto,@id_estado,fecha_entrega);
            
            SELECT @id_pedido := pedidos.id_pedido FROM pedidos WHERE pedidos.no_orden_compra = desc_orden_compra;

			SELECT @id_estado := todos_los_estados.id_estado FROM todos_los_estados
            WHERE desc_tipo_estado = 'ORDENES DE TRABAJO' AND desc_estados = 'ABIERTO';
            	
				
            INSERT INTO ordenes_trabajo(id_pedido,id_estado) VALUES(@id_pedido,@id_estado);
			
            SET resultado = 'SE HA AGREGADO CORRECTAMENTE EL PEDIDO';					
			SET r_id_pedido = @id_pedido;
        END IF;       
	
    ELSE
		SET resultado = 'NO HA SIDO POSIBLE AGREGAR EL PEDIDO, ES POSIBLE QUE YA EXISTA UN PEDIDO CON ESTA DESCRIPCION';
		SET r_id_pedido = 0;
    END IF;

END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_orden_produccion(
IN id_pedido		 		INT,
IN desc_producto			VARCHAR(50),
IN cantidad_cliente			INT
)
BEGIN
	DECLARE id_producto INT;
    DECLARE id_orden_trabajo INT;
    DECLARE id_orden_p INT;
    DECLARE id_estado INT;  
	DECLARE id_tipo_proceso INT;
    
	IF EXISTS(SELECT * FROM ordenes_trabajo WHERE ordenes_trabajo.id_pedido = id_pedido)
    THEN
		SELECT @id_orden_trabajo := ordenes_trabajo.id_orden_trabajo FROM ordenes_trabajo WHERE ordenes_trabajo.id_pedido = id_pedido;
		SELECT @id_producto := productos.id_producto FROM productos WHERE productos.clave_producto = desc_producto;
		
        IF @id_producto IS NOT NULL THEN		
			
            SELECT @id_estado := todos_los_estados.id_estado FROM todos_los_estados
            WHERE desc_tipo_estado = 'ORDENES DE PRODUCCION' AND desc_estados = 'ABIERTO';           
            
            INSERT INTO ordenes_produccion(id_orden_trabajo,id_producto,id_estado,cantidad_cliente,fecha_registro)
            VALUES(@id_orden_trabajo,@id_producto,@id_estado,cantidad_cliente,now());        
			
			SELECT @id_orden_p := max(id_orden_produccion) FROM ordenes_produccion;
            
            SELECT @id_estado := todos_los_estados.id_estado FROM todos_los_estados
            WHERE desc_tipo_estado = 'PROCESOS DE PRODUCCION' AND desc_estados = 'PLANEACION';
            
            SELECT @id_tipo_proceso := tipos_proceso.id_tipo_proceso FROM tipos_proceso WHERE desc_tipo_proceso = 'MAQUINADO';
            
			INSERT INTO procesos_produccion(id_orden_produccion,id_tipo_proceso,id_estado) 
            VALUES(@id_orden_p,@id_tipo_proceso,@id_estado);
            
        END IF;
	
	END IF;	
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_orden_maquina(
IN id_orden_produccion		INT,
IN desc_producto			VARCHAR(50),
IN nuevo_worker				FLOAT,
IN nueva_cantidad_total		INT,
IN desc_maquina				VARCHAR(50),
IN desc_material			VARCHAR(100),
IN fecha_montaje_molde		DATE,
IN fecha_inicio_produccion	DATE,
IN nuevo_piezas_por_turno	INT,
IN n_orden					INT,
INOUT resultado				VARCHAR(100)
)
BEGIN

	DECLARE id_material INT;
    DECLARE id_maquina INT;
    DECLARE id_proceso_produccion INT;
    DECLARE id_nuevo_estado INT;
    DECLARE id_producto INT;
    DECLARE cantidad_por_lote_planeado INT;
    DECLARE id_orden_trabajo INT;
	IF EXISTS(SELECT * FROM ordenes_produccion WHERE id_orden_produccion = id_orden_produccion)
    THEN
		        
        SELECT @id_material := materiales.id_material FROM materiales WHERE materiales.desc_material = desc_material;
        SELECT @id_maquina := maquinas.id_maquina FROM maquinas WHERE maquinas.desc_maquina = desc_maquina;
        SELECT @id_producto := productos.id_producto FROM productos WHERE productos.clave_producto = desc_producto;	
        /*procesos para seleccionar el estado*/
		SELECT @id_nuevo_estado := todos_los_estados.id_estado 
        FROM todos_los_estados WHERE  desc_tipo_estado = 'PROCESOS DE PRODUCCION'
        AND desc_estados = 'EN ESPERA';
        /*fin seleccion estado*/
			
		CALL actualizar_orden_produccion(nuevo_worker,nueva_cantidad_total,fecha_montaje_molde,
					fecha_inicio_produccion,nuevo_piezas_por_turno,id_orden_produccion,@id_producto);
        
		SET SQL_SAFE_UPDATES=0;		      
		UPDATE procesos_produccion 
        SET 
        procesos_produccion.id_maquina = @id_maquina,
        procesos_produccion.id_estado = @id_nuevo_estado
        WHERE procesos_produccion.id_orden_produccion = id_orden_produccion;
		SET SQL_SAFE_UPDATES=1;
		
		CALL agregar_lotes_planeados(id_orden_produccion,
        nueva_cantidad_total,nuevo_piezas_por_turno,fecha_inicio_produccion,nuevo_worker);
        
        
        IF n_orden = 1 THEN
			
            SELECT @id_orden_trabajo := ot.id_orden_trabajo FROM ordenes_trabajo AS ot WHERE ot.id_orden_trabajo = 
			(SELECT op.id_orden_trabajo FROM ordenes_produccion  AS op WHERE op.id_orden_produccion = id_orden_produccion);
		
			INSERT INTO requisiciones(id_orden_trabajo,fecha_creacion) VALUES(@id_orden_trabajo,NOW());
            
        END IF;
        
		 call agregar_material_orden_requerida(id_orden_produccion,@id_material);
        
        SET resultado = 'TODO SE REALIZO CORRECTAMENTE';					
    
    ELSE
    
		SET resultado = 'NO FUE POSIBLE COMPLETAR LA OPERACION';					
        
    END IF;

END //
DELIMITER ;            


DELIMITER //
CREATE PROCEDURE agregar_material_orden_requerida(
IN id_orden_produccion	INT,
IN id_material			INT
)
BEGIN
	DECLARE id_requisicion INT;    
    DECLARE id_estado 		INT;
    DECLARE id_material_requerido INT;
    
    SELECT @id_requisicion := max(requisiciones.id_requisicion) FROM requisiciones;	
    
    IF NOT EXISTS(SELECT * FROM  materiales_requeridos AS mr 
    WHERE mr.id_material = id_material AND mr.id_requisicion = @id_requisicion) THEN    
		SELECT @id_estado := todos_los_estados.id_estado FROM todos_los_estados WHERE desc_tipo_estado = 'REQUISICIONES' and desc_estados = 'ABIERTO';
		INSERT INTO materiales_requeridos(id_material,id_estado,id_requisicion) 
		VALUES(id_material,@id_estado,@id_requisicion);    
    END IF;
    
    SELECT @id_material_requerido := mr.id_material_requerido 
    FROM  materiales_requeridos AS mr WHERE mr.id_material = id_material AND mr.id_requisicion = @id_requisicion;
    
    INSERT INTO materiales_ordenes_requeridas(id_material_requerido,id_orden_produccion)
    VALUES(@id_material_requerido,id_orden_produccion);    		    
    
END	//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE actualizar_orden_produccion(
IN nuevo_worker 			FLOAT,
IN nueva_cantidad_total		INT,
IN fecha_montaje_molde		DATE,
IN fecha_inicio_produccion	DATE,
IN nuevo_piezas_por_turno	INT,
IN id_orden_produccion		INT,
IN id_producto				INT
)
BEGIN
    UPDATE ordenes_produccion 
    SET 
    ordenes_produccion.worker = nuevo_worker,
    ordenes_produccion.cantidad_total = nueva_cantidad_total,
    ordenes_produccion.fecha_montaje = fecha_montaje_molde,
    ordenes_produccion.fecha_inicio = fecha_inicio_produccion,
    ordenes_produccion.piezas_por_turno = nuevo_piezas_por_turno
    WHERE ordenes_produccion.id_orden_produccion = id_orden_produccion AND ordenes_produccion.id_producto = @id_producto;								
    
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_lotes_planeados(
IN id_orden_produccion INT,
IN nueva_cantidad_total INT,
IN nuevo_piezas_por_turno INT,
IN fecha_inicio_produccion DATE,
IN nuevo_worker FLOAT
)
BEGIN
	DECLARE iterador INT;
    
	IF fecha_inicio_produccion IS NOT NULL THEN      
		IF nuevo_worker >= 1 THEN        
			SET nuevo_piezas_por_turno = nuevo_piezas_por_turno * 2;        
        END IF;        
        SET iterador = 1;                        
     my_loop: LOOP
     
        IF nueva_cantidad_total = 0 THEN
			LEAVE my_loop;
		END IF;
		
			IF nueva_cantidad_total >= nuevo_piezas_por_turno THEN
				SET nueva_cantidad_total = nueva_cantidad_total - nuevo_piezas_por_turno;  
            ELSE
				SET nuevo_piezas_por_turno = nueva_cantidad_total;
                SET nueva_cantidad_total = 0;
            END IF;
			
			INSERT INTO lotes_planeados(id_orden_produccion,cantidad_planeada,fecha_planeada)
			VALUES(id_orden_produccion,nuevo_piezas_por_turno,fecha_inicio_produccion);        		            
			
            dias_semana_loop: LOOP
            
				SET fecha_inicio_produccion = ADDDATE(fecha_inicio_produccion,INTERVAL 1 DAY);
				
				IF WEEKDAY(fecha_inicio_produccion) < 5 THEN
					LEAVE dias_semana_loop;
				END IF;
            
            END LOOP dias_semana_loop;
			
        SET iterador = iterador + 1;        			
        
      END LOOP my_loop;
      
    END IF;    
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE guardar_observacion(
IN observacion VARCHAR(250),
IN orden_produccion INT,
INOUT respuesta VARCHAR(150)
)
BEGIN
    IF EXISTS (SELECT * FROM ordenes_produccion WHERE ordenes_produccion.id_orden_produccion = orden_produccion)
    THEN

        UPDATE ordenes_produccion SET observaciones = observacion WHERE ordenes_produccion.id_orden_produccion = orden_produccion;
        SET respuesta = 'OBSERVACION AGREGADA';
	ELSE SET respuesta = 'NO FUE POSIBLE ENCONTRAR ESTA ORDEN DE TRABAJO';
    END IF;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE modificar_barras_necesarias(
IN id_orden_produccion INT,
IN barras_necesarias	INT,
INOUT respuesta	VARCHAR(150)
)
BEGIN
		
	DECLARE id_material_requerido INT;
    
	IF EXISTS(SELECT * FROM materiales_ordenes_requeridas AS bn 
    WHERE bn.id_orden_produccion = id_orden_produccion) THEN			
		
        SELECT @id_material_requerido := mr.id_material_requerido FROM materiales_requeridos AS mr JOIN
        materiales_ordenes_requeridas AS mor ON mor.id_material_requerido = mr.id_material_requerido WHERE
        mor.id_orden_produccion = id_orden_produccion;
        
        SET SQL_SAFE_UPDATES=0;		      
        UPDATE materiales_ordenes_requeridas AS bn SET bn.barras_necesarias =  barras_necesarias
        WHERE bn.id_orden_produccion = id_orden_produccion;
        
        UPDATE materiales_requeridos AS mr SET mr.cantidad_total = mr.cantidad_total + barras_necesarias 
        WHERE mr.id_material_requerido = @id_material_requerido;
        
        SET SQL_SAFE_UPDATES=1;		      
        SET respuesta = 'SE HA MODIFICADO LA ORDEN DE PRODUCCION.';		
        ELSE SET respuesta = 'OCURRIO UN ERROR AL MODIFICAR LA ORDEN';			
        
        
    END IF;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_parcialidad_requisicion(
IN desc_proveedor 		VARCHAR(150),
IN id_orden_trabajo		INT,
IN solicitante			VARCHAR(50),
IN terminos 			VARCHAR(100),
IN lugar_entrega		VARCHAR(100),
IN comentarios			VARCHAR(255),
IN sub_total			FLOAT,
IN IVA					FLOAT,
IN TOTAL				FLOAT,
INOUT respuesta			BOOLEAN
)BEGIN
	
    DECLARE id_proveedor INT;
	DECLARE id_requisicion INT;
    
    SELECT @id_proveedor := pr.id_proveedor FROM proveedores AS pr WHERE pr.desc_proveedor = desc_proveedor;
	SELECT @id_requisicion := rq.id_requisicion FROM requisiciones AS rq WHERE rq.id_orden_trabajo = id_orden_trabajo;
    
    IF @id_proveedor IS NOT NULL AND @id_requisicion IS NOT NULL THEN
    
		INSERT parcialidades_requisicion(id_proveedor,id_requisicion,solicitante,fecha_solicitud,terminos,lugar_entrega,comentarios,sub_total,IVA,TOTAL) 
        VALUES(@id_proveedor,@id_requisicion,solicitante,NOW(),terminos,lugar_entrega,comentarios,sub_total,IVA,TOTAL);
		SET respuesta = TRUE;
        
	ELSE SET respuesta = FALSE;
        
    END IF;
	
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE agregar_parcialidad_orden_requerida(
IN desc_material_requerido 	VARCHAR(100),
IN cantidad					INT,
IN num_parcialidad			INT,
IN unidad					VARCHAR(10),
IN precio_total				FLOAT,
INOUT respuesta				BOOLEAN
)BEGIN

	DECLARE id_parcialidad_requisicion  INT;
    DECLARE id_material_requerido 		INT;
    
    SELECT @id_parcialidad_requisicion := MAX(pr.id_parcialidad_requisicion) FROM parcialidades_requisicion AS pr;
    
    SELECT @id_material_requerido := mr.id_material_requerido 
    FROM materiales_requeridos AS mr WHERE mr.id_requisicion = 
    (SELECT pr.id_requisicion FROM parcialidades_requisicion AS pr WHERE pr.id_parcialidad_requisicion = @id_parcialidad_requisicion)
    AND mr.id_material = (SELECT id_material FROM materiales WHERE materiales.desc_material = desc_material_requerido);
    
	IF @id_material_requerido IS NOT NULL AND @id_parcialidad_requisicion IS NOT NULL THEN 
		INSERT INTO parcialidades_orden_requerida(
		id_parcialidad_requisicion,id_material_requerido,cantidad,num_parcialidad,fecha_solicitud,
        unidad,precio_total) VALUES
        (@id_parcialidad_requisicion,@id_material_requerido,cantidad,num_parcialidad,NOW(),unidad,precio_total);
        SET respuesta = TRUE;
        
        ELSE SET respuesta = FALSE;
        
    END IF;
    
END //
DELIMITER ;


