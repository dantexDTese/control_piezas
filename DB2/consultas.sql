
USE control_piezas_2;

/*para el modulo de planeacion*/
SELECT
pd.no_orden_compra,
ot.id_orden_trabajo,
pd.fecha_entrega 
FROM pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
JOIN estados ON ot.id_estado = estados.id_estado WHERE estados.desc_estado = 'PLANEACION' GROUP BY pd.no_orden_compra;

/*para el modulo de planeacion*/
SELECT
ot.id_orden_trabajo,
op.id_orden_produccion,
pr.clave_producto,
op.cantidad_cliente
FROM ordenes_trabajo AS ot JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo 
JOIN productos AS pr ON pr.id_producto = op.id_producto GROUP BY op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente;

