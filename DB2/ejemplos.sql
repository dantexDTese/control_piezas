
use control_piezas_2;

SELECT * FROM productos;
SELECT * FROM maquinas;
SELECT * FROM clientes;
SELECT * FROM contactos;
SELECT * FROM estados;
SELECT * FROM pedidos;
SELECT * FROM ordenes_trabajo;
SELECT * FROM ordenes_produccion;
SELECT * FROM procesos_produccion;
SELECT * FROM tipos_proceso;
SELECT * FROM lotes_produccion;
SELECT * FROM requisiciones;
SELECT * FROM proveedores;
SELECT * FROM materiales_proveedor;
SELECT * FROM parcialidades_requisicion;
SELECT * FROM materiales_ordenes_requeridas;
SELECT * FROM parcialidades_orden_requerida;
SELECT * FROM materiales_requeridos;
SELECT * FROM requisiciones;

SELECT * FROM bitacoraPedidos;
SELECT * FROM todos_los_estados;
SELECT * FROM PedidosPendientes;
SELECT * FROM productosEnEspera;
SELECT * FROM procedimiento_total;
select * from materiales_ordenes_requeridas;
select * from ver_ordenes_produccion;


DESCRIBE proveedores;

SELECT num_parcialidad from parcialidades_orden_requerida as por WHERE
por.id_material_requerido = (SELECT id_material_requerido FROM materiales_requeridos AS mr 
WHERE mr.id_requisicion = (SELECT id_requisicion FROM requisiciones AS rq WHERE rq.id_orden_trabajo = 1)
AND mr.id_material = (SELECT id_material FROM materiales AS mt WHERE mt.desc_material = 'MATERIAL 1'));



SELECT precio_unitario FROM materiales_proveedor AS mp 
WHERE mp.id_material = (SELECT id_material FROM materiales WHERE desc_material = 'MATERIAL 1') 
AND mp.id_proveedor = (SELECT id_proveedor FROM proveedores AS pr WHERE pr.desc_proveedor = 'proveedor2');
	
    

SELECT mr.id_material_requerido FROM materiales_requeridos AS mr JOIN
materiales_ordenes_requeridas AS mor ON mor.id_material_requerido = mr.id_material_requerido WHERE
mor.id_orden_produccion = 1;


SELECT * FROM proveedores;            

