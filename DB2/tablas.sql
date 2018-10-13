 DROP DATABASE IF EXISTS control_piezas_2;        

CREATE DATABASE control_piezas_2;    

USE control_piezas_2;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS (TABLAS FUERTES)*/
CREATE TABLE materiales(
id_material          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_material       VARCHAR (100) NOT NULL
);

CREATE TABLE productos(
id_producto        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_producto     VARCHAR (50) NOT NULL    
);

CREATE TABLE empaques(
id_empaque         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_empaque       VARCHAR (100) NOT NULL
);

CREATE TABLE tipos_proceso(
id_tipo_proceso         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_proceso      VARCHAR (50) NOT NULL    
);

CREATE TABLE defectos_producto(
id_defecto_producto         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_defecto_producto       VARCHAR (100) NOT NULL    
);

CREATE TABLE operadores(
id_operador         INT NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE maquinas(
id_maquina          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_maquina        VARCHAR (50) NOT NULL    
);

CREATE TABLE tipos_estado(
id_tipo_estado 				INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_estado		VARCHAR(100) NOT NULL
);

CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_tipo_estado		INT NOT NULL REFERENCES tipos_estado (id_tipo_estado),
desc_estados        VARCHAR (50) NOT NULL
);

CREATE TABLE turnos(
id_turno            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_turno          VARCHAR (20) NOT NULL    
);

CREATE TABLE clientes(
id_cliente          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL
);

CREATE TABLE contactos(
id_contacto 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente 		INT NOT NULL REFERENCES clientes(id_cliente),
desc_contacto	VARCHAR(50) NOT NULL
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/ 

/** TABLAS DEBILES*/
CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_recepcion				DATE NOT NULL,
no_orden_compra				VARCHAR(50) NOT NULL,
id_contacto					INT NOT NULL REFERENCES contactos (id_contacto),
id_estado					INT NOT NULL REFERENCES estados	(id_estado),
fecha_entrega				DATE,
fecha_confirmacion_entrega 	DATE
);

CREATE TABLE ordenes_trabajo(
id_orden_trabajo     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_pedido			INT NOT NULL REFERENCES pedidos(id_pedido),
id_estado			INT NOT NULL REFERENCES estados(id_estado),
fecha_inicio        DATETIME,
fecha_terminacion   DATETIME
);

CREATE TABLE ordenes_produccion(
id_orden_produccion     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_trabajo        INT NOT NULL REFERENCES ordenes_trabajo(id_orden_trabajo),
id_producto             INT NOT NULL REFERENCES productos(id_producto),
id_material             INT REFERENCES materiales(id_material),
id_estado				INT	NOT NULL REFERENCES estados(id_estado),
cantidad_cliente        INT NOT NULL,
worker					FLOAT,
cantidad_total          INT ,
barras_necesarias       FLOAT,
turnos_necesarios       INT,
turnos_reales           INT,
fecha_registro          DATE NOT NULL,
fecha_montaje           DATE,
fecha_desmontaje        DATE,
fecha_inicio            DATE,
fecha_fin               DATE,
observaciones			VARCHAR(250)      
);

CREATE TABLE procesos_produccion(
id_proceso_produccion       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion         INT NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
id_tipo_proceso             INT NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
id_estado					INT	NOT NULL REFERENCES estados(id_estado),
fecha_inicio_proceso        DATETIME,
fecha_fin_proceso           DATETIME    
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proceso_produccion      INT NOT NULL REFERENCES procesos_produccion (id_proceso_produccion),
id_maquina                  INT NOT NULL REFERENCES maquinas (id_maquina),
desc_lote                   VARCHAR(50),
fecha_trabajo				DATETIME,
cantidad_operador           INT ,
cantidad_administrador      INT ,
scrap_operador              INT ,
scrap_administrador         INT ,
merma                       FLOAT,
tiempo_muerto               TIME
);

CREATE TABLE parcialidades(
id_parcialidad 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_pedido			INT NOT NULL REFERENCES pedidos (id_pedido),
fecha_entrega		DATETIME NOT NULL,
cantidad_entregada	INT NOT NULL
);
/** FIN TABLAS DEBILES 1*/

/** TABLAS RELACIONALES*/
CREATE TABLE defectos_lotes(
id_defecto_lote             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_defecto_producto         INT NOT NULL REFERENCES defectos_producto (id_defecto_producto),
id_lote_produccion          INT NOT NULL REFERENCES lotes_produccion (id_lote_produccion),    
observaciones               VARCHAR (250) NOT NULL
);

CREATE TABLE maquinas_operadores(
id_maquina_operador         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_maquina                  INT NOT NULL REFERENCES maquinas (id_maquina),
id_operador                 INT NOT NULL REFERENCES operadores (id_operador)
);

CREATE TABLE turnos_operadores(
id_turno_operador           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_turno                    INT NOT NULL REFERENCES turnos (id_turno),
id_operador		            INT NOT NULL REFERENCES operadores (id_operador)
);

CREATE TABLE empaques_ordenes_p(
id_empaques_ordenes_p 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion			INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_empaque					INT NOT NULL REFERENCES empaques (id_empaque),
cantidad					INTEGER NOT NULL
);
/**FIN TABLAS RELACIONALES*/
