CREATE TABLE carrera(
codigo VARCHAR(10),
nombre VARCHAR(50),
titulo VARCHAR(50),
CONSTRAINTS pkcarrera PRIMARY KEY (codigo)
);
--CARRERA
------------------------------------------------------
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/
CREATE OR REPLACE PROCEDURE insertarcarrera(codigo IN carrera.codigo%TYPE,nombre IN libro.nombre%TYPE,
titulo in carrera.titulo%type)
AS
BEGIN
	INSERT INTO carrera VALUES(codigo,nombre,titulo);
END;
/
CREATE OR REPLACE PROCEDURE modificarcarrera (codigoin IN carrera.codigo%TYPE,nombrein IN carrera.nombre%TYPE,tituloin in carrera.titulo%type)
AS
BEGIN
UPDATE carrera SET nombre=nombrein,codigo=codigoin,titulo=tituloin WHERE codigo=codigoin;
END;
/


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
create or replace procedure eliminarcarrera(codigoin IN carrera.codigo%TYPE)
as
begin
    delete from carrera where codigo=codigoin;
end;
/

--CURSO
------------------------------------------------------
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

CREATE OR REPLACE PROCEDURE insertarCurso(codigo IN curso.codigo%TYPE,nombre in curso.nombre%type,creditos in curso.creditos%type,hsemanales in curso.hsemanales%type)
AS
BEGIN
	INSERT INTO curso VALUES(codigo,nombre,creditos,hsemanales);
END;
/

CREATE OR REPLACE PROCEDURE modificarcurso (codigoin IN curso.codigo%TYPE,nombrein IN curso.nombre%TYPE,creditosin in curso.creditos%type,hsemanalesin in curso.hsemanales%type)
AS
BEGIN
UPDATE curso SET nombre=nombrein,codigo=codigoin,creditos=creditosin,hsemanales=hsemanalesin WHERE codigo=codigoin;
END;
/


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
create or replace procedure eliminarcurso(codigoin IN curso.codigo%TYPE)
as
begin
    delete from curso where codigo=codigoin;
end;
/

--CICLO
------------------------------------------------------
CREATE TABLE ciclo(
id number,
numero NUMBER,
annio NUMBER,
fec_inicio VARCHAR,
fec_final VARCHAR,
CONSTRAINTS pkciclo PRIMARY KEY (id)
);


CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

CREATE OR REPLACE PROCEDURE insertarCiclo(id IN ciclo.id%TYPE,numero in ciclo.numero%type,annio in ciclo.annio%type,fec_inicio in ciclo.fec_inicio%type,fec_final in ciclo.fec_final%type)
AS
BEGIN
	INSERT INTO ciclo VALUES(id,numero,annio,fec_inicio,fec_final);
END;
/

CREATE OR REPLACE PROCEDURE modificarCiclo (idin IN ciclo.codigo%TYPE,numero IN ciclo.numero%TYPE,fec_inicioin in ciclo.fec_inicio%type,fec_finalin in ciclo.fec_final%type)
AS
BEGIN
UPDATE ciclo SET id=idin,numero=numeroin,annio=annioin,fec_inicio=fec_inicioin,fec_final=fec_finalin WHERE id=idin;
END;
/


CREATE OR REPLACE FUNCTION buscarCiclo(idbuscar IN ciclo.id%TYPE)
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
       SELECT id,numero,annio,fec_inicio,fec_final FROM ciclo WHERE id=idbuscar; 
RETURN ciclo_cursor; 
END;
/
CREATE OR REPLACE FUNCTION listarCiclo
RETURN Types.ref_cursor 
AS 
        ciclo_cursor types.ref_cursor; 
BEGIN 
  OPEN ciclo_cursor FOR 
       SELECT id,numero,annio,fec_inicio,fec_final FROM ciclo; 
RETURN ciclo_cursor; 
END;
/
create or replace procedure eliminarCiclo(codigoin IN ciclo.id%TYPE)
as
begin
    delete from curso where id=codigoin;
end;
/

--INSERTAR
------------------------------------------------------



CREATE table usuario(
    cedula varchar(11),
    nombre varchar(30),
    nombreUsuario varchar(30),
    contrasea varchar(10),
    constraint PKUSUARIO primary key (cedula)

);

--INSERTAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarUsuario(cedulain varchar, nombrein varchar,
nombreUsuarioin varchar, contraseain varchar, perfilin varchar)
as
BEGIN
 INSERT INTO usuario VALUES(cedulain,nombrein,nombreUsuarioin,contraseain,perfilin);
END;
------------------------------------------------------

--ACTUALIZAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE actualizaUsuario(cedulain in varchar, nombrein in varchar,
nombreUsuarioin in varchar, contraseain in varchar, perfilin in varchar)
as
BEGIN
 UPDATE usuario SET nombre=nombrein,nombreUsuario=nombreUsuarioin,
		    contrasea=contraseain,perfil=perfilin WHERE cedula=cedulain;
END;
------------------------------------------------------

