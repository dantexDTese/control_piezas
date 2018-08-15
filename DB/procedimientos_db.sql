USE control_piezas;

DELIMITER //
CREATE PROCEDURE agregar_orden_compra(
	IN desc_orden_compra VARCHAR(50),
    IN nombre_cliente VARCHAR(50),
    INOUT resultado VARCHAR(100))
BEGIN
	DECLARE id_cliente_b INT;

	IF NOT EXISTS (SELECT * FROM ordenes_compra JOIN clientes ON clientes.id_cliente = ordenes_compra.id_cliente 
				WHERE ordenes_compra.desc_orden_compra = desc_orden_compra AND clientes.nombre_cliente = nombre_cliente) 
	THEN
    
		SELECT @id_cliente_b := clientes.id_cliente FROM clientes WHERE clientes.nombre_cliente = nombre_cliente;
        
        INSERT INTO ordenes_compra(id_cliente,desc_orden_compra,fecha_peticion) VALUES(@id_cliente_b,desc_orden_compra,now());
        
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


call agregar_cliente('STOCK',@resultado);

select @resultado;

select * from clientes;