USE control_piezas_2;

/*yaaaa*/
CREATE VIEW bitacoraPedidos 
AS
SELECT 
ot.id_orden_trabajo,
pd.no_orden_compra,
pr.clave_producto,
pd.fecha_entrega,
pd.fecha_confirmacion_entrega,
pd.fecha_recepcion,
es.desc_estado,
cn.desc_contacto,
cl.nombre_cliente,
op.cantidad_cliente
FROM pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido JOIN contactos cn ON
pd.id_contacto = cn.id_contacto JOIN clientes cl ON cl.id_cliente = cn.id_cliente
JOIN ordenes_produccion op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN productos AS pr ON pr.id_producto = op.id_producto JOIN
estados es ON pd.id_estado = es.id_estado;

/*ya*/
CREATE VIEW procedimiento_total
AS
SELECT 
ot.id_orden_trabajo,
pr.clave_producto,
op.cantidad_total,
pd.no_orden_compra,
op.id_orden_produccion,
op.piezas_por_turno,
mt.desc_material,
op.worker,
tp.desc_tipo_proceso,
mq.desc_maquina,
es.desc_estado
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido 
JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo 
JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
JOIN productos AS pr ON pr.id_producto = op.id_producto 
JOIN lotes_planeados AS lp ON lp.id_orden_produccion = op.id_orden_produccion 
JOIN tipos_proceso AS tp ON lp.id_tipo_proceso = tp.id_tipo_proceso 
JOIN maquinas AS mq ON mq.id_maquina = lp.id_maquina
JOIN estados AS es ON es.id_estado = ot.id_estado;



/*ya*/
CREATE VIEW todos_los_estados
AS
SELECT es.id_estado,desc_tipo_estado,desc_estado 
FROM tipos_estado AS tp JOIN estados AS es ON tp.id_tipo_estado = es.id_tipo_estado;


/*CREATE VIEW procesando_producto
AS
SELECT 
op.id_orden_produccion,
op.cantidad_total,
pr.clave_producto,
mq.desc_maquina,
tes.desc_tipo_estado,
tes.desc_estado,
tp.desc_tipo_proceso,
lp.cantidad_administrador,
mt.desc_material,
lp.fecha_trabajo
FROM ordenes_produccion AS op JOIN productos AS pr ON op.id_producto = pr.id_producto
JOIN procesos_produccion AS pp ON op.id_orden_produccion = pp.id_orden_produccion
JOIN lotes_produccion AS lp ON lp.id_proceso_produccion = pp.id_proceso_produccion
JOIN maquinas AS mq ON mq.id_maquina = pp.id_maquina 
JOIN todos_los_estados AS tes ON tes.id_estado = pp.id_estado
JOIN tipos_proceso AS tp ON tp.id_tipo_proceso = pp.id_tipo_proceso
JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
WHERE desc_tipo_estado = 'PROCESOS DE PRODUCCION' AND desc_estado = 'PRODUCCION';
*/

/*
CREATE VIEW bitacora_ordenes_trabajo
AS
select 
op.id_orden_produccion,
op.fecha_registro,
pr.clave_producto,
op.cantidad_cliente,
op.fecha_inicio,
op.fecha_fin,
pd.id_pedido,
pd.fecha_entrega,
st.desc_estado,
op.observaciones
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
JOIN ordenes_produccion AS op ON op.id_orden_trabajo = ot.id_orden_trabajo
JOIN productos AS pr ON pr.id_producto = op.id_producto
JOIN estados AS st ON st.id_estado = op.id_estado;
*/

CREATE VIEW ver_ordenes_produccion
AS
SELECT 
bp.id_orden_trabajo,
bp.no_orden_compra,
bp.fecha_entrega AS fecha_entrega_pedido,
bp.fecha_confirmacion_entrega AS fecha_confirmacion_entrega_pedido,
bp.fecha_recepcion AS fecha_recepcion_pedido,
bp.desc_contacto,
bp.nombre_cliente,
op.cantidad_cliente,
pt.clave_producto,
pt.cantidad_total,
pt.id_orden_produccion,
pt.desc_material,
pt.desc_maquina,
mo.barras_necesarias,
op.piezas_por_turno,
op.turnos_necesarios,
op.fecha_registro AS fecha_registro_op,
op.fecha_montaje,
op.fecha_desmontaje,
op.fecha_inicio AS fecha_inicio_op,
op.fecha_fin AS fecha_fin_op,
op.observaciones AS observaciones_op
FROM bitacoraPedidos AS bp JOIN procedimiento_total AS pt ON bp.id_orden_trabajo = pt.id_orden_trabajo 
JOIN ordenes_produccion AS op ON pt.id_orden_produccion = op.id_orden_produccion 
JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
GROUP BY pt.id_orden_produccion;


CREATE VIEW fechas_planeadas
AS
SELECT op.id_orden_produccion,lpn.cantidad_planeada,DAY(fecha_planeada) AS dia ,lpn.fecha_planeada,mq.desc_maquina,tpo.desc_tipo_proceso 
FROM lotes_planeados AS lpn JOIN ordenes_produccion AS op ON op.id_orden_produccion = lpn.id_orden_produccion 
JOIN tipos_proceso AS tpo ON tpo.id_tipo_proceso = lpn.id_tipo_proceso
JOIN maquinas AS mq ON mq.id_maquina = lpn.id_maquina;


CREATE VIEW requisicion_ordenes AS
SELECT
mr.id_material_requerido,
mr.id_material,
mo.barras_necesarias,
pt.id_orden_trabajo,
pt.clave_producto,
pt.cantidad_total,
pt.no_orden_compra,
pt.id_orden_produccion,
pt.piezas_por_turno,
pt.desc_material,
pt.desc_tipo_proceso,
pt.desc_maquina,
pt.desc_estado
FROM materiales_orden AS mo 
JOIN procedimiento_total AS pt ON mo.id_orden_produccion = pt.id_orden_produccion 
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido  GROUP BY id_orden_trabajo;



CREATE VIEW bitacora_requisiciones
AS
SELECT ms.id_requisicion,mt.desc_material,ms.cantidad,desc_estado
FROM materiales_solicitados AS ms JOIN materiales AS mt ON ms.id_material = mt.id_material 
JOIN todos_los_estados AS tes ON tes.id_estado = ms.id_estado;



SELECT * FROM bitacora_requisiciones;
SELECT * FROM requisicion_ordenes;
SELECT * FROM materiales_orden;
SELECT * FROM materiales_requeridos;
SELECT * FROM materiales_solicitados;
SELECT * FROM requisiciones;
SELECT * FROM materiales_orden_requisicion;

 

/*
select cantidad_total,desc_material 
FROM materiales_requeridos AS mr JOIN materiales AS mt ON mt.id_material = mr.id_material
WHERE id_requisicion = (SELECT id_requisicion FROM requisiciones WHERE id_orden_trabajo = 1);

SELECT piezas_por_turno FROM productos_material WHERE 
id_material = (SELECT id_material FROM materiales WHERE desc_material = 'MATERIAL 1')
AND id_producto = (SELECT id_producto FROM productos WHERE clave_producto = '6613-8');

SELECT (SELECT op.cantidad_total FROM ordenes_produccion AS op WHERE op.id_orden_produccion = 1)/
(SELECT pm.piezas_por_barra FROM productos_material AS pm WHERE pm.id_material = 1 
AND pm.id_producto = (SELECT id_producto FROM ordenes_produccion WHERE
id_orden_produccion = 1)) AS barras_necesarias;*/