--ELIMINAR
------------------------------------------------------
CREATE OR REPLACE PROCEDURE eliminarUsuario(cedulain varchar)
as
BEGIN
 DELETE usuario WHERE cedula=cedulain;
END;
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
end; 
------------------------------------------------------

--CONSULTAR
------------------------------------------------------
CREATE OR REPLACE function consultarUsuario(cedulain in varchar)
return Types.ref_cursor 
as 
        usuario_cursor types.ref_cursor; 
begin 
  open usuario_cursor for 
       select nombre,nombreUsuario,contrasea,perfil
       FROM usuario where cedula=cedulain;
return usuario_cursor;
end;  
------------------------------------------------------


----------------------------CREACION DE LA TABLA--------------------------------------------

CREATE TABLE comprobantePago(
id VARCHAR(10),
nombre VARCHAR(50),
cedula VARCHAR(11),
perfil VARCHAR(1),
salarioBruto number(9,5),
ccss number(7,5),
bancoPopular number(7,5),
ingresosAcumulados number(20,10),
fechaIngreso VARCHAR(10),
vacDisfQuinc number(4,3),
vacAcumAno number(4,3),
vacDisfAno number(4,3),
CONSTRAINTS pkComprobantePago PRIMARY KEY (id)
);

------------CREACION DEL PAQUETE QUE CONTIENE EL TIPO REF_CURSOR---------------------------------

CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;

---------------------iNSERTAR UN COMPROBANTE DE PAGO-----------------------------------------------------------------------


CREATE OR REPLACE PROCEDURE insertarComprobantePago(id in varchar,nombre in varchar,cedula in varchar,
perfil in varchar, salarioBruto in number,ccss in number,bancoPopular in number,ingresosAcumulados in number,
fechaIngreso in VARCHAR,vacDisfQuinc in number,vacAcumAno in number,vacDisfAno in number)
AS
BEGIN
	INSERT INTO comprobantePago VALUES(id,nombre,cedula,perfil,salarioBruto,ccss,bancoPopular,ingresosAcumulados,fechaIngreso,vacDisfQuinc,vacAcumAno,vacDisfAno);
END;


/*exec insertarComprobantePago('1','Michael','1-13330581','p',10000,1000,500,500000,'10/10/2007',3,20,15); con esta no sirvio*/
/*exec insertarComprobantePago('1','Michl','1-13330','p',1,1,5,500,'10/10/07',3,20,15); esta si sirvio*/

-----------------------------Modificar un comprobante de pago---------------------------------------------------------------

CREATE OR REPLACE PROCEDURE modificaComprobantePago (id in varchar,nombreX in varchar,cedulaX in varchar,
perfilX in varchar, salarioBrutoX in number,ccssX in number,bancoPopularX in number,ingresosAcumuladosX in number,
fechaIngresoX in VARCHAR,vacDisfQuincX in number,vacAcumAnoX in number,vacDisfAnoX in number)
AS
BEGIN
UPDATE comprobantePago SET nombre = nombreX, cedula = cedulaX, perfil = perfilX, salarioBruto = salarioBrutoX, ccss = ccssX, bancoPopular = bancoPopularX, fechaIngreso = fechaIngresoX, vacDisfQuincX = vacDisfQuincX, vacAcumAno = vacAcumAnoX, vacDisfAno = vacDisfAnoX WHERE id = idX;
END;

--------------------------------Buscar un comprobante de pago------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION buscarComprobantePago(idbuscar IN varchar)
RETURN Types.ref_cursor 
AS 
        cursorComprobante types.ref_cursor; 
BEGIN 
  OPEN cursorComprobante FOR 
       SELECT id,nombre,cedula,perfil,salarioBruto,ccss,bancoPopular,ingresosAcumulados,fechaIngreso,vacDisfQuinc,vacAcumAno,vacDisfAno from comprobantePago WHERE id=idbuscar; 
RETURN cursorComprobante; 
END;

------------------------------Lista todos los comprobantes de pago-------------------------------------------------------------

CREATE OR REPLACE FUNCTION listarComprobantePago
RETURN Types.ref_cursor 
AS 
        cursorComprobante types.ref_cursor; 
BEGIN 
  OPEN cursorComprobante FOR 
       SELECT id,nombre,cedula,perfil,salarioBruto,ccss,bancoPopular,ingresosAcumulados,fechaIngreso,vacDisfQuinc,vacAcumAno,vacDisfAno from comprobantePago; 
RETURN cursorComprobante; 
END;

-------------------------------Eliminar un comprobante de pago------------------------------------------------------------

create or replace procedure eliminarComprobantePago(idin IN varchar)
as
begin
    delete from comprobantePago where id=idin;
end;

--------------------------------------------------------


---Login

CREATE OR REPLACE FUNCTION login(idin IN VARCHAR), passwordin IN VARCHAR)
RETURN Types.ref_cursor 
AS 
        n_cursor types.ref_cursor; 
BEGIN 
  OPEN n_cursor FOR 
	  SELECT COUNT(nombreUsuario) AS esta FROM usuario WHERE nombreUsuario=idin AND contrasea=passwordin;
	 RETURN n_cursor; 
END;
