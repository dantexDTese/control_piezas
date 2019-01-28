use control_piezas;


################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
														#PEDIDOS
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
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
	DECLARE id_cliente INT;
    DECLARE id_pedido INT;
    DECLARE id_estado INT;
    
    IF NOT EXISTS (SELECT * FROM pedidos WHERE no_orden_compra = desc_orden_compra)
    THEN
		
			SET id_cliente = (SELECT cl.id_cliente FROM clientes AS cl WHERE cl.nombre_cliente = desc_cliente);
            SELECT @id_estado := es.id_estado FROM estados AS es WHERE  es.desc_estado = 'ABIERTO';
            
			INSERT INTO pedidos(fecha_recepcion,no_orden_compra,id_cliente,desc_contacto,id_estado,fecha_entrega) 
            VALUES (now(),desc_orden_compra,id_cliente,desc_contacto,@id_estado,fecha_entrega);
            
            SELECT @id_pedido := pedidos.id_pedido FROM pedidos WHERE pedidos.no_orden_compra = desc_orden_compra;            	
				
            INSERT INTO ordenes_trabajo(id_pedido,id_estado) VALUES(@id_pedido,@id_estado);
			
            SET resultado = 'SE HA AGREGADO CORRECTAMENTE EL PEDIDO';					
			SET r_id_pedido = @id_pedido;
            
    ELSE
		SET resultado = 'NO HA SIDO POSIBLE AGREGAR EL PEDIDO, ES POSIBLE QUE YA EXISTA UN PEDIDO CON ESTA DESCRIPCION';
		SET r_id_pedido = 0;
    END IF;

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_orden_produccion(
IN id_pedido		 		INT,
IN clave_producto			VARCHAR(50),
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
		SELECT @id_producto := productos.id_producto FROM productos WHERE productos.clave_producto = clave_producto;
		
        IF @id_producto IS NOT NULL THEN		
			
            SELECT @id_estado := es.id_estado FROM estados AS es WHERE  es.desc_estado = 'ABIERTO';
            
            INSERT INTO ordenes_produccion(id_orden_trabajo,id_producto,id_estado,cantidad_cliente,fecha_registro)
            VALUES(@id_orden_trabajo,@id_producto,@id_estado,cantidad_cliente,now());        
			
        END IF;
	
	END IF;	
END //
DELIMITER ;
##FIN DE PEDIDOS
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
														#PLANEACION
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
#PARA PLANEACION
DELIMITER //
CREATE PROCEDURE agregar_orden_maquina(
IN id_orden_produccion		INT,
IN desc_producto			VARCHAR(50),
IN nuevo_worker				FLOAT,
IN nueva_cantidad_total		INT,
IN desc_maquina				VARCHAR(50),
IN id_material				INT,
IN fecha_montaje_molde		DATE,
IN fecha_inicio_produccion	DATE,
IN nuevo_piezas_por_turno	INT, 	
IN n_orden					INT,
IN dias_trabajo				INT,
INOUT resultado				VARCHAR(100)
)
BEGIN
	
    DECLARE id_maquina INT;
    DECLARE id_producto INT;
    
	IF EXISTS(SELECT * FROM ordenes_produccion WHERE id_orden_produccion = id_orden_produccion) THEN
	
        SELECT @id_producto := productos.id_producto FROM productos WHERE productos.clave_producto = desc_producto;	
		SET id_maquina = (SELECT mq.id_maquina FROM maquinas AS mq WHERE mq.desc_maquina = desc_maquina);
		CALL actualizar_orden_produccion(nuevo_worker,nueva_cantidad_total,fecha_montaje_molde,fecha_inicio_produccion,nuevo_piezas_por_turno,id_orden_produccion,@id_producto);
			
		CALL agregar_lotes_planeados(id_orden_produccion,nueva_cantidad_total,nuevo_piezas_por_turno,fecha_inicio_produccion,nuevo_worker,id_maquina,dias_trabajo);
						
		CALL agregar_material_requerido(id_orden_produccion,desc_maquina,id_material);
			
		SET resultado = 'TODO SE REALIZO CORRECTAMENTE';					
    
    ELSE
    
		SET resultado = 'NO FUE POSIBLE COMPLETAR LA OPERACION';					
        
    END IF;

END //
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
)BEGIN
	DECLARE n_turnos_necesarios FLOAT;
    SET n_turnos_necesarios = CEILING(nueva_cantidad_total / nuevo_piezas_por_turno);
    
    UPDATE ordenes_produccion SET 
    ordenes_produccion.worker = nuevo_worker,
    ordenes_produccion.cantidad_total = nueva_cantidad_total,
    ordenes_produccion.fecha_montaje = fecha_montaje_molde,
    ordenes_produccion.fecha_inicio = fecha_inicio_produccion,
    ordenes_produccion.piezas_por_turno = nuevo_piezas_por_turno,
    ordenes_produccion.turnos_necesarios = n_turnos_necesarios
    WHERE ordenes_produccion.id_orden_produccion = id_orden_produccion
    AND ordenes_produccion.id_producto = id_producto;								
    
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE agregar_lotes_planeados(
IN id_orden_produccion 			INT,
IN nueva_cantidad_total 		INT,
IN nuevo_piezas_por_turno 		INT,
IN fecha_inicio_produccion 		DATE,
IN nuevo_worker 				FLOAT,
IN id_maquina					INT,
IN dias_trabajo					INT
)
BEGIN
	DECLARE id_estado INT;
    DECLARE id_tipo_proceso INT;
    
    SET id_tipo_proceso = (SELECT tp.id_tipo_proceso  FROM tipos_proceso  AS tp WHERE tp.desc_tipo_proceso = 'MAQUINADO');
    SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = 'ABIERTO');
	
    IF fecha_inicio_produccion IS NOT NULL THEN      
        
     my_loop: LOOP
		
        IF nueva_cantidad_total = 0 OR dias_trabajo = 0 THEN
			LEAVE my_loop;
		END IF;
		
		IF nueva_cantidad_total >= nuevo_piezas_por_turno THEN
			SET nueva_cantidad_total = nueva_cantidad_total - nuevo_piezas_por_turno;  
		ELSE
			SET nuevo_piezas_por_turno = nueva_cantidad_total;
			SET nueva_cantidad_total = 0;
		END IF;
			            
		INSERT INTO lotes_planeados(id_orden_produccion,id_tipo_proceso,cantidad_planeada,fecha_planeada,id_maquina,id_estado)
        VALUES(id_orden_produccion,id_tipo_proceso,nuevo_piezas_por_turno,fecha_inicio_produccion,id_maquina,id_estado);        		            
			
		#parte para obtener los dias de la semana que no sean domingos.
		dias_semana_loop: LOOP
            
			SET fecha_inicio_produccion = ADDDATE(fecha_inicio_produccion,INTERVAL 1 DAY);
				
			IF WEEKDAY(fecha_inicio_produccion) < 6 THEN
				LEAVE dias_semana_loop;
			END IF;
            
		END LOOP dias_semana_loop;
			
        SET dias_trabajo = dias_trabajo - 1;        			
        
      END LOOP my_loop;
      
    END IF;    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_nueva_planeacion(
IN id_orden_produccion 			INT,
IN cantidad_planeada			INT,
IN piezas_turno			 		INT,
IN fecha_inicio 		 		DATE,
IN nuevo_worker 				FLOAT,
IN desc_maquina					VARCHAR(50),
IN desc_tipo_proceso			VARCHAR(50),
IN dias_trabajo					INT,
INOUT respuesta					VARCHAR(255)
)BEGIN
	DECLARE id_estado INT;
    DECLARE id_maquina INT;
    DECLARE id_tipo_proceso INT;
    DECLARE piezas_turno_registro INT;
    
    SET piezas_turno_registro = piezas_turno;
    SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = 'ABIERTO');
    SET id_maquina = (SELECT mq.id_maquina FROM maquinas AS mq WHERE mq.desc_maquina = desc_maquina);
    SET id_tipo_proceso = (SELECT tp.id_tipo_proceso FROM tipos_proceso AS tp WHERE tp.desc_tipo_proceso = desc_tipo_proceso);
    
    IF EXISTS(SELECT * FROM ordenes_produccion AS op WHERE op.id_orden_produccion = id_orden_produccion ) AND 
			id_estado IS NOT NULL AND id_maquina IS NOT NULL AND id_tipo_proceso IS NOT NULL THEN 
				
			IF fecha_inicio IS NOT NULL THEN      
				
                my_loop: LOOP
		
					IF cantidad_planeada = 0 OR dias_trabajo = 0 THEN
						LEAVE my_loop;
					END IF;
					
					IF cantidad_planeada >= piezas_turno THEN
						SET cantidad_planeada = cantidad_planeada - piezas_turno;  
					ELSE
						SET piezas_turno = cantidad_planeada;
						SET cantidad_planeada = 0;
					END IF;
									
		
					INSERT INTO lotes_planeados(id_orden_produccion,id_tipo_proceso,cantidad_planeada,fecha_planeada,id_maquina,id_estado,piezas_turno_lote,worker_lote)
					VALUES(id_orden_produccion,id_tipo_proceso,piezas_turno,fecha_inicio,id_maquina,id_estado,piezas_turno_registro,nuevo_worker);        		            
						
					#parte para obtener los dias de la semana que no sean domingos.
					dias_semana_loop: LOOP
						
						SET fecha_inicio = ADDDATE(fecha_inicio,INTERVAL 1 DAY);
							
						IF WEEKDAY(fecha_inicio) < 6 THEN
							LEAVE dias_semana_loop;
						END IF;
						
					END LOOP dias_semana_loop;
						
					SET dias_trabajo = dias_trabajo - 1;        			
					
				  END LOOP my_loop;
            
            END IF;
            
            SET respuesta = 'SE HA REGISTRADO CORRECTAMENTE';
	ELSE
			SET respuesta = 'NO SE HA PODIDO REGISTRAR CORRECTAMENTE, ALGUNOS DE SUS CAMPOS NO SON VALIDOS';
	END IF;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_material_requerido(
IN id_orden_produccion			INT,
IN desc_maquina					VARCHAR(50),
IN id_material					INT
)BEGIN
    DECLARE id_material_requerido 	INT;
    DECLARE barras_necesarias 		FLOAT;
    DECLARE id_orden_trabajo		INT;
    
    SET barras_necesarias = ( SELECT op.cantidad_total FROM ordenes_produccion AS op WHERE op.id_orden_produccion = id_orden_produccion ) 
    /(SELECT pm.piezas_por_barra FROM ver_productos_maquinas AS pm WHERE pm.desc_maquina = desc_maquina AND pm.id_producto = (SELECT op.id_producto FROM ordenes_produccion AS op WHERE
	op.id_orden_produccion = id_orden_produccion ));
    
    SELECT @id_orden_trabajo := op.id_orden_trabajo FROM ordenes_produccion AS  op WHERE op.id_orden_produccion = id_orden_produccion; 
    
    IF NOT EXISTS(SELECT * FROM  materiales_requeridos AS mr WHERE mr.id_material = id_material AND mr.id_orden_trabajo = @id_orden_trabajo) THEN
    
		INSERT INTO materiales_requeridos(id_material,id_orden_trabajo,cantidad_total) VALUES(id_material,@id_orden_trabajo,barras_necesarias);    
    
    ELSE
    	SET SQL_SAFE_UPDATES=0;		      
			UPDATE materiales_requeridos AS mr SET mr.cantidad_total = mr.cantidad_total + barras_necesarias WHERE mr.id_material = id_material AND mr.id_orden_trabajo = @id_orden_trabajo;
		SET SQL_SAFE_UPDATES=1;		      
	
    END IF;
    
    SELECT @id_material_requerido := mr.id_material_requerido FROM  materiales_requeridos AS mr WHERE mr.id_material = id_material AND mr.id_orden_trabajo = @id_orden_trabajo;

    INSERT INTO materiales_orden(id_orden_produccion,id_material_requerido,barras_necesarias) VALUES(id_orden_produccion,@id_material_requerido,barras_necesarias);    		    
    
