USE control_piezas_2;

INSERT INTO clientes(nombre_cliente) VALUES('PLASTONIUM');
INSERT INTO clientes(nombre_cliente) VALUES('EATON');
INSERT INTO clientes(nombre_cliente) VALUES('ARROW HART');
INSERT INTO clientes(nombre_cliente) VALUES('COOPER');
INSERT INTO clientes(nombre_cliente) VALUES('PLASCO');

INSERT INTO contactos(id_cliente,desc_contacto) VALUES(1,'Jazmin Maldonado');
INSERT INTO contactos(id_cliente,desc_contacto) VALUES(2,'Martha Figueroa');

insert into productos(clave_producto) values('331A2452P7');
insert into productos(clave_producto) values('331A2452P8');
insert into productos(clave_producto) values('331A2452P9');
insert into productos(clave_producto) values('331A2452P11');
insert into productos(clave_producto) values('331A2452P12');
insert into productos(clave_producto) values('192A7923P23');
insert into productos(clave_producto) values('192A7923P30');
insert into productos(clave_producto) values('315A7143P1');
insert into productos(clave_producto) values('315A7143P2');
insert into productos(clave_producto) values('R6032P5');
insert into productos(clave_producto) values('4069QST');
insert into productos(clave_producto) values('954810');
insert into productos(clave_producto) values('954820');
insert into productos(clave_producto) values('INSERT 8-32');
insert into productos(clave_producto) values('INSERT 4-40');
insert into productos(clave_producto) values('INSERT 10-32');
insert into productos(clave_producto) values('81492-8');
insert into productos(clave_producto) values('81492-40');
insert into productos(clave_producto) values('81492-9AF');
insert into productos(clave_producto) values('6611-8');
insert into productos(clave_producto) values('6613-8');
insert into productos(clave_producto) values('6471-8');
insert into productos(clave_producto) values('7900-7');
insert into productos(clave_producto) values('1547-169');
insert into productos(clave_producto) values('1547-170');
insert into productos(clave_producto) values('WD038841');
insert into productos(clave_producto) values('WD033025');
insert into productos(clave_producto) values('852B001');
insert into productos(clave_producto) values('564B001');
insert into productos(clave_producto) values('WD35082');
insert into productos(clave_producto) values('WD13101');
insert into productos(clave_producto) values('WD13102');
insert into productos(clave_producto) values('WD13105');
insert into productos(clave_producto) values('WD13106');
insert into productos(clave_producto) values('WD13107');
insert into productos(clave_producto) values('WD14101');
insert into productos(clave_producto) values('WD11705');
insert into productos(clave_producto) values('6369-208AH');
insert into productos(clave_producto) values('HL20444-3AF');
insert into productos(clave_producto) values('26414-32AF');
insert into productos(clave_producto) values('1895-16');
insert into productos(clave_producto) values('071-01-12');
insert into productos(clave_producto) values('XT71101-7');
insert into productos(clave_producto) values('1895-15AF');
insert into productos(clave_producto) values('AH7810EH');
insert into productos(clave_producto) values('XT3330-6AF');
insert into productos(clave_producto) values('02-41-5206');
insert into productos(clave_producto) values('02-41-5016');
insert into productos(clave_producto) values('1P2-0300');
insert into productos(clave_producto) values('1P2-0306');
insert into productos(clave_producto) values('1P2-0308');
insert into productos(clave_producto) values('1P2-0309');
insert into productos(clave_producto) values('1P2-0310');
insert into productos(clave_producto) values('1P2-0312');
insert into productos(clave_producto) values('1P2-0314');
insert into productos(clave_producto) values('1P2-0323');
insert into productos(clave_producto) values('1P2-0344');


insert into estados(desc_estado) VALUES('ABIERTO');
insert into estados(desc_estado) VALUES('CERRADO');
insert into estados(desc_estado) VALUES('PROCESANDO');
insert into estados(desc_estado) VALUES('CANCELADO');


insert into estados(desc_estado) VALUES('APROBADA');
insert into estados(desc_estado) VALUES('RECHAZADA');

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

INSERT INTO turnos(desc_turno) VALUES('M');
INSERT INTO turnos(desc_turno) VALUES('V');

INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,1);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,2);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,3);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,4);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,5);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(1,6);

INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,7);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,8);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,9);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,10);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,11);
INSERT INTO productos_cliente(id_cliente,id_producto) VALUES(2,12);

INSERT INTO tipos_operaciones_almacenes(desc_tipo_operacion) VALUES('ENTRADA');
INSERT INTO tipos_operaciones_almacenes(desc_tipo_operacion) VALUES('SALIDA');

INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(1);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(2);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(3);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(4);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(5);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(6);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(7);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(8);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(9);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(10);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(11);
INSERT INTO almacen_productos_terminados(id_producto_cliente) VALUES(12);

