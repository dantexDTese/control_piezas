CREATE DATABASE control_piezas;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS*/
CREATE TABLE tipos_usuario(
id_tipo_usuario         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_usuario       INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE clientes(
id_Cliente          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL,
direccion           VARCHAR (20) NOT NULL
);

CREATE TABLE materiales(
id_materia          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_material       VARCHAR (100) NOT NULL
);

CREATE TABLE productos(
id_producto        INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_producto     VARCHAR (50) NOT NULL    
);


CREATE TABLE empaques(
id_empaque         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_empaque       VARCHAR (100) NOT NULL
);

CREATE TABLE tipo_proceso(
id_tipo_proceso         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_proceeso      VARCHAR (50) NOT NULL    
);

CREATE TABLE defectos_producto(
id_defecto_producto         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_defecto_producto       VARCHAR (100) NOT NULL    
);

CREATE TABLE operador(
id_operador         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE maquina(
id_maquina          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_maquina        VARCHAR (50) NOT NULL    
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/