DELIMITER //
CREATE FUNCTION CantidadRestanteRequisicion(desc_material INT) RETURNS INT
BEGIN
	DECLARE suma_barras_necesarias INT;
    DECLARE barras_restantes INT;
    
	SELECT @suma_barras_necesarias := SUM(barras_necesarias) AS barras_necesarias ,desc_material FROM 
    requisicion_ordenes AS ro WHERE ro.desc_material = desc_material GROUP BY desc_material;
	
    SELECT @barras_restantes := @suma_barras_necesarias - SUM(cantidad) from requisicion_ordenes AS ro 
	JOIN parcialidades_orden_requerida AS por ON ro.id_material_orden_requerida = por.id_material_orden_requerida 
    WHERE ro.desc_material = desc_material GROUP BY desc_material; 

	
END ; //
