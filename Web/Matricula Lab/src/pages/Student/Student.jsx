import React, { Component } from "react";
import "../../css/Courses.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import Cookies from "universal-cookie";
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/inscripciones/alumno";
const isLoggedIn = localStorage.getItem("logged");
const user = JSON.parse(isLoggedIn);
let rol;
if (user != null) {
  rol = user.rol;
}
export default class Student extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inscripciones: [],
    };
    this.tabledata = this.tabledata.bind(this);
  }

  componentDidMount() {
    if (
      !(
        cookies.get("username", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("roles", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("ced", { path: process.env.REACT_APP_AUTH })
      )
    )
      this.props.history.push("/login");
    this.refreshPage();
  }
  refreshPage() {
    let options;
    if (rol === "EST") {
      options = {
        url: requestURL + "?ced=" + cookies.get("ced"),
        method: "GET",
        header: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      };
    } else {
      let query = new URLSearchParams(this.props.location.search);
       options = {
        url:requestURL+"?ced=" +
          query.get("cedula"),
        method: "GET",
        header: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      };
    }
    axios(options)
      .then((response) => {
        console.log(response.data);
        let inscripciones = response.data;
        Object.keys(response.data).map(function (key) {
          if (inscripciones[key].nota === 0)
            inscripciones[key].nota = "Sin Ingresar al Sistema";
        });

        this.setState({
          inscripciones: inscripciones,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }
  tabledata() {
    let data = {
      columns: [
        {
          label: "Curso",
          field: "curso",
          sort: "asc",
        },
        {
          label: "Nota",
          field: "nota",
          sort: "asc",
        },
      ],
      rows: this.state.inscripciones,
    };
    for(let i in data.rows){
      data.rows[i]['curso'] = data.rows[i].grupo.curso.nombre
    } 
    return data;
  }
  render() {
    return (
      <div>
        {cookies.get("roles") === "EST" ? (
          <MDBDataTable
            searchLabel="Filtrar"
            responsive
            hover={true}
            data={this.tabledata()}
          />
        ) : (
          <MDBDataTable
            searchLabel="Filtrar"
            responsive
            hover={true}
            data={this.tabledata()}
          />
        )}
      </div>
    );
  }
}
