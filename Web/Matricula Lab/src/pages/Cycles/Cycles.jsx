import React, { Component, useState } from 'react';
import '../../css/Cycles.css'
import axios from 'axios';
import { MDBDataTable, MDBDataTableV5 } from 'mdbreact';
import { Button } from 'react-bootstrap/lib/inputgroup';
export default class Cycles extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cycles: [],
            defaultCycle: null
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateCyclesSort = this.updateCyclesSort.bind(this);
        this.logChange = this.logChange.bind(this);
    }
    logChange = () => {
        console.log(this.state.defaultCycle)
    }
    openModal = () => {
        this.setState({ show: true });
    };
    closeModal = () => {
        this.setState({ show: false });
    };
    componentDidMount() {
        this.updateCyclesSort();
    }
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

    }
    updateCyclesSort() {
        let options = {
            url: process.env.PROYECT_DOMAIN + "Matricula/api/ciclos/listar",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }
        axios(options).then(response => {
            console.log(response.data)
            this.setState({

                cycles: response.data,
                defaultCycle: cycles.length
            });
        }).catch(error => {
            console.log(error);
        });
    };

    showLogs2 = (e) => {
        this.state.defaultCycle = e;
    };

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

            ],
            rows: this.state.cycles
        }
        return data
    }
    render() {


        return (
            <div>
                <MDBDataTableV5
                    hover
                    entriesOptions={[10, 30, 25]}
                    entries={10}
                    pagesAmount={4}
                    data={this.tabledata()}
                    checkbox
                    headCheckboxID='id2'
                    bodyCheckboxID='checkboxes2'
                    getValueCheckBox={(e) => {
                        showLogs2(e);
                    }}
                    onChange={this.logChange()}
                />
                <Row>
                    <Button onChange={storeDefaultCycle()}>Guardar cambios</Button>
                </Row>
            </div>

        );
    }
}
