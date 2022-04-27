import React, { Component } from 'react';
import '../../css/Cycles.css'
import axios from 'axios';
import { Button } from "react-bootstrap";
import { MDBDataTable } from 'mdbreact';
export default class Cycles extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cycles: [],
            show: false,
            defaultCycle: null
        }
        this.tabledata = this.tabledata.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.estadoCiclo=this.estadoCiclo.bind(this);

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

    estadoCiclo(id, opcion){

        let data;
        if(opcion === 'Activar'){
           data= {
                'id': id,
                'estado':1
            };
            var url="http://localhost:8088/Matricula/api/ciclos/cicloActivar";
            let request = new Request(url, {method: 'PUT', headers: { 'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
            'Access-Control-Allow-Methods':'*',
        },body: JSON.stringify(data)});
              (async ()=>{
                const response = await fetch(request);
                this.refreshPage();                           
            })();
        }
        else{
            data= {
                'id': id,
                'estado':2
            };
            var url="http://localhost:8088/Matricula/api/ciclos/cicloDesActivar";
            let request = new Request(url, {method: 'PUT', headers: { 'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
            'Access-Control-Allow-Methods':'*',
        },body: JSON.stringify(data)});
              (async ()=>{
                const response = await fetch(request);
                this.refreshPage();                           
            })();
        }
    }
    tabledata() {

        let data = {
            columns: [
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
                    label: 'Gestión de estado',
                    field: 'estadoC',
                    sort: 'asc'
                }, 

            ],
            rows: this.state.cycles   
        }
        for(let i in data.rows){
            if(data.rows[i].estado === 1){
                data.rows[i]['estado']="Activo"
            data.rows[i]['estadoC'] = 
            <Button variant="secondary" onClick={() => this.estadoCiclo(data.rows[i].id, 'Desactivar')}>
              Desactivar
            </Button>
            }
            else{
                data.rows[i]['estadoC'] = 
                <Button variant="secondary" onClick={() => this.estadoCiclo(data.rows[i].id, 'Activar')}>
                Activar
              </Button>
              data.rows[i]['estado']="Inactivo"
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
                />
            </div>

        );
    }
}
