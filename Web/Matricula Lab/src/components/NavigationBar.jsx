import React, { Component } from "react";
import { Navbar, Container, Nav } from "react-bootstrap";
import { withRouter } from "react-router";
import Cookies from "universal-cookie";
import "../css/NavigationBar.css";
const cookies = new Cookies();
class NavigationBar extends Component {
  componentDidMount() {
    if (
      !(
        cookies.get("username", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("roles", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("ced", { path: process.env.REACT_APP_AUTH })
      )
    ) {
      this.props.history.push("/login");
      localStorage.clear();
    }
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
          <div className="Header  container-fluid">
            <Navbar collapseOnSelect expand="lg" variant="dark">
              <Container>
                <Nav>
                  <Nav.Link href="#/menu">Inicio</Nav.Link>
                  <Nav.Link href="#/carreras">Carreras</Nav.Link>
                  <Nav.Link href="#/ciclos">Ciclos</Nav.Link>
                  <Nav.Link href="#/estudiantes">Estudiantes</Nav.Link>
                  <Nav.Link href="#/profesores">Profesores</Nav.Link>
                  <Nav.Link href="#/oferta"> Oferta académica</Nav.Link>
                </Nav>
              </Container>
              <Nav>
                <Nav.Link href="">{user.nombre}</Nav.Link>
                <Nav.Link
                  onClick={() => {
                    this.props.history.push("/logout");
                  }}
                >
                  Salir
                </Nav.Link>
              </Nav>
            </Navbar>
          </div>
        );
      case "EST":
        return (
          <div className="Header  container-fluid">
            <Navbar collapseOnSelect expand="lg" variant="dark">
              <Container>
                <Nav>
                  <Nav.Link href="#/menu">Inicio</Nav.Link>
                  <Nav.Link href="#/historial">Historial Académico</Nav.Link>
                </Nav>
              </Container>
              <Nav>
                <Nav.Link href="">{user.nombre}</Nav.Link>
                <Nav.Link
                  onClick={() => {
                    this.props.history.push("/logout");
                  }}
                >
                  Salir
                </Nav.Link>
              </Nav>
            </Navbar>
          </div>
        );
      case "PROF":
        return (
          <div className="Header  container-fluid">
            <Navbar collapseOnSelect expand="lg" variant="dark">
              <Container>
                <Nav>
                  <Nav.Link href="#/menu">Inicio</Nav.Link>
                  <Nav.Link href="#/misGrupos">Grupos</Nav.Link>
                </Nav>
              </Container>
              <Nav>
                <Nav.Link href="">{user.nombre}</Nav.Link>
                <Nav.Link
                  onClick={() => {
                    this.props.history.push("/logout");
                  }}
                >
                  Salir
                </Nav.Link>
              </Nav>
            </Navbar>
          </div>
        );
      case "MAT":
        return (
          <div className="Header  container-fluid">
            <Navbar collapseOnSelect expand="lg" variant="dark">
              <Container>
                <Nav>
                  <Nav.Link href="#/carreras">Carreras</Nav.Link>
                  <Nav.Link href="#/grupos">Grupos</Nav.Link>
                  <Nav.Link href="#/estudiantes">Estudiantes</Nav.Link>
                  <Nav.Link href="#/profesores">Profesores</Nav.Link>
                  <Nav.Link href="#/matricula">Matricula</Nav.Link>
                  <Nav.Link href="#/historial">Historial</Nav.Link>
                  <Nav.Link
                    onClick={() => {
                      this.props.history.push("/logout");
                    }}
                  >
                    Salir
                  </Nav.Link>
                </Nav>
              </Container>
            </Navbar>
          </div>
        );
      default:
        return (
          <div className="Header  container-fluid">
            <Navbar collapseOnSelect expand="lg" variant="dark">
              <Container>
                <Nav></Nav>
              </Container>
            </Navbar>
          </div>
        );
    }
  }
}
export default withRouter(NavigationBar);
