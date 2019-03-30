USE control_piezas;


DELIMITER //
CREATE FUNCTION obtener_suma_materiales(id_orden_produccion INT) RETURNS INT
BEGIN
	DECLARE suma INT;
		SET suma = (SELECT SUM(cantidad) FROM materiales_orden_requisicion AS mor JOIN materiales_orden AS mo ON mor.id_material_orden = mo.id_material_orden
				WHERE mo.id_orden_produccion = id_orden_produccion);
		IF suma IS NULL THEN
			RETURN 0;
		ELSE
			RETURN suma;
		END IF;
END //
DELIMITER ;



DELIMITER //
CREATE FUNCTION obtenerIdRequisicion(id_orden_produccion INT) RETURNS INT
BEGIN
	DECLARE id_res INT;	
	SET id_res = (SELECT id_requisicion from requisiciones WHERE id_requisicion = (SELECT id_requisicion FROM materiales_solicitados WHERE id_material_solicitado = 
	(SELECT id_material_solicitado FROM materiales_orden_requisicion WHERE id_material_orden = (SELECT mo.id_material_orden FROM 
	materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion))));
    return id_res;
END //
DELIMITER ;





DELIMITER // 
CREATE FUNCTION cantidad_restante_parcialidad(id_orden_produccion INT) RETURNS INT
BEGIN
	DECLARE suma INT;
    SET suma = (SELECT SUM(cantidad) FROM registros_entradas_salidas AS res JOIN
	parcialidades_entrega AS pe ON res.id_registro_entrada_salida = pe.id_registro_entrada_salida WHERE pe.id_orden_produccion = id_orden_produccion);
	
    IF suma IS NOT NULL THEN
    
		return suma;        
        
	ELSE return 0;
		
    END IF;
    
END //
DELIMITER ;




DELIMITER //
CREATE FUNCTION obtener_piezas_procesar(id_orden_produccion INT, desc_proceso VARCHAR(50)) RETURNS INT
BEGIN
	DECLARE suma INT;
	DECLARE sumaLotes INT;	
		
        SET sumaLotes = (SELECT SUM(lp.cantidad_planeada) FROM todos_lotes_planeados AS lp WHERE lp.id_orden_produccion = id_orden_produccion AND lp.desc_tipo_proceso = desc_proceso);
        IF sumaLotes IS NULL THEN
			SET sumaLotes = 0;
        END IF;
    
        SET suma = (SELECT cantidad_total FROM ordenes_produccion AS op WHERE op.id_orden_produccion = id_orden_produccion) - sumaLotes;
        
        
        IF suma < 0 OR suma IS NULL THEN
			return 0;
		ELSE
			return suma;
        END IF;
END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION todos_lotes_cerrados(id_orden_produccion INT) RETURNS BOOLEAN
BEGIN
	IF EXISTS(SELECT * FROM todos_lotes_planeados AS lp WHERE lp.desc_estado = 'ABIERTO' AND lp.id_orden_produccion = id_orden_produccion ) THEN
		return false;
	ELSE
		return true;
    END IF;
END //
DELIMITER ;


DELIMITER //
CREATE FUNCTION obtener_barras_faltantes_asignar(id_orden_produccion INT) RETURNS INT
BEGIN 
	DECLARE barras_necesarias FLOAT;
    DECLARE barras_restantes FLOAT;
    
    SET barras_necesarias = (SELECT mo.barras_necesarias FROM materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion);
    SET barras_necesarias = (SELECT CEILING(barras_necesarias));
    
    SET barras_restantes = (SELECT SUM(me.cantidad) FROM materiales_entregados AS me WHERE me.id_material_orden =
                    (SELECT mo.id_material_orden FROM materiales_orden AS mo WHERE mo.id_orden_produccion = id_orden_produccion));
    
    IF barras_restantes IS NULL THEN
		
        SET barras_restantes = 0;
    
    END IF;
    
    RETURN barras_necesarias - barras_restantes;
    
END //
DELIMITER ;

DELIMITER //
CREATE FUNCTION obtener_fecha_siguiente_lote_planeado(id_orden_produccion INT) RETURNS VARCHAR(50)
BEGIN
		
    DECLARE fecha_siguiente 	VARCHAR(50);
	SET fecha_siguiente = (SELECT lp.fecha_planeada FROM todos_lotes_planeados AS lp WHERE lp.id_orden_produccion = id_orden_produccion AND desc_estado = 'ABIERTO' LIMIT 1);
			
    RETURN fecha_siguiente;        
END //    
DELIMITER ;

DELIMITER //
CREATE FUNCTION cantidad_restante_compras(id_material_solicitado INT,cantidad_total INT) RETURNS INT
BEGIN
	DECLARE suma INT;
    SET suma = (SELECT SUM(cantidad) FROM materiales_solicitud_compras AS msc WHERE msc.id_material_solicitado = id_material_solicitado);
   
   IF suma IS NULL THEN
		SET suma = 0;
	END IF;
   
   RETURN cantidad_total - suma;
END //
DELIMITER ;



DELIMITER //
CREATE FUNCTION obtener_cantidad_utilizada_material(desc_lote VARCHAR(200)) RETURNS INT
BEGIN
	
    DECLARE cantidad 	INT;
    
	SET cantidad = (SELECT SUM(me.cantidad) FROM entradas_materiales AS em INNER JOIN materiales_entregados AS me ON 
					em.id_entrada_material = me.id_entrada_material WHERE em.desc_lote = desc_lote);
    
    IF cantidad IS NULL THEN
		SET cantidad = 0;
    END IF;
    
    RETURN cantidad;
END //
DELIMITER ;