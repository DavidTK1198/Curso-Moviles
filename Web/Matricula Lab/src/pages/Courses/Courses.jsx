import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import { Link } from 'react-router-dom';
import AddCourseModal from './Components/AddCourseModal';
import GenericModal from '../../components/GenericModal';
export default class Courses extends Component {
    constructor(props){
        super(props);
        this.state = {
            courses: [],
            show: false,
            showDel: false,
            delID: "",
            careerID: ""
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        //this.openModalDel = this.openModalDel.bind(this);
        //this.closeModalDel = this.closeModalDel.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.deleteCourse = this.deleteCourse.bind(this);
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
    let options = {
        url: "http://localhost:8088/Matricula/api/cursos/cursoCarrera?codigo=" + query.get('codigo'),
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
            courses: response.data,
            careerID: query.get('codigo')
        });
    }).catch(error => {
      console.log(error);
      });
};
  deleteCourse(){
    let options = {
      url: 'http://localhost:8088/Matricula/api/cursos?id=' + this.state.delID,
      method: 'PUT',
      header: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods':'*',
      }
    }
  axios(options)
      .then(response => {
          this.closeModalDel();
          this.refreshPage();
      }).catch(error => {
          console.log(error);
      });
  }
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
              },
              {
                label: 'Eliminar',
                field: 'delete',
                sort:  'asc',
              }            
            ],
            rows: this.state.courses   
            }          
            for(let i in data.rows){
              data.rows[i]['delete'] = 
              <Button variant="secondary" onClick={() => this.openModalDel(data.rows[i]['codigo'])}>
                Eliminar
              </Button> 
            }   
            return data
    }   
    render() {
        return (
            <div>     
            <div className='d-flex justify-content-end mt-3 mr-3'>   
              <Button size="sm" onClick={this.openModal} variant="success" key="AddButton">
                <i className="bi bi-plus-square"></i> {' '}
                Agregar curso
              </Button>
              </div>       
              <MDBDataTable
                searchLabel='Buscar'
                responsive
                hover={true}
                data={this.tabledata()}              
              />
              <AddCourseModal
                show = {this.state.show}
                careerID = {this.state.careerID}
                refreshPage = {this.refreshPage}
                closeModal = {this.closeModal}
              />
              <GenericModal
                show={this.state.showDel}
                close={this.closeModalDel}
                action={this.deleteCourse}
                header={"Eliminar curso"}
                body={"¿Esta seguro que desea eliminar este curso?"} />
            </div>
        );
    }
}
