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

/**SEGUIMIENTO ORDEN PRODUCCION*/
SELECT desc_tipo_proceso FROM tipos_proceso AS pc 
WHERE pc.id_tipo_proceso = (SELECT id_tipo_proceso FROM lotes_planeados WHERE id_orden_produccion = 1 GROUP BY id_tipo_proceso);


/**OBTENER PROCESOS DE UNA ORDEN DE PRODUCCION*/
SELECT * FROM lotes_produccion AS lpr WHERE lpr.id_lote_planeado = (SELECT id_lote_planeado FROM lotes_planeados AS lp WHERE lp.id_orden_produccion = 1 
 AND lp.id_tipo_proceso = (SELECT id_tipo_proceso FROM tipos_proceso WHERE desc_tipo_proceso = 'MAQUINADO'));

/**PLANEACION Y REQUISICION SELECCION ORDENES DE PRODUCCION*/
SELECT id_pedido,no_orden_compra,fecha_entrega FROM pedidos 
WHERE id_pedido = ( SELECT id_pedido FROM ordenes_trabajo WHERE id_orden_trabajo = ( SELECT id_orden_trabajo FROM ordenes_produccion WHERE id_estado = (
SELECT id_estado FROM estados WHERE desc_estado = 'ABIERTO' AND id_orden_produccion NOT IN (SELECT id_orden_produccion FROM lotes_planeados)) GROUP BY id_orden_trabajo));



------------------------------------------
SELECT * FROM pedidos;
SELECT * FROM ordenes_trabajo;
SELECT * FROM ordenes_produccion;
-------------------------------------------
SELECT * FROM lotes_planeados;
SELECT * FROM materiales_orden;
SELECT * FROM materiales_requeridos;
-------------------------------------------
SELECT * FROM materiales_orden_requisicion;
SELECT * FROM materiales_solicitados;
SELECT * FROM requisiciones;
------------------------------------------
SELECT * FROM lotes_produccion;
SELECT * FROM defectos_lotes;

SELECT * FROM (SELECT * FROM ordenes_produccion WHERE id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'PLANEACION1')) AS ordenes_abiertas;

SELECT * FROM bitacoraPedidos WHERE no_orden_compra = '' and MONTH(fecha_recepcion) = 1  AND YEAR(fecha_recepcion) = 2019;

SELECT op.id_orden_trabajo,op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente FROM ordenes_produccion_abiertas AS op 
INNER JOIN productos AS pr ON op.id_producto = pr.id_producto
LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion
WHERE (lp.id_lote_planeado IS NULL OR lp.id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO'))
AND op.id_orden_trabajo = 1;

SELECT op.id_orden_trabajo,op.id_orden_produccion,pr.clave_producto,op.cantidad_cliente FROM ordenes_produccion_abiertas AS op 
INNER JOIN productos AS pr ON op.id_producto = pr.id_producto LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion
WHERE (lp.id_lote_planeado IS NULL OR lp.id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO')) AND op.id_orden_trabajo = 1;