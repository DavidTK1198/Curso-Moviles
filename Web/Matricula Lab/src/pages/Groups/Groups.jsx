import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import AddGroupModal from './Components/AddGroupModal';
import { Link } from 'react-router-dom';
import GenericModal from '../../components/GenericModal';
export default class Groups extends Component {
    constructor(props){
        super(props);
        this.state = {
            groups: [],
            show: false,
            delID: ""
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        //this.openModalDel = this.openModalDel.bind(this);
        //this.closeModalDel = this.closeModalDel.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.deleteGroup = this.deleteGroup.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    openModalDel(id) {
      this.setState({ showDel: true, delID: id });
    };
    closeModalDel = () => {
      this.setState({ showDel: false });
    };
    componentDidMount() {
      this.refreshPage();
  }
  refreshPage() {
    let query = new URLSearchParams(this.props.location.search);
    console.log(query.get('codigo'))
    console.log(query.get('ciclo'))
    let options = {
        url: 'http://localhost:8088/Matricula/api/grupos/listar?ciclo='+ query.get('ciclo') + '&codigo=' + query.get('codigo'),
        method: "GET",
        header: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods':'*',
        }
    }
    axios(options).then(response => {
        console.log(response.data)
        this.setState({
            groups: response.data
        });
    }).catch(error => {
        console.log(error)
      });
};
deleteGroup(idGroup){
  let options = {
    url: 'http://localhost:8088/Matricula/api/grupos?id=' + this.state.delID,
    method: "PUT",
    header: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
}
axios(options)
    .then(response => {
        this.refreshPage();
    }).catch(error => {
      console.log(error);
    });
}
    tabledata() {
           let data = {
            columns: [
              {
                label: 'Curso',
                field: 'curso',
                sort:  'asc',
                 
              },
              {
                label: 'Horario',
                field: 'horario',
                sort:  'asc',
              },
              {
                label: 'Total',
                field: 'cupo',
                sort:  'asc',
              },
              {
                label: 'Disponible',
                field: 'disponible',
                sort:  'asc',
              },
              {
                label: 'Profesor',
                field: 'profesor',
                sort:  'asc',
              }
              
            ],
            rows: this.state.groups   
            }
            for(let i in data.rows){
              data.rows[i]['curso'] =data.rows[i].curso.nombre;
              data.rows[i]['profesor'] =data.rows[i].profesor.nombre;
            } 
                
            return data
    }   
    render() {
        return (
            <div>
              <Button size="sm" onClick={this.openModal} variant="success" key="AddButton">
                <i className="bi bi-plus-square"></i> {' '}
                    Agregar grupo
              </Button>           
              <MDBDataTable 
                searchLabel='Buscar'
                responsive
                hover={true}
                data={this.tabledata()}              
                />
              <AddGroupModal
                show = {this.state.show}
                closeModal = {this.closeModal}
              />
              <GenericModal
                show={this.state.showDel}
                close={this.closeModalDel}
                action={this.deleteCourse}
                header={"Eliminar grupo"}
                body={"¿Esta seguro que desea eliminar este grupo?"} />
            </div>
        );
    }
}
