package com.sistema.Endpoint;
import com.sistema.Filter.RestfulFilter;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("/api")
public class RegistroApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
       
        classes.add(Login.class);
        classes.add(Usuarios.class);
        classes.add(Cursos.class);
        classes.add(Profesores.class);
        classes.add(Grupos.class);
        classes.add(Ciclos.class);
        classes.add(Alumnos.class);
        classes.add(Inscripciones.class);
        classes.add(Carreras.class);
        classes.add(RestfulFilter.class);
        classes.add(MultiPartFeature.class);
        
        return classes;
    }
}
