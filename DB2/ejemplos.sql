use control_piezas_2;

select * from productos;
select * from maquinas;
select * from clientes;
select * from contactos;
select * from estados;
select * from pedidos;
select * from ordenes_trabajo;
select * from ordenes_produccion;
select * from procesos_produccion;
select * from bitacoraPedidos;
select * from procesos_produccion;
select * from tipos_proceso;
select * from lotes_produccion;
drop database control_piezas_2;


INSERT INTO lotes_produccion(id_proceso_produccion,id_maquina) VALUES(1,1);
INSERT INTO lotes_produccion(id_proceso_produccion,id_maquina) VALUES(1,1);