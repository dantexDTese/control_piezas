
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

