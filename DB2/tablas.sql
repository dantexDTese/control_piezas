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


CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
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

CREATE TABLE turnos(
id_turno 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_turno		VARCHAR(2) NOT NULL
);

CREATE TABLE tipos_inspeccion(
id_tipo_inspeccion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_inspeccion VARCHAR(150)
);

CREATE TABLE resultados_inspeccion(
id_resultado_inspeccion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_resultado_inspeccion		VARCHAR(5)
);

CREATE TABLE tipos_operaciones_almacenes(
id_tipo_operacion_almacen			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_operacion					VARCHAR(20)  CHECK (desc_tipo_operacion	 = 'ENTRADA' OR desc_tipo_operacion = 'SALIDA')
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/

/** TABLAS DEBILES*/
CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado					INT NOT NULL REFERENCES estados	(id_estado)	,
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
barras_necesarias				FLOAT DEFAULT 0.0
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
cantidad							INT NOT NULL DEFAULT 0
);

CREATE TABLE lotes_planeados(
id_lote_planeado 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion 	INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_tipo_proceso			INT NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
cantidad_planeada		INT NOT NULL,
fecha_planeada			DATE ,
id_maquina				INT NOT NULL REFERENCES maquinas(id_maquina),
id_estado				INT NOT NULL REFERENCES estados(id_estado)
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_lote_planeado			INT NOT NULL REFERENCES lotes_planeados(id_lote_planeado),
desc_lote                   VARCHAR(50),
fecha_trabajo				DATETIME,
cantidad_operador           INT ,
cantidad_administrador      INT ,
scrap_operador              INT ,
scrap_administrador         INT ,
merma                       FLOAT,
tiempo_muerto               TIME,
turno						INT NOT NULL REFERENCES turnos(id_turno)
); 

CREATE TABLE entradas_materiales(
id_entrada_material 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_registro			DATETIME NOT NULL,
id_material				INT NOT NULL REFERENCES materiales(id_material),
id_proveedor			INT NOT NULL REFERENCES proveedores(id_proveedor),
cantidad				INT NOT NULL,
codigo					VARCHAR(50) NOT NULL,
certificado				VARCHAR(50) NOT NULL,
orden_compra			VARCHAR(50) NOT NULL,
inspector				VARCHAR(50) NOT NULL,
id_estado				INT NOT NULL REFERENCES estados(id_estado),
comentarios				VARCHAR(300),
factura					VARCHAR(255),
no_parte				VARCHAR(255)
);

CREATE TABLE inventarios(
id_inventario 			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_inventario		DATE NOT NULL,
persona_responsable		VARCHAR(255)
);

CREATE TABLE parcialidades_entrega(
id_parcialidad_entrega			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_registro_entrada_salida		INT NOT NULL REFERENCES registros_entradas_salidas(id_registro_entrada_salida),
id_orden_produccion				INT NOT NULL REFERENCES	ordenes_produccion(id_orden_produccion)
);

CREATE TABLE parcialidades_pedido(
id_parcialidad_pedido		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_parcialidad			DATE,
id_pedido					INT NOT NULL REFERENCES pedidos(id_pedido),
fecha_entrega				DATE
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

CREATE TABLE inspeccion_entradas(
id_inspeccion_entradas 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_entrada_material			INT NOT NULL REFERENCES entradas_materiales(id_entrada_material),
id_tipo_inspeccion			INT NOT NULL REFERENCES tipos_inspeccion(id_tipo_inspeccion),
id_resultado_inspeccion		INT NOT NULL REFERENCES resultados_inspeccion(id_resultado_inspeccion)	
);

CREATE TABLE inspeccion_dimenciones(
id_inspeccion_dimencion			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_tipo_inspeccion				INT NOT NULL REFERENCES tipos_inspeccion(id_tipo_inspeccion),
id_entrada_material				INT NOT NULL REFERENCES entradas_materiales(id_entrada_material),
resultado_inspeccion			FLOAT NOT NULL
);

CREATE TABLE productos_cliente(
id_producto_cliente 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente				INT NOT NULL REFERENCES clientes(id_cliente),
id_producto				INT NOT NULL REFERENCES productos(id_producto)
);

CREATE TABLE almacen_productos_terminados(
id_almacen_producto_terminado 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_producto_cliente					INT NOT NULL REFERENCES productos_cliente(id_producto_cliente),
total								INT NOT NULL DEFAULT 0
);

CREATE TABLE registros_entradas_salidas(
id_registro_entrada_salida 			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_almacen_producto_terminado		INT NOT NULL REFERENCES almacen_productos_terminados(id_almacen_producto_terminado),
id_tipo_operacion_almacen			INT NOT NULL REFERENCES tipos_operaciones_almacenes(id_tipo_operacion_almacen),
fecha_registro						DATE NOT NULL,
cantidad							INT NOT NULL,
total_registrado					INT NOT NULL
);


CREATE TABLE productos_inventario(
id_producto_inventario 			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_inventario					INT NOT NULL REFERENCES inventarios(id_inventario),
id_producto						INT NOT NULL REFERENCES productos(id_producto),
cantidad						INT NOT NULL
);

CREATE TABLE parcialidades_entrega(
id_parcialidad_entrega			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion				INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_registro_entrada_salida		INT NOT NULL REFERENCES registros_entradas_salidas(id_registro_entrada_salida),
id_parcialidad_pedido			INT NOT NULL REFERENCES parcialides_pedido(id_parcialidad_pedido)
);
/**FIN TABLAS RELACIONALES*/