END	//
DELIMITER ;
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
														#PRODUCCION
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################


DELIMITER //
CREATE PROCEDURE guardar_observacion(
IN observacion VARCHAR(250),
IN orden_produccion INT,
INOUT respuesta VARCHAR(150)
)BEGIN
    IF EXISTS (SELECT * FROM ordenes_produccion WHERE ordenes_produccion.id_orden_produccion = orden_produccion)
    THEN

        UPDATE ordenes_produccion SET observaciones = observacion WHERE ordenes_produccion.id_orden_produccion = orden_produccion;
        SET respuesta = 'OBSERVACION AGREGADA';
	ELSE SET respuesta = 'NO FUE POSIBLE ENCONTRAR ESTA ORDEN DE PRODUCCION';
    END IF;
    
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE cerrar_orden_produccion(
IN id_orden_produccion 		INT,
INOUT respuesta				VARCHAR(255)
)BEGIN

	DECLARE id_estado_cerrado INT;
    
    SET id_estado_cerrado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO');
    
	IF EXISTS(SELECT * FROM ordenes_produccion AS op WHERE op.id_orden_produccion = id_orden_produccion)THEN
		SET SQL_SAFE_UPDATES=0;		
        UPDATE ordenes_produccion AS op SET op.id_estado = id_estado_cerrado,
        op.fecha_fin = NOW()
        WHERE op.id_orden_produccion = id_orden_produccion;
        SET SQL_SAFE_UPDATES=1;
		
        SET respuesta = 'LA ORDEN HA SIDO CERRADA';
        
	ELSE SET respuesta = 'LA ORDEN NO HA SIDO CERRADA';
    
    END IF;

