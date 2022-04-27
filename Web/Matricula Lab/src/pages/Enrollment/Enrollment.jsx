import React, { Component } from "react";
import "../../css/Professors.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import { Link } from "react-router-dom";
export default class Enrollment extends Component {
  constructor(props) {
    super(props);
    this.state = {
      student: {},
      cycle: {},
      cycles: [],
      groups: [],
      inscripciones: []
    };
    this.refreshPage = this.refreshPage.bind(this);
  }

  componentDidMount() {
    this.refreshPage();
  }
  refreshPage() {
    let options = {
      url: "http://localhost:8088/Matricula/api/alumnos/listar",
      method: "GET",
      header: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    };
    axios(options)
      .then((response) => {
        console.log(response.data);
        this.setState({
          students: response.data,
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
          label: "Cédula",
          field: "cedula",
          sort: "asc",
        },
        {
          label: "Correo",
          field: "email",
          sort: "asc",
        },
        {
          label: "Fecha de nacimiento",
          field: "fech_nac",
          sort: "asc",
        },
        {
          label: "Nombre",
          field: "nombre",
          sort: "asc",
        },
        {
          label: "Teléfono",
          field: "teléfono",
          sort: "asc",
        },
        {
          label: "Historial",
          field: "historial",
          sort: "asc",
        },
        {
          label: "Matricula",
          field: "matricula",
          sort: "asc",
        },
      ],
      rows: this.state.students,
    };
    for (let i in data.rows) {
      data.rows[i]["historial"] = (
        <Link
          to={{
            pathname: "/historial",
            search: `?cedula=${data.rows[i]["cedula"]}`,
          }}
        >
          ver
        </Link>
      );

      data.rows[i]["matricula"] = (
        <Link
          to={{
            pathname: "/historial",
            search: `?cedula=${data.rows[i]["cedula"]}`,
          }}
        >
          ver
        </Link>
      );
    }
    return data;
  }
  render() {
    return (
      <div>
        <MDBDataTable
          searchLabel="Buscar"
          responsive
          hover={true}
          data={this.tabledata()}
        />
      </div>
    );
  }
}
