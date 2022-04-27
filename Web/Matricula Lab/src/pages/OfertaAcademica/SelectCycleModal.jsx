import React, { Component } from "react";
import axios from "axios";
import "../../css/AddGroupModal.css";
import { Modal, Button, Form } from "react-bootstrap";
import { MDBDataTable } from 'mdbreact';

export default class SelectCycleModal extends Component {
  constructor(props) {
    super(props);
    this.state = {

        cycles: [],
        show: false,
        defaultCycle: null
    };
    this.tabledata = this.tabledata.bind(this);
    this.refreshPage = this.refreshPage.bind(this);
    this.selecionarCiclo = this.selecionarCiclo.bind(this);
  }

  componentDidMount() {
    this.refreshPage();
}

refreshPage() {
    let options = {
        url: 'http://localhost:8088/Matricula/api/ciclos/listar',
        method: "GET",
        header: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        }
    }
    axios(options).then(response => {
        let ciclo = response.data.map(function(c){
            if(c.estado === 1)
                return c
        })
        console.log(response.data)
        this.setState({
            cycles: response.data,
            defaultCycle: ciclo[0]
        });
        console.log(this.state.defaultCycle)
    })
};

selecionarCiclo(codigo) {
  let input = document.getElementById("ciclo");
  let ciclo = this.state.cycles.find(element => element.id === codigo);
  input.value = `${ciclo.annio}-${ciclo.numero}`;
}
  tabledata() {
    let data = {
      columns: [
        {
          label: "Año",
          field: "annio",
          sort: "asc",
        },
        {
          label: "Numero",
          field: "numero",
          sort: "asc",
        },
        {
          label: "Estado",
          field: "estado",
          sort: "asc",
        },
        {
          label: "Fecha de Inicio",
          field: "fec_inicio",
          sort: "asc",
        },
        {
          label: "Fecha de fin",
          field: "fec_final",
          sort: "asc",
        },
        {
          label: "Selecionar ciclo",
          field: "seleccionar",
          sort: "asc",
        },
      ],
      rows: this.state.cycles,
    };
    for (let i in data.rows) {
      if(data.rows[i].estado ===1 || data.rows[i].estado==="Activo"){
        data.rows[i]['estado']="Activo"
    data.rows[i]['seleccionar'] = 
    <Button variant="primary"  onClick={() =>this.selecionarCiclo(data.rows[i].id)}>
      Selecionar
    </Button>
    }
    else{
      data.rows[i]["seleccionar"] = (
        <Button
          variant="primary"
          onClick={() =>this.selecionarCiclo(data.rows[i].id)}
        >
          Selecionar
        </Button>
      );
      
      data.rows[i]['estado']="Inactivo"
      }
    }
    return data;
  }

  render() {
    let render = this.props.show;
    let closeModal = this.props.closeModal;
    return (

        
      <Modal   size="lg"
        show={render}
        onHide={() => {
          closeModal();
        }}
      >
        <Modal.Header closeButton>Selecione el ciclo</Modal.Header>
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