END //
DELIMITER ;

select * from ordenes_produccion;

DELIMITER //
CREATE PROCEDURE hacer_modificaciones_op(
IN id_orden_produccion			INT,
IN desc_empaque					VARCHAR(255),
IN fecha_desmontaje				VARCHAR(100),
IN validacion_compras			BOOLEAN,
IN validacion_produccion		BOOLEAN,
IN validacion_matenimiento		BOOLEAN,
IN validacion_calidad			BOOLEAN,
INOUT respuesta					VARCHAR(255)
)BEGIN
	UPDATE ordenes_produccion AS op SET 
    op.desc_empaque = desc_empaque,
    op.fecha_desmontaje = fecha_desmontaje,
    op.validacion_compras = validacion_compras,
    op.validacion_produccion = validacion_produccion,
    op.validacion_matenimiento = validacion_matenimiento,
    op.validacion_calidad = validacion_calidad 
    WHERE op.id_orden_produccion = id_orden_produccion;
    
    SET respuesta = 'MODIFICACIONES GUARDADAS';
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE terminar_lote_produccion(
IN id_lote_planeado			INT,
IN desc_lote				VARCHAR(100),
IN tiempo_muerto			VARCHAR(50),
IN desc_turno				VARCHAR(5),
IN cantidad_registrada		INT,
IN cod_operador				VARCHAR(5),
INOUT respuesta				VARCHAR(255)
)BEGIN
	
    DECLARE id_turno			INT;
    DECLARE id_operador 		INT;
    DECLARE id_estado			INT;
    DECLARE num_lote 			INT;
    DECLARE id_orden_trabajo	INT;
    
    
    SET id_turno = (SELECT tr.id_turno FROM turnos AS tr WHERE tr.desc_turno = desc_turno);
    SET id_operador = (SELECT opr.id_operador FROM operadores AS opr WHERE opr.no_operador = cod_operador);
    SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = 'CERRADO');
	SET id_orden_trabajo = (SELECT ot.id_orden_trabajo FROM lotes_planeados AS lp 
							INNER JOIN ordenes_produccion AS op ON lp.id_orden_produccion = op.id_orden_produccion
							INNER JOIN ordenes_trabajo AS ot ON ot.id_orden_trabajo = op.id_orden_trabajo
							INNER JOIN pedidos	AS pd ON ot.id_pedido = pd.id_pedido
							WHERE lp.id_lote_planeado = id_lote_planeado GROUP BY ot.id_orden_trabajo);
                            
	SET num_lote = (SELECT COUNT(*) AS num_lote FROM (
					SELECT op.id_orden_trabajo,op.id_orden_produccion FROM todas_ordenes_produccion AS op WHERE op.id_orden_trabajo = 1) AS op
					INNER JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion
					INNER JOIN lotes_produccion AS lpr ON lp.id_lote_planeado = lpr.id_lote_planeado) + 1;
	
        
    IF EXISTS(SELECT * FROM lotes_planeados AS lp WHERE lp.id_lote_planeado = id_lote_planeado) THEN 
		UPDATE lotes_planeados AS lp SET lp.id_estado = id_estado WHERE lp.id_lote_planeado = id_lote_planeado;
    END IF;
    
    
    
    INSERT INTO lotes_produccion(id_lote_planeado,desc_lote,fecha_trabajo,tiempo_muerto,turno,cantidad_registrada,id_operador,num_lote)	
    VALUES(id_lote_planeado,desc_lote,NOW(),tiempo_muerto,id_turno,cantidad_registrada,id_operador,num_lote);
    
    SET respuesta = 'EL LOTE SE HA GUARDADO EXITOSAMENTE, CUANDO NECESITA COMPLETAR LOS REGISTROS SELECCIONE LA OPCION DE CONTROL DE PRODUCCION';

