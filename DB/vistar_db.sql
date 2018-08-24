use control_piezas;

CREATE VIEW ordenes_trabajo_activas AS
SELECT ordenes_trabajo.desc_orden_trabajo,clientes.nombre_cliente, ordenes_produccion.id_estado 
FROM ordenes_trabajo JOIN clientes ON ordenes_trabajo.id_cliente = clientes.id_cliente 
JOIN ordenes_produccion
ON ordenes_trabajo.id_orden_trabajo = ordenes_produccion.id_orden_produccion 
JOIN estados ON estados.id_estado = ordenes_produccion.id_estado 
WHERE ordenes_produccion.id_estado = 1;


CREATE VIEW vista_ordenes_produccion AS
SELECT op.id_orden_produccion,pr.clave_producto,es.desc_estados,cantidad_total,mt.desc_material,op.fecha_montaje 
FROM ordenes_produccion AS op JOIN estados AS es ON op.id_estado = es.id_estado 
JOIN productos AS pr ON op.id_producto = pr.id_producto JOIN materiales AS mt ON op.id_material = mt.id_material
WHERE op.fecha_montaje IS NOT NULL AND es.desc_estados != 'CERRADA';



SELECT *
FROM vista_ordenes_produccion AS vop JOIN procesos_produccion 
AS pp ON vop.id_orden_produccion = pp.id_orden_produccion JOIN estados AS es ON es.id_estado = pp.id_estado 
JOIN tipos_proceso AS tp ON tp.id_tipo_proceso = pp.id_tipo_proceso; 