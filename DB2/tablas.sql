CREATE DATABASE control_piezas_2;

USE control_piezas_2;

CREATE TABLE clientes(
id_cliente          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL
);


CREATE TABLE productos(
id_producto        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_producto     VARCHAR (50) NOT NULL    
);

CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_estados        VARCHAR (50) NOT NULL
);

CREATE TABLE contactos(
id_contacto 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente 		INT NOT NULL REFERENCES clientes(id_cliente),
desc_contacto	VARCHAR(50) NOT NULL
);


CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_recepcion				DATE NOT NULL,
no_orden					VARCHAR(50) NOT NULL,
id_contacto					INT NOT NULL REFERENCES contactos (id_contacto),
id_estado					INT NOT NULL REFERENCES estados	(id_estado),
fecha_entrega				DATE,
fecha_confirmacion_entrega 	DATE
);

CREATE TABLE parcialidades_pedidos(
id_parcialidad_pedido		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_entrega_parcialidad	DATE
);

CREATE TABLE productos_pedidos(
id_producto_pedido		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_producto				INT NOT NULL REFERENCES productos (id_producto),
id_pedido				INT NOT NULL REFERENCES pedidos	(id_pedido),
cantidad				INT NOT NULL
);

