set linesize 160
set pagesize 150
PROMPT  DROPEO DE TABLAS
drop table Inscripcion cascade constraint;
drop table Grupo cascade constraint;
drop table carrera cascade constraint;
drop table profesor cascade constraint;
drop table alumno cascade constraint;
drop table ciclo cascade constraint;
drop table curso cascade constraint;
drop table usuario cascade constraint;

PROMPT  DROPEO DE SECUENCIAS
drop sequence secuenciagrupo;
drop sequence secuenciainscripcion;
drop sequence secuenciacurcar;
drop sequence secuenciaciclo;
PROMPT  CREACION DE SECUENCIAS
create sequence secuenciagrupo start with 1;
create sequence secuenciainscripcion start with 1000;
create sequence secuenciacurcar start with 100;
create sequence secuenciaciclo start with 10000;
PROMPT  CREACION DEl CURSOR
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
--CARRERA
------------------------------------------------------
PROMPT SE CREA CARRERA
PROMPT =========================================================
CREATE TABLE carrera(
codigo VARCHAR(10),
nombre VARCHAR(50),
titulo VARCHAR(50),
CONSTRAINTS pkcarrera PRIMARY KEY (codigo)
);

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarcarrera(codigo IN carrera.codigo%TYPE,nombre IN carrera.nombre%TYPE,
titulo in carrera.titulo%type)
AS
BEGIN
	INSERT INTO carrera VALUES(codigo,nombre,titulo);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarcarrera (codigoin IN carrera.codigo%TYPE,nombrein IN carrera.nombre%TYPE,tituloin in carrera.titulo%type)
