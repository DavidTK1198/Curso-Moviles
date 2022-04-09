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
