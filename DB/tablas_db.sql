CREATE DATABASE control_piezas;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS (TABLAS FUERTES)*/
CREATE TABLE tipos_usuario(
id_tipo_usuario         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_usuario       INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE clientes(
id_cliente          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL,
direccion           VARCHAR (20) NOT NULL
);

CREATE TABLE materiales(
id_material          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
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

CREATE TABLE tipos_proceso(
id_tipo_proceso         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_proceeso      VARCHAR (50) NOT NULL    
);

CREATE TABLE defectos_producto(
id_defecto_producto         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_defecto_producto       VARCHAR (100) NOT NULL    
);

CREATE TABLE operadores(
id_operador         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE maquinas(
id_maquina          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_maquina        VARCHAR (50) NOT NULL    
);

CREATE TABLE estados(
id_estado           INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_estados        VARCHAR (50) NOT NULL
);

CREATE TABLE turnos(
id_turno            INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_turno          VARCHAR (20) NOT NULL    
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/ 

/** TABLAS DEBILES*/
CREATE TABLE ordenes_compra(
id_orden_compra     INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente          INTEGER NOT NULL REFERENCES clientes(id_cliente),
fecha_peticion      DATETIME NOT NULL,
fecha_inicio        DATETIME,
fecha_terminacion   DATETIME
);

CREATE TABLE ordenes_produccion(
id_orden_produccion     INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_compra         INTEGER NOT NULL REFERENCES ordenes_compra(id_orden_compra),
id_empaque              INTEGER NOT NULL REFERENCES empaques(id_empaque),
id_producto             INTEGER NOT NULL REFERENCES productos(id_producto),
cantidad_total          INTEGER NOT NULL,
cantidad_cliente        INTEGER NOT NULL,
barras_necesarias       FLOAT,
turnos_necesarios       INTEGER,
turnos_reales           INTEGER,
fecha_registro          DATETIME NOT NULL,
fecha_inicio            DATETIME,
fecha_fin               DATETIME      
);

CREATE TABLE procesos_produccion(
id_proceso_produccion       INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion         INTEGER NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
id_tipo_proceso             INTEGER NOT NULL REFERENCES tipos_proceso(id_tipo_proceso),
fecha_inicio_proceso        DATETIME NOT NULL,
fecha_fin_proceso           DATETIME    
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_proceeso_produccion      INTEGER NOT NULL REFERENCES procesos_produccion (id_proceso_produccion),
id_maquina                  INTEGER NOT NULL REFERENCES maquinas (id_maquina),
desc_lote                   VARCHAR(50) NOT NULL,
cantidad_operador           INTEGER NOT NULL,
cantidad_administrador      INTEGER NOT NULL,
scrap_operador              INTEGER NOT NULL,
scrap_administrador         INTEGER NOT NULL
merma                       FLOAT NOT NULL,
tiempo_muerto               TIME NOT NULL
);
/** FIN TABLAS DEBILES 1*/

/** TABLAS RELACIONALES*/

CREATE TABLE estados_ordenes_p(
id_estado_orden_p             INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado                     INTEGER NOT NULL REFERENCES estados (id_estado),
id_orden_produccion           INTEGER NOT NULL REFERENCES ordenes_produccion (id_orden_produccion),
fecha_hora_asignado           DATETIME
);


CREATE TABLE materiales_productos(
id_material_producto        INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material                 INTEGER NOT NULL REFERENCES materiales (id_material),
id_producto                 INTEGER NOT NULL REFERENCES productos  (id_producto)
);

CREATE TABLE defectos_lotes(
id_defecto_lote             INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_defecto_producto         INTEGER NOT NULL REFERENCES defectos_producto (id_defecto_producto),
id_lote_produccion          INTEGER NOT NULL REFERENCES lotes_produccion (id_lote_produccion),    
observaciones               VARCHAR (250) NOT NULL
);

CREATE TABLE maquinas_operadores(
id_maquina_operador         INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_maquina                  INTEGER NOT NULL REFERENCES maquinas (id_maquina),
id_operador                 INTEGER NOT NULL REFERENCES operadores (id_operador)
);

CREATE TABLE turnos_operadores(
id_turno_operador           INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_turno                    INTEGER NOT NULL REFERENCES turnos (id_turno),
id_turno_operador           INTEGER NOT NULL REFERENCES operadores (id_operador)
);
/**FIN TABLAS RELACIONALES*/