AS
BEGIN
UPDATE carrera SET nombre=nombrein,codigo=codigoin,titulo=tituloin WHERE codigo=codigoin;
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscarcarrera(idbuscar IN carrera.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        carrera_cursor types.ref_cursor; 
BEGIN 
  OPEN carrera_cursor FOR 
       SELECT codigo,nombre,titulo FROM carrera WHERE codigo=idbuscar; 
RETURN carrera_cursor; 
END;
/
CREATE OR REPLACE FUNCTION buscarcarreranombre(idbuscar IN carrera.nombre%TYPE)
RETURN Types.ref_cursor 
AS 
        carrera_cursor types.ref_cursor; 
BEGIN 
  OPEN carrera_cursor FOR 
       SELECT codigo,nombre,titulo FROM carrera  WHERE UPPER(nombre) LIKE '%'||UPPER(idbuscar)||'%';
RETURN carrera_cursor; 
END;
/
--LISTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarcarrera
RETURN Types.ref_cursor 
AS 
        carrera_cursor types.ref_cursor; 
BEGIN 
  OPEN carrera_cursor FOR 
       SELECT codigo,nombre,titulo FROM carrera; 
RETURN carrera_cursor; 
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarcarrera(codigoin IN carrera.codigo%TYPE)
as
begin
    delete from carrera where codigo=codigoin;
end;
/
--CURSO
------------------------------------------------------
PROMPT SE CREA CURSO
PROMPT =========================================================
CREATE TABLE curso(
codigo VARCHAR(10),
nombre VARCHAR(50),
creditos NUMBER,
hsemanales NUMBER,
fkcarrera VARCHAR(10),
CONSTRAINTS pkcurso PRIMARY KEY (codigo)
);
alter table  Curso add constraint FKCARRERACURSO foreign key (fkcarrera) references carrera; 
--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarCurso(codigo IN curso.codigo%TYPE,nombre in curso.nombre%type,creditos in curso.creditos%type,
hsemanales in curso.hsemanales%type,carrerain in curso.fkcarrera%TYPE)
AS
BEGIN
	INSERT INTO curso VALUES(codigo,nombre,creditos,hsemanales,carrerain);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarcurso (codigoin IN curso.codigo%TYPE,nombrein IN curso.nombre%TYPE,creditosin in curso.creditos%type,
hsemanalesin in curso.hsemanales%type,carrerain in curso.fkcarrera%TYPE)
AS
BEGIN
UPDATE curso SET nombre=nombrein,codigo=codigoin,creditos=creditosin,hsemanales=hsemanalesin,fkcarrera=carrerain
 WHERE codigo=codigoin;
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscarcurso(idbuscar IN curso.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
         SELECT  e.codigo,e.nombre,e.creditos,e.hsemanales,c.codigo as car_codigo,c.nombre as car_name,c.titulo 
	   FROM curso e, carrera c 
	   WHERE e.codigo=idbuscar AND e.fkcarrera=c.codigo; 
RETURN curso_cursor; 
END;
/

CREATE OR REPLACE FUNCTION buscarcursopornombre(idbuscar IN curso.nombre%TYPE)
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
       SELECT  e.codigo,e.nombre,e.creditos,e.hsemanales,c.codigo as car_codigo,c.nombre as car_name,c.titulo 
	   FROM curso e, carrera c
	   WHERE UPPER(e.nombre) LIKE '%'||UPPER(idbuscar)||'%' AND e.fkcarrera=c.codigo;
RETURN curso_cursor; 
END;
/
CREATE OR REPLACE FUNCTION buscarcursoporcarrera(idbuscar IN carrera.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
    SELECT e.codigo,e.nombre,e.creditos,e.hsemanales,c.codigo as car_codigo,c.nombre as car_name,c.titulo
   FROM curso e, carrera c
    WHERE e.fkcarrera=idbuscar AND e.fkcarrera=c.codigo;
RETURN curso_cursor; 
END;
/
--LISTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarcurso
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
        SELECT e.codigo,e.nombre,e.creditos,e.hsemanales,c.codigo as car_codigo,c.nombre as car_name,c.titulo
   FROM curso e, carrera c
    WHERE  e.fkcarrera=c.codigo;
RETURN curso_cursor; 
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarcurso(codigoin IN curso.codigo%TYPE)
as
begin
    delete from curso where codigo=codigoin;
end;
/

--CICLO
------------------------------------------------------
PROMPT SE CREA CICLO
PROMPT =========================================================
CREATE TABLE ciclo(
id number,
estado number,
numero NUMBER,
annio NUMBER,
fec_inicio VARCHAR(20),
fec_final VARCHAR(20),
CONSTRAINTS pkciclo PRIMARY KEY (id)
);

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarCiclo(annio in ciclo.annio%type,numero in ciclo.numero%type,estado IN ciclo.estado%TYPE,fec_inicio in ciclo.fec_inicio%type,fec_final in ciclo.fec_final%type)
AS
BEGIN
	INSERT INTO ciclo VALUES(secuenciaciclo.nextval,estado,numero,annio,fec_inicio,fec_final);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarCiclo (idin in ciclo.id%TYPE,annioin IN ciclo.annio%TYPE,numeroin IN ciclo.numero%TYPE,estadoin IN ciclo.estado%TYPE,fec_inicioin in ciclo.fec_inicio%type,fec_finalin in ciclo.fec_final%type)
AS
BEGIN
UPDATE ciclo SET estado=estadoin,numero=numeroin,annio=annioin,fec_inicio=fec_inicioin,fec_final=fec_finalin WHERE id=idin;
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscarCiclo(idbuscar IN ciclo.id%TYPE)
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
       SELECT id,estado,numero,annio,fec_inicio,fec_final FROM ciclo WHERE id=idbuscar; 
RETURN ciclo_cursor; 
END;
/
CREATE OR REPLACE FUNCTION buscarCicloAnnio(idbuscar IN ciclo.annio%TYPE)
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
       SELECT id,estado,numero,annio,fec_inicio,fec_final FROM ciclo WHERE annio=idbuscar; 
RETURN ciclo_cursor; 
END;
/

CREATE OR REPLACE FUNCTION cicloactivo
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
       SELECT id,estado,numero,annio,fec_inicio,fec_final FROM ciclo WHERE estado=1; 
RETURN ciclo_cursor; 
END;
/
CREATE OR REPLACE FUNCTION revisaractivo
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
	  SELECT COUNT(id) AS esta FROM ciclo WHERE estado=1;
	 RETURN ciclo_cursor; 
END;
/
--LISTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarCiclo
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
       SELECT id,numero,estado,annio,fec_inicio,fec_final FROM ciclo; 
RETURN ciclo_cursor; 
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarCiclo(codigoin IN ciclo.id%TYPE)
as
begin
    delete from ciclo where id=codigoin;
end;
/
PROMPT SE CREA PROFESOR
PROMPT =========================================================
Create table profesor(         
cedula varchar(15), 
nombre varchar(100),
telefono varchar(15),
email varchar(50),
constraint PKPROFESOR primary key (cedula)
);


--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarprofesor(cedula IN profesor.cedula%TYPE,nombre IN profesor.nombre%TYPE,
telefono IN profesor.telefono%type,email IN profesor.email%TYPE)
AS
BEGIN
	INSERT INTO profesor VALUES(cedula,nombre,telefono,email);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarProfesor (cedulain IN profesor.cedula%TYPE,nombrein IN profesor.nombre%TYPE,
telefonoin IN profesor.telefono%type,emailin IN profesor.email%TYPE)
AS
BEGIN
UPDATE profesor SET cedula=cedulain,nombre=nombrein,telefono=telefonoin,email=emailin WHERE cedula=cedulain;
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscarProfesor(idbuscar IN profesor.cedula%TYPE)
RETURN Types.ref_cursor 
AS 
        profesor_cursor types.ref_cursor; 
BEGIN 
  OPEN profesor_cursor FOR 
       SELECT cedula,nombre,email,telefono FROM profesor WHERE cedula=idbuscar; 
RETURN profesor_cursor; 
END;
/


CREATE OR REPLACE FUNCTION buscarprofesorpornombre(idbuscar IN profesor.nombre%TYPE)
RETURN Types.ref_cursor 
AS 
        profesor_cursor types.ref_cursor; 
BEGIN 
  OPEN profesor_cursor FOR 
       SELECT cedula,nombre,email,telefono FROM profesor WHERE UPPER(nombre) LIKE '%'||UPPER(idbuscar)||'%'; 
RETURN profesor_cursor; 
END;
/
--LISTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarProfesor
RETURN Types.ref_cursor 
AS 
        profesor_cursor types.ref_cursor; 
BEGIN 
  OPEN profesor_cursor FOR 
        SELECT cedula,nombre,email,telefono FROM profesor; 
RETURN profesor_cursor; 
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarProfesor(codigoin IN profesor.cedula%TYPE)
as
begin
    delete from profesor where cedula=codigoin;
end;
/

PROMPT SE CREA ALUMNO
PROMPT =========================================================
Create table Alumno(         
cedula varchar(15), 
nombre varchar(100),
telefono varchar(15),
email varchar(50),
fec_nac varchar(20),
carrerafk varchar(20),
constraint PKALUMNO primary key (cedula)
);
alter table  Alumno add constraint FKCARRERA foreign key (carrerafk) references carrera; 

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarAlumno(cedula IN Alumno.cedula%TYPE,nombre IN Alumno.nombre%TYPE,
telefono IN Alumno.telefono%type,email IN Alumno.email%TYPE,fec_nac IN Alumno.fec_nac%TYPE,carrera IN Alumno.carrerafk%type)
AS
BEGIN
	INSERT INTO Alumno VALUES(cedula,nombre,telefono,email,fec_nac,carrera);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarAlumno (cedulain IN Alumno.cedula%TYPE,nombrein IN Alumno.nombre%TYPE,
telefonoin IN Alumno.telefono%type,emailin IN Alumno.email%TYPE,fec_nacin IN Alumno.fec_nac%TYPE)
AS
BEGIN
UPDATE Alumno SET nombre=nombrein,cedula=cedulain,telefono=telefonoin,email=emailin,fec_nac=fec_nacin WHERE cedula=cedulain;
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscarAlumno(idbuscar IN Alumno.cedula%TYPE)
RETURN Types.ref_cursor 
AS 
        alumno_cursor types.ref_cursor; 
BEGIN 
  OPEN alumno_cursor FOR 
         SELECT e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,c.codigo as car_codigo,c.nombre as car_name,c.titulo FROM Alumno e, Carrera c
        WHERE e.cedula=idbuscar and e.carrerafk=c.codigo; 
RETURN alumno_cursor; 
END;
/

CREATE OR REPLACE FUNCTION buscarAlumnopornombre(idbuscar IN Alumno.cedula%TYPE)
RETURN Types.ref_cursor 
AS 
        alumno_cursor types.ref_cursor; 
BEGIN 
  OPEN alumno_cursor FOR 
         SELECT e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,c.codigo as car_codigo,c.nombre as car_name,c.titulo FROM Alumno e, Carrera c
        WHERE UPPER(e.nombre) LIKE '%'||UPPER(idbuscar)||'%' and e.carrerafk=c.codigo; 
RETURN alumno_cursor; 
END;
/

CREATE OR REPLACE FUNCTION buscarAlumnoporcarrera(idbuscar IN Alumno.cedula%TYPE)
RETURN Types.ref_cursor 
AS 
        alumno_cursor types.ref_cursor; 
BEGIN 
  OPEN alumno_cursor FOR 
         SELECT e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,c.codigo as car_codigo,c.nombre as car_name,c.titulo FROM Alumno e, Carrera c
        WHERE e.carrerafk=idbuscar and e.carrerafk=c.codigo; 
RETURN alumno_cursor; 
END;
/
--LISTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarAlumno
RETURN Types.ref_cursor 
AS 
        alumno_cursor types.ref_cursor; 
BEGIN 
  OPEN alumno_cursor FOR 
   SELECT e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,c.codigo as car_codigo,c.nombre as car_name,c.titulo FROM Alumno e, Carrera c
        WHERE e.carrerafk=c.codigo; 
RETURN alumno_cursor; 
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarAlumno(codigoin IN Alumno.cedula%TYPE)
as
begin
    delete from Alumno where cedula=codigoin;
end;
/


PROMPT SE CREA GRUPO
PROMPT =========================================================
--GRUPO
------------------------------------------------------
CREATE table Grupo(
idgrupo number,
numgrupo number,
cupo number,
disponible number,
cursofk varchar(15) not null,
horario varchar(30),
ciclofk number not null,
profesorfk varchar(15) not null,
constraint  PKGRUPO primary key (idgrupo)
);

alter table  Grupo add constraint FKCURSO foreign key (cursofk) references curso; 
alter table  Grupo add constraint FKCICLO foreign key (ciclofk) references ciclo; 
alter table  Grupo add constraint FKPROFESOR foreign key (profesorfk) references profesor; 

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarGrupo(numgrupoin IN grupo.numgrupo%TYPE,cupoin IN grupo.cupo%TYPE,disponiblein IN grupo.disponible%TYPE,
cursoin IN grupo.cursofk%TYPE,
horarioin IN grupo.horario%type,cicloin IN grupo.ciclofk%TYPE,profesorin IN grupo.profesorfk%TYPE)
AS
BEGIN
	INSERT INTO grupo VALUES(secuenciagrupo.nextval,numgrupoin,cupoin,disponiblein,cursoin,horarioin,cicloin,profesorin);
END;
/

--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarGrupo (idin grupo.idgrupo%TYPE,cupoin IN grupo.cupo%TYPE,
horarioin IN grupo.horario%type)
AS
BEGIN
UPDATE grupo SET cupo=cupoin,horario=horarioin
WHERE idgrupo=idin;
END;
/

--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION buscargrupo(idbuscar IN grupo.idgrupo%TYPE)
RETURN Types.ref_cursor 
AS 
        grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
           SELECT g.idgrupo as identidad,g.numgrupo,g.cupo,g.disponible,g.horario,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp,
           c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
            FROM grupo g
              INNER JOIN Curso c ON g.cursofk=c.codigo
              INNER JOIN profesor p  ON g.profesorfk=p.cedula
       WHERE  g.idgrupo=idbuscar;
RETURN grupo_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarGrupo(cursoin IN grupo.cursofk%TYPE,
cicloin IN grupo.ciclofk%TYPE)
RETURN Types.ref_cursor 
AS 
        grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
       SELECT g.idgrupo as identidad,g.numgrupo,g.cupo,g.disponible,g.horario,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp, 
       c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
       FROM grupo g
         INNER JOIN Curso c ON g.cursofk=c.codigo
           INNER JOIN profesor p  ON g.profesorfk=p.cedula
       WHERE  g.cursofk=cursoin and g.ciclofk=cicloin;
RETURN grupo_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarporCiclo(cicloin IN grupo.ciclofk%TYPE)
RETURN Types.ref_cursor 
AS 
        grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
       SELECT g.idgrupo as identidad,g.numgrupo,g.cupo,g.disponible,g.horario,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp, 
       c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
       FROM grupo g
         INNER JOIN Curso c ON g.cursofk=c.codigo
           INNER JOIN profesor p  ON g.profesorfk=p.cedula
       WHERE  g.ciclofk=cicloin;
RETURN grupo_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarGrupoPorProfesor(idbuscar IN grupo.profesorfk%TYPE)
RETURN Types.ref_cursor 
AS 
        grupo_cursor types.ref_cursor; 
BEGIN 
  OPEN grupo_cursor FOR 
       SELECT g.idgrupo as identidad,g.numgrupo,g.cupo,g.disponible,g.horario,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp, 
       c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
       FROM grupo g
         INNER JOIN Curso c ON g.cursofk=c.codigo
		  INNER JOIN profesor p  ON g.profesorfk=p.cedula
		  INNER JOIN Ciclo cl on g.ciclofk=cl.id
       WHERE  g.profesorfk=idbuscar AND cl.estado=1;
RETURN grupo_cursor; 
END;
/

--INSCRIPCION
------------------------------------------------------
PROMPT SE CREA Inscripcion
PROMPT =========================================================
CREATE TABLE Inscripcion(
id number,
fkgrupo number not null,
fkalumno varchar(15)not null,
nota NUMBER,
CONSTRAINTS pkInscripcion PRIMARY KEY (id),
CONSTRAINTS ukinscripcion UNIQUE (fkgrupo,fkalumno)
);
alter table  Inscripcion add constraint FKGRUPO foreign key (fkgrupo) references Grupo; 
alter table  Inscripcion add constraint FKALUMNO foreign key (fkalumno) references Alumno; 

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarInscripcion(grupoin IN grupo.idgrupo%TYPE,alumnoin IN Alumno.cedula%TYPE)
AS
BEGIN
	INSERT INTO Inscripcion VALUES(secuenciainscripcion.nextval,grupoin,alumnoin,null);
END;
/
--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE FUNCTION listarPorGrupo(idbuscar IN Inscripcion.fkgrupo%TYPE)
RETURN Types.ref_cursor 
AS 
        inscripcion_cursor types.ref_cursor; 
BEGIN 
  OPEN inscripcion_cursor FOR 
       SELECT i.id as identidad,i.nota,e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,
	   g.idgrupo as identidadg,g.numgrupo,g.cupo,g.disponible,g.horario,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp,
           c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
            FROM  Inscripcion i
		INNER JOIN Alumno e ON i.fkalumno=e.cedula
		INNER JOIN grupo g ON i.fkgrupo=g.idgrupo
        INNER JOIN Curso c ON g.cursofk=c.codigo
        INNER JOIN profesor p on g.profesorfk=p.cedula
       WHERE  i.fkgrupo=idbuscar;
RETURN inscripcion_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarPorAlumno(idbuscar IN Inscripcion.fkalumno%TYPE)
RETURN Types.ref_cursor 
AS 
        inscripcion_cursor types.ref_cursor; 
BEGIN 
  OPEN inscripcion_cursor FOR 
       SELECT i.id as identidad,i.nota,e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,
	   g.idgrupo as identidadg,g.numgrupo,g.cupo,g.disponible,g.horario,
		c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales,p.cedula as cedp,p.nombre as nomp,p.email as emailp,p.telefono as telp 
	   FROM  Inscripcion i
		INNER JOIN Alumno e ON i.fkalumno=e.cedula
		INNER JOIN grupo g ON i.fkgrupo=g.idgrupo
		INNER JOIN Curso c ON g.cursofk=c.codigo
        INNER JOIN profesor p ON g.profesorfk=p.cedula
       WHERE  i.fkalumno=idbuscar;
RETURN inscripcion_cursor; 
END;
/

CREATE OR REPLACE FUNCTION buscarInscripcion(idbuscar IN inscripcion.id%TYPE)
RETURN Types.ref_cursor 
AS 
        inscripcion_cursor types.ref_cursor; 
BEGIN 
  OPEN inscripcion_cursor FOR 
      SELECT i.id as identidad,i.nota,e.cedula,e.nombre,e.email,e.telefono,e.fec_nac,
	   g.idgrupo as identidadg,g.numgrupo,g.cupo,g.disponible,g.horario,
           c.codigo,c.nombre as nomcur,c.creditos,c.hsemanales
            FROM  Inscripcion i
		INNER JOIN Alumno e ON i.fkalumno=e.cedula
		INNER JOIN grupo g ON i.fkgrupo=g.idgrupo
                INNER JOIN Curso c ON g.cursofk=c.codigo
       WHERE  i.id=idbuscar;
RETURN inscripcion_cursor; 
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarInscripcion (idin IN Inscripcion.id%TYPE,
notain IN Inscripcion.nota%TYPE)
AS
BEGIN
UPDATE Inscripcion SET nota=notain
WHERE id=idin;
END;
/
--ELIMINAR
------------------------------------------------------
create or replace procedure eliminarInscripcion(idin IN Inscripcion.id%TYPE)
as
begin
    delete from Inscripcion where id=idin;
end;
/

PROMPT SE CREA USUARIO
PROMPT =========================================================
--Usuario
------------------------------------------------------
CREATE table usuario(
cedula varchar(15),
rol varchar(15),
nombreUsuario varchar(30),
contrasea varchar(10),
constraint PKUSUARIO primary key (cedula)
);

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarUsuario(cedulain IN usuario.cedula%TYPE, rolin IN usuario.rol%TYPE,
nombreUsuarioin IN usuario.nombreUsuario%TYPE, contraseain IN Usuario.contrasea%TYPE)
as
BEGIN
 INSERT INTO usuario VALUES(cedulain,rolin,nombreUsuarioin,contraseain);
END;
/
------------------------------------------------------

--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE actualizaUsuario(cedulain IN usuario.cedula%TYPE, rolin IN usuario.rol%TYPE,
nombreUsuarioin IN usuario.nombreUsuario%TYPE, contraseain IN Usuario.contrasea%TYPE)
as
BEGIN
 UPDATE usuario SET nombreUsuario=nombreUsuarioin,
		    contrasea=contraseain,rol=rolin WHERE cedula=cedulain;
END;
/
------------------------------------------------------

--ELIMINAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE eliminarUsuario(cedulain IN usuario.cedula%TYPE)
as
BEGIN
 DELETE usuario WHERE cedula=cedulain;
END;
/
------------------------------------------------------

--LISTAR
------------------------------------------------------
CREATE OR REPLACE function listarUsuarios
return Types.ref_cursor 
as 
        usuario_cursor types.ref_cursor; 
begin 
  open usuario_cursor for 
       select cedula,rol,nombreUsuario,contrasea
       FROM usuario Where rol='ADM' OR rol='MAT';
return usuario_cursor;
end;
/
------------------------------------------------------

--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE function consultarUsuario(cedulain IN usuario.cedula%TYPE)
return Types.ref_cursor 
as 
        usuario_cursor types.ref_cursor; 
begin 
  open usuario_cursor for 
       select cedula,rol,nombreUsuario,contrasea
       FROM usuario where cedula=cedulain;
return usuario_cursor;
end;
/
---Login

CREATE OR REPLACE FUNCTION login(idin IN usuario.cedula%TYPE, passwordin IN usuario.contrasea%TYPE)
RETURN Types.ref_cursor 
AS 
        n_cursor types.ref_cursor; 
BEGIN 
  OPEN n_cursor FOR 
	  SELECT cedula,nombreUsuario,rol FROM usuario WHERE cedula=idin AND contrasea=passwordin;
	 RETURN n_cursor; 
END;
/
PROMPT DATOS QUEMADOS
------------------------------------------------------
INSERT INTO usuario VALUES('117250610','ADM','DavidTK1198','admin');
INSERT INTO usuario VALUES('207760240','EST','EstebanUG','123');
INSERT INTO usuario VALUES('111','PROF','PedritoA','111');
INSERT INTO usuario VALUES('987654321','MAT','MariaE','222');
INSERT INTO carrera VALUES('EIF','Ingenieria en Sistemas','Bachillerato');
INSERT INTO carrera VALUES('MAC','Ensenanza de Matematica','Licenciatura');
INSERT INTO carrera VALUES('CDN','Ciencias del Movimiento Humano','Bachillerato');
INSERT INTO Alumno VALUES('333','Emmanuel Barrientos','4030-6832','emmanuel@gmail.com','9/11/1992','EIF');
INSERT INTO Alumno VALUES('444','Daniel Madrigal','6079-7171','ddavidb09@gmail.com','4/05/1998','MAC');
INSERT INTO Alumno VALUES('207760240','Esteban Madrigal','6079-7271','esteban@gmail.com','5/05/1998','EIF');
INSERT INTO profesor VALUES('111','Pedro Alvarez','4032-2525','pedroa@gmail.com');
INSERT INTO profesor VALUES('222','Roberto Alvarez','4032-2525','pedroa@gmail.com');
INSERT INTO curso VALUES('EIF200','Fundamentos de Informatica',3,8,'EIF');
INSERT INTO curso VALUES('MAT030','Matematica para Informatica',4,11,'EIF');
INSERT INTO curso VALUES('EIF201','Programacion I',4,11,'EIF');
INSERT INTO curso VALUES('EIF204','Programacion II',4,11,'EIF');
INSERT INTO curso VALUES('EIF202','Soporte Tecnico',3,8,'EIF');
INSERT INTO curso VALUES('EIF203','Estructuras Discretas para Informatica',3,8,'EIF');
INSERT INTO curso VALUES('EIF206','Programacion III',4,11,'EIF');
INSERT INTO curso VALUES('EIF207','Estructuras de Datos',4,11,'EIF');
INSERT INTO curso VALUES('EIF205','Arquitectura de Computadoras',3,8,'EIF');
INSERT INTO curso VALUES('EIF404','La Organizacion y su Entorno',3,8,'EIF');
INSERT INTO curso VALUES('EIF209','Programacion IV',4,11,'EIF');
INSERT INTO curso VALUES('EIF210','Ingenieria de Sistemas I',4,11,'EIF');
INSERT INTO curso VALUES('EIF401','Ingenieria de Sistemas II',4,11,'EIF');
INSERT INTO curso VALUES('EIF406','Ingenieria de Sistemas III',4,11,'EIF');
INSERT INTO curso VALUES('EIF211','Diseno e Implementacion de Bases de Datos',4,11,'EIF');
INSERT INTO curso VALUES('EIF212','Sistemas Operativos',3,8,'EIF');
INSERT INTO curso VALUES('EIF208','Comunicaciones y Redes de Computadores',3,8,'EIF');
INSERT INTO curso VALUES('CDN201','Cumbia recreativa',3,8,'CDN');
INSERT INTO curso VALUES('CDN205','Desarrollo Humano',3,8,'CDN');
INSERT INTO ciclo VALUES(secuenciaciclo.nextval,1,1,2022,'7/3/2022','25/6/2022');
INSERT INTO ciclo VALUES(secuenciaciclo.nextval,2,2,2022,'8/8/2022','25/11/2022');
INSERT INTO ciclo VALUES(secuenciaciclo.nextval,2,2,2021,'7/8/2021','27/11/2021');
INSERT INTO grupo VALUES(secuenciagrupo.nextval,1,20,20,'EIF201','L-V 8-9:40',10000,'111');
INSERT INTO grupo VALUES(secuenciagrupo.nextval,2,20,20,'EIF200','L-V 8-9:40',10000,'111');
INSERT INTO grupo VALUES(secuenciagrupo.nextval,3,20,20,'EIF201','M-J 8-9:40',10000,'222');
INSERT INTO Inscripcion VALUES(secuenciainscripcion.nextval,1,207760240,null);
INSERT INTO Inscripcion VALUES(secuenciainscripcion.nextval,1,333,null);
commit;




