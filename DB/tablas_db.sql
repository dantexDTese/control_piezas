CREATE DATABASE control_piezas;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS (TABLAS FUERTES)*/
USE control_piezas;

CREATE TABLE tipos_usuario(
id_tipo_usuario         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_usuario       VARCHAR(20)
);

CREATE TABLE clientes(
id_cliente          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL,
direccion           VARCHAR (20) NOT NULL
);

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
desc_tipo_proceeso      VARCHAR (50) NOT NULL    
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

CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_estados        VARCHAR (50) NOT NULL
);

CREATE TABLE turnos(
id_turno            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_turno          VARCHAR (20) NOT NULL    
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/ 

/** TABLAS DEBILES*/
CREATE TABLE ordenes_compra(
id_orden_compra     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente          INT NOT NULL REFERENCES clientes(id_cliente),
fecha_peticion      DATETIME NOT NULL,
fecha_inicio        DATETIME,
fecha_terminacion   DATETIME
);

CREATE TABLE ordenes_produccion(
id_orden_produccion     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_compra         INT NOT NULL REFERENCES ordenes_compra(id_orden_compra),
id_empaque              INT NOT NULL REFERENCES empaques(id_empaque),
id_producto             INT NOT NULL REFERENCES productos(id_producto),
cantidad_total          INT NOT NULL,
cantidad_cliente        INT NOT NULL,
barras_necesarias       FLOAT,
turnos_necesarios       INT,
turnos_reales           INT,
fecha_registro          DATETIME NOT NULL,
fecha_inicio            DATETIME,
fecha_fin               DATETIME      
);

CREATE TABLE procesos_produccion(
id_proceso_produccion       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion         INT NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
id_tipo_proceso             INT NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
fecha_inicio_proceso        DATETIME NOT NULL,
fecha_fin_proceso           DATETIME    
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proceeso_produccion      INT NOT NULL REFERENCES procesos_produccion (id_proceso_produccion),
id_maquina                  INT NOT NULL REFERENCES maquinas (id_maquina),
desc_lote                   VARCHAR(50) NOT NULL,
cantidad_operador           INT NOT NULL,
cantidad_administrador      INT NOT NULL,
scrap_operador              INT NOT NULL,
scrap_administrador         INT NOT NULL,
merma                       FLOAT NOT NULL,
tiempo_muerto               TIME NOT NULL
);
/** FIN TABLAS DEBILES 1*/

/** TABLAS RELACIONALES*/

CREATE TABLE estados_ordenes_p(
id_estado_orden_p             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado                     INT NOT NULL REFERENCES estados (id_estado),
id_orden_produccion           INT NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
fecha_hora_asignado           DATETIME
);


CREATE TABLE materiales_productos(
id_material_producto        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material                 INT NOT NULL REFERENCES materiales (id_material),
id_producto                 INT NOT NULL REFERENCES productos  (id_producto)
);

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
/**FIN TABLAS RELACIONALES*/

