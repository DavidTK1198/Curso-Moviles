package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class Profesor {
    private String cedula;
    private String nombre;
      private String teléfono;
    private String email;

    public String getCedula() {
        return cedula;
    }

    public Profesor(String cedula, String nombre, String teléfono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.teléfono = teléfono;
        this.email = email;
    }

    public Profesor() {
          this("","","","");
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
  
}
