import React, { Component } from "react";
import "../../css/Professors.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import { Link } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from "universal-cookie";
const cookies = new Cookies();
export default class Students extends Component {
  constructor(props) {
    super(props);
    this.state = {
      students: [],
    };
    this.tabledata = this.tabledata.bind(this);
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.updateStudentsSort = this.updateStudentsSort.bind(this);
  }

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
  openModal = () => {
    this.setState({ show: true });
  };
  closeModal = () => {
    this.setState({ show: false });
  };
  componentDidMount() {
    this.updateStudentsSort();
  }
  updateStudentsSort() {
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
        toast.error("Error cargando los estudiantes.", {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
      });
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
        }
      
      ],
      rows: this.state.students,
    };
    for (let i in data.rows) {
      if( cookies.get("roles")==="ADM")
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
            pathname: "/matricula",
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
        <ToastContainer />
      </div>
    );
  }
}
