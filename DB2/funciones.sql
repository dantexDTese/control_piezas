DELIMITER //
CREATE FUNCTION obtenerSumaMateriales(id_orden_produccion INT) RETURNS INT
BEGIN
	DECLARE suma INT;
		SET suma = (SELECT SUM(cantidad) FROM materiales_orden_requisicion AS mor JOIN materiales_orden AS mo ON mor.id_material_orden = mo.id_material_orden
				WHERE mo.id_orden_produccion = id_orden_produccion);
	
    RETURN suma;
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

