DROP DATABASE IF EXISTS control_piezas;

CREATE DATABASE control_piezas;    

USE control_piezas;
/** CATALOGOS DE LA BASE DE DATOS CONTROL_PIEZAS (TABLAS FUERTES)*/

CREATE TABLE dimenciones(
id_dimencion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_dimencion		VARCHAR(10) NOT NULL
);

INSERT INTO dimenciones(desc_dimencion) VALUES('1/8');
INSERT INTO dimenciones(desc_dimencion) VALUES('5/32');
INSERT INTO dimenciones(desc_dimencion) VALUES('3/16');
INSERT INTO dimenciones(desc_dimencion) VALUES('7/32');
INSERT INTO dimenciones(desc_dimencion) VALUES('1/4');
INSERT INTO dimenciones(desc_dimencion) VALUES('5/16');
INSERT INTO dimenciones(desc_dimencion) VALUES('3/8');
INSERT INTO dimenciones(desc_dimencion) VALUES('7/16');
INSERT INTO dimenciones(desc_dimencion) VALUES('1/2');
INSERT INTO dimenciones(desc_dimencion) VALUES('9/16');
INSERT INTO dimenciones(desc_dimencion) VALUES('5/8');
INSERT INTO dimenciones(desc_dimencion) VALUES('3/4');
INSERT INTO dimenciones(desc_dimencion) VALUES('7/8');
INSERT INTO dimenciones(desc_dimencion) VALUES('1');
INSERT INTO dimenciones(desc_dimencion) VALUES('1 1/2');


CREATE TABLE tipos_material(
id_tipo_material		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_material		VARCHAR(100)
);

INSERT INTO tipos_material (desc_tipo_material) VALUES ('Aluminio');
INSERT INTO tipos_material (desc_tipo_material) VALUES ('Laton');
INSERT INTO tipos_material (desc_tipo_material) VALUES ('Acero 12L14');
INSERT INTO tipos_material (desc_tipo_material) VALUES ('Inoxidable 303 (T303)');
INSERT INTO tipos_material (desc_tipo_material) VALUES ('Inoxidable 304');
INSERT INTO tipos_material (desc_tipo_material) VALUES ('Colado Gris');

CREATE TABLE formas(
id_forma		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_forma		VARCHAR(5) NOT NULL,
desc_forma		VARCHAR(20) NOT NULL
);

INSERT INTO formas(clave_forma,desc_forma) VALUES('C','Cuadrado');
INSERT INTO formas(clave_forma,desc_forma) VALUES('R','Redondo');
INSERT INTO formas(clave_forma,desc_forma) VALUES('H','Hexagonal');

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
IN forma				VARCHAR(5),
IN longitud_barra		FLOAT
)
BEGIN
	DECLARE id_tipo_material INT;
    DECLARE id_dimencion	 INT;
    DECLARE id_forma 		 INT;
    
    SET id_tipo_material = (SELECT tm.id_tipo_material FROM tipos_material AS tm WHERE tm.desc_tipo_material = desc_material);
    SET id_dimencion = (SELECT dm.id_dimencion FROM dimenciones AS dm WHERE dm.desc_dimencion = dimencion);
    SET id_forma = (SELECT fm.id_forma FROM formas AS fm WHERE fm.clave_forma = forma);
    
    IF id_tipo_material IS NOT NULL AND id_dimencion IS NOT NULL AND id_forma IS NOT NULL THEN
		
        INSERT INTO materiales(id_dimencion,id_tipo_material,id_forma,longitud_barra)
        VALUES(id_dimencion,id_tipo_material,id_forma,longitud_barra);
        
    END IF;


END //
DELIMITER ;

SELECT * FROM materiales;

