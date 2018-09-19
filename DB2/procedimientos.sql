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
    IF NOT EXISTS (SELECT * FROM pedidos WHERE no_orden_compra = desc_orden_compra)
    THEN
		SELECT @id_contacto := contactos.id_contacto FROM contactos JOIN clientes ON contactos.id_cliente = clientes.id_cliente
        WHERE clientes.nombre_cliente = desc_cliente AND contactos.desc_contacto = desc_contacto;
        
        IF @id_contacto IS NOT NULL THEN
			INSERT INTO pedidos(fecha_recepcion,no_orden_compra,id_contacto,id_estado,fecha_entrega) 
            VALUES (now(),desc_orden_compra,@id_contacto,1,fecha_entrega);
            
            SELECT @id_pedido := pedidos.id_pedido FROM pedidos WHERE pedidos.no_orden_compra = desc_orden_compra;

			
				
            INSERT INTO ordenes_trabajo(id_pedido,id_estado) VALUES(@id_pedido,1);
			
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
			
            INSERT INTO ordenes_produccion(id_orden_trabajo,id_producto,id_estado,cantidad_cliente,fecha_registro)
            VALUES(@id_orden_trabajo,@id_producto,1,cantidad_cliente,now());        
			
			SELECT @id_orden_p := max(id_orden_produccion) FROM ordenes_produccion;
            
            SELECT @id_estado := estados.id_estado FROM estados WHERE estados.desc_estados = 'PLANEACION';
            
            SELECT @id_tipo_proceso := tipos_proceso.id_tipo_proceso FROM tipos_proceso WHERE desc_tipo_proceso = 'MAQUINADO';
            
			INSERT INTO procesos_produccion(id_orden_produccion,id_tipo_proceso,id_estado) 
            VALUES(@id_orden_p,@id_tipo_proceso,@id_estado);
            
        END IF;
	
	END IF;	
END //
DELIMITER ;

CALL agregar_orden_maquina(1,123,123,'maquina1','MATERIAL 1');

DELIMITER //
CREATE PROCEDURE agregar_orden_maquina(
id_orden_produccion		INT,
nuevo_worker			FLOAT,
nueva_cantidad_total	INT,
desc_maquina			VARCHAR(100),
desc_material			VARCHAR(100)
)
BEGIN
	DECLARE id_material INT;
    DECLARE id_maquina INT;
    DECLARE id_proceso_produccion INT;
    DECLARE id_nuevo_estado INT;
    

	IF EXISTS(SELECT * FROM ordenes_produccion WHERE id_orden_produccion = id_orden_produccion)
    THEN
		
        SELECT @id_material := materiales.id_material FROM materiales WHERE materiales.desc_material = desc_material;
        SELECT @id_maquina := maquinas.id_maquina FROM maquinas WHERE maquinas.desc_maquina = desc_maquina;
		SELECT @id_nuevo_estado := estados.id_estado FROM estados WHERE estados.desc_estados = 'PRODUCCION';
        
        UPDATE ordenes_produccion 
        SET 
        ordenes_produccion.id_material = @id_material,
        ordenes_produccion.worker = nuevo_worker,
        ordenes_produccion.cantidad_total = nueva_cantidad_total
        WHERE ordenes_produccion.id_orden_produccion = id_orden_produccion;
			
		SET SQL_SAFE_UPDATES=0;		
		UPDATE procesos_produccion 
        SET 
        procesos_produccion.id_estado = @id_nuevo_estado 
        WHERE procesos_produccion.id_orden_produccion = id_orden_produccion;
		SET SQL_SAFE_UPDATES=1;
        
        SELECT @id_proceso_produccion := procesos_produccion.id_proceso_produccion FROM procesos_produccion WHERE id_orden_produccion = id_orden_produccion;    
		
        INSERT INTO lotes_produccion(id_proceso_produccion,id_maquina) VALUES(@id_proceso_produccion,@id_maquina);
        
    END IF;


END //
DELIMITER ;            

describe lotes_produccion;
select * from maquinas;
select * from materiales;
select * from ordenes_produccion;
select * from procesos_produccion;
select * from lotes_produccion;
SELECT * FROM ESTADOS;

