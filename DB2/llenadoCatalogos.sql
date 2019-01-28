#IMPORTACION CATALOGOS

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//catalogoProductos.csv' INTO TABLE productos
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//tiposDefectos.csv' INTO TABLE defectos_producto
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//dimenciones.csv' INTO TABLE dimenciones
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//formas.csv' INTO TABLE formas
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//Maquinas.csv' INTO TABLE maquinas
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//Materiales.csv' INTO TABLE materiales
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//Materiales.csv' INTO TABLE tipos_proceso
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//Operadores.csv' INTO TABLE operadores
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

LOAD DATA INFILE 'C://Users//cesar//Documents//freelancer//control_piezas//DB2//CATALOGOS//tiemposMuertos.csv' INTO TABLE tiempos_muertos
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';