import React, { Component } from "react";
import "../../css/Courses.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import { Link } from 'react-router-dom';
import Cookies from "universal-cookie";
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/grupos/profesor";
const isLoggedIn = localStorage.getItem("logged");
const user = JSON.parse(isLoggedIn);
let rol;
if (user != null) {
  rol = user.rol;
}
export default class Professor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      grupos: [],
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
    if (rol === "PROF") {
      options = {
        url: requestURL + "?ced=" + cookies.get("ced"),
        method: "GET",
        header: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      };
    }
    axios(options).then(response => {
        console.log(response.data)
        this.setState({
          grupos: response.data,
        });
    }).catch(error => {
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
          label: "Numero",
          field: "idEntidad",
          sort: "asc",
        },
        {
          label: "Horario",
          field: "horario",
          sort: "asc",
        },
      ],
      rows: this.state.grupos,
    };
    for(let i in data.rows){
        let cName = data.rows[i].curso.nombre;
        data.rows[i]['curso'] = 
        <Link 
          to={{ pathname: "/grupo", search: `?codigo=${data.rows[i].idEntidad}` }}>
          {cName}
        </Link> 
      }   
    return data;
  }
  render() {
    return (
      <div>
          <MDBDataTable
            searchLabel="Filtrar"
            responsive
            hover={true}
            data={this.tabledata()}
          />
      </div>
    );
  }
}
