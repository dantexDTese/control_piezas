USE control_piezas_2;

------------------------------------------
SELECT * FROM pedidos;
SELECT * FROM ordenes_trabajo;
SELECT * FROM ordenes_produccion;
-------------------------------------------
SELECT * FROM lotes_planeados;
SELECT * FROM materiales_orden;
SELECT * FROM materiales_requeridos;
-------------------------------------------
SELECT * FROM requisiciones;
SELECT * FROM materiales_solicitados;
SELECT * FROM materiales_orden_requisicion;
------------------------------------------
SELECT * FROM lotes_produccion;
SELECT * FROM defectos_lotes;
------------------------------------------
SELECT * FROM entradas_materiales;


#PLANEACION
#SELECCIONAR TODAS LOS PEDIDOS QUE NO ESTEN EN PLANEACION O SU PLANEACION PASADA 
SELECT pd.id_pedido,pd.no_orden_compra,pd.fecha_entrega FROM pedidos AS pd 
JOIN ordenes_trabajo AS ot ON  pd.id_pedido = ot.id_pedido
JOIN ordenes_por_planear AS opp ON ot.id_orden_trabajo = opp.id_orden_trabajo GROUP BY pd.id_pedido;


#REQUISICIONES
#SELECCIONAR LAS PARCIALIDADES DE UNA REQUISICION DE UN MATERIAL DE ESA REQUISICION Y CLASIFICADAS POR ORDEN DE PRODUCCION
SELECT ms.parcialidad,mor.cantidad,ms.fecha_solicitud,ms.fecha_entrega,mo.id_orden_produccion FROM materiales_orden_requisicion AS mor
INNER JOIN materiales_orden AS mo ON mo.id_material_orden = mor.id_material_orden
INNER JOIN (SELECT * FROM materiales_solicitados 
WHERE id_material = (SELECT id_material FROM materiales WHERE desc_material = 'MATERIAL 1') AND id_requisicion = 1) AS ms
ON ms.id_material_solicitado = mor.id_material_solicitado;

SELECT * FROM todos_lotes_planeados AS tlp
INNER JOIN todas_ordenes_produccion AS op ON tlp.id_orden_produccion = op.id_orden_produccion
LEFT JOIN lotes_produccion AS lp ON tlp.id_lote_planeado = lp.id_lote_planeado;

select * from ver_ordenes_produccion;

SELECT id_pedido,id_orden_trabajo,no_orden_compra,nombre_cliente FROM todos_pedidos WHERE desc_estado = 'ABIERTO';
