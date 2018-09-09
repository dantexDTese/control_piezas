
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


select * from bitacoraPedidos;
select * from pedidos;


select * from estados;
select * from parcialidades;

insert into estados(desc_estados) VALUES('ABIERTO');


SELECT pr.fecha_entrega,pr.cantidad_entregada FROM bitacoraPedidos bp JOIN parcialidades pr ON bp.id_pedido = pr.id_pedido
WHERE bp.no_orden_compra;