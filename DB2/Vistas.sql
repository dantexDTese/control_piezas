USE control_piezas_2;

#TODOS LOS PEDIDOS MOSTRANDO SU DESCRIPCION DEL ESTADO,CONTACTO Y CLIENTE
CREATE VIEW todos_pedidos
AS
SELECT pd.id_pedido,ot.id_orden_trabajo,es.desc_estado,cn.desc_contacto,pd.fecha_recepcion,pd.no_orden_compra,pd.fecha_entrega,
pd.fecha_confirmacion_entrega,cl.nombre_cliente,ot.fecha_inicio,ot.fecha_terminacion 
FROM pedidos AS pd
INNER JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
INNER JOIN estados AS es ON pd.id_estado = es.id_estado
INNER JOIN contactos AS cn ON pd.id_contacto = cn.id_contacto
INNER JOIN clientes AS cl ON cn.id_cliente = cl.id_cliente
GROUP BY pd.id_pedido;


#TODAS LAS ORDENES DE PRODUCCION MOSTRANDO LA CLAVE DEL PRODUCTO Y LA DESCRIPCION DE SU ESTADO
CREATE VIEW todas_ordenes_produccion
AS
SELECT op.id_orden_produccion,op.id_orden_trabajo,pr.clave_producto,es.desc_estado,op.id_empaque,op.cantidad_cliente,op.cantidad_total,op.worker,op.piezas_por_turno,
op.turnos_necesarios,op.turnos_reales,op.fecha_registro,op.fecha_montaje,op.fecha_desmontaje,op.fecha_inicio,op.fecha_fin,op.observaciones
FROM ordenes_produccion AS op 
INNER JOIN productos AS pr ON op.id_producto = pr.id_producto
INNER JOIN estados AS es ON op.id_estado = es.id_estado;

#TODOS LOS LOTES PLANEADOS MOSTRANDO SU TIPO DE PROCESO, MAQUINA Y ESTADO
CREATE VIEW todos_lotes_planeados
AS
SELECT lp.id_lote_planeado,lp.id_orden_produccion,tp.desc_tipo_proceso,lp.cantidad_planeada,lp.fecha_planeada,mq.desc_maquina,es.desc_estado 
FROM lotes_planeados AS lp
INNER JOIN tipos_proceso AS tp ON lp.id_tipo_proceso = tp.id_tipo_proceso
INNER JOIN maquinas AS mq ON lp.id_maquina = mq.id_maquina
INNER JOIN estados AS es ON lp.id_estado = es.id_estado;

#PRINCIPALES
CREATE VIEW ordenes_produccion_abiertas
AS
SELECT * FROM todas_ordenes_produccion WHERE desc_estado = 'ABIERTO';

CREATE VIEW lotes_planeados_abiertos
AS
SELECT * FROM lotes_planeados WHERE id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'ABIERTO');

#PEDIDOS
CREATE VIEW bitacoraPedidos 
AS
SELECT 
ot.id_orden_trabajo,
pd.no_orden_compra,
op.clave_producto,
pd.fecha_entrega,
pd.fecha_confirmacion_entrega,
pd.fecha_recepcion,
pd.desc_estado,
pd.desc_contacto,
pd.nombre_cliente,
op.cantidad_cliente
FROM todos_pedidos AS pd 
JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido 
JOIN todas_ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo;


/*ya*/
CREATE VIEW procedimiento_total
AS
SELECT 
ot.id_orden_trabajo,
op.clave_producto,
op.cantidad_total,
pd.no_orden_compra,
op.id_orden_produccion,
op.piezas_por_turno,
mt.desc_material,
op.worker,
lp.desc_tipo_proceso,
lp.desc_maquina,
lp.desc_estado
from pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido 
JOIN todas_ordenes_produccion AS op ON ot.id_orden_trabajo = op.id_orden_trabajo 
JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
JOIN todos_lotes_planeados AS lp ON lp.id_orden_produccion = op.id_orden_produccion;


/*CREATE VIEW procesando_producto
AS
SELECT 
op.id_orden_produccion,
op.cantidad_total,
pr.clave_producto,
mq.desc_maquina,
tes.desc_tipo_estado,
tes.desc_estado,
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
JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
WHERE desc_tipo_estado = 'PROCESOS DE PRODUCCION' AND desc_estado = 'PRODUCCION';
*/


CREATE VIEW bitacora_ordenes_trabajo
AS
SELECT 
op.id_orden_produccion,
op.fecha_registro,
op.clave_producto,
op.cantidad_cliente,
op.fecha_inicio,
op.fecha_fin,
obtenerIdRequisicion(op.id_orden_produccion), 
pd.fecha_entrega,
op.desc_estado,
op.observaciones
FROM pedidos AS pd JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
INNER JOIN todas_ordenes_produccion AS op ON op.id_orden_trabajo = ot.id_orden_trabajo;



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
op.clave_producto,
op.cantidad_total,
op.id_orden_produccion,
mt.desc_material,
mq.desc_maquina,
mo.barras_necesarias,
op.piezas_por_turno,
op.turnos_necesarios,
op.fecha_registro AS fecha_registro_op,
op.fecha_montaje,
op.fecha_desmontaje,
op.fecha_inicio AS fecha_inicio_op,
op.fecha_fin AS fecha_fin_op,
op.observaciones AS observaciones_op
FROM bitacoraPedidos AS bp
INNER JOIN todas_ordenes_produccion AS op ON bp.id_orden_trabajo = op.id_orden_trabajo
INNER JOIN materiales_orden AS mo ON mo.id_orden_produccion = op.id_orden_produccion
INNER JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
INNER JOIN materiales AS mt ON mt.id_material = mr.id_material
INNER JOIN lotes_planeados AS lp ON lp.id_orden_produccion = op.id_orden_produccion
INNER JOIN maquinas AS mq ON mq.id_maquina = lp.id_maquina
GROUP BY op.id_orden_produccion;

