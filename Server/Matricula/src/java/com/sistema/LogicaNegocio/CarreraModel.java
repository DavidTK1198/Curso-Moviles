package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioCarrera;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class CarreraModel {

    private static CarreraModel uniqueInstance;
    private Carrera current;
    private final ServicioCarrera carrera_DBA;
    private List<Carrera> carreras;

    public static CarreraModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CarreraModel();

        }
        return uniqueInstance;
    }

    private CarreraModel() {
        this.current = new Carrera();
        this.carreras = new ArrayList<>();
        this.carrera_DBA = ServicioCarrera.getInstance();
    }

    public Carrera getCurrent() {
        return current;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    public void setCurrent(Carrera current) {
        this.current = current;
    }

    public void todasLasCarreras() throws GlobalException, NoDataException {
        carreras = (List<Carrera>) carrera_DBA.listarCarrera();
    }

    public void buscarporCodigo() throws GlobalException, NoDataException {
        current = carrera_DBA.buscarCarrera(current.getCodigo(), "codigo");
    }


    public void buscarporNombre() throws GlobalException, NoDataException {
        current = carrera_DBA.buscarCarrera(current.getNombre(), "nombre");
    }

    public void insertarCarrera(Carrera p) throws GlobalException, NoDataException {
       carrera_DBA.insertarCarrera(current);
    }
}
