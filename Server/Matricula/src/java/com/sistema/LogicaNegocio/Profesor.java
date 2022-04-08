package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class Profesor {
    private String cédula;
    private String nombre;
      private String teléfono;
    private String email;

    public String getCédula() {
        return cédula;
    }

    public Profesor(String cédula, String nombre, String teléfono, String email) {
        this.cédula = cédula;
        this.nombre = nombre;
        this.teléfono = teléfono;
        this.email = email;
    }

    public Profesor() {
    }

    public void setCédula(String cédula) {
        this.cédula = cédula;
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
  
}
