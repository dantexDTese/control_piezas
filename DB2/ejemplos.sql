drop database control_piezas_2;

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
SELECT * FROM bitacoraPedidos;
SELECT * FROM procesos_produccion;
SELECT * FROM tipos_proceso;
SELECT * FROM lotes_produccion;
SELECT * FROM todos_los_estados;

SELECT * FROM bitacoraPedidos;
SELECT * FROM PedidosPendientes;
SELECT * FROM productosEnEspera;
SELECT * FROM procedimiento_total;
SELECT * FROM todos_los_estados;
SELECT * FROM procesando_producto;


SELECT 
bp.id_orden_trabajo,
bp.no_orden_compra,
bp.fecha_entrega AS fecha_entrega_pedido,
bp.fecha_confirmacion_entrega AS fecha_confirmacion_entrega_pedido,
bp.fecha_recepcion AS fecha_recepcion_pedido,
bp.desc_contacto,bp.nombre_cliente,
bp.cantidad_cliente,
bp.clave_producto,
pt.cantidad_total,
pt.id_orden_produccion,
pt.desc_material,
pt.desc_maquina,
op.barras_necesarias,
op.piezas_por_turno,
op.turnos_necesarios,
op.fecha_registro AS fecha_registro_op,
op.fecha_montaje,op.fecha_desmontaje,
op.fecha_inicio AS fecha_inicio_op,
op.fecha_fin AS fecha_fin_op,
op.observaciones AS observaciones_op
FROM bitacorapedidos AS bp JOIN procedimiento_total AS pt ON bp.id_orden_trabajo = pt.id_orden_trabajo 
JOIN ordenes_produccion AS op ON pt.id_orden_produccion = op.id_orden_produccion;
