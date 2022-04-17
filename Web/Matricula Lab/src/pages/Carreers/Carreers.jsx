import React, { Component } from 'react';
import '../../css/Carreers.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
export default class Carreers extends Component {
    constructor(props){
        super(props);
        this.state = {
            carreers: []
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateCarreersSort = this.updateCarreersSort.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    componentDidMount() {
      this.updateCarreersSort();
  }
    updateCarreersSort() {
      let options = {
          url: "http://localhost:8088/Matricula/api/carreras/listar",
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
              carreers: response.data
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
         label: 'Nombre',
         field: 'nombre',
         sort:  'asc',
          
       },
       {
         label: 'Titulo',
         field: 'titulo',
         sort:  'asc',
       }      
     ],
     rows: this.state.carreers   
     }     
     return data
}  
    render() {
        return (
            <div>            
              <MDBDataTable                     
                searchLabel='Buscar'
                //autoWidth={true}
                responsive
                hover={true}
                data={this.tabledata()}              
                />
            </div>
        );
    }
}
