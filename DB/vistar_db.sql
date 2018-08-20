

CREATE VIEW ordenes_trabajo_activas AS
SELECT ordenes_trabajo.desc_orden_trabajo,clientes.nombre_cliente, ordenes_produccion.id_estado 
FROM ordenes_trabajo JOIN clientes ON ordenes_trabajo.id_cliente = clientes.id_cliente 
JOIN ordenes_produccion
ON ordenes_trabajo.id_orden_trabajo = ordenes_produccion.id_orden_produccion 
JOIN estados ON estados.id_estado = ordenes_produccion.id_estado 
WHERE ordenes_produccion.id_estado = 1;


select COUNT(*) from ordenes_produccion JOIN ordenes_trabajo on ordenes_produccion.id_orden_trabajo = ordenes_trabajo.id_orden_trabajo
where ordenes_trabajo.desc_orden_trabajo = 'asd';


select COUNT(*) from ordenes_produccion JOIN ordenes_trabajo on ordenes_produccion.id_orden_trabajo = ordenes_trabajo.id_orden_trabajo
JOIN estados on ordenes_produccion.id_estado = estados.id_estado WHERE ordenes_trabajo.desc_orden_trabajo = 'asd' AND estados.desc_estados = 'ABIERTAS';

use control_piezas;

select * from ordenes_trabajo_activas;

select * from ordenes_produccion;