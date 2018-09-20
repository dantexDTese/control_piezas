select * from productos;
select * from maquinas;
select * from clientes;
select * from contactos;
select * from estados;
select * from ordenes_trabajo;
select * from ordenes_produccion;
select * from procesos_produccion;
select * from bitacoraPedidos;
select * from procesos_produccion;
select * from tipos_proceso;

select * from estados;

use control_piezas_2;

select bp.no_orden_compra,op.cantidad_cliente,pr.clave_producto from bitacorapedidos AS bp 
JOIN ordenes_trabajo AS ot ON bp.id_pedido = ot.id_pedido
JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN productos as pr ON
pr.id_producto = op.id_producto;

select * from bitacorapedidos AS bp JOIN ordenes_trabajo AS ot ON bp.id_pedido = ot.id_pedido
JOIN ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo JOIN productos as pr ON
pr.id_producto = op.id_producto;
            
SELECT desc_material FROM materiales;

