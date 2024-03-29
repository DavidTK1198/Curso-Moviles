import React, { Component } from 'react';
import '../../css/Professors.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
export default class Professors extends Component {
    constructor(props){
        super(props);
        this.state = {
            professors: []
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updateProfessorsSort = this.updateProfessorsSort.bind(this);
    }
    openModal = () => {
      this.setState({ show: true });
    };
    closeModal = () => {
      this.setState({ show: false });
    };
    componentDidMount() {
      this.updateProfessorsSort();
  }
    updateProfessorsSort() {
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
        toast.error("Error cargando datos del profesor!.", {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
      });
        });
  }; 
  tabledata() {
    let data = {
     columns: [
       {
         label: 'Cédula',
         field: 'cedula',
         sort:  'asc',
          
       },
       {
         label: 'Correo',
         field: 'email',
         sort:  'asc',
          
       },
       {
         label: 'Nombre',
         field: 'nombre',
         sort:  'asc',
       },
       {
         label: 'Teléfono',
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
                //autoWidth={true}
                responsive
                hover={true}
                data={this.tabledata()}              
                />
                <ToastContainer />
            </div>
        );
    }
}
