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

CREATE OR REPLACE PROCEDURE insertarCiclo(id IN ciclo.id%TYPE,numero in ciclo.numero%type,fec_inicio in ciclo.fec_inicio%type,fec_final in ciclo.fec_final%type)
AS
BEGIN
	INSERT INTO curso VALUES(id,numero,fec_inicio,fec_final);
END;
/

CREATE OR REPLACE PROCEDURE modificarCiclo (codigoin IN curso.codigo%TYPE,nombrein IN curso.nombre%TYPE,creditosin in curso.creditos%type,hsemanalesin in curso.hsemanales%type)
AS
BEGIN
UPDATE curso SET nombre=nombrein,codigo=codigoin,creditos=creditosin,hsemanales=hsemanalesin WHERE codigo=codigoin;
END;
/


CREATE OR REPLACE FUNCTION buscarCiclo(idbuscar IN curso.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        curso_cursor types.ref_cursor; 
BEGIN 
  OPEN curso_cursor FOR 
       SELECT codigo,nombre,creditos,hsemanales FROM curso WHERE codigo=idbuscar; 
RETURN curso_cursor; 
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
create or replace procedure eliminarCiclo(codigoin IN curso.codigo%TYPE)
as
begin
    delete from curso where codigo=codigoin;
end;
/


create table perfil(
id varchar(11),
nombre varchar2(30),
constraints pkperfil primary key (id));
/
create or replace package types
as
     type ref_cursor is ref cursor;
End;
/
create or replace procedure insertarPerfil (id in varchar,descripcion in varchar) as
begin
insert into perfil values(id,descripcion);
end;
/
create or replace function listar
return Types.ref_cursor 
as 
       perfil_cursor types.ref_cursor; 
begin 
  open perfil_cursor for 
       select id,descripcion from perfil; 
return perfil_cursor; 
end;
/
create or replace procedure modificarPerfil (idin in perfil.id%type,descripin in perfil.descripcion%type) as
begin
update perfil set descripcion=descripin where id=idin;
end;
/
create or replace function buscarID (idbuscar in varchar)
return Types.ref_cursor 
as 
       perfil_cursor types.ref_cursor; 
begin 
  open perfil_cursor for 
       select id,descripcion from perfil where id=idbuscar; 
return perfil_cursor; 
end;
/
create or replace procedure eliminarPorId(idin in varchar)
as
begin
    delete from perfil where id=idin;
end;
/
CREATE TABLE contactos (
id varchar(11),
cedula varchar(11),
nombre VARCHAR(30),
oraganizacion VARCHAR(30),
direccion VARCHAR(30),
cargo VARCHAR(12),
email varchar(15),
telefonoTrabajo VARCHAR(10),
telefonoCasa VARCHAR(10),
telefonoCelular VARCHAR(10),
fax VARCHAR(10),
CONSTRAINTS pkcontactos PRIMARY KEY (id)
);


CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;

CREATE OR REPLACE PROCEDURE insertarContactos(id in varchar,cedula in varchar,nombre in VARCHAR,oraganizacion in VARCHAR, direccion in VARCHAR, cargo in VARCHAR, email in varchar, telefonoTrabajo in VARCHAR,telefonoCasa in VARCHAR, telefonoCelular in VARCHAR, fax in VARCHAR)
AS
BEGIN
	INSERT INTO Contactos VALUES(id ,cedula,nombre,oraganizacion, direccion, cargo, email, telefonoTrabajo,telefonoCasa, telefonoCelular, fax);
END;



CREATE OR REPLACE PROCEDURE modificarContactos(inid in varchar,incedula in varchar,innombre in VARCHAR,inoraganizacion in VARCHAR, indireccion in VARCHAR, incargo in VARCHAR, inemail in varchar, intelefonoTrabajo in VARCHAR,intelefonoCasa in VARCHAR, intelefonoCelular in VARCHAR, infax in VARCHAR)
AS
BEGIN
	update contactos set id=inid ,cedula=incedula,nombre=innombre,oraganizacion=inoraganizacion, direccion=indireccion, cargo=incargo, email=inemail, telefonoTrabajo=intelefonoTrabajo,telefonoCasa=intelefonoCasa, telefonoCelular=intelefonoCelular, fax=infax;
END;



CREATE OR REPLACE FUNCTION buscarContactos(idbuscar IN Contactos.id%TYPE)
RETURN Types.ref_cursor 
AS 
        contactos_cursor types.ref_cursor; 
BEGIN 
  OPEN contactos_cursor FOR 
       SELECT id ,cedula,nombre,oraganizacion, direccion, cargo, email, telefonoTrabajo,telefonoCasa, telefonoCelular, fax FROM Contactos WHERE id=idbuscar; 
RETURN contactos_cursor; 
END;



CREATE OR REPLACE procedure eliminarContactos(idbuscar IN Contactos.id%TYPE)
as
begin
	delete from contactos where id=idbuscar;
end;

CREATE table usuario(
    cedula varchar(11),
    nombre varchar(30),
    nombreUsuario varchar(30),
    contrasea varchar(10),
    perfil varchar(11),
    constraint PKUSUARIO primary key (cedula)
   -- constraint FKPERFIL2 foreign key (perfil) refences perfil(id)
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
