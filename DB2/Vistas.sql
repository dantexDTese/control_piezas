USE control_piezas_2;

CREATE VIEW bitacoraPedidos 
AS
SELECT 
pd.id_pedido,
pd.no_orden_compra,
pd.fecha_entrega,
pd.fecha_confirmacion_entrega,
pd.fecha_recepcion,
es.desc_estados,
cn.desc_contacto,
cl.nombre_cliente,
op.cantidad_cliente,
pr.clave_producto
FROM pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido JOIN contactos cn ON
pd.id_contacto = cn.id_contacto JOIN clientes cl ON cl.id_cliente = cn.id_cliente
JOIN ordenes_produccion op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN productos AS pr ON pr.id_producto = op.id_producto JOIN
estados es ON pd.id_estado = es.id_estado;

CREATE VIEW OrdenesPendientes
AS
select no_orden_compra,fecha_recepcion from bitacoraPedidos AS bp 
JOIN ordenes_trabajo AS ot ON bp.id_pedido = ot.id_pedido JOIN ordenes_produccion AS op
ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN procesos_produccion AS pp ON 
op.id_orden_produccion = pp.id_orden_produccion JOIN estados ON
pp.id_estado = estados.id_estado WHERE estados.desc_estados = "PLANEACION" GROUP BY no_orden_compra;

CREATE VIEW productosEnEspera
AS
select bp.no_orden_compra,op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente from bitacoraPedidos AS bp 
JOIN ordenes_trabajo AS ot ON bp.id_pedido = ot.id_pedido JOIN ordenes_produccion AS op
ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN procesos_produccion AS pp ON 
op.id_orden_produccion = pp.id_orden_produccion JOIN estados ON
pp.id_estado = estados.id_estado JOIN productos AS pr ON pr.id_producto = op.id_producto
WHERE estados.desc_estados = "PLANEACION" GROUP BY op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente;

CREATE VIEW procedimiento_total
AS
select pd.no_orden_compra,op.id_orden_produccion,op.cantidad_total,op.worker,
mt.desc_material,pr.clave_producto,tp.desc_tipo_proceso,mq.desc_maquina 
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido JOIN
ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN materiales AS mt ON
mt.id_material = op.id_material JOIN productos AS pr ON pr.id_producto = op.id_producto JOIN
procesos_produccion AS pp ON pp.id_orden_produccion = op.id_orden_produccion JOIN tipos_proceso
AS tp ON pp.id_tipo_proceso = tp.id_tipo_proceso JOIN lotes_produccion AS lp ON lp.id_proceso_produccion = 
pp.id_proceso_produccion JOIN maquinas AS mq ON mq.id_maquina = lp.id_maquina;


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
JOIN maquinas AS mq ON mq.id_maquina = lp.id_maquina 
JOIN todos_los_estados AS tes ON tes.id_estado = pp.id_estado
JOIN tipos_proceso AS tp ON tp.id_tipo_proceso = pp.id_tipo_proceso
JOIN materiales AS mt ON mt.id_material = op.id_material
WHERE desc_tipo_estado = 'PROCESOS DE PRODUCCION' AND desc_estados = 'PRODUCCION';


CREATE VIEW bitacora_ordenes_trabajo
AS
select op.id_orden_produccion,op.fecha_registro,pr.clave_producto,op.cantidad_cliente,op.fecha_inicio,op.fecha_fin,
pd.id_pedido,pd.fecha_entrega,st.desc_estados,op.observaciones
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
JOIN ordenes_produccion AS op ON op.id_orden_trabajo = ot.id_orden_trabajo
JOIN productos AS pr ON pr.id_producto = op.id_producto
JOIN estados AS st ON st.id_estado = op.id_estado;