END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE modificar_lote_produccion(
IN desc_lote					VARCHAR(150),
IN cantidad_operador			INT,
IN cantidad_administrador		INT,
IN scrap_operador				INT,
IN scrap_administrador			INT,
IN merma						FLOAT,
IN rechazo						INT,
IN cantidad_rechazo_liberado	INT,
IN scrap_ajustable				INT,
IN barras_utilizadas			FLOAT,
INOUT respuesta					VARCHAR(255)
)BEGIN
	IF EXISTS(SELECT * FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote) THEN 
		
        UPDATE lotes_produccion AS lp SET lp.cantidad_operador = cantidad_operador, lp.cantidad_administrador = cantidad_administrador, lp.scrap_operador = scrap_operador,
        lp.scrap_administrador = scrap_administrador, lp.merma = merma, lp.rechazo = rechazo, lp.cantidad_rechazo_liberado = cantidad_rechazo_liberado, lp.scrap_ajustable = scrap_ajustable,
        lp.barras_utilizadas = barras_utilizadas WHERE lp.desc_lote = desc_lote;
		
        SET respuesta = 'EL LOTE SE HA MODIFICADO CORRECTAMENTE';
	
    ELSE SET respuesta = 'EL LOTE NO SE HA PODIDO MODIFICAR';
        
    END IF;
END //
DELIMITER ;

SELECT * FROM defectos_lotes;

DELIMITER //
CREATE PROCEDURE agregar_defecto_produccion(
IN desc_lote					VARCHAR(50),
IN desc_defecto_producto		VARCHAR(100),
IN cantidad						INT,
INOUT respuesta 				VARCHAR(100)		
)
BEGIN

	DECLARE id_lote_produccion INT;
    DECLARE id_defecto_producto INT;
    
    SET id_lote_produccion = (SELECT lp.id_lote_produccion FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote);
	
    SET id_defecto_producto = (SELECT dp.id_defecto_producto FROM defectos_producto AS dp 
								WHERE dp.desc_defecto_producto = desc_defecto_producto);
                                
    if id_lote_produccion IS NOT NULL AND id_defecto_producto IS NOT NULL THEN
		
        INSERT INTO defectos_lotes(id_defecto_producto,id_lote_produccion,cantidad) VALUES(id_defecto_producto,
        id_lote_produccion,cantidad);
        
        SET respuesta = 'SE HAN AGREGADO LOS DEFECTOS';
        
	ELSE SET respuesta = 'NO SE HAN PODIDO AGREGAR LOS DEFECTOS';
    
    END IF;
	
 END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE modificar_defecto_produccion(
IN id_defecto_lote 		INT,
IN cantidad				INT,
INOUT respuesta			VARCHAR(100)
)BEGIN
	
	IF EXISTS(SELECT * FROM defectos_lotes AS dl WHERE dl.id_defecto_lote = id_defecto_lote) THEN
		
			UPDATE defectos_lotes AS dl SET dl.cantidad = cantidad WHERE dl.id_defecto_lote = id_defecto_lote;
            
            SET respuesta = 'SE HA MODIFICADO CORRECTAMENTE';
            
	ELSE SET respuesta = 'NO SE HA PODIDO MODIFICAR DE MANERA CORRECTA';
    
    END IF;

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_tiempos_muertos(
IN desc_lote					VARCHAR(100),
IN desc_tipo_tiempo_muerto		VARCHAR(150),
INOUT respuesta					VARCHAR(100)
)BEGIN
	DECLARE id_lote_produccion INT;
    DECLARE id_tipo_tiempo_muerto INT;
    
    SET id_lote_produccion = (SELECT lp.id_lote_produccion FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote);
    SET id_tipo_tiempo_muerto = (SELECT ttm.id_tipo_tiempo_muerto FROM tipos_tiempo_muerto AS ttm WHERE ttm.desc_tiempo_muerto = desc_tipo_tiempo_muerto);
    
    IF id_lote_produccion IS NOT NULL AND id_tipo_tiempo_muerto IS NOT NULL THEN 
    
		INSERT INTO tiempos_muertos_lote(id_lote_produccion,id_tipo_tiempo_muerto) VALUES(id_lote_produccion,id_tipo_tiempo_muerto);
		
        SET respuesta = 'DESCRIPCION AGREGADA';
        
    END IF;
    
END //
DELIMITER ;


SELECT * FROM defectos_lotes;

DELIMITER //
CREATE PROCEDURE eliminar_defecto_lote(
IN desc_lote				VARCHAR(50),
IN desc_defecto_producto	VARCHAR(100),
INOUT respuesta				VARCHAR(200)	
)BEGIN 
	
    DECLARE id_lote INT;
    DECLARE id_defecto INT;
    
    SET id_lote = (SELECT lp.id_lote_produccion FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote);
    SET id_defecto = (SELECT dp.id_defecto_producto FROM defectos_producto AS dp WHERE dp.desc_defecto_producto = desc_defecto_producto);

	DELETE FROM defectos_lotes WHERE id_lote_produccion = id_lote AND id_defecto_producto = id_defecto;
    
    SET respuesta = 'EL REGISTRO SE HA BORRADO DE FORMA CORRECTA';

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE eliminar_tiempo_muerto_lote(
IN desc_lote 				VARCHAR(50),
IN desc_tiempo_muerto		VARCHAR(150),
INOUT respuesta				VARCHAR(200)
)BEGIN 
	
    DECLARE id_lote 	INT;
    DECLARE idt_muerto  INT;
	
    SET id_lote = (SELECT lp.id_lote_produccion FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote);
    SET idt_muerto = (SELECT tm.id_tipo_tiempo_muerto FROM tipos_tiempo_muerto AS tm WHERE tm.desc_tiempo_muerto = desc_tiempo_muerto);
    
    DELETE FROM tiempos_muertos_lote WHERE id_lote_produccion = id_lote AND id_tipo_tiempo_muerto = idt_muerto;
    
    SET respuesta = 'EL REGISTRO SE HA BORRADO DE FORMA CORRECTA';

END //
DELIMITER ;


################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
														#REQUISICIONES
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################

