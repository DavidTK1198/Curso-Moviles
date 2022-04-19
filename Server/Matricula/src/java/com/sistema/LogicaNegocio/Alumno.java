package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class Alumno {

    private String cedula;
    private String nombre;
    private String teléfono;
    private String email;
    private String fech_nac;
    private Carrera carrera;

    public Alumno() {

        cedula = "";
        nombre = "";
        teléfono = "";
        email = "";
        fech_nac = "";
        carrera = new Carrera();
    }

    public Alumno(String cedula, String nombre, String teléfono, String email, String fech_nac, Carrera carrera) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.teléfono = teléfono;
        this.email = email;
        this.fech_nac = fech_nac;
        this.carrera = carrera;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(String teléfono) {
        this.teléfono = teléfono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFech_nac() {
        return fech_nac;
    }

    public void setFech_nac(String fech_nac) {
        this.fech_nac = fech_nac;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

}
