USE control_piezas_2;

INSERT INTO clientes(nombre_cliente) VALUES('PLASTONIUM');
INSERT INTO clientes(nombre_cliente) VALUES('EATON');
INSERT INTO clientes(nombre_cliente) VALUES('ARROW HART');

INSERT INTO contactos(id_cliente,desc_contacto) VALUES(1,'Jazmin Maldonado');
INSERT INTO contactos(id_cliente,desc_contacto) VALUES(2,'Martha Figueroa');

insert into productos(clave_producto) values('6613-8');
insert into productos(clave_producto) values('81432-8');	
insert into productos(clave_producto) values('331A2452P7');	

INSERT INTO tipos_estado(desc_tipo_estado) VALUES('PEDIDOS');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('ORDENES DE TRABAJO');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('ORDENES DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('PROCESOS DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('LOTES DE PRODUCCION');
INSERT INTO tipos_estado(desc_tipo_estado) VALUES('REQUISICIONES');
insert into estados(id_tipo_estado,desc_estados) VALUES(1,'ABIERTO');
insert into estados(id_tipo_estado,desc_estados) VALUES(1,'CERRADO');

insert into estados(id_tipo_estado,desc_estados) VALUES(2,'ABIERTO');
insert into estados(id_tipo_estado,desc_estados) VALUES(2,'CERRADO');

insert into estados(id_tipo_estado,desc_estados) VALUES(3,'ABIERTO');
insert into estados(id_tipo_estado,desc_estados) VALUES(3,'CERRADO');

insert into estados(id_tipo_estado,desc_estados) VALUES(4,'PLANEACION');
INSERT INTO estados(id_tipo_estado,desc_estados) VALUES(4,'EN ESPERA');
INSERT INTO estados(id_tipo_estado,desc_estados) VALUES(4,'PRODUCCION');

INSERT INTO estados(id_tipo_estado,desc_estados) VALUES(6,'ABIERTO');
INSERT INTO estados(id_tipo_estado,desc_estados) VALUES(6,'CERRADO');
INSERT INTO estados(id_tipo_estado,desc_estados) VALUES(6,'CANCELADA');

INSERT INTO tipos_proceso(desc_tipo_proceso) VALUE('MAQUINADO');

INSERT INTO maquinas(desc_maquina) VALUES('maquina1');
INSERT INTO maquinas(desc_maquina) VALUES('maquina2');

INSERT INTO materiales(desc_material) VALUES('MATERIAL 1');

INSERT INTO proveedores(desc_proveedor,direccion) VALUES('PROVEEDOR1','DIRECCION1');
INSERT INTO proveedores(desc_proveedor,direccion) VALUES('PROVEEDOR2','DIRECCION2');
INSERT INTO proveedores(desc_proveedor,direccion) VALUES('PROVEEDOR3','DIRECCION3');


