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
es.desc_estados,
cn.desc_contacto,
cl.nombre_cliente,
op.cantidad_cliente
FROM pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido JOIN contactos cn ON
pd.id_contacto = cn.id_contacto JOIN clientes cl ON cl.id_cliente = cn.id_cliente
JOIN ordenes_produccion op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN productos AS pr ON pr.id_producto = op.id_producto JOIN
estados es ON pd.id_estado = es.id_estado;

/*ya*/
CREATE VIEW PedidosPendientes
AS
select 
bp.no_orden_compra,
ot.id_orden_trabajo,
fecha_entrega 
from bitacoraPedidos AS bp 
JOIN ordenes_trabajo AS ot ON bp.id_orden_trabajo = ot.id_orden_trabajo 
JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN procesos_produccion AS pp ON 
op.id_orden_produccion = pp.id_orden_produccion JOIN estados ON
pp.id_estado = estados.id_estado WHERE estados.desc_estados = "PLANEACION" GROUP BY bp.no_orden_compra;


CREATE VIEW productosEnEspera
AS
select 
bp.id_orden_trabajo,
op.id_orden_produccion,
pr.clave_producto,
op.cantidad_cliente
 from bitacoraPedidos AS bp 
JOIN ordenes_trabajo AS ot ON bp.id_orden_trabajo = ot.id_orden_trabajo JOIN ordenes_produccion AS op
ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN procesos_produccion AS pp ON 
op.id_orden_produccion = pp.id_orden_produccion JOIN estados ON
pp.id_estado = estados.id_estado JOIN productos AS pr ON pr.id_producto = op.id_producto
WHERE estados.desc_estados = "PLANEACION" GROUP BY op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente;

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
mq.desc_maquina 
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido 
JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo 
JOIN materiales_ordenes_requeridas AS mor ON mor.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mor.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
JOIN productos AS pr ON pr.id_producto = op.id_producto 
JOIN procesos_produccion AS pp ON pp.id_orden_produccion = op.id_orden_produccion 
JOIN tipos_proceso AS tp ON pp.id_tipo_proceso = tp.id_tipo_proceso 
JOIN maquinas AS mq ON mq.id_maquina = pp.id_maquina;

/*ya*/
CREATE VIEW todos_los_estados
AS
SELECT es.id_estado,desc_tipo_estado,desc_estados 
FROM tipos_estado AS tp JOIN estados as es on tp.id_tipo_estado = es.id_tipo_estado;


CREATE VIEW procesando_producto
AS
SELECT 
op.id_orden_produccion,
op.cantidad_total,
pr.clave_producto,
mq.desc_maquina,
tes.desc_tipo_estado,
tes.desc_estados,
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
JOIN materiales_ordenes_requeridas AS mor ON mor.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mor.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
WHERE desc_tipo_estado = 'PROCESOS DE PRODUCCION' AND desc_estados = 'PRODUCCION';


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
st.desc_estados,
op.observaciones
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
JOIN ordenes_produccion AS op ON op.id_orden_trabajo = ot.id_orden_trabajo
JOIN productos AS pr ON pr.id_producto = op.id_producto
JOIN estados AS st ON st.id_estado = op.id_estado;


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
mor.barras_necesarias,
op.piezas_por_turno,
op.turnos_necesarios,
op.fecha_registro AS fecha_registro_op,
op.fecha_montaje,op.fecha_desmontaje,
op.fecha_inicio AS fecha_inicio_op,
op.fecha_fin AS fecha_fin_op,
op.observaciones AS observaciones_op
FROM bitacoraPedidos AS bp JOIN procedimiento_total AS pt ON bp.id_orden_trabajo = pt.id_orden_trabajo 
JOIN ordenes_produccion AS op ON pt.id_orden_produccion = op.id_orden_produccion 
JOIN materiales_ordenes_requeridas AS mor ON mor.id_orden_produccion = op.id_orden_produccion
GROUP BY pt.id_orden_produccion;


CREATE VIEW fechas_planeadas
AS
SELECT op.id_orden_produccion,lpn.cantidad_planeada,DAY(fecha_planeada) AS dia ,fecha_planeada,mq.desc_maquina 
FROM lotes_planeados AS lpn JOIN ordenes_produccion AS op ON op.id_orden_produccion = lpn.id_orden_produccion 
JOIN procesos_produccion AS pp ON pp.id_orden_produccion = op.id_orden_produccion
JOIN maquinas AS mq ON mq.id_maquina = pp.id_maquina;


CREATE VIEW requisicion_ordenes AS
SELECT
mr.id_material_requerido,
mr.id_material,
mr.id_requisicion,
mor.barras_necesarias,
pt.id_orden_trabajo,
pt.clave_producto,
pt.cantidad_total,
pt.no_orden_compra,
pt.id_orden_produccion,
pt.piezas_por_turno,
pt.desc_material,
pt.desc_tipo_proceso,
pt.desc_maquina,
st.desc_estados
FROM materiales_ordenes_requeridas AS mor 
JOIN procedimiento_total AS pt ON mor.id_orden_produccion = pt.id_orden_produccion 
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mor.id_material_requerido
JOIN estados AS st ON st.id_estado = mr.id_estado;



select cantidad_total,desc_material 
FROM materiales_requeridos AS mr JOIN materiales AS mt ON mt.id_material = mr.id_material
WHERE id_requisicion = (SELECT id_requisicion FROM requisiciones WHERE id_orden_trabajo = 1);


SELECT * FROM materiales;
SELECT * FROM productos;


select * from productos_material;

SELECT piezas_por_turno FROM productos_material WHERE 
id_material = (SELECT id_material FROM materiales WHERE desc_material = 'MATERIAL 1')
AND id_producto = (SELECT id_producto FROM productos WHERE clave_producto = '6613-8');