DELIMITER //
CREATE PROCEDURE agregar_requisicion(
IN solicitante	    	VARCHAR(50),
IN uso_material			VARCHAR(255),
INOUT id_requisicion	INT
)BEGIN	
	INSERT INTO requisiciones(fecha_creacion,solicitante,uso_material) VALUES(NOW(),solicitante,uso_material);    
	SET id_requisicion = (SELECT MAX(rq.id_requisicion) FROM requisiciones AS rq);    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_materiales_solicitados(
IN	id_requisicion				INT,
IN	no_partida					INT,
IN	id_material					INT,
IN	cantidad_barras				INT,
IN	unidad						VARCHAR(10),
IN	parcialidad					INT,
IN 	fecha_solicitada			DATE,
IN	cuenta_cargo				VARCHAR(20),
INOUT id_material_solicitado	INT
)BEGIN 

	DECLARE id_estado	INT;    
    
	IF EXISTS(SELECT * FROM requisiciones AS rq WHERE rq.id_requisicion = id_requisicion) THEN		
		
		
        SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE  es.desc_estado = 'ABIERTO');
        
        INSERT INTO materiales_solicitados(id_requisicion,no_partida,id_material,cantidad,unidad,parcialidad,fecha_solicitud,cuenta_cargo,id_estado)
		VALUES(id_requisicion,no_partida,id_material,cantidad_barras,unidad,parcialidad,fecha_solicitada,cuenta_cargo,id_estado);		
        
        SET id_material_solicitado = (SELECT MAX(ms.id_material_solicitado) FROM materiales_solicitados AS ms);        
	ELSE 
		SET id_material_solicitado = 0;            
    END IF;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE agregar_materiales_orden_requisicion(
IN id_material_solicitado		INT,
IN id_orden_produccion			INT,
IN cantidad						INT
)BEGIN
	DECLARE id_material_orden INT;    
    SET id_material_orden = (SELECT mr.id_material_orden FROM materiales_orden AS mr WHERE mr.id_orden_produccion = id_orden_produccion);    
    
    IF id_material_orden IS NOT NULL THEN
		INSERT INTO materiales_orden_requisicion(id_material_solicitado,id_material_orden,cantidad) VALUES(id_material_solicitado,id_material_orden,cantidad);	
    ELSE 
		SELECT 'MATERIAL NULO';
    END IF;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_comentario_requisicion(
IN id_requisicion 			INT,
IN comentario				VARCHAR(255),
INOUT respuesta				VARCHAR(150)
)BEGIN 
	IF EXISTS(SELECT * FROM requisiciones AS rq WHERE rq.id_requisicion = id_requisicion ) THEN 
		
        UPDATE requisiciones AS rq SET rq.comentarios = comentario WHERE rq.id_requisicion = id_requisicion;
		SET respuesta = 'COMENTARIO MODIFICADO';
        
	ELSE
		
        SET respuesta = 'NO SE HA PODIDO MODIFICCAR EL COMENTARIO';
        
    END IF;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE asignar_material_requisicion(
IN id_orden_produccion 			INT,
IN desc_lote					VARCHAR(200),
IN cantidad						INT,
INOUT respuesta					VARCHAR(150)
)BEGIN 
	
	DECLARE id_entrada_material	INT;
    DECLARE barras_necesarias FLOAT;
    DECLARE suma_materiales_entregados INT;
    DECLARE suma_materiales_entregados_orden INT;
    
    SET id_entrada_material = (SELECT em.id_entrada_material FROM entradas_materiales AS em WHERE em.desc_lote = desc_lote);
     
	SET barras_necesarias = (SELECT mo.barras_necesarias FROM materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion);
      
	SET barras_necesarias = (SELECT CEILING(barras_necesarias));
    
    SET suma_materiales_entregados = (SELECT SUM(me.cantidad) FROM materiales_entregados AS me WHERE me.id_entrada_material = id_entrada_material);
    
    SET suma_materiales_entregados_orden = (SELECT SUM(me.cantidad) FROM materiales_entregados AS me WHERE me.id_material_orden = 
    (SELECT mo.id_material_orden FROM materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion));
    
    IF suma_materiales_entregados IS NULL THEN SET suma_materiales_entregados = 0; END IF;
	
    IF suma_materiales_entregados_orden IS NULL THEN SET suma_materiales_entregados_orden = 0; END IF;

    IF id_entrada_material IS NOT NULL THEN
		
        IF cantidad + suma_materiales_entregados <= (SELECT vem.cantidad FROM ver_entradas_materiales AS vem WHERE vem.id_entrada_material = id_entrada_material) 
			AND cantidad+suma_materiales_entregados_orden <= barras_necesarias THEN 
        
			INSERT INTO materiales_entregados(id_entrada_material,id_material_orden,cantidad,fecha) VALUES(id_entrada_material,
            (SELECT mo.id_material_orden FROM materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion),cantidad,NOW());
        
			SET respuesta = 'EL MATERIAL HA SIDO ASIGNADO CORRECTAMENTE';
		
        ELSE SET respuesta = 'EL MATERIAL NO HA SIDO ASIGNADO DE FORMA CORRECTA, POSIBLEMENTE LA CANTIDAD QUE ASIGNO NO ES CORRECTA';
        
        END IF;
        
        ELSE SET respuesta = 'ID NULO';
        
    END IF;
    
		

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE cerrar_parte_requisicion(
IN id_requisicion 		INT,
IN desc_material		VARCHAR(150),
INOUT respuesta			VARCHAR(255)
)BEGIN 
	
    DECLARE id_material_solicitado INT;
    DECLARE id_estado_cerrado 	INT;
    
    SET id_material_solicitado = (SELECT ms.id_material FROM materiales_solicitados AS ms WHERE ms.id_requisicion = id_requisicion AND
    ms.id_material = (SELECT vm.id_material FROM ver_materiales AS vm WHERE CONCAT(desc_tipo_material,' ',desc_dimencion,' ',clave_forma) = desc_material) GROUP BY ms.id_material);
	
    SET id_estado_cerrado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = 'CERRADO');
    
    IF id_material_solicitado IS NOT NULL THEN 
		
        SET SQL_SAFE_UPDATES = 0;
        
        UPDATE materiales_solicitados AS ms SET ms.id_estado = id_estado_cerrado WHERE ms.id_requisicion = id_requisicion AND ms.id_material = id_material_solicitado;
        
        SET SQL_SAFE_UPDATES = 1;
		
        SET respuesta = 'OPERACION EXITOSA';
    
    ELSE SET respuesta = 'NO SE PUDO CUMPLIR LA OPERACION';
    
    END IF;
    
