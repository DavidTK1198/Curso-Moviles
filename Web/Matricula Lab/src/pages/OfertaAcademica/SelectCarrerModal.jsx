import React, { Component } from "react";
import "../../css/Careers.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import { Modal, Button } from "react-bootstrap";
export default class SelectCarrerModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      careers: [],
      show: false,
    };
    this.tabledata = this.tabledata.bind(this);
    this.refreshPage = this.refreshPage.bind(this);
    this.selecionarCarrera = this.selecionarCarrera.bind(this);
  }

  componentDidMount() {
    this.refreshPage();
  }

  selecionarCarrera(codigo) {
    let input = document.getElementById("carrera");
    let carrera = this.state.careers.find(element => element.codigo === codigo);
    input.value = `${carrera.nombre}-${carrera.codigo}`;
    this.setState({
      show:false
    })
  }
  refreshPage() {
    let options = {
      url: "http://localhost:8088/Matricula/api/carreras/listar",
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
          careers: response.data,
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
          label: "Titulo",
          field: "titulo",
          sort: "asc",
        },
        {
          label: "Selecionar Carrera",
          field: "seleccionar",
          sort: "asc",
        },
      ],
      rows: this.state.careers,
    };

    for (let i in data.rows) {
      data.rows[i]["seleccionar"] = (
        <Button
          variant="primary"
          onClick={() =>this.selecionarCarrera(data.rows[i].codigo)}
        >
          Selecionar
        </Button>
      );
    }
    return data;
  }
  render() {
    let render = this.props.show;
    let closeModal = this.props.closeModal;
    return (
      <Modal
        size="lg"
        show={render}
        onHide={() => {
          closeModal();
        }}
      >
        <Modal.Header closeButton>Selecione la Carrera</Modal.Header>
        <Modal.Body>
          <MDBDataTable
            hover
            entriesOptions={[10, 30, 25]}
            entries={10}
            pagesAmount={4}
            data={this.tabledata()}
          />
        </Modal.Body>
      </Modal>
    );
  }
}
