USE control_piezas_2;

INSERT INTO clientes(nombre_cliente) VALUES('PLASTONIUM');
INSERT INTO clientes(nombre_cliente) VALUES('EATON');
INSERT INTO clientes(nombre_cliente) VALUES('ARROW HART');

INSERT INTO contactos(id_cliente,desc_contacto) VALUES(1,'Jazmin Maldonado');
INSERT INTO contactos(id_cliente,desc_contacto) VALUES(2,'Martha Figueroa');

insert into productos(clave_producto) values('6613-8');
insert into productos(clave_producto) values('81432-8');	
insert into productos(clave_producto) values('331A2452P7');	
	
SELECT * FROM ordenes_trabajo;
SELECT * FROM estados;

/*INSERT INTO tipos_estado(desc_tipo_estado) VALUES('PEDIDOS');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('ORDENES DE TRABAJO');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('ORDENES DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('PROCESOS DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('LOTES DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('REQUISICIONES');
*/

insert into estados(desc_estado) VALUES('ABIERTO');
insert into estados(desc_estado) VALUES('CERRADO');
insert into estados(desc_estado) VALUES('PROCESANDO');
insert into estados(desc_estado) VALUES('CANCELADO');

	
INSERT INTO tipos_proceso(desc_tipo_proceso) VALUE('MAQUINADO');

INSERT INTO maquinas(desc_maquina) VALUES('maquina1');
INSERT INTO maquinas(desc_maquina) VALUES('maquina2');

INSERT INTO materiales(desc_material) VALUES('MATERIAL 1');
INSERT INTO materiales(desc_material) VALUES('MATERIAL 2');

INSERT INTO proveedores(desc_proveedor,direccion,IVA) VALUES('PROVEEDOR1','DIRECCION1',10);
INSERT INTO proveedores(desc_proveedor,direccion,IVA) VALUES('PROVEEDOR2','DIRECCION2',5);
INSERT INTO proveedores(desc_proveedor,direccion,IVA) VALUES('PROVEEDOR3','DIRECCION3',20);


INSERT INTO materiales_proveedor(id_material,id_proveedor,precio_unitario) VALUES(1,1,20);
INSERT INTO materiales_proveedor(id_material,id_proveedor,precio_unitario) VALUES(1,2,30);
INSERT INTO materiales_proveedor(id_material,id_proveedor,precio_unitario) VALUES(1,3,40);

INSERT INTO productos_material(id_material,id_producto,piezas_por_turno,piezas_por_barra) VALUES(1,1,100,20);
INSERT INTO productos_material(id_material,id_producto,piezas_por_turno,piezas_por_barra) VALUES(1,2,50,20);



INSERT INTO productos_material(id_material,id_producto,piezas_por_turno,piezas_por_barra) VALUES(2,1,100,20);
INSERT INTO productos_material(id_material,id_producto,piezas_por_turno,piezas_por_barra) VALUES(2,2,50,20);


/*
SELECT piezas_por_turno FROM productos_material WHERE
id_material = (SELECT id_material FROM materiales WHERE desc_material = 'MATERIAL 1') 
AND id_producto = (SELECT id_producto FROM productos WHERE clave_producto = '6613-8');

select op.id_orden_produccion,ro.clave_producto,ro.cantidad_total,ro.desc_material,op.fecha_inicio 
FROM requisicion_ordenes AS ro JOIN ordenes_produccion AS op ON
op.id_orden_produccion = ro.id_orden_produccion WHERE ro.id_orden_trabajo =1;


select id_orden_trabajo AS num_pedido,no_orden_compra FROM requisicion_ordenes GROUP BY num_pedido;

select ro.id_orden_produccion,ro.clave_producto,ro.cantidad_total,ro.desc_material,ro.barras_necesarias,op.fecha_inicio FROM requisicion_ordenes AS ro 
JOIN ordenes_produccion AS op ON op.id_orden_produccion = ro.id_orden_produccion WHERE ro.id_orden_trabajo = 1;
 
select * from requisicion_ordenes;
*/