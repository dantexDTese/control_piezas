USE control_piezas;

#TODOS LOS PEDIDOS MOSTRANDO SU DESCRIPCION DEL ESTADO,CONTACTO Y CLIENTE
CREATE VIEW todos_pedidos
AS
SELECT 
pd.id_pedido,ot.id_orden_trabajo,es.desc_estado,cn.desc_contacto,pd.fecha_recepcion,
pd.no_orden_compra,pd.fecha_entrega,pd.fecha_confirmacion_entrega,cl.nombre_cliente,
ot.fecha_inicio,ot.fecha_terminacion 
FROM pedidos AS pd
INNER JOIN ordenes_trabajo AS ot ON pd.id_pedido = ot.id_pedido
INNER JOIN estados AS es ON pd.id_estado = es.id_estado
INNER JOIN contactos AS cn ON cn.id_contacto = pd.id_contacto
INNER JOIN clientes AS cl ON cl.id_cliente = cn.id_cliente
GROUP BY pd.id_pedido;

#TODAS LAS ORDENES DE PRODUCCION MOSTRANDO LA CLAVE DEL PRODUCTO Y LA DESCRIPCION DE SU ESTADO
CREATE VIEW todas_ordenes_produccion
AS
SELECT op.id_orden_produccion,op.id_orden_trabajo,pr.clave_producto,es.desc_estado,op.desc_empaque,op.cantidad_cliente,op.cantidad_total,
 op.worker,op.piezas_por_turno AS piezas_turno_registro,op.turnos_necesarios,op.turnos_reales,op.fecha_registro,op.fecha_montaje,op.fecha_desmontaje,op.fecha_inicio,op.fecha_fin,op.observaciones,
vm.id_material,vm.desc_tipo_material,vm.desc_dimencion,vm.desc_forma,vm.clave_forma,validacion_compras,validacion_produccion,validacion_matenimiento,validacion_calidad,pr.desc_producto
FROM ordenes_produccion AS op 
INNER JOIN productos AS pr ON op.id_producto = pr.id_producto
INNER JOIN ver_materiales AS vm ON pr.id_material = vm.id_material
INNER JOIN estados AS es ON op.id_estado = es.id_estado;

#MAQUINAS QUE TRABAJAN A LOS PRODUCTOS Y SUS PROCESOS
CREATE VIEW ver_productos_maquinas
AS
SELECT pm.id_producto_maquina,pr.id_producto,pm.piezas_por_turno,pm.piezas_por_barra,pm.piezas_por_hora,pr.clave_producto,mq.desc_maquina,tp.clave_tipo_proceso,tp.desc_tipo_proceso 
FROM productos_maquinas AS pm 
INNER JOIN productos AS pr ON pm.id_producto = pr.id_producto
LEFT JOIN maquinas AS mq ON pm.id_maquina = mq.id_maquina
INNER JOIN tipos_proceso AS tp ON pm.id_tipo_proceso = tp.id_tipo_proceso;

#TODOS LOS LOTES PLANEADOS MOSTRANDO SU TIPO DE PROCESO, MAQUINA Y ESTADO
CREATE VIEW todos_lotes_planeados
AS
SELECT lp.id_lote_planeado,lp.id_orden_produccion,tp.desc_tipo_proceso,lp.cantidad_planeada,lp.fecha_planeada,mq.desc_maquina,es.desc_estado 
FROM lotes_planeados AS lp
INNER JOIN tipos_proceso AS tp ON lp.id_tipo_proceso = tp.id_tipo_proceso
INNER JOIN maquinas AS mq ON lp.id_maquina = mq.id_maquina
INNER JOIN estados AS es ON lp.id_estado = es.id_estado;

SELECT lp.id_orden_produccion,pd.id_pedido,lp.desc_tipo_proceso,cantidad_planeada,fecha_planeada,desc_estado FROM todos_lotes_planeados AS lp
INNER JOIN (SELECT id_orden_trabajo,id_orden_produccion FROM todas_ordenes_produccion) AS op ON op.id_orden_produccion = lp.id_orden_produccion
INNER JOIN (SELECT id_pedido,id_orden_trabajo FROM todos_pedidos) AS pd ON pd.id_pedido = op.id_orden_trabajo WHERE desc_estado = 'ABIERTO';

SELECT 
lp.desc_tipo_proceso,cantidad_planeada,fecha_planeada FROM todos_lotes_planeados AS lp 
INNER JOIN (SELECT id_orden_trabajo,id_orden_produccion FROM todas_ordenes_produccion) AS op 
ON op.id_orden_produccion = lp.id_orden_produccion INNER JOIN (SELECT id_pedido,id_orden_trabajo FROM todos_pedidos) 
 AS pd ON pd.id_pedido = op.id_orden_trabajo WHERE desc_estado = 'ABIERTO' AND lp.id_orden_produccion = 1 AND pd.id_pedido = 1;

#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################
								#ENTRADA DE MATERIALES
#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################

CREATE VIEW resultados_inspeccion_propieades
AS
SELECT id_inspeccion_entradas,id_entrada_material,desc_resultado_inspeccion,desc_tipo_inspeccion FROM inspeccion_entradas AS ie 
INNER JOIN resultados_inspeccion AS ri ON ie.id_resultado_inspeccion = ri.id_resultado_inspeccion
INNER JOIN tipos_inspeccion AS ti ON ti.id_tipo_inspeccion = ie.id_tipo_inspeccion;


CREATE VIEW resultados_inspeccion_dimenciones
AS
SELECT id_inspeccion_dimencion,id_entrada_material,resultado_inspeccion,desc_tipo_inspeccion FROM inspeccion_dimenciones AS isd 
INNER JOIN tipos_inspeccion AS ti ON isd.id_tipo_inspeccion = ti.id_tipo_inspeccion;
#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################
								#ALMACEN
#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################
#######################################################################################################################################
CREATE VIEW productos_almacen
AS
SELECT pr.clave_producto,apt.id_almacen_producto_terminado,apt.total FROM
almacen_productos_terminados AS apt
INNER JOIN productos AS pr ON apt.id_producto = pr.id_producto;

#SELECCIONA TODAS LAS ENTRADAS Y SALIDAS DE MATERIAL
CREATE VIEW entradas_salidas
AS
SELECT res.id_registro_entrada_salida,apt.id_almacen_producto_terminado,toa.desc_tipo_operacion,res.fecha_registro,res.cantidad,res.total_registrado 
FROM registros_entradas_salidas AS res
JOIN almacen_productos_terminados AS apt ON res.id_almacen_producto_terminado = apt.id_almacen_producto_terminado
JOIN tipos_operaciones_almacenes AS toa ON res.id_tipo_operacion_almacen = toa.id_tipo_operacion_almacen;

CREATE VIEW ver_entradas_materiales
AS
SELECT em.id_entrada_material,amp.id_almacen_materia_prima,amp.cantidad_total,em.fecha_registro,CONCAT(mt.desc_tipo_material,' ',mt.desc_dimencion,' ',mt.clave_forma) AS desc_material,
pr.desc_proveedor,em.cantidad,em.codigo,em.certificado,em.orden_compra,em.inspector,es.desc_estado,em.comentarios,em.factura,em.desc_lote
FROM entradas_materiales AS em 
LEFT JOIN  almacen_materias_primas AS amp ON amp.id_entrada_material = em.id_entrada_material
INNER JOIN ver_materiales AS mt ON em.id_material = mt.id_material
INNER JOIN proveedores AS pr ON em.id_proveedor = em.id_proveedor
INNER JOIN estados AS es ON em.id_estado = em.id_estado
GROUP BY em.id_entrada_material;