#PLANEACION

#SELECCIONAR TODAS LAS ORDENES QUE NO ESTEN EN PLANEACION O QUE SU PLANEACION ESTE CERRADA
CREATE VIEW ordenes_por_planear
AS
SELECT op.id_orden_trabajo,op.id_orden_produccion,op.clave_producto,op.cantidad_cliente 
FROM ordenes_produccion_abiertas AS op 
LEFT JOIN lotes_planeados AS lp ON op.id_orden_produccion = lp.id_orden_produccion
WHERE (lp.id_lote_planeado IS NULL OR lp.id_estado = (SELECT id_estado FROM estados WHERE desc_estado = 'CERRADO'));

#SELECIONAR TODOS LOS DIAS QUE SE HAYAN PLANEADO RESPECTO A UNA MAQUINA
CREATE VIEW fechas_planeadas
AS
SELECT op.id_orden_produccion,lpn.cantidad_planeada,DAY(fecha_planeada) AS dia ,lpn.fecha_planeada,lpn.desc_maquina,lpn.desc_tipo_proceso 
FROM todos_lotes_planeados AS lpn JOIN ordenes_produccion AS op ON op.id_orden_produccion = lpn.id_orden_produccion;


CREATE VIEW requisicion_ordenes AS
SELECT
mr.id_material_requerido,
mr.id_material,
mo.barras_necesarias,
pt.id_orden_trabajo,
pt.clave_producto,
pt.cantidad_total,
pt.no_orden_compra,
pt.id_orden_produccion,
pt.piezas_por_turno,
pt.desc_material,
pt.desc_tipo_proceso,
pt.desc_maquina,
pt.desc_estado
FROM materiales_orden AS mo 
JOIN procedimiento_total AS pt ON mo.id_orden_produccion = pt.id_orden_produccion 
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido  GROUP BY id_orden_trabajo;


CREATE VIEW bitacora_requisiciones
AS
SELECT ms.id_requisicion,mt.desc_material,ms.cantidad,desc_estado
FROM materiales_solicitados AS ms JOIN materiales AS mt ON ms.id_material = mt.id_material 
JOIN estados AS es ON es.id_estado = ms.id_estado;


#REQUISICIONES
#SELECCIONAR LAS ORDENES A LAS QUE AUN NO SE LES HAYA DADO UNA REQUISICION
CREATE VIEW faltantes_requisicion
AS
SELECT 	
pd.id_pedido,
pd.no_orden_compra,
ot.id_orden_trabajo,
op.id_orden_produccion,
pr.clave_producto,
op.cantidad_total,
mt.desc_material,
mo.barras_necesarias,
op.fecha_inicio,
obtenerSumaMateriales(op.id_orden_produccion)
FROM ordenes_produccion AS op 
JOIN ordenes_trabajo AS ot ON ot.id_orden_trabajo = op.id_orden_trabajo
JOIN pedidos AS pd ON pd.id_pedido = ot.id_pedido
JOIN materiales_orden AS mo ON op.id_orden_produccion = mo.id_orden_produccion
JOIN materiales_requeridos AS mr ON mr.id_material_requerido = mo.id_material_requerido
JOIN materiales AS mt ON mt.id_material = mr.id_material
JOIN productos AS pr ON pr.id_producto = op.id_producto
WHERE mo.barras_necesarias > obtenerSumaMateriales(op.id_orden_produccion) OR obtenerSumaMateriales(op.id_orden_produccion) IS NULL
GROUP BY id_orden_produccion;

	
#SELECCIONAR EL REGISTRO DE LAS ENTRADAS DE MATERIALES
CREATE VIEW registro_entrada_materiales
AS
SELECT id_entrada_material,fecha_registro,desc_material,desc_proveedor,cantidad,codigo,certificado,orden_compra,inspector,es.desc_estado,comentarios,factura,no_parte 
FROM entradas_materiales AS em
INNER JOIN materiales AS mt ON em.id_material = mt.id_material
INNER JOIN proveedores AS pr ON em.id_proveedor = pr.id_proveedor
INNER JOIN estados AS es ON em.id_estado = es.id_estado;

#ALMACEN
#SELECCIONAR MATERIAL DE LOS CLIENTES
CREATE VIEW productos_clientes
AS
SELECT mc.id_producto_cliente,cl.nombre_cliente,pr.clave_producto,apt.id_almacen_producto_terminado,apt.total FROM productos_cliente AS mc 
JOIN almacen_productos_terminados AS apt ON apt.id_producto_cliente = mc.id_producto_cliente
JOIN clientes AS cl ON mc.id_cliente = cl.id_cliente
JOIN productos AS pr ON mc.id_producto = pr.id_producto;

#SELECCIONA TODAS LAS ENTRADAS Y SALIDAS DE MATERIAL
CREATE VIEW entradas_salidas
AS
SELECT res.id_registro_entrada_salida,pc.id_almacen_producto_terminado,toa.desc_tipo_operacion,res.fecha_registro,res.cantidad,res.total_registrado 
FROM registros_entradas_salidas AS res
JOIN productos_clientes AS pc ON res.id_almacen_producto_terminado = pc.id_almacen_producto_terminado
JOIN tipos_operaciones_almacenes AS toa ON res.id_tipo_operacion_almacen = toa.id_tipo_operacion_almacen;


SELECT * FROM productos_clientes;
