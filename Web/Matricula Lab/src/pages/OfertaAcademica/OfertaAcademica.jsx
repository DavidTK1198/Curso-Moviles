import React, { Component } from "react";
import { Button } from "react-bootstrap";
import "../../css/Courses.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import { Link } from "react-router-dom";
import SelectCycleModal from "./SelectCycleModal";
import ReactDOM from 'react-dom';
import SelectCarrerModal from "./SelectCarrerModal";
export default class Courses extends Component {
  constructor(props) {
    super(props);
    this.state = {
      courses: [],
      show: false,
      showDel: false,
    };
    this.tabledata = this.tabledata.bind(this);
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.refreshPage = this.refreshPage.bind(this);
    this.openModalDel=this.openModalDel.bind(this)
    this.closeModalDel=this.closeModalDel.bind(this);
  }
  openModal = () => {
    this.setState({ show: true });
  };
  closeModal = () => {
    this.setState({ show: false });
  };
  openModalDel() {
    this.setState({ showDel: true });
  }
  closeModalDel = () => {
    this.setState({ showDel: false });
  };


  refreshPage() {
    return new Promise((resolve,reject)=>{
    let query = document.getElementById("carrera");
    let codigo=query.value.split("-").pop()
    let options = {
      url:
        "http://localhost:8088/Matricula/api/cursos/cursoCarrera?codigo=" +
        codigo,
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
          courses: response.data,
        });
        ReactDOM.render(
            <MDBDataTable
            searchLabel="Buscar"
            responsive
            hover={true}
            data={this.tabledata()}
          />,
          document.getElementById('tabla')
        );
      resolve("ok")
      })
      .catch((error) => {
        console.log(error);
        reject("fail")
      });
    })
  }
  tabledata() {
    let ciclo = document.getElementById("ciclo");
    let id = ciclo.getAttribute("data-ciclo");
    console.log(id);
    let data = {
      columns: [
        {
          label: "Codigo",
          field: "codigo",
          sort: "asc",
        },
        {
            label: "Nombre",
            field: "nombre",
            sort: "asc",
          },
        {
          label: "Creditos",
          field: "creditos",
          sort: "asc",
        },
        {
          label: "Horas semanales",
          field: "hsemanales",
          sort: "asc",
        },
        {
          label: "Grupos",
          field: "grupos",
          sort: "asc",
        },
      ],
      rows: this.state.courses,
    };
    for(let i in data.rows){
      data.rows[i]['grupos'] = <Link to={{ pathname: "/grupos", search: `?ciclo=${id}&codigo=${data.rows[i]['codigo']}` }}>
      Grupos</Link> 
    }   
    return data;
  }
  render() {
    return (
      <div>   
        <div className="d-flex justify-content-end mt-3 mr-3  w-50">
          <input type="text" className="w-100" placeholder="Ciclo a selecionar..." readOnly  id="ciclo"       
           data-ciclo=""></input>

          <Button
            size="sm"
            onClick={this.openModal}
            variant="success"
            key="AddButton"
            className="w-25"
          >
            <i className="bi bi-plus-square"></i> Selecionar ciclo
          </Button>
        </div>
        <div className="d-flex justify-content-end mt-3 mr-3 w-50">
          <input
            type="text"
            placeholder="Carrera a selecionar..."
            readOnly 
            className="w-100"
            id="carrera"
          />
          <Button
            size="sm"
            onClick={this.openModalDel}
            variant="success"
            key="AddButton"
            className="w-25"
          >
            <i className="bi bi-plus-square"></i> Selecionar carrera
          </Button>
        </div>
        <div className='d-flex justify-content-end mt-3 mr-3'>   
              <Button size="sm"    onClick={async() => await this.refreshPage()}variant="primary" key="AddButton">
                Cargar Datos
              </Button>
              </div> 
        <div id="tabla"></div>
        <SelectCycleModal
          show={this.state.show}
          careerID={this.state.careerID}
          closeModal={this.closeModal}
        />
        <SelectCarrerModal
          show={this.state.showDel}
          closeModal={this.closeModalDel}
        />
      </div>
    );
  }
}
