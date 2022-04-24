import React from "react";
import "../css/Login.css";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";
import Cookies from "universal-cookie";
import {TripleMaze } 
from 'react-spinner-animated';
import ReactDOM from 'react-dom';
import 'react-spinner-animated/dist/index.css';
import { Container, Form, Button } from "react-bootstrap";
// <Image src={logo} fluid height={300} width={300} className='img-fluid hover-shadow' onClick={() => {console.log(cookies)}}/>
/*
va en component didmount
if (cookies.get('username', { path: process.env.REACT_APP_AUTH })
        && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
        && cookies.get('token', { path: process.env.REACT_APP_AUTH })
        && cookies.get('full_name', { path: process.env.REACT_APP_AUTH }))
        this.props.history.push('/menu');
*/

const requestURL = "http://localhost:8088/Matricula/api/login";
const cookies = new Cookies();

export default class Login extends React.Component {
  /*
  Login class controls the request-response communication
  sent and received by the client in order to get authorization to access the other modules.
  */
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      pwd: "",
      disabled: true,
      show:true,
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);

  }

  async handleSubmit(e) {
    e.preventDefault();
    this.setState(
      {
        username: e.target.username.value,
        pwd: e.target.pwd.value,
        disabled: false,
      },
      () => {
        var options = {
          url: requestURL,
          method: "POST",
          header: {
            Accept: "application/json",
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*'
          },
          data: {
            id: this.state.username,
            clave: this.state.pwd,
          },
        };
        axios(options).then((response) => {
          if (response.status===200) {
            cookies.set("username", response.data.nombre, {
              path: process.env.REACT_APP_AUTH,
              sameSite: "lax",
            });
            cookies.set("roles", response.data.rol, {
              path: process.env.REACT_APP_AUTH,
              sameSite: "lax",
            });
            cookies.set("ced", response.data.id, {
              path: process.env.REACT_APP_AUTH,
              sameSite: "lax",
            });
            this.props.history.push("/menu");
            localStorage.setItem("logged",JSON.stringify(response.data));
            window.location.reload(false);
          } else window.location.reload(false);
        });
      }
    );

  }

  componentDidMount() {
  }
  async handleInputChange(e) {
    this.setState({ [e.target.name]: e.target.value }, () => {
      if (this.state.username.length === 0 || this.state.pwd.length === 0)
        this.setState({ disabled: true });
      else this.setState({ disabled: false });
    });
  }

 

  render() {
    return (
      <Container className="w-auto text-center mx-auto p-3 mt-2 container">
        <Form className="centered-element" onSubmit={this.handleSubmit} >
          <Form.Group className="mb-3"></Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Cedula: </Form.Label>
            <Form.Control
              autoFocus
              type="text"
              name="username"
              onChange={this.handleInputChange}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Contraseña: </Form.Label>
            <Form.Control
              type="password"
              name="pwd"
              onChange={this.handleInputChange}
            />
          </Form.Group>
          <div className="text-center">
            <Button
              className="btnSFR"
              type="submit"
              disabled={this.state.disabled}
            >
              Ingresar
            </Button>
          </div>
          <div id="loginf"></div>
        </Form>
 
      </Container>
    );
  }
}
