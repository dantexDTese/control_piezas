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
desc_estados        VARCHAR (50) NOT NULL
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
fecha_terminacion   DATETIME,
observaciones		VARCHAR(255)
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
id_lote_planeado 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
cantidad_planeada	INT NOT NULL,
fecha_planeada		DATE 
);

CREATE TABLE procesos_produccion(
id_proceso_produccion       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion         INT NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
id_tipo_proceso             INT NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
id_maquina                  INT REFERENCES maquinas (id_maquina),
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
id_requisicion 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_trabajo    INT NOT NULL REFERENCES ordenes_trabajo(id_orden_trabajo),
fecha_creacion		DATE,
fecha_cerrarda		DATE
);


CREATE TABLE materiales_ordenes_requeridas(
id_material_orden_requerida 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material_requerido           INT REFERENCES materiales_requeridos(id_material_requerido),
id_orden_produccion				INT REFERENCES ordenes_produccion(id_orden_produccion),
barras_necesarias				INT DEFAULT 0
);

CREATE TABLE materiales_requeridos(
id_material_requerido   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material             INT NOT NULL REFERENCES materiales(id_material),
id_estado               INT NOT NULL REFERENCES estados(id_estado),
id_requisicion          INT NOT NULL REFERENCES requisiciones(id_requisicion),
cantidad_total          INT DEFAULT 0
);

CREATE TABLE parcialidades_requisicion(
id_parcialidad_requisicion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proveedor					INT REFERENCES proveedores(id_proveedor),
id_requisicion					INT REFERENCES requisiciones(id_requisicion),
solicitante						VARCHAR(50),
fecha_solicitud					DATE,
terminos						VARCHAR(100),
lugar_entrega					VARCHAR(100),
comentarios						VARCHAR(255),
sub_total						FLOAT,
IVA								FLOAT,
TOTAL							FLOAT
);


CREATE TABLE parcialidades_orden_requerida(
id_parcialidad_orden_requerida		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_parcialidad_requisicion			INT NOT NULL REFERENCES parcialidades_requisicion(id_parcialidad_requisicion),
id_material_requerido      			INT NOT NULL REFERENCES materiales_requeridos(id_material_requerido),
cantidad							INT NOT NULL DEFAULT 0,
num_parcialidad						INT NOT NULL DEFAULT 0,
fecha_solicitud						DATE,
fecha_entrega						DATE,
unidad								VARCHAR(10),
precio_total							FLOAT DEFAULT 0
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
/**FIN TABLAS RELACIONALES*/


