DROP DATABASE IF EXISTS control_piezas;

CREATE DATABASE control_piezas;    

USE control_piezas;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS (TABLAS FUERTES)*/

CREATE TABLE dimenciones(
id_dimencion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_dimencion		VARCHAR(10) NOT NULL
);

CREATE TABLE tipos_material(	
id_tipo_material		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_material		VARCHAR(100)
);

CREATE TABLE formas(
id_forma		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_forma		VARCHAR(5) NOT NULL,
desc_forma		VARCHAR(20) NOT NULL
);

CREATE TABLE materiales(
id_material          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_dimencion		 INT NOT NULL REFERENCES dimenciones(id_dimencion),
id_tipo_material	 INT NOT NULL REFERENCES tipos_material(id_tipo_material),
id_forma			 INT NOT NULL REFERENCES formas(id_forma),
longitud_barra		 FLOAT NOT NULL
);

DELIMITER //
CREATE PROCEDURE agregar_material(
IN desc_material		VARCHAR(50),
IN dimencion			VARCHAR(20),
IN forma				VARCHAR(20),
IN longitud_barra		FLOAT,
INOUT respuesta			VARCHAR(255)
)BEGIN
	DECLARE id_tipo_material INT;
    DECLARE id_dimencion	 INT;
    DECLARE id_forma 		 INT;
	
		SET id_tipo_material = (SELECT tm.id_tipo_material FROM tipos_material AS tm WHERE tm.desc_tipo_material = desc_material);
		SET id_dimencion = (SELECT dm.id_dimencion FROM dimenciones AS dm WHERE dm.desc_dimencion = dimencion);
		SET id_forma = (SELECT fm.id_forma FROM formas AS fm WHERE fm.clave_forma = forma);
		
        IF id_forma IS NULL THEN
            SET id_forma = (SELECT fm.id_forma FROM formas AS fm WHERE fm.desc_forma = forma);
			SET forma = (SELECT fm.clave_forma FROM formas AS fm WHERE fm.id_forma = id_forma);
        END IF;
        
        IF EXISTS(SELECT * FROM ver_materiales AS vm WHERE CONCAT(vm.desc_tipo_material," ",vm.desc_dimencion," ",vm.clave_forma) = CONCAT(desc_material," ",dimencion," ",forma)) THEN
        
			SET respuesta = 'ESTE MATERIAL YA EXISTE';
	
		ELSE
			IF id_tipo_material IS NOT NULL AND id_dimencion IS NOT NULL AND id_forma IS NOT NULL THEN
					
					INSERT INTO materiales(id_dimencion,id_tipo_material,id_forma,longitud_barra)
					VALUES(id_dimencion,id_tipo_material,id_forma,longitud_barra);
					SET respuesta = 'EL MATERIAL SE HA GUARDADO CORRECTAMENTE';
					
					ELSE SET respuesta = 'EL MATERIAL NO SE HA PODIDO GUARDAR';
				END IF;
		
		END IF;

END //
DELIMITER ;

#MATERIALES 
CREATE VIEW ver_materiales
AS
SELECT mt.id_material,tm.desc_tipo_material,dm.desc_dimencion,fm.clave_forma,fm.desc_forma,mt.longitud_barra 
FROM materiales AS mt
INNER JOIN tipos_material AS tm ON mt.id_tipo_material = tm.id_tipo_material
INNER JOIN dimenciones AS dm ON mt.id_dimencion = dm.id_dimencion
INNER JOIN formas AS fm ON mt.id_forma = fm.id_forma ORDER BY id_material;

CREATE TABLE productos(
id_producto        	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_producto     	VARCHAR (50) NOT NULL,
desc_producto		VARCHAR(150),
id_material			INT REFERENCES materiales(id_material)
);

CREATE TABLE almacen_productos_terminados(
id_almacen_producto_terminado 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_producto							INT NOT NULL REFERENCES productos(id_producto),
total								INT NOT NULL DEFAULT 0
);

DELIMITER //
CREATE PROCEDURE agregar_producto(
IN clave_producto			VARCHAR(50),
IN desc_producto			VARCHAR(150),
IN material					VARCHAR(50),
IN dimencion				VARCHAR(20),
IN forma					VARCHAR(5)
)
BEGIN
	DECLARE id_material INT;
    DECLARE id_producto_agregado INT;
    
    SET id_material = ( SELECT vm.id_material FROM ver_materiales AS vm  WHERE vm.desc_tipo_material = material AND vm.desc_dimencion = dimencion AND vm.clave_forma = forma);
    
    IF id_material IS NOT NULL THEN
    
		INSERT INTO productos(clave_producto,desc_producto,id_material) VALUES(clave_producto,desc_producto,id_material);
		SET id_producto_agregado = (SELECT MAX(pr.id_producto) FROM productos AS pr);
         
         INSERT INTO almacen_productos_terminados(id_producto,total) VALUES(id_producto_agregado,0);
    END IF;
END //
DELIMITER ;

