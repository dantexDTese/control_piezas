USE control_piezas;

DELIMITER //
CREATE PROCEDURE agregar_orden_trabajo(
	IN desc_orden_trabajo VARCHAR(50),
    IN nombre_cliente VARCHAR(50),
    INOUT resultado VARCHAR(100))
BEGIN
	DECLARE id_cliente_b INT;
	IF NOT EXISTS (SELECT * FROM ordenes_trabajo JOIN clientes ON clientes.id_cliente = ordenes_trabajo.id_cliente 
				WHERE ordenes_trabajo.desc_orden_trabajo = desc_orden_trabajo AND clientes.nombre_cliente = nombre_cliente) 
	THEN   
		SELECT @id_cliente_b := clientes.id_cliente FROM clientes WHERE clientes.nombre_cliente = nombre_cliente;
        INSERT INTO ordenes_trabajo(id_cliente,desc_orden_trabajo,fecha_peticion) VALUES(@id_cliente_b,desc_orden_trabajo,now());
		SET resultado = 'ORDEN AGREGADA CORRECTAMENTE';	
    ELSE
		SET resultado = 'ESTA ORDEN NO PUEDO SER AGREGADO POR QUE YA EXISTE';	
	END IF;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_cliente(IN nombre_cliente VARCHAR(20),INOUT resultado VARCHAR(100))
BEGIN
	IF NOT EXISTS (SELECT * FROM clientes WHERE clientes.nombre_cliente = nombre_cliente)
    THEN
		INSERT INTO clientes(nombre_cliente) VALUES(nombre_cliente);
        SET resultado = 'CLIENTE AGREGADO CORRECTAMENTE';
    ELSE
		SET resultado = 'ESTE CLIENTE NO PUEDE SER AGREGADO POR QUE YA EXISTE';
    END IF;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE agregar_ordenes_produccion(IN desc_orden_trabajo VARCHAR(30), IN clave_producto VARCHAR(50),
IN cantidad_total INT,IN desc_maquina VARCHAR(50),IN desc_material,IN cantidad_cliente INT,IN turnos_necesarios INT,INOUT respuesta VARCHAR(50))
BEGIN
	
    DECLARE id_orden_trabajo INT;
    DECLARE id_producto INT;
    DECLARE id_orden_produccion INT;
    
    SELECT @id_orden_trabajo := ordenes_trabajo.id_orden_trabajo
				FROM ordenes_trabajo WHERE ordenes_trabajo.desc_orden_trabajo = desc_orden_trabajo;
                    
    SELECT @id_producto := ordenes_produccion.id_producto FROM productos WHERE productos.clave_producto = clave_producto;
    
    INSERT INTO ordenes_produccion(id_orden_trabajo,id_producto,cantidad_total,cantidad_cliente,turnos_necesarios,fecha_registro)
	VALUES(@id_orden_trabajo,@id_producto,cantidad_total,cantidad_cliente,turnos_necesarios,now());       
		
    SELECT @id_orden_produccion:= id_orden_produccion FROM ordenes_produccion ORDER BY id_orden_produccion DESC LIMIT 1;
    
        
END //
DELIMITER ;

describe procesos_produccion;
