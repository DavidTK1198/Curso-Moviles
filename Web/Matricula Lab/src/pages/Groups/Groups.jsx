import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import AddGroupModal from './Components/AddGroupModal';
export default class Groups extends Component {
    constructor(props){
        super(props);
        this.state = {
            groups: [],
            show: false
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateGroupsSort = this.updateGroupsSort.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    componentDidMount() {
      this.updateGroupsSort();
  }
    updateGroupsSort() {
      let options = {
          url: "http://localhost:8088/Matricula/api/grupos/listar",
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
              courses: response.data
          });
      }).catch(error => {
        console.log(error);
        });
  };
    tabledata() {
           let data = {
            columns: [
              {
                label: 'Codigo',
                field: 'codigo',
                sort:  'asc',
                 
              },
              {
                label: 'Creditos',
                field: 'creditos',
                sort:  'asc',
                 
              },
              {
                label: 'Horas semanales',
                field: 'hsemanales',
                sort:  'asc',
              },
              {
                label: 'Nombre',
                field: 'nombre',
                sort:  'asc',
              }
              
            ],
            rows: this.state.groups   
            }         
            return data
    }   
    render() {
        return (
            <div>
              <Button size="sm" onClick={this.openModal} variant="success" key="AddIncidenceButton">
                <i className="bi bi-plus-square"></i> {' '}
                    Agregar Incidencia
              </Button>           
              <MDBDataTable 
                searchLabel='Buscar'
                //autoWidth={true}
                responsive
                hover={true}
                data={this.tabledata()}              
                />
              <AddGroupModal
                show = {this.state.show}
                closeModal = {this.closeModal}
              />
            </div>
        );
    }
}
