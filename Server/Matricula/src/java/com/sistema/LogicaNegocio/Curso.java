package com.sistema.LogicaNegocio;
import java.util.ArrayList;
/**
 *
 * @author DavidTK1198
 */
public class Curso {
    private String nombre;
      private int hsemanales;
      private String  codigo;
      private int creditos;
      private Carrera carrera;
    private ArrayList<Grupo> grupos;

    public int getHsemanales() {
        return hsemanales;
    }

    public Curso() {
            this("","",0,0,new Carrera());
            this.grupos=new ArrayList<>();
    }

    public Curso( String codigo,String nombre, int creditos,int hsemanales, Carrera carrera) {
        this.nombre = nombre;
        this.hsemanales = hsemanales;
        this.codigo = codigo;
        this.creditos = creditos;
        this.carrera = carrera;
        this.grupos=new ArrayList<>();
    }

    public void setHsemanales(int hsemanales) {
        this.hsemanales = hsemanales;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

   
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarGrupo(Grupo grupo){
        grupos.add(grupo);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public ArrayList getGrupos(){
        return grupos;
    }

    public void setGrupos(ArrayList<Grupo> grupos){
        this.grupos = grupos;
    }
}
