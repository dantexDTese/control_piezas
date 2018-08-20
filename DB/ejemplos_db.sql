use control_piezas;


INSERT INTO productos(clave_producto) VALUES('producto1');
INSERT INTO maquinas(desc_maquina) VALUES('maquina1');
INSERT INTO materiales(desc_material) VALUES('materiales1');

SELECT * FROM maquinas;
SELECT * FROM productos;
SELECT * FROM materiales;

select * from ordenes_trabajo;
select * from ordenes_produccion;
select * from procesos_produccion;
select * from lotes_produccion;


