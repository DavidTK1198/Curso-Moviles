import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import AddGroupModal from './Components/AddGroupModal';
import { ToastContainer, toast } from 'react-toastify';
import { Link } from 'react-router-dom';
import GenericModal from '../../components/GenericModal';
export default class Groups extends Component {
    constructor(props){
        super(props);
        this.state = {
            groups: [],
            show: false,
            showGroup: false
        }
        this.tabledata = this.tabledata.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
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
    let options = {
        url: "http://localhost:8088/Matricula/api/grupos/grupoCurso?codigo=" + query.get('codigo'),
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
        console.log(error)
      });
};
deleteGroup(idGroup){
  let options = {
    url: "",
    method: "DELETE",
    header: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }
}
axios(options)
    .then(response => {
        this.refreshPage();
        toast.success("El grupo fue eliminado correctamente!", {
            position: toast.POSITION.TOP_RIGHT,
            pauseOnHover: true,
            theme: 'colored',
            autoClose: 5000
        });
    }).catch(error => {
        toast.error("Error al remover el grupo.", {
            position: toast.POSITION.TOP_RIGHT,
            pauseOnHover: true,
            theme: 'colored',
            autoClose: 5000
        });
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
              }
              
            ],
            rows: this.state.groups   
            }
            for(let i in data.rows){
              let cName = data.rows[i]['nombre'];
              data.rows[i]['nombre'] = 
              <Link 
                to={{ pathname: "/grupo", search: `?codigo=${data.rows[i]['codigo']}` }}>
                {cName}
              </Link> 
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
              <ToastContainer />
            </div>
        );
    }
}
