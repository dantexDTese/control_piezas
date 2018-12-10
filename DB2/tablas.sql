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
desc_estado         VARCHAR (50) NOT NULL
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

CREATE TABLE proveedores(
id_proveedor 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_proveedor	VARCHAR(150),
direccion		VARCHAR(150),
IVA				FLOAT
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/

/** TABLAS DEBILES*/
CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado					INT NOT NULL REFERENCES estados	(id_estado),
id_contacto					INT NOT NULL REFERENCES contactos (id_contacto),
fecha_recepcion				DATE NOT NULL,
no_orden_compra				VARCHAR(50) NOT NULL,
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
id_estado				INT	NOT NULL REFERENCES estados(id_estado),
id_empaque				INT REFERENCES empaques(id_empaque),
cantidad_cliente        INT NOT NULL,
cantidad_total          INT,
worker					FLOAT,
piezas_por_turno		INT,
turnos_necesarios       INT,
turnos_reales           INT,
fecha_registro          DATE NOT NULL,
fecha_montaje           DATE,
fecha_desmontaje        DATE,
fecha_inicio            DATE,
fecha_fin               DATE,
observaciones			VARCHAR(250)      
);

CREATE TABLE lotes_planeados(
id_lote_planeado 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion 	INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_tipo_proceso			INT NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
cantidad_planeada		INT NOT NULL,
fecha_planeada			DATE ,
id_maquina				INT NOT NULL REFERENCES maquinas(id_maquina)
);

CREATE TABLE procesos_produccion(
id_proceso_produccion       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_lote_planeado			INT NOT NULL REFERENCES lotes_planeados(id_lote_planeado),
id_estado					INT	NOT NULL REFERENCES estados(id_estado),
fecha_inicio_proceso        DATE,
fecha_fin_proceso           DATE    
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proceso_produccion      INT NOT NULL REFERENCES procesos_produccion (id_proceso_produccion),
desc_lote                   VARCHAR(50),
fecha_trabajo				DATETIME,
cantidad_operador           INT ,
cantidad_administrador      INT ,
scrap_operador              INT ,
scrap_administrador         INT ,
merma                       FLOAT,
tiempo_muerto               TIME
); 

CREATE TABLE requisiciones(
id_requisicion 					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proveedor					INT REFERENCES proveedores(id_proveedor),
fecha_creacion					DATE,
fecha_cerrada					DATE,
solicitante						VARCHAR(50) NOT NULL,
terminos						VARCHAR(100),
lugar_entrega					VARCHAR(100),
comentarios						VARCHAR(255),
uso_material					VARCHAR(255) NOT NULL,
sub_total						FLOAT,
IVA								FLOAT,
TOTAL							FLOAT
);

CREATE TABLE materiales_requeridos(
id_material_requerido   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material             INT NOT NULL REFERENCES materiales(id_material),
id_orden_trabajo		INT NOT NULL REFERENCES ordenes_trabajo(id_orden_trabajo),
cantidad_total          FLOAT DEFAULT 0
);

CREATE TABLE materiales_orden(
id_material_orden			 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion				INT REFERENCES ordenes_produccion(id_orden_produccion),
id_material_requerido           INT REFERENCES materiales_requeridos(id_material_requerido),
barras_necesarias				FLOAT DEFAULT 0
);

CREATE TABLE materiales_solicitados(
id_material_solicitado		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_requisicion				INT NOT NULL REFERENCES requisiciones(id_requisicion),
no_partida					INT NOT NULL,
id_material					INT NOT NULL REFERENCES materiales(id_material),
cantidad					INT NOT NULL,
unidad						VARCHAR(10) DEFAULT 'PZ',
parcialidad					INT NOT NULL,
fecha_solicitud				DATE NOT NULL,
fecha_entrega				DATE,
cuenta_cargo				VARCHAR(10),
precio_total				FLOAT,
id_estado					INT NOT NULL REFERENCES estados(id_estado)	
);


CREATE TABLE materiales_orden_requisicion(
id_material_orden_requisicion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material_solicitado				INT NOT NULL REFERENCES	materiales_solicitados(id_material_solicitado),
id_material_orden					INT NOT NULL REFERENCES materiales_orden(id_material_orden),
cantidad							INT NOT NULL
);
/**FIN TABLAS DEBILES 1*/

/**TABLAS RELACIONALES*/
CREATE TABLE defectos_lotes(
id_defecto_lote             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_defecto_producto         INT NOT NULL REFERENCES defectos_producto (id_defecto_producto),
id_lote_produccion          INT NOT NULL REFERENCES lotes_produccion (id_lote_produccion),    
observaciones               VARCHAR (250) NOT NULL
);

CREATE TABLE empaques_ordenes_p(
id_empaques_ordenes_p 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion			INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_empaque					INT NOT NULL REFERENCES empaques (id_empaque),
cantidad					INTEGER NOT NULL
);

CREATE TABLE materiales_proveedor(
id_material_proveedor		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material					INT NOT NULL REFERENCES materiales(id_material),
id_proveedor				INT NOT NULL REFERENCES proveedores(id_proveedor),
precio_unitario				FLOAT
);

CREATE TABLE productos_material(
id_producto_material 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material 			INT NOT NULL REFERENCES materiales(id_material),
id_producto				INT NOT NULL REFERENCES productos(id_producto),
piezas_por_turno		INT NOT NULL,
piezas_por_barra		INT NOT NULL
);
/**FIN TABLAS RELACIONALES*/

