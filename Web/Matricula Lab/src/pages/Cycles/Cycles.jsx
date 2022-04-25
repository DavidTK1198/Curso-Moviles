import React, { Component } from 'react';
import '../../css/Cycles.css'
import axios from 'axios';
import { Button } from "react-bootstrap";
import { MDBDataTable } from 'mdbreact';
//import { Button } from 'react-bootstrap/lib/inputgroup';
export default class Cycles extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cycles: [],
            show: false,
            defaultCycle: null
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        //this.showLogs2 = this.showLogs2.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        //this.logChange = this.logChange.bind(this);
    }
    /*
    logChange = () => {
        console.log(this.state.defaultCycle)
    }*/
    openModal = () => {
        this.setState({ show: true });
    };
    closeModal = () => {
        this.setState({ show: false });
    };
    componentDidMount() {
        this.refreshPage();
    }
    /*
    storeDefaultCycle() {
        let actCycle
        if(this.state.defaultCycle!=null) actCycle = this.state.defaultCycle;
         else actCycle = {
            "annio": 2000,
            "estado": 1,
            "fec_final": "1/1/2000",
            "fec_inicio": "1/1/2000",
             "id": 0,
            "numero": 0
        }
        let options = {
            url: process.env.PROYECT_DOMAIN + `"Matricula/api/ciclos/cicloActivar"`,
            method: 'PUT',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: actCycle
        }
        axios(options)
            .then(response => {
      
            }).catch(error => {
                console.log(error);
                
            });

    }*/
    cicloActivar(){

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
/*
    showLogs2 = (e) => {
        this.setState({ defaultCycle: e });
    };*/
    estadoCiclo(id, opcion){
        if(opcion === 'Activar'){
            let options = {
                url: 'http://localhost:8088/Matricula/api/ciclos/cicloActivar',
                method: "PUT",
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                }
            }
            axios(options).then(response => {
                if(response.status === 200) {
                    console.log("OK!")
                }
                this.refreshPage()
            })
        }
        else{
            let options = {
                url: 'http://localhost:8088/Matricula/api/ciclos/cicloDesActivar',
                method: "PUT",
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                }
            }
            axios(options).then(response => {
                if(response.status === 200) {
                    console.log("OK!")
                }
                this.refreshPage()
            })
        }
    }
    tabledata() {

        let data = {
            columns: [
                {
                    label: 'Codigo',
                    field: 'id',
                    sort: 'asc',

                },
                {
                    label: 'Año',
                    field: 'annio',
                    sort: 'asc',

                },
                {
                    label: 'Numero',
                    field: 'numero',
                    sort: 'asc',
                },
                {
                    label: 'Estado',
                    field: 'estado',
                    sort: 'asc',
                },
                {
                    label: 'Fecha de Inicio',
                    field: 'fec_inicio',
                    sort: 'asc',
                },
                {
                    label: 'Fecha de fin',
                    field: 'fec_final',
                    sort: 'asc',
                },
                {
                    label: 'Estado de ciclo',
                    field: 'estadoC',
                    sort: 'asc'
                }

            ],
            rows: this.state.cycles   
        }
        for(let i in data.rows){
            if(data.rows[i].estado !== 1){
            data.rows[i]['estadoC'] = 
            <Button variant="secondary" onClick={() => this.estadoCiclo(data.rows[i].id, 'Desactivar')}>
              Activar
            </Button>
            }
            else{
                data.rows[i]['estadoC'] = 
                <Button variant="secondary" onClick={() => this.estadoCiclo(data.rows[i].id, 'Activar')}>
                Desactivar
              </Button>
              }
            }  
        return data
    }
    render() {


        return (
            <div>
                <MDBDataTable
                    hover
                    entriesOptions={[10, 30, 25]}
                    entries={10}
                    pagesAmount={4}
                    data={this.tabledata()}
                    /*getValueCheckBox={(e) => {
                        this.showLogs2(e);
                    }}*/
                    //onChange={this.logChange()}
                />
            </div>

        );
    }
}