CALL agregar_material('Laton','5/16','R',3.630);
CALL agregar_material('Laton','1/4','R',3.630);
CALL agregar_material('Laton','7/16','R',3.630);
CALL agregar_material('Laton','1/2','R',3.630);
CALL agregar_material('Laton','7/32','C',3.630);
CALL agregar_material('Laton','3/8','R',3.630);
CALL agregar_material('Laton','3/16','C',3.630);
CALL agregar_material('Laton','7/16','H',3.630);
CALL agregar_material('Laton','1/2','H',3.630);
CALL agregar_material('Acero 12L14','1/2','R',3.620);
CALL agregar_material('Acero 12L14','5/32','H',3.640);
CALL agregar_material('Acero 12L14','1/4','R',3.620);
CALL agregar_material('Acero 12L14','1/8','R',3.620);
CALL agregar_material('Acero 12L14','3/16','R',3.620);
CALL agregar_material('Acero 12L14','7/32','R',3.620);

CALL agregar_material('Inoxidable 303 (T303)','1/2','R',3.650);
CALL agregar_material('Inoxidable 303 (T303)','9/16','R',3.650);
CALL agregar_material('Inoxidable 303 (T303)','5/16','R',3.650);
CALL agregar_material('Inoxidable 303 (T303)','3/16','R',3.650);

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


CALL agregar_producto('331A2452P12','INSERT MOLDED, METRIC FOR 192A7923P23','Laton','7/16','R');   
CALL agregar_producto('331A2452P11','INSERT MOLDED M10X1.5 METRIC 331A2452P11','Acero 12L14','1/2','R');
CALL agregar_producto('331A2452P9',	'INSERT MOLDED, METRIC M5 X 0.8','Laton','5/16','R');
CALL agregar_producto('331A2452P8',	'INSERT MOLDED, METRIC M4 X 0.7','Laton','1/4','R');
CALL agregar_producto('331A2452P7',	'INSERT MOLDED, METRIC M3.3 X 0.6','Laton','5/16','R');
CALL agregar_producto('4069QST','BASE INSERT','Laton','1/2','R');
CALL agregar_producto('R6032P5','INSERT MOLDED IN PARTS','Laton','5/16','R');
CALL agregar_producto('315A7143P1',	'INSERT, CROSSBAR','Acero 12L14','5/32','H');
CALL agregar_producto('315A7143P2','INSERT, CROSSBAR','Acero 12L14','5/32', 'H' );
CALL agregar_producto('192A7923P30','INSERT, METRIC, M3.5 X 6','Laton','5/16','R');
CALL agregar_producto('192A7923P23','INSERT 8/32 GE 192A7923P23','Laton','1/4','R');

