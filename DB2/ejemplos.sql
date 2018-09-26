use control_piezas_2;

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
select * from lotes_produccion;
drop database control_piezas_2;


select * from ordenes_produccion AS op JOIN productos AS pr ON op.id_producto = pr.id_producto
JOIN procesos_produccion AS pp ON pp.id_orden_produccion = op.id_orden_produccion
JOIN estados AS st ON st.id_estado = pp.id_estado
JOIN tipos_proceso AS tp ON tp.id_tipo_proceso = pp.id_tipo_proceso 
JOIN lotes_produccion AS lp ON lp.id_proceso_produccion = pp.id_proceso_produccion
JOIN maquinas AS mq ON mq.id_maquina = lp.id_maquina
WHERE op.id_orden_produccion = 1 AND st.desc_estados = 'EN PROCESOS';
;



select * from estados;