
import React, { Component } from 'react';
import { Navbar,  Container, Nav } from "react-bootstrap";

import '../css/NavigationBar.css';
export default class NavigationBar extends Component {
    render() {
        return (
            <div className="Header  container-fluid">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Container>
                    
                    <Nav>
                    <Nav.Link href="#/auth">Login</Nav.Link>   
                    <Nav.Link href="#/carreras">Carreras</Nav.Link>
                    <Nav.Link href="#/carrera">Carrera</Nav.Link>
                    <Nav.Link href="#/cursos">Cursos</Nav.Link>
                    <Nav.Link href="#/grupos">Grupos</Nav.Link>
                    <Nav.Link href="#/estudiantes">Estudiantes</Nav.Link>
                    <Nav.Link href="#/profesores">Profesores</Nav.Link>
                    <Nav.Link href="#/matricula">Matricula</Nav.Link>
                    <Nav.Link href="#/historial">Historial</Nav.Link>
                    </Nav>
                    
                  </Container>
                </Navbar>
            </div>
        );
    }
};
