create database prueba;

use prueba;

create table tabla1(
id_tabla1 INT NOT NULL PRIMARY KEY AUTO_INCREMENT
);

create table tabla2(
id_tabla2 INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
id_tabla1_0 INT NOT NULL REFERENCES tabla1 (id_tabla1),
id_tabla1_1 INT NOT NULL REFERENCES tabla1 (id_tabla1)
);

insert into tabla1 VALUES(1);
insert into tabla1 VALUES(2);

SELECT * FROM TABLA2;

INSERT INTO tabla2(id_tabla1_0,id_tabla1_1) VALUES(1,2);