import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import AddGroupModal from './Components/AddGroupModal';
import EditGroupModal from './Components/EditGroupModal';
import { Link } from 'react-router-dom';
export default class Groups extends Component {
    constructor(props){
        super(props);
        this.state = {
            groups: [],
            show: false,
            showEdit: false,
            curso: "",
            ciclo: "",
            profesor: ""
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        //this.openModalDel = this.openModalDel.bind(this);
        //this.closeModalDel = this.closeModalDel.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    openModalEdit(cur, cic, prof) {
      console.log(cur)
      console.log(cic.id)
      console.log(prof)
      this.setState({ showEdit: true, curso: cur, ciclo: cic, profesor: prof });
    };
    closeModalEdit = () => {
      this.setState({ showEdit: false });
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
              },              
              {
                label: 'Editar',
                field: 'editar',
                sort:  'asc',
              } 
              
            ],
            rows: this.state.groups   
            }
            for(let i in data.rows){
              data.rows[i]['curso'] =data.rows[i].curso.nombre;
              data.rows[i]['profesor'] =data.rows[i].profesor.nombre;
            } 
            for(let i in data.rows){
              data.rows[i]['editar'] = 
              <Button variant="secondary" key="EditButton" onClick={() => this.openModalEdit(data.rows[i].curso, data.rows[i].ciclo, data.rows[i].profesor)}>
                Editar
              </Button> 
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
                curso = {this.state.curso}
                ciclo = {this.state.ciclo}
                show = {this.state.show}
                closeModal = {this.closeModal}
              />
              <EditGroupModal
                curso = {this.state.curso}
                ciclo = {this.state.ciclo}
                profesor = {this.state.profesor}
                show={this.state.showEdit}
                closeModal={this.closeModalEdit}
              />
            </div>
        );
    }
}