CREATE TABLE tipos_proceso(
id_tipo_proceso         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_tipo_proceso		VARCHAR(5) NOT NULL,
desc_tipo_proceso      VARCHAR (50) NOT NULL    
);

CREATE TABLE defectos_producto(
id_defecto_producto         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
codigo_defecto				INT NOT NULL,
desc_defecto_producto       VARCHAR (100) NOT NULL    
);

CREATE TABLE maquinas(
id_maquina          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_maquina        VARCHAR (50) NOT NULL    
);

CREATE TABLE productos_maquinas(
id_producto_maquina	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_producto			INT NOT NULL REFERENCES productos(id_producto),
id_tipo_proceso		INT NOT NULL REFERENCES tipos_procesos(id_tipo_proceso),
id_maquina			INT REFERENCES maquinas(id_maquina),
piezas_por_turno	INT,
piezas_por_barra	INT,
piezas_por_hora		INT
);

DELIMITER //
CREATE PROCEDURE agregar_producto_material(
IN clave_producto			VARCHAR(50),
IN desc_tipo_proceso		VARCHAR(50),
IN piezas_hora				INT,
IN piezas_turno				INT,
IN piezas_barra				INT,
IN desc_maquina				VARCHAR(50),
INOUT respuesta				VARCHAR(250)
)BEGIN
	DECLARE id_producto INT;
    DECLARE id_tipo_proceso INT;
    DECLARE id_maquina INT;
    
    SET id_producto = (SELECT pr.id_producto FROM productos AS pr WHERE pr.clave_producto = clave_producto);
	SET id_tipo_proceso =(SELECT  tp.id_tipo_proceso FROM tipos_proceso AS tp WHERE tp.desc_tipo_proceso  = desc_tipo_proceso);
	SET id_maquina = (SELECT mq.id_maquina FROM maquinas AS mq WHERE mq.desc_maquina = desc_maquina);
    
    IF id_producto IS NOT NULL AND id_tipo_proceso IS NOT NULL AND
    NOT EXISTS (SELECT * FROM productos_maquinas AS pm WHERE pm.id_producto = id_producto 
    AND pm.id_tipo_proceso = id_tipo_proceso AND pm.id_maquina = id_maquina	) THEN 
		INSERT INTO productos_maquinas(id_producto,id_tipo_proceso,id_maquina,piezas_por_turno,piezas_por_barra,piezas_por_hora)
        VALUES(id_producto,id_tipo_proceso,id_maquina,piezas_turno,piezas_barra,piezas_hora);
        
        SET respuesta = 'LA MAQUINA SE HA ASIGNADO CORRECTAMENTE';
	ELSE
		SET respuesta = 'LA MAQUINA NO SE HA PODIDO ASIGNAR, POR FAVOR VERIFIQUE QUE NO HA SIDO ASIGNADA ANTERIORMENTE A ESTE PRODUCTO';
        
    END IF;
END //
DELIMITER ;


CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_estado         VARCHAR (50) NOT NULL
);

CREATE TABLE clientes(
id_cliente          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL,
desc_cliente		VARCHAR(200) NOT NULL
);

CREATE TABLE contactos(
id_contacto		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_cliente		INT NOT NULL REFERENCES clientes(id_cliente),
desc_contacto	VARCHAR(100) NOT NULL,
departamento	VARCHAR(150) ,
telefono		VARCHAR(100) ,
extencion		VARCHAR(100) ,
celular			VARCHAR(100) ,
correo			VARCHAR(100)
);

DELIMITER //
CREATE PROCEDURE agregar_contacto(
IN nombre_cliente		VARCHAR(20),
IN desc_contacto		VARCHAR(100),
INOUT respuesta			VARCHAR(255)
)BEGIN
	
    DECLARE id_cliente INT;
    
    SET id_cliente = (SELECT cl.id_cliente FROM clientes AS cl WHERE cl.nombre_cliente = nombre_cliente);

	IF id_cliente IS NOT NULL THEN
		
        INSERT INTO contactos(id_cliente,desc_contacto) VALUES(id_cliente,desc_contacto);
		
        SET respuesta = 'EL CONTACTO SE HA AGREGADO CORRECTAMENTE';
	
    ELSE
		SET respuesta = 'EL CONTACTO NO SE HA PODIDO AGREGAR';
    
    END IF;
END //
DELIMITER ;

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

INSERT INTO turnos(desc_turno) VALUES('M');
INSERT INTO turnos(desc_turno) VALUES('V');

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

CREATE TABLE operadores(
id_operador			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
no_operador			VARCHAR(10),
nombre_operador		VARCHAR(20)
);

CREATE TABLE tipos_tiempo_muerto(
id_tipo_tiempo_muerto		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_tiempo_muerto			VARCHAR(5),
desc_tiempo_muerto			VARCHAR(150)
);
/**FIN DE CATALOGOS CONTROL_PIEZAS*/