END //
DELIMITER ;
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
													#ENTRADA MATERIAL
################################################################################################################################################
################################################################################################################################################
################################################################################################################################################
DELIMITER //
CREATE PROCEDURE agregar_entrada_material(
IN desc_proveedor		VARCHAR(150),
IN desc_material  		VARCHAR(100),
IN cantidad				INT,
IN codigo				VARCHAR(50),
IN certificado			VARCHAR(50),
IN orden_compra			VARCHAR(50),
IN inspector			VARCHAR(50),
INOUT respuesta			VARCHAR(250) 
)BEGIN
	DECLARE id_material INT;
    DECLARE id_proveedor INT;
	DECLARE id_estado INT;
        
    SET id_proveedor = (SELECT pr.id_proveedor FROM proveedores AS pr WHERE pr.desc_proveedor = desc_proveedor);
    SET id_material = (SELECT mt.id_material FROM ver_materiales AS mt WHERE CONCAT(mt.desc_tipo_material,' ',mt.desc_dimencion,' ',mt.clave_forma) = desc_material);
	SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = 'ABIERTO');
    
	IF NOT EXISTS(SELECT * FROM entradas_materiales AS em WHERE em.codigo = codigo) AND id_material IS NOT NULL AND id_proveedor IS NOT NULL THEN
    
		INSERT INTO entradas_materiales(fecha_registro,id_material,id_proveedor,cantidad,codigo,certificado,orden_compra,inspector,id_estado) 
        VALUES(NOW(),id_material,id_proveedor,cantidad,codigo,certificado,orden_compra,inspector,id_estado);
        
        
        SET respuesta = 'SE HA GUARDADO CORRECTAMENTE';
    
    ELSE 
		
        SET respuesta = 'NO SE HA PODIDO GUARDAR CORRECTAMENTE';
    
    END IF;

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE actualizar_entrada_material(
IN id_entrada_material		INT,
IN noParte					VARCHAR(150),
IN factura					VARCHAR(150),
IN comentarios				VARCHAR(300),
IN desc_estado				VARCHAR(100),
IN desc_lote				VARCHAR(200),
INOUT respuesta 			VARCHAR(255)
)BEGIN
	DECLARE id_estado INT;	
    DECLARE cantidad_almacen INT;
    IF EXISTS(SELECT * FROM entradas_materiales AS em WHERE em.id_entrada_material = id_entrada_material) THEN
	
		SET id_estado = (SELECT es.id_estado FROM estados AS es WHERE es.desc_estado = desc_estado);
			
        SET SQL_SAFE_UPDATES = 0;
			
			UPDATE entradas_materiales AS em SET em.factura = factura, em.no_parte=noParte,em.comentarios = comentarios, em.id_estado = id_estado,
            em.desc_lote = desc_lote WHERE em.id_entrada_material = id_entrada_material;
            
        SET SQL_SAFE_UPDATES = 1;
        
        IF desc_estado = 'APROBADA' THEN
        
			SET cantidad_almacen = (SELECT em.cantidad FROM entradas_materiales AS em WHERE em.id_entrada_material = id_entrada_material);
            INSERT INTO almacen_materias_primas(id_entrada_material,cantidad_total)  VALUES(id_entrada_material,cantidad_almacen);
            
        END IF;
        
        SET respuesta = 'SE HA REGISTRADO CORRECTAMENTE';
        
    ELSE      
		SET respuesta = 'NO SE HA PODIDO REGISTRAR CORRECTAMENTE';
        
    END IF;
	
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_inspeccion_propiedades(
IN id_entrada_material	 			INT,
IN desc_tipo_inspeccion				VARCHAR(100),
IN desc_resultado					VARCHAR(5)
)BEGIN
	DECLARE id_tipo_inspeccion INT;
    DECLARE id_resultado_inspeccion INT;
    
    SET id_tipo_inspeccion = (SELECT ti.id_tipo_inspeccion FROM tipos_inspeccion AS ti WHERE ti.desc_tipo_inspeccion = desc_tipo_inspeccion);
    SET id_resultado_inspeccion = (SELECT ri.id_resultado_inspeccion FROM resultados_inspeccion AS ri WHERE ri.desc_resultado_inspeccion = desc_resultado);
	
    IF id_tipo_inspeccion IS NOT NULL AND id_resultado_inspeccion IS NOT NULL THEN
		
        INSERT INTO inspeccion_entradas(id_entrada_material,id_tipo_inspeccion,id_resultado_inspeccion)
        VALUES(id_entrada_material,id_tipo_inspeccion,id_resultado_inspeccion);
    
    END IF;


END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_inspeccion_dimenciones(
IN id_entrada_material 			INT,
IN desc_tipo_inspeccion				VARCHAR(100),
IN resultado_inspeccion				FLOAT
)BEGIN
	DECLARE id_tipo_inspeccion INT;
	
    SET id_tipo_inspeccion = (SELECT ti.id_tipo_inspeccion FROM tipos_inspeccion AS ti WHERE ti.desc_tipo_inspeccion = desc_tipo_inspeccion);
    
    IF id_tipo_inspeccion IS NOT NULL AND resultado_inspeccion > 0 THEN
		
        INSERT INTO inspeccion_dimenciones(id_tipo_inspeccion,id_entrada_material,resultado_inspeccion)
        VALUES(id_tipo_inspeccion,id_entrada_material,resultado_inspeccion);
        
    END IF;


