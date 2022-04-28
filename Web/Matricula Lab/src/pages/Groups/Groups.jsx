import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import AddGroupModal from './Components/AddGroupModal';
import EditGroupModal from './Components/EditGroupModal';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
export default class Groups extends Component {
    constructor(props){
        super(props);
        this.state = {
            groups: [],
            show: false,
            showEdit: false,
            curso: "",
            ciclo: "",
            profesor: "",
            idG: ""
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
      this.refreshPage();
    };
    closeModal = () => {
      this.setState({ show: false });
      this.refreshPage();
    };
    openModalEdit(cur, cic, prof, id) {
      this.refreshPage();
      this.setState({ showEdit: true, curso: cur, ciclo: cic, profesor: prof, idG: id });
    };
    closeModalEdit = () => {
      this.setState({ showEdit: false });
      this.refreshPage();
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
      toast.success("Error cargando grupos!", {
        position: toast.POSITION.TOP_RIGHT,
        pauseOnHover: true,
        theme: 'colored',
        autoClose: 5000
    });
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
              <Button variant="secondary" key="EditButton" onClick={() => this.openModalEdit(data.rows[i].curso, data.rows[i].ciclo, data.rows[i].profesor, data.rows[i].idEntidad)}>
                Editar
              </Button> 
            }  
                
            return data
    }   
    render() {
      let query = new URLSearchParams(this.props.location.search);
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
                codigo={query.get('codigo')}
                show = {this.state.show}
                refreshPage={this.refreshPage}
                closeModal = {this.closeModal}
              />
              <EditGroupModal
                idG = {this.state.idG}
                curso = {this.state.curso}
                ciclo = {this.state.ciclo}
                profesor = {this.state.profesor}
                show={this.state.showEdit}
                refreshPage={this.refreshPage}
                closeModal={this.closeModalEdit}
              />
               <ToastContainer />
            </div>
        );
    }
}
