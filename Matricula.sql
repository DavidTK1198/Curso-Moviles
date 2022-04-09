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
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
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
CONSTRAINTS pkcurso PRIMARY KEY (codigo)
);

CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarCurso(codigo IN curso.codigo%TYPE,nombre in curso.nombre%type,creditos in curso.creditos%type,hsemanales in curso.hsemanales%type)
AS
BEGIN
	INSERT INTO curso VALUES(codigo,nombre,creditos,hsemanales);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarcurso (codigoin IN curso.codigo%TYPE,nombrein IN curso.nombre%TYPE,creditosin in curso.creditos%type,hsemanalesin in curso.hsemanales%type)
AS
BEGIN
UPDATE curso SET nombre=nombrein,codigo=codigoin,creditos=creditosin,hsemanales=hsemanalesin WHERE codigo=codigoin;
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
       SELECT codigo,nombre,creditos,hsemanales FROM curso WHERE codigo=idbuscar; 
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
       SELECT codigo,nombre,creditos,hsemanales FROM curso; 
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
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarCiclo(id IN ciclo.id%TYPE,estado IN ciclo.estado%TYPE,numero in ciclo.numero%type,annio in ciclo.annio%type,fec_inicio in ciclo.fec_inicio%type,fec_final in ciclo.fec_final%type)
AS
BEGIN
	INSERT INTO ciclo VALUES(id,numero,estado,annio,fec_inicio,fec_final);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarCiclo (idin IN ciclo.id%TYPE,estadoin IN ciclo.estado%TYPE,numeroin IN ciclo.numero%TYPE,annioin IN ciclo.annio%TYPE,fec_inicioin in ciclo.fec_inicio%type,fec_finalin in ciclo.fec_final%type)
AS
BEGIN
UPDATE ciclo SET id=idin,estado=estadoin,numero=numeroin,annio=annioin,fec_inicio=fec_inicioin,fec_final=fec_finalin WHERE id=idin;
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
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarprofesor(cedula IN profesor.cedula%TYPE,nombre IN profesor.nombre%TYPE,
telefono IN profesor.telefono%type,email IN profesor.email)
AS
BEGIN
	INSERT INTO profesor VALUES(cedula,nombre,telefono,email);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarProfesor (cedulain IN profesor.cedula%TYPE,nombrein IN profesor.nombre%TYPE,
telefonoin IN profesor.telefono%type,emailin IN profesor.email)
AS
BEGIN
UPDATE profesor SET nombre=nombrein,cedula=cedulain,telefono=telefonoin,emailin WHERE cedula=cedulain;
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
teléfono varchar(15),
email varchar(50),
fec_nac varchar(20),
carrerafk varchar(20)
constraint FKCARRERA foreign key (carrerafk),
constraint PKALUMNO primary key (cedula)
);
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarAlumno(cedula IN Alumno.cedula%TYPE,nombre IN Alumno.nombre%TYPE,
telefono IN Alumno.telefono%type,email IN Alumno.email,fec_nac IN Alumno.fec_nac%TYPE,carrera IN Alumno.carrerafk%type)
AS
BEGIN
	INSERT INTO Alumno VALUES(cedula,nombre,telefono,email,fec_nac,carrerafk);
END;
/
--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE modificarAlumno (cedulain IN Alumno.cedula%TYPE,nombrein IN Alumno.nombre%TYPE,
telefonoin IN Alumno.telefono%type,emailin IN Alumno.email,fec_nacin IN Alumno.fec_nac%TYPE)
AS
BEGIN
UPDATE profesor SET nombre=nombrein,cedula=cedulain,telefono=telefonoin,emailin,fec_nac=fec_nacin WHERE cedula=cedulain;
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
       SELECT cedula,nombre,email,telefono,fec_nac,carrerafk FROM Alumno WHERE cedula=idbuscar; 
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
        SELECT cedula,nombre,email,telefono,fec_nac,carrerafk FROM Alumno; 
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
codigocurso varchar(15),
numgrupo number,
horario varchar(30),
ciclofk number,
profesorfk varchar(15),
constraint FKCURSO primary key (codigocurso),
constraint FKCICLO foreign key (ciclofk),
constraint FKPROFESOR foreign key (profesorfk),
constraint  primary key (numgrupo,codigocurso,ciclofk)--mejor un sequence
);
PROMPT SE GRUPO_ESTUDIANTE
PROMPT =========================================================
create table Grupo_Alumno(

);

PROMPT SE HISTORIAL
PROMPT =========================================================
create table Historial(
alumnofk varchar(15),
cursofk varchar(15),
nota number,
);
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
nombreUsuarioin IN usuario.nombreUsuario, contraseain IN Usuario.contrasea%TYPE)
as
BEGIN
 INSERT INTO usuario VALUES(cedulain,nombrein,nombreUsuarioin,contraseain,rolin);
END
/
------------------------------------------------------

--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE actualizaUsuario(cedulain IN usuario.cedula%TYPE, rolin IN usuario.rol%TYPE,
nombreUsuarioin IN usuario.nombreUsuario, contraseain IN Usuario.contrasea%TYPE)
as
BEGIN
 UPDATE usuario SET nombre=nombrein,nombreUsuario=nombreUsuarioin,
		    contrasea=contraseain,rol=rollin WHERE cedula=cedulain;
END
/
------------------------------------------------------

--ELIMINAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE eliminarUsuario(cedulain IN usuario.cedula%TYPE)
as
BEGIN
 DELETE usuario WHERE cedula=cedulain;
END
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
       select nombre,nombreUsuario,contrasea,perfil
       FROM usuario ;
return usuario_cursor;
end
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
       select nombre,nombreUsuario,contrasea,rol
       FROM usuario where cedula=cedulain;
return usuario_cursor;
end
/
---Login

CREATE OR REPLACE FUNCTION login(idin IN usuario.cedula%TYPE, passwordin IN usuario.contrasea%TYPE)
RETURN Types.ref_cursor 
AS 
        n_cursor types.ref_cursor; 
BEGIN 
  OPEN n_cursor FOR 
	  SELECT COUNT(nombreUsuario) AS esta FROM usuario WHERE nombreUsuario=idin AND contrasea=passwordin;
	 RETURN n_cursor; 
END
/