END //
DELIMITER ;

###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
															#ALAMCEN PRODUCTO TERMINADO
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################

DELIMITER //
CREATE PROCEDURE registrar_entrada_almacen(
IN cantidad_registrar					INT,
IN id_almacen_producto_terminado		INT,
INOUT respuesta 						VARCHAR(255)
)BEGIN
	DECLARE id_tipo_operacion_almacen INT;
    DECLARE total_almacenado	      INT;
    IF EXISTS (SELECT * FROM almacen_productos_terminados AS apt WHERE apt.id_almacen_producto_terminado = id_almacen_producto_terminado ) THEN
		
        SET id_tipo_operacion_almacen = (SELECT toa.id_tipo_operacion_almacen FROM tipos_operaciones_almacenes AS toa WHERE toa.desc_tipo_operacion = 'ENTRADA');
        SET total_almacenado = (SELECT apt.total FROM almacen_productos_terminados AS apt WHERE apt.id_almacen_producto_terminado = id_almacen_producto_terminado) + cantidad_registrar;
        
        INSERT INTO registros_entradas_salidas(id_almacen_producto_terminado,id_tipo_operacion_almacen,fecha_registro,cantidad,total_registrado)
        VALUES(id_almacen_producto_terminado,id_tipo_operacion_almacen,NOW(),cantidad_registrar,total_almacenado);
		
        SET SQL_SAFE_UPDATES = 0;
		
        UPDATE almacen_productos_terminados AS apt SET apt.total = total_almacenado WHERE apt.id_almacen_producto_terminado = id_almacen_producto_terminado;
        
        SET SQL_SAFE_UPDATES = 1;
        
        SET respuesta = 'EL PRODUCTO SE HA REGISTRADO CORRECTAMENTE';
        
    ELSE SET respuesta = 'OCURRIO UN ERROR AL REGISTRAR ESTE PRODUCTO';
    END IF;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_inventario(
IN responsable 		VARCHAR(255),
INOUT respuesta				INT
)BEGIN

	INSERT INTO inventarios(fecha_inventario,persona_responsable) VALUES(NOW(),responsable);
	SET respuesta = (SELECT MAX(inv.id_inventario) FROM inventarios AS inv);
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_producto_inventario(
IN id_inventario			INT,
IN clave_producto			VARCHAR(50),
IN cantidad					INT
)BEGIN
	DECLARE id_producto INT;
    SET id_producto = (SELECT pr.id_producto FROM productos AS pr WHERE pr.clave_producto = clave_producto);
    
    IF id_producto > 0 AND id_inventario > 0 THEN
		
        INSERT INTO productos_inventario(id_inventario,id_producto,cantidad) VALUES(id_inventario,id_producto,cantidad);
        
    END IF;
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_parcialidad(
IN id_pedido		INT,
IN fecha_entrega	DATE,
INOUT respuesta		INT
)BEGIN
	INSERT INTO parcialidades_pedido(fecha_parcialidad,id_pedido,fecha_entrega) VALUES(NOW(),id_pedido,fecha_entrega);
    
    SET respuesta = (SELECT MAX(pp.id_parcialidad_pedido) FROM parcialidades_pedido AS pp);

END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE registrar_salida_producto(
IN clave_producto		VARCHAR(50),
IN cantidad_salida		INT,
INOUT respuesta			INT
)BEGIN
	DECLARE id_almacen_producto_terminado INT;
    DECLARE id_tipo_operacion_almacen INT;
    DECLARE total_almacenado	      INT;
    
    SET id_almacen_producto_terminado = (SELECT pa.id_almacen_producto_terminado FROM productos_almacen AS pa WHERE pa.clave_producto = clave_producto);
    
    IF id_almacen_producto_terminado IS NOT NULL THEN
    
		SET id_tipo_operacion_almacen = (SELECT toa.id_tipo_operacion_almacen FROM tipos_operaciones_almacenes AS toa WHERE toa.desc_tipo_operacion = 'SALIDA');
		SET total_almacenado = (SELECT apt.total FROM almacen_productos_terminados AS apt WHERE apt.id_almacen_producto_terminado = id_almacen_producto_terminado) - cantidad_salida;
			
		INSERT INTO registros_entradas_salidas(id_almacen_producto_terminado,id_tipo_operacion_almacen,fecha_registro,cantidad,total_registrado)
		VALUES(id_almacen_producto_terminado,id_tipo_operacion_almacen,NOW(),cantidad_salida,total_almacenado);
			
			SET SQL_SAFE_UPDATES = 0;
			
			UPDATE almacen_productos_terminados AS apt SET apt.total = total_almacenado WHERE apt.id_almacen_producto_terminado = id_almacen_producto_terminado;
			
			SET SQL_SAFE_UPDATES = 1;
			
		SET respuesta = (SELECT MAX(res.id_registro_entrada_salida) FROM registros_entradas_salidas AS res);
	
    ELSE SET respuesta = 0;
	
    END IF;
        
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_parcialidad_entrega(
IN id_orden_produccion						INT,
IN id_registro_entrada_salida				INT,
IN id_parcialidad_pedido					INT
)
BEGIN
	INSERT INTO parcialidades_entrega(id_orden_produccion,id_registro_entrada_salida,id_parcialidad_pedido) VALUES(id_orden_produccion,id_registro_entrada_salida,id_parcialidad_pedido);
END //
DELIMITER ;


