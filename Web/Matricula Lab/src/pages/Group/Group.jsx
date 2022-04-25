import React, { Component } from "react";
import "../../css/Courses.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import Cookies from "universal-cookie";
import { InputGroup, Button ,FormControl} from "react-bootstrap";
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/inscripciones";
const isLoggedIn = localStorage.getItem("logged");
const user = JSON.parse(isLoggedIn);
let rol;
if (user != null) {
  rol = user.rol;
}
export default class Group extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inscripciones: [],
    };
    this.tabledata = this.tabledata.bind(this);
    this.enviarNota = this.enviarNota.bind(this);
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

  enviarNota(id){
    let nota=document.getElementById(id).value;
    let data= {
      'idEntidad':id,
      'nota': nota,
      }
    var url="http://localhost:8088/Matricula/api/inscripciones";
    let request = new Request(url, {method: 'PUT', headers: { 'Content-Type': 'application/json'},body: JSON.stringify(data)});
      (async ()=>{
        const response = await fetch(request);
        this.refreshPage();                           
    })();

  }
  refreshPage() {
    let options;
    let query = new URLSearchParams(this.props.location.search);
    options = {
      url: requestURL+"/grupo"+"?id=" + query.get("codigo"),
      method: "GET",
      header: {
        'Accept': "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        'Access-Control-Allow-Methods':'*',
      },
    };
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
          label: "Nombre",
          field: "nombre",
          sort: "asc",
        },
        {
          label: "Cédula",
          field: "cedula",
          sort: "asc",
        },
        {
          label: "Nota",
          field: "nota",
          sort: "asc",
        },
        {
          label: 'Digitar',
          field: 'digitar',
          sort:  'asc',
        },
        {
          label: 'Actualizar',
          field: 'actualizar',
          sort:  'asc',
        }
        ,
      ],
      rows: this.state.inscripciones,
    };
      for (let i in data.rows) {
        data.rows[i]["nombre"] = data.rows[i].estudiante.nombre;
        data.rows[i]["cedula"] = data.rows[i].estudiante.cedula;
        data.rows[i]['digitar'] = 
        <InputGroup className="mb-3" >
        <FormControl
          placeholder="Nota"
          aria-label="Nota"
          aria-describedby="basic-addon1" id={data.rows[i].idEntidad}
        />
      </InputGroup>
      data.rows[i]['actualizar'] = 
      <Button variant="success" onClick={() => this.enviarNota(data.rows[i].idEntidad)} >
        Actualizar
      </Button> 
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