CALL agregar_producto('26414-32AF','CENTER POST','Acero 12L14', '1/2', 'R');
CALL agregar_producto('6369-208AH','PIN','Acero 12L14', '1/4', 'R' );
CALL agregar_producto('6471-8','GROUND TERMINAL','Laton', '1/2', 'R' );
CALL agregar_producto('6611-8','GROUND TERMINAL',	'Laton', '1/2', 'R' );
CALL agregar_producto('6613-8','GROUND TERMINAL','Laton', '1/2', 'R' );
CALL agregar_producto('81492-40','OPER LEVER ROLL','Acero 12L14', '1/8' ,'R' );
CALL agregar_producto('81492-8','ROLLER ##','Acero 12L14','1/4','R' );
CALL agregar_producto('81492-9AF','SWITCH BLADE PIN##','Acero 12L14','1/8','R');
CALL agregar_producto('WD035082','ESWP126 BUSHING  0207436','Inoxidable 303 (T303)', '5/16', 'R' );
CALL agregar_producto('XT7101-7','NUT 8809/5289','Laton' ,'3/16', 'C' );
CALL agregar_producto('XT3330-6AF','RIVET - 80','Acero 12L14', '7/32',  'R' );
CALL agregar_producto('WD033025','ESWP126 PLUNGER%%','Inoxidable 303 (T303)', '3/16' ,'R' );
CALL agregar_producto('7900-7',	'HINGE PIN','Inoxidable 303 (T303)' ,'9/16', 'R' );
CALL agregar_producto('1547-169','HINGE PIN','Inoxidable 303 (T303)', '1/2' ,'R' );
CALL agregar_producto('1547-170','HINGE PIN','Inoxidable 303 (T303)', '9/16', 'R' );
CALL agregar_producto('IP2-0300','INSERTO SCHNEIDER 31085-148-01 / 31041-005-01','Laton', '1/4' ,'R' );
CALL agregar_producto('IP2-0308','INSERTO SCHNEIDER 31043-067-01','Laton' ,'5/16' ,'R' );
CALL agregar_producto('IP2-0309','INSERTO SCHNEIDER 31063-013-01','Laton', '5/16', 'R' );
CALL agregar_producto('IP2-0310','INSERTO SCHNEIDER 31041-006-01','Laton', '1/4' ,'R' );
CALL agregar_producto('IP2-0312','INSERTO SCHNEIDER 31074-193-01','Laton', '3/8' ,'R' );
CALL agregar_producto('IP2-0314','INSERTO SCHNEIDER 31074-191-01','Laton', '7/16', 'R' );
CALL agregar_producto('IP2-0323','INSERTO SCHNEIDER 31041-060-01','Inoxidable 303 (T303)', '3/16', 'R' );
CALL agregar_producto('IP2-0345','INSERTO I-44001-204-01','Laton' ,'1/4' ,'R' );
CALL agregar_producto('HL20444-3AF','',	'Acero 12L14', '3/16', 'R' );
CALL agregar_producto('1895-16'	,'','Laton', '3/8', 'R' );
CALL agregar_producto('IP2-0303','','Laton', '1/4', 'R' );
CALL agregar_producto('IP2-0307','','Laton', '1/4', 'R' );
CALL agregar_producto('IP2-0313','','Laton', '1/2', 'R' );
CALL agregar_producto('IP2-0316','','Laton', '7/16', 'H' );
CALL agregar_producto('IP2-0317','','Laton', '1/2', 'H' );


CREATE TABLE tipos_proceso(
id_tipo_proceso         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_tipo_proceso		VARCHAR(5) NOT NULL,
desc_tipo_proceso      VARCHAR (50) NOT NULL    
);

INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('M','Maquinado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('B','Barrenado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('A','Avellanado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('R','Ranurado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('T','Torneado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('E','Machueleado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('O','Moleteado');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('V','Validación');
INSERT INTO tipos_proceso(clave_tipo_proceso,desc_tipo_proceso) VALUES('F','Rectificación');

SELECT * FROM tipos_proceso;

CREATE TABLE defectos_producto(
id_defecto_producto         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
codigo_defecto				INT NOT NULL,
desc_defecto_producto       VARCHAR (100) NOT NULL    
);
	
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(100	,'F.E CILINDRADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(110	,'F.E LARGO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(120	,'B. DESCENTRALIZADO ');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(130	,'FILO DE REBABA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(140	,'SIN CUERDA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(150	,'MOLETEADO FINO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(160	,'MOLETEADO DESALINEADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(170	,'SIN MOLETAEADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(180	,'PASA PIN');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(190	,'GOLPE');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(200	,'INCOMPLETAS');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(210	,'NO PASA PIN');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(220	,'SIN CAJA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(230	,'SIN CHAFLAN');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(240	,'MAL LIMADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(250	,'SIN BARRENO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(260	,'CHAFLAN PROFUNDO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(270	,'SIN CILINDRADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(280	,'NO PASA GO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(290	,'PASA NO GO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(300	,'NO PASA GAUGE');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(310	,'F.E. VUELTAS');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(320	,'PROFUNDIDAD BARRENO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(330	,'F.E. CAJA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(340	,'RAYADA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(350	,'CHUECAS');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(360	,'QUEMADAS');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(370	,'PUNTO DE CORTE');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(380	,'SIN RECUBRIMIENTO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(390	,'PUNTA DE BROCA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(400	,'HERRAMIENTA ROTA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(410	,'DOBLE BARRENO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(420	,'OTRO MATERIAL');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(430	,'EXCESO DE REBABA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(440	,'ESCALON');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(450	,'DIENTES DESBATADOS');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(460	,'CUERDA DAÑADA');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(470	,'MAL TORNEADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(480	,'CHAFLAN MALTRATADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(490	,'F.E. ANCHO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(500	,'CHAFLÁN DESPOSTILLADO');
INSERT INTO defectos_producto(codigo_defecto,desc_defecto_producto) VALUES(510	,'F.E. ESPESOR');


SELECT * FROM defectos_producto;

CREATE TABLE maquinas(
id_maquina          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_maquina        VARCHAR (50) NOT NULL    
);

INSERT INTO maquinas(desc_maquina) VALUES('TD26-1');
INSERT INTO maquinas(desc_maquina) VALUES('TD26-2');
INSERT INTO maquinas(desc_maquina) VALUES('TD26-3');
INSERT INTO maquinas(desc_maquina) VALUES('A-20');
INSERT INTO maquinas(desc_maquina) VALUES('A-25');
INSERT INTO maquinas(desc_maquina) VALUES('STROHM');
INSERT INTO maquinas(desc_maquina) VALUES('Tomo');
INSERT INTO maquinas(desc_maquina) VALUES('TR01');
INSERT INTO maquinas(desc_maquina) VALUES('FT01');
INSERT INTO maquinas(desc_maquina) VALUES('Taladro 01');
INSERT INTO maquinas(desc_maquina) VALUES('Fresa 01');
INSERT INTO maquinas(desc_maquina) VALUES('Fresa 02');

SELECT * FROM maquinas;

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
IN piezas_turno			INT,
IN piezas_barra			INT,
IN desc_maquina			VARCHAR(50)
)
BEGIN
	DECLARE id_producto INT;
    DECLARE id_tipo_proceso INT;
    DECLARE id_maquina INT;
    
    SET id_producto = (SELECT pr.id_producto FROM productos AS pr WHERE pr.clave_producto = clave_producto);
	SET id_tipo_proceso =(SELECT  tp.id_tipo_proceso FROM tipos_proceso AS tp WHERE tp.desc_tipo_proceso  = desc_tipo_proceso);
	SET id_maquina = (SELECT mq.id_maquina FROM maquinas AS mq WHERE mq.desc_maquina = desc_maquina);
    
    IF id_producto IS NOT NULL AND id_tipo_proceso IS NOT NULL THEN 
		INSERT INTO productos_maquinas(id_producto,id_tipo_proceso,id_maquina,piezas_por_turno,piezas_por_barra,piezas_por_hora)
        VALUES(id_producto,id_tipo_proceso,id_maquina,piezas_turno,piezas_barra,piezas_hora);
    END IF;
END //
DELIMITER ;

CALL agregar_producto_material('331A2452P12','Maquinado',165,1155 ,189 ,'A-20');
CALL agregar_producto_material('331A2452P12','Maquinado',165,1155 ,189 ,'A-25');	
CALL agregar_producto_material('331A2452P11','Maquinado',86,602,197,'TD26-1');
CALL agregar_producto_material('331A2452P11','Maquinado',86,602,197,'TD26-2');	
CALL agregar_producto_material('331A2452P9','Maquinado',153,1071,269,'TD26-2');
CALL agregar_producto_material('331A2452P9','Maquinado',153,1071,269,'TD26-3'); 
CALL agregar_producto_material('331A2452P8','Maquinado',153,1071 ,392,'TD26-2');
CALL agregar_producto_material('331A2452P8','Maquinado',153,1071 ,392,'TD26-3');
CALL agregar_producto_material('331A2452P7','Maquinado',153,1071,462,'TD26-2');
CALL agregar_producto_material('331A2452P7','Maquinado',153,1071,462,'TD26-3');
CALL agregar_producto_material('4069QST','Maquinado',153,1071,230,'TD26-2');
CALL agregar_producto_material('4069QST','Maquinado',153,1071,230,'TD26-3');
CALL agregar_producto_material('R6032P5','Maquinado',153,1071 ,286 ,'TD26-2');
CALL agregar_producto_material('R6032P5','Maquinado',153,1071 ,286 ,'TD26-3');
CALL agregar_producto_material('315A7143P1','Maquinado',260,1820,52,'TD26-2');
CALL agregar_producto_material('315A7143P1','Maquinado',260,1820,52,'TD26-3');
#CALL agregar_producto_material('315A7143P1',' Barrenado',315,2205,0,'');  		
CALL agregar_producto_material('315A7143P2','Maquinado',260,1820,85,'A-20');
CALL agregar_producto_material('315A7143P2','Maquinado',260,1820,85,' A-25');
#CALL agregar_producto_material('315A7143P2','Barrenado',315,2205,0,'');		 
CALL agregar_producto_material('192A7923P30','Maquinado',153,1071,330,'A-20');
CALL agregar_producto_material('192A7923P30','Maquinado',153,1071,330,'A-25');	
CALL agregar_producto_material('192A7923P23','Maquinado',260,1820,169,'TD26-1');
CALL agregar_producto_material('192A7923P23','Maquinado',260,1820,169,'TD26-2');
CALL agregar_producto_material('192A7923P23','Maquinado',260,1820,169,'TD26-3');	
CALL agregar_producto_material('26414-32AF','Maquinado',60,420,67,'STROM');
CALL agregar_producto_material('6369-208AH','Mquinado',86,602,117,'A-20');
CALL agregar_producto_material('6369-208AH','Mquinado',86,602,117,'A-25');
CALL agregar_producto_material('6471-8','Maquinado',58,406,60,'A-20');
CALL agregar_producto_material('6471-8','Maquinado',58,406,60,'A-25');
CALL agregar_producto_material('6611-8','Maquinado',58,406,56,'A-20');
CALL agregar_producto_material('6611-8','Maquinado',58,406,56,'A-25');
CALL agregar_producto_material('6613-8','Maquinado',58,406,57,'A-20');
CALL agregar_producto_material('6613-8','Maquinado',58,406,57,'A-25');
CALL agregar_producto_material('81492-40','Maquinado',215,1505,467,'A-20');
CALL agregar_producto_material('81492-40','Maquinado',215,1505,467,'A-25');
#CALL agregar_producto_material('81492-40','Torneado',32,224,0,'');
CALL agregar_producto_material('81492-40','Barrenado',85,595,0,'Fresa 01');
CALL agregar_producto_material('81492-40','Machueleado',28,196,0,'Fresa 01');	 		 
CALL agregar_producto_material('81492-40','Barrenado',85,595,0,'Fresa 02');
CALL agregar_producto_material('81492-40','Machueleado',28,196,0,'Fresa 02');	 		 
CALL agregar_producto_material('81492-8','Maquinado',240,1800,668,'A-20');
CALL agregar_producto_material('81492-8','Maquinado',240,1800,668,'A-25');
#CALL agregar_producto_material('81492-8','Torneado',22,154,0,'');
CALL agregar_producto_material('81492-8','Barrenado',85,595,0,'Fresa 01');
CALL agregar_producto_material('81492-8','Barrenado',85,595,0,'Fresa 02');
CALL agregar_producto_material('81492-8','Machueleado',28,196,0,'Fresa 01');
CALL agregar_producto_material('81492-8','Machueleado',28,196,0,'Fresa 02');
#CALL agregar_producto_material('81492-8','Ranurado',85,595,0,''); 		  
CALL agregar_producto_material('81492-9AF','Maquinado',154,1078,325,'A-20');
CALL agregar_producto_material('81492-9AF','Maquinado',154,1078,325,'A-25');
CALL agregar_producto_material('WD035082','Maquinado',108,756,360,'TD26-1');
CALL agregar_producto_material('WD035082','Maquinado',108,756,360,'TD26-2');
CALL agregar_producto_material('XT7101-7','Maquinado',154,1078,347,'TD26-1');
CALL agregar_producto_material('XT7101-7','Maquinado',154,1078,347,'TD26-2');
CALL agregar_producto_material('XT7101-7','Maquinado',154,1078,347,'TD26-3');
CALL agregar_producto_material('XT7101-7','Barrenado',153,1071,0,'Fresa 01');
CALL agregar_producto_material('XT7101-7','Barrenado',153,1071,0,'Fresa 02');
#CALL agregar_producto_material('XT7101-7','Ranurado',153,1071,0,'');		   
CALL agregar_producto_material('XT3330-6AF','Maquinado',58,406,112,'A-25');
#CALL agregar_producto_material('XT3330-6AF','Barrenado',153,1071,0,'');
CALL agregar_producto_material('WD033025','Maquinado',108,756,220,'TD26-2');
CALL agregar_producto_material('WD033025','Maquinado',108,756,220,'D26-3');
CALL agregar_producto_material('WD033025','Barrenado',172,1204,0,'Fresa 01');	
CALL agregar_producto_material('WD033025','Barrenado',172,1204,0,'Fresa 02');	
#CALL agregar_producto_material('WD033025','Avellanado',315,2205,0,'');
CALL agregar_producto_material('7900-7','Maquinado',60,420,82,'STROM');
CALL agregar_producto_material('1547-169','Maquinado',60,420,80,'STROM');
CALL agregar_producto_material('1547-170','Maquinado',60,420,96,'STROM');
CALL agregar_producto_material('1547-170','Barrenado',172,1204,0,'Fresa 01');
CALL agregar_producto_material('1547-170','Barrenado',172,1204,0,'Fresa 02');
#CALL agregar_producto_material('1547-170','Avellanado',315,2205,0,'');
CALL agregar_producto_material('IP2-0300','Maquinado',153,1071,251,'A-20');
CALL agregar_producto_material('IP2-0308','Maquinado',215,1505,173,'A-20');
CALL agregar_producto_material('IP2-0308','Maquinado',215,1505,173,'A-25');
CALL agregar_producto_material('IP2-0309','Maquinado',260,1820,305,'A-20');
CALL agregar_producto_material('IP2-0309','Maquinado',260,1820,305,'A-25');
CALL agregar_producto_material('IP2-0310','Maquinado',260,1820,335,'A-20');
CALL agregar_producto_material('IP2-0310','Maquinado',260,1820,335,'A-25');
CALL agregar_producto_material('IP2-0310','Maquinado',153,1071,335,'TD26-1'); 
CALL agregar_producto_material('IP2-0312','Maquinado',215,1505,187,'A-20');
CALL agregar_producto_material('IP2-0312','Maquinado',215,1505,187,'A-25');
CALL agregar_producto_material('IP2-0314','Maquinado',215,1505,163,'A-20');
CALL agregar_producto_material('IP2-0314','Maquinado',215,1505,163,'A-25');
CALL agregar_producto_material('IP2-0323','Barrenado',98,686,284,'A-20');
CALL agregar_producto_material('IP2-0323','Barrenado',98,686,284,'A-25');
CALL agregar_producto_material('IP2-0345','Barrenado',260,1820,289,'A-20');
CALL agregar_producto_material('IP2-0345','Barrenado',260,1820,289,'A-25');

CREATE TABLE estados(
id_estado           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_estado         VARCHAR (50) NOT NULL
);

insert into estados(desc_estado) VALUES('ABIERTO');
insert into estados(desc_estado) VALUES('CERRADO');
insert into estados(desc_estado) VALUES('PROCESANDO');
insert into estados(desc_estado) VALUES('CANCELADO');
insert into estados(desc_estado) VALUES('APROBADA');
insert into estados(desc_estado) VALUES('RECHAZADA');

CREATE TABLE clientes(
id_cliente          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
nombre_cliente      VARCHAR (20) NOT NULL,
desc_cliente		VARCHAR(200) NOT NULL
);

INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES('PLASTONIUM','');
INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES('EATON','');
INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES('ARROW HART','');
INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES('COOPER','');
INSERT INTO clientes(nombre_cliente,desc_cliente) VALUES('PLASCO','');


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

INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('Certificado de material');
INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('Identificación de materia prima');
INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('Apariencia');
INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('Empaque');
INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('DIMENCION1');
INSERT INTO tipos_inspeccion(desc_tipo_inspeccion) VALUES('DIMENCION2');

CREATE TABLE resultados_inspeccion(
id_resultado_inspeccion		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_resultado_inspeccion		VARCHAR(5)
);

INSERT INTO resultados_inspeccion(desc_resultado_inspeccion) VALUES('C');
INSERT INTO resultados_inspeccion(desc_resultado_inspeccion) VALUES('R');

CREATE TABLE tipos_operaciones_almacenes(
id_tipo_operacion_almacen			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
desc_tipo_operacion					VARCHAR(20)  CHECK (desc_tipo_operacion	 = 'ENTRADA' OR desc_tipo_operacion = 'SALIDA')
);

INSERT INTO tipos_operaciones_almacenes(desc_tipo_operacion) VALUES('ENTRADA');
INSERT INTO tipos_operaciones_almacenes(desc_tipo_operacion) VALUES('SALIDA');


CREATE TABLE operadores(
id_operador			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
no_operador			VARCHAR(10),
nombre_operador		VARCHAR(20)
);

INSERT INTO operadores(no_operador,nombre_operador) VALUES('1','Nazaria');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('2','Esther');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('23','Yonathan');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('27','Bianca ');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('28','Jorge');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('29','Jonathan');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('31','Oralia');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('32','Omar');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('34','Verónica');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('MAF','Marcos');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('MBF','Moises');
INSERT INTO operadores(no_operador,nombre_operador) VALUES('AC','Adrián');


SELECT * FROM operadores;

CREATE TABLE tipos_tiempo_muerto(
id_tipo_tiempo_muerto		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
clave_tiempo_muerto			VARCHAR(5),
desc_tiempo_muerto			VARCHAR(150)
);

INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('MM','Máquina');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('HM','Herramienta de Máquina');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('MO','Mano de Obra');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('MS','Materiales');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('CM','Cambio de Modelo');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('QC','Calidad');
INSERT INTO tipos_tiempo_muerto(clave_tiempo_muerto,desc_tiempo_muerto) VALUES('EE','Energía Eléctrica');

SELECT * FROM tipos_tiempo_muerto;

/**FIN DE CATALOGOS CONTROL_PIEZAS*/

/** TABLAS DEBILES*/
CREATE TABLE pedidos(
id_pedido					INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_estado					INT NOT NULL REFERENCES estados	(id_estado)	,
id_cliente					INT NOT NULL REFERENCES clientes (id_cliente),
desc_contacto				VARCHAR(150),
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
id_maquina				INT REFERENCES maquinas(id_maquina),
id_estado				INT NOT NULL REFERENCES estados(id_estado),
piezas_turno_lote		INT ,
worker_lote				INT 
);

CREATE TABLE lotes_produccion(
id_lote_produccion          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_lote_planeado			INT REFERENCES lotes_planeados(id_lote_planeado),
desc_lote                   VARCHAR(50),
fecha_trabajo				DATETIME,
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
cantidad_rechazo_liberado	INT,
scrap_ajustable				INT,
barras_utilizadas			FLOAT,
num_lote					INT,
id_etiqueta					INT NOT NULL REFERENCES etiquetas(id_etiqueta)
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
no_parte				VARCHAR(255),
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

CREATE TABLE materiales_proveedor(
id_material_proveedor		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_material					INT NOT NULL REFERENCES materiales(id_material),
id_proveedor				INT NOT NULL REFERENCES proveedores(id_proveedor),
precio_unitario				FLOAT
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