/** TABLAS DEBILES*/
CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado					INT NOT NULL REFERENCES estados	(id_estado),
id_contacto					INT NOT NULL REFERENCES contacto (id_contacto),
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
desc_empaque			VARCHAR(250),
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
observaciones			VARCHAR(250),
validacion_compras		BOOLEAN DEFAULT FALSE,
validacion_produccion	BOOLEAN DEFAULT FALSE,
validacion_matenimiento	BOOLEAN DEFAULT FALSE,
validacion_calidad		BOOLEAN DEFAULT FALSE
);

CREATE TABLE requisiciones(
id_requisicion 					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_creacion					DATE,
fecha_cerrada					DATE,
solicitante						VARCHAR(50) NOT NULL,
terminos						VARCHAR(100),
uso_material					VARCHAR(255) NOT NULL,
comentarios						VARCHAR(500)
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
id_maquina				INT REFERENCES maquinas(id_maquina),
id_estado				INT NOT NULL REFERENCES estados(id_estado),
piezas_turno_lote		INT ,
worker_lote				INT 
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_lote_planeado			INT REFERENCES lotes_planeados(id_lote_planeado),
desc_lote                   VARCHAR(50),
fecha_trabajo				DATE,
cantidad_operador           INT ,
cantidad_administrador      INT ,
scrap_operador              INT ,
scrap_administrador         INT ,
merma                       FLOAT,
tiempo_muerto               TIME,
turno						INT NOT NULL REFERENCES turnos(id_turno),
cantidad_registrada			INT,
id_operador					INT NOT NULL REFERENCES operadores(id_operador),
rechazo						INT,
cantidad_rechazo_liberado	 INT,
scrap_ajustable				INT,
barras_utilizadas			FLOAT,
num_lote					INT,
id_etiqueta					INT NOT NULL REFERENCES etiquetas(id_etiqueta),
id_estado					INT NOT NULL REFERENCES estados(id_estado)
); 

CREATE TABLE entradas_materiales(
id_entrada_material 	INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material				INT NOT NULL REFERENCES materiales(id_material),
fecha_registro			DATE NOT NULL,
id_proveedor			INT NOT NULL REFERENCES proveedores(id_proveedor),
cantidad				INT NOT NULL,
codigo					VARCHAR(50) NOT NULL,
certificado				VARCHAR(50) NOT NULL,
orden_compra			VARCHAR(50) NOT NULL,
inspector				VARCHAR(50) NOT NULL,
id_estado				INT NOT NULL REFERENCES estados(id_estado),
comentarios				VARCHAR(300),
factura					VARCHAR(255),
desc_lote				VARCHAR(200)
);

CREATE TABLE inventarios(
id_inventario 			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_inventario		DATE NOT NULL,
persona_responsable		VARCHAR(255)
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
cantidad					INT NOT NULL
);

CREATE TABLE empaques_ordenes_p(
id_empaques_ordenes_p 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_orden_produccion			INT NOT NULL REFERENCES ordenes_produccion(id_orden_produccion),
id_empaque					INT NOT NULL REFERENCES empaques (id_empaque),
cantidad					INTEGER NOT NULL
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

CREATE TABLE tiempos_muertos_lote(
id_tiempo_muerto_lote		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_lote_produccion			INT NOT NULL REFERENCES lotes_produccion(id_lote_produccion),
id_tipo_tiempo_muerto		INT NOT NULL REFERENCES tipos_tiempos_muerto(id_tipo_tiempo_muerto)
);

CREATE TABLE materiales_entregados(
id_material_entregado		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material_orden			INT NOT NULL REFERENCES materiales_orden(id_material_orden),
id_entrada_material			INT NOT NULL REFERENCES entredas_materiales(id_entrada_material),
cantidad					INT NOT NULL,
fecha						DATE NOT NULL
);

CREATE TABLE almacen_materias_primas(
id_almacen_materia_prima 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_entrada_material				INT NOT NULL REFERENCES entradas_materiales(id_entrada_material),
cantidad_total					INT NOT NULL
);

CREATE TABLE salidas_materiales(
id_salida_material			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_almacen_materia_prima	INT NOT NULL REFERENCES	almacenes_materias_primas(id_almacen_material_prima),
cantidad_salida				INT NOT NULL,
fecha_salida				DATE NOT NULL
);

CREATE TABLE materiales_solicitados_compras(
id_material_solicitado_compras 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
fecha_registro						DATE NOT NULL
);

CREATE TABLE materiales_solicitud_compras(
id_material_solicitud_compras		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material_solicitado_compras		INT NOT NULL REFERENCES materiales_solicitados_compras(id_material_solicitado_compras),
id_material_solicitado				INT NOT NULL REFERENCES materiales_solicitados(id_material_solicitado),
cantidad							INT NOT NULL
);

/**FIN TABLAS RELACIONALES*/
CREATE TABLE etiquetas(
id_etiqueta			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
codigo_etiqueta		VARCHAR(100),
fecha				DATE,
folio				VARCHAR(50),
piezas_por_bolsa	INT,
piezas_totales		INT
);

CREATE TABLE etiquetas_lotes(
id_etiqueta_lote		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_etiqueta				INT NOT NULL REFERENCES etiquetas(id_etiqueta),
num_etiqueta			INT NOT NULL,
cantidad				INT NOT NULL
);
