 
 
import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGraduationCap,faChalkboardTeacher,faSchool,faUsers,faBookOpen,faBuildingColumns} from '@fortawesome/free-solid-svg-icons';

const cookies = new Cookies();

export default class Menu extends Component {
    /*
    Menu class corresponds to the component that is shown to the user
    if the auth-request was accepted by the server.
    */
    componentDidMount() {
        if (!(cookies.get('username', { path: process.env.REACT_APP_AUTH })
            && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
            && cookies.get('ced', { path: process.env.REACT_APP_AUTH })
            ))
            this.props.history.push('/login');
    }

    render() {

        const isLoggedIn = localStorage.getItem("logged");
        const user = JSON.parse(isLoggedIn);
        let rol;
        if (user != null) {
          rol = user.rol;
        }
        switch (rol) {
          case "ADM":
            return (
                <div>
                    <h1 className="text-center h1 mt-5">Bienvenido al Sistema de Gestión Academica</h1>
                    <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                        <div className="text-center h2 mb-5">
                            Cuenta con la Siguientes Opciones
                        </div>
                        <div className="text-center d-flex justify-content-between">
                        <div>
                        <div className='h3'>Carreras</div>
                        <FontAwesomeIcon icon={faBookOpen } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        <div>
                        <div className='h3'>Ciclos</div>
                        <FontAwesomeIcon icon={faSchool } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        <div>
                        <div className='h3'>Estudiantes</div>
                        <FontAwesomeIcon icon={faUsers } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        <div>
                        <div className='h3'>Profesores</div>
                        <FontAwesomeIcon icon={faChalkboardTeacher } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        <div>
                        <div className='h3'>Oferta Academica</div>
                        <FontAwesomeIcon icon={faGraduationCap } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        </div>
                    </Container>
                </div>
            );
          case "EST":
            return (
                <div>
                    <h1 className="text-center h1 mt-5">Bienvenido al Sistema de Gestión Academica</h1>
                    <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                        <div className="text-center h2 mb-5">
                            Cuenta con la Siguientes Opciones
                        </div>
                        <div className="text-center d-flex justify-content-center">
                        <div>
                        <div className='h3'>Historial Académico</div>
                        <FontAwesomeIcon icon={faBuildingColumns} className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        </div>
                    </Container>
                </div>
            );
          case "PROF":
            return (
                <div>
                    <h1 className="text-center h1 mt-5">Bienvenido al Sistema de Gestión Academica</h1>
                    <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                        <div className="text-center h2 mb-5">
                            Cuenta con la Siguientes Opciones
                        </div>
                        <div className="text-center d-flex justify-content-center mb-5">
                        <div>
                        <div className='h3'>Grupos</div>
                        <FontAwesomeIcon icon={faUsers } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        </div>
                    </Container>
                </div>
            );
          case "MAT":
            return (
                <div>
                    <h1 className="text-center h1 mt-5">Bienvenido al Sistema de Gestión Academica</h1>
                    <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                        <div className="text-center h2 mb-5">
                            Cuenta con la Siguientes Opciones
                        </div>
                        <div className="text-center d-flex justify-content-center ">
                        <div>
                        <div className='h3'>Estudiantes</div>
                        <FontAwesomeIcon icon={faUsers } className='icon' size='2x'></FontAwesomeIcon>
                        </div>
                        
                        </div>
                    </Container>
                </div>
            );
          default:
            return (
                <div>
                    <h1 className="text-center h1 mt-5">Error del sistema Por favor contactar al encargado de soporte</h1>
                 
                </div>
            );
        }




        
    }
}