###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
															#ALAMCEN MATERIA PRIMA
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
DELIMITER //
CREATE PROCEDURE registrar_salida_material(
IN id_almacen_materia_prima			INT,
IN cantidad							INT,
INOUT respuesta						VARCHAR(255)
)BEGIN
	
    IF EXISTS(SELECT * FROM almacen_materias_primas AS amp WHERE amp.id_almacen_materia_prima = id_almacen_materia_prima) THEN
		
        INSERT INTO salidas_materiales(id_almacen_materia_prima,cantidad_salida,fecha_salida) VALUES(id_almacen_materia_prima,cantidad,NOW());

		SET SQL_SAFE_UPDATES = 0;
		UPDATE almacen_materias_primas AS amp SET amp.cantidad_total = amp.cantidad_total - cantidad WHERE amp.id_almacen_materia_prima = id_almacen_materia_prima; 
		SET SQL_SAFE_UPDATES = 1;
        SET respuesta = 'LA OPERACION SE HA REALIZADO CORRECTAMENTE';
        
	ELSE
		SET respuesta = 'NO SE HA PODIDO REGISTRAR LA SALIDA CORRECTAMENTE';
    END IF;
    
END //
DELIMITER ;

###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
															#ETIQUETAS
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
DELIMITER //
CREATE PROCEDURE guardar_etiqueta(
IN codigo_etiqueta			VARCHAR(100),
IN folio					VARCHAR(50),
IN piezas_por_bolsa			INT,
IN piezas_totales			INT,
INOUT respuesta				INT
)BEGIN
	
    
	INSERT INTO etiquetas(codigo_etiqueta,fecha,folio,piezas_por_bolsa,piezas_totales) VALUES(codigo_etiqueta,NOW(),folio,piezas_por_bolsa,piezas_totales);
    
    SET respuesta = (SELECT MAX(et.id_etiqueta) FROM etiquetas AS et);
    
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_etiqueta_lote_produccion(
IN desc_lote				VARCHAR(50),
IN id_etiqueta				INT
)BEGIN 
	IF EXISTS(SELECT * FROM etiquetas AS et WHERE et.id_etiqueta = id_etiqueta) AND 
    EXISTS(SELECT * FROM lotes_produccion AS lp WHERE lp.desc_lote = desc_lote) THEN 
    
        SET SQL_SAFE_UPDATES = 0;
        UPDATE lotes_produccion AS lp SET lp.id_etiqueta = id_etiqueta WHERE lp.desc_lote = desc_lote;
		SET SQL_SAFE_UPDATES = 1;
        
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_etiqueta_lote(
IN id_etiqueta			INT,
IN num_etiqueta			INT,
IN cantidad				INT
)BEGIN
	IF EXISTS(SELECT * FROM etiquetas AS et WHERE et.id_etiqueta = id_etiqueta) THEN
		INSERT INTO etiquetas_lotes(id_etiqueta,num_etiqueta,cantidad) VALUES(id_etiqueta,num_etiqueta,cantidad);
    END IF;
END //
DELIMITER ;
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
															#CATALOGOS
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################
###################################################################################################################################################


DELIMITER //
CREATE PROCEDURE agregar_modificar_cliente(
IN nombre_cliente				VARCHAR(100),
IN desc_cliente					VARCHAR(255),
INOUT respuesta					VARCHAR(155)
)BEGIN 
	
    IF NOT EXISTS (SELECT * FROM clientes AS cl WHERE cl.nombre_cliente = nombre_cliente) THEN
		
        INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES(nombre_cliente,desc_cliente);
        SET respuesta = 'EL CLIENTE SE HA GUARDADO CORRECTAMENTE';
	
    ELSE 
		UPDATE clientes AS cl SET cl.desc_cliente = desc_cliente WHERE cl.nombre_cliente = nombre_cliente;
		SET respuesta = 'EL CLIENTE SE HA MODIFICADO CORRECTAMENTE';
    END IF;
    
END // 
DELIMITER ;

DELIMITER //
CREATE PROCEDURE agregar_modificar_proveedor(
IN desc_proveedor				VARCHAR(100),
IN direccion					VARCHAR(255),
INOUT respuesta					VARCHAR(155)
)BEGIN 
	
    IF NOT EXISTS (SELECT * FROM proveedores AS prv WHERE prv.desc_proveedor = desc_proveedor) THEN
		
        INSERT INTO proveedores(desc_proveedor,direccion) VALUES(desc_proveedor,direccion);
        SET respuesta = 'EL PROVEEDOR SE HA GUARDADO CORRECTAMENTE';
	
    ELSE 
		UPDATE proveedores AS prv SET prv.direccion = direccion WHERE prv.desc_proveedor = desc_proveedor;
		SET respuesta = 'EL PROVEEDOR SE HA MODIFICADO CORRECTAMENTE';
    END IF;
    
END // 
DELIMITER ;


DELIMITER //
CREATE PROCEDURE agregar_maquina(
IN desc_maquina					VARCHAR(100),
INOUT respuesta					VARCHAR(155)
)BEGIN 
	
    IF NOT EXISTS (SELECT * FROM maquinas AS mq WHERE mq.desc_maquina = desc_maquina) THEN
		
        INSERT INTO maquinas(desc_maquina) VALUES(desc_maquina);
        SET respuesta = 'LA MAQUINA SE HA GUARDADO CORRECTAMENTE';
	
    ELSE 
		SET respuesta = 'ESTA MAQUINA YA EXISTE, POR FAVOR INTENTE DE NUEVO';
    END IF;
    
END // 
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_modificar_operador(
IN cod_operador						VARCHAR(50),
IN nombre_operador					VARCHAR(255),
INOUT respuesta						VARCHAR(155)
)BEGIN 
	
    IF NOT EXISTS (SELECT * FROM operadores AS oper WHERE oper.no_operador = cod_operador) THEN
		
        INSERT INTO operadores(no_operador,nombre_operador) VALUES(cod_operador,nombre_operador);
        SET respuesta = 'EL OPERADOR SE HA GUARDADO CORRECTAMENTE';
	
    ELSE 
		UPDATE operadores AS opr SET opr.nombre_operador = nombre_operador WHERE opr.no_operador = cod_operador;
		SET respuesta = 'EL OPERADOR SE HA MODIFICADO CORRECTAMENTE';
    END IF;
    
END // 
DELIMITER ;