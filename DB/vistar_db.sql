use control_piezas;

CREATE VIEW ordenes_trabajo_activas AS
SELECT ordenes_trabajo.desc_orden_trabajo,clientes.nombre_cliente, ordenes_produccion.id_estado 
FROM ordenes_trabajo JOIN clientes ON ordenes_trabajo.id_cliente = clientes.id_cliente 
JOIN ordenes_produccion
ON ordenes_trabajo.id_orden_trabajo = ordenes_produccion.id_orden_produccion 
JOIN estados ON estados.id_estado = ordenes_produccion.id_estado 
WHERE ordenes_produccion.id_estado = 1;

CREATE VIEW vista_ordenes_produccion AS
select op.id_orden_produccion,op.id_estado,op.cantidad_total,op.cantidad_cliente,op.barras_necesarias,
op.turnos_necesarios,op.fecha_montaje,p.id_producto,p.clave_producto,m.id_material,m.desc_material from ordenes_produccion AS op JOIN productos AS p ON op.id_producto = p.id_producto
JOIN materiales AS m ON op.id_material = m.id_material;


select * from ordenes_produccion;