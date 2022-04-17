import React, { Component } from 'react';
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
export default class Courses extends Component {
    constructor(props){
        super(props);
        this.state = {
            courses: []
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateCoursesSort = this.updateCoursesSort.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    componentDidMount() {
      this.updateCoursesSort();
  }
    updateCoursesSort() {
      let options = {
          url: "http://localhost:8088/Matricula/api/cursos/listar",
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
                label: 'codigo',
                field: 'codigo',
                sort:  'asc',
                 
              },
              {
                label: 'creditos',
                field: 'creditos',
                sort:  'asc',
                 
              },
              {
                label: 'hsemanales',
                field: 'hsemanales',
                sort:  'asc',
              },
              {
                label: 'nombre',
                field: 'nombre',
                sort:  'asc',
              }
              
            ],
            rows: this.state.courses   
            }         
            return data
    }   
    render() {
        return (
            <div>           
              <MDBDataTable 
                searchLabel='Buscar'
                autoWidth={true}
                responsive
                hover={true}
                data={this.tabledata()}              
                />
            </div>
        );
    }
}
