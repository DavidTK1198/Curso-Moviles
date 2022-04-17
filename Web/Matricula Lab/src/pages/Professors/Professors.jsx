import React, { Component } from 'react';
import '../../css/Professors.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
export default class Professors extends Component {
    constructor(props){
        super(props);
        this.state = {
            professors: []
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
          url: "http://localhost:8088/Matricula/api/profesores/listar",
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
            professors: response.data
          });
      }).catch(error => {
        console.log(error);
        });
  }; 
  tabledata() {
    let data = {
     columns: [
       {
         label: 'cédula',
         field: 'cédula',
         sort:  'asc',
          
       },
       {
         label: 'email',
         field: 'email',
         sort:  'asc',
          
       },
       {
         label: 'nombre',
         field: 'nombre',
         sort:  'asc',
       },
       {
         label: 'teléfono',
         field: 'teléfono',
         sort:  'asc',
       }
       
     ],
     rows: this.state.professors   
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
