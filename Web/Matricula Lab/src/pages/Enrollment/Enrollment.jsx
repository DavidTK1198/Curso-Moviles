import React, { Component } from "react";
import "../../css/Courses.css";
import axios from "axios";
import { MDBDataTable } from "mdbreact";
import Cookies from "universal-cookie";
import { ToastContainer, toast } from 'react-toastify';
import { Modal, Button, Form,} from "react-bootstrap";
import SelectCycleModal from "../OfertaAcademica/SelectCycleModal";
import 'react-toastify/dist/ReactToastify.css';
import ReactDOM from 'react-dom';
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/inscripciones/alumno";
const isLoggedIn = localStorage.getItem("logged");
const user = JSON.parse(isLoggedIn);
let rol;
if (user != null) {
  rol = user.rol;
}
export default class Student extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inscripciones: [],
      show: false,
      grupos:[]
    };
    this.tabledata = this.tabledata.bind(this);
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.cargar=this.cargar.bind(this);
    this.tabledata2 = this.tabledata2.bind(this);
    this.tabledata3 = this.tabledata3.bind(this);
  }

  openModal = () => {
    this.setState({ show: true });
  };
  closeModal = () => {
    this.setState({ show: false });
  };
  componentDidMount() {
    if (
      !(
        cookies.get("username", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("roles", { path: process.env.REACT_APP_AUTH }) &&
        cookies.get("ced", { path: process.env.REACT_APP_AUTH })
      )
    )
      this.props.history.push("/login");
    this.refreshPage();
  }
  refreshPage() {
    let options;
      let query = new URLSearchParams(this.props.location.search);
       options = {
        url:requestURL+"?ced=" +
          query.get("cedula"),
        method: "GET",
        header: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      };

    axios(options)
      .then((response) => {
        let inscripciones = response.data;
        this.setState({
          inscripciones: inscripciones,
        });
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error cargando los datos del estudiante.", {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
      });
      });

  }

  cargar(){
    return new Promise((resolve,reject)=>{
      let ciclo = document.getElementById("ciclo");
     if(ciclo.value === ""){return reject("error")}
     let id=ciclo.getAttribute("data-ciclo");
     if(ciclo.value === ""){return reject("error")}
      let options = {
        url: 'http://localhost:8088/Matricula/api/grupos/ciclo?id='+id,
        method: "GET",
        header: {
          Accept: "application/json",
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
      };
      axios(options)
        .then((response) => {
          this.setState({
            grupos: response.data,
          });
         
        }).then(()=>{
        resolve("ok")
        }
          
        )
        .catch((error) => {
          console.log(error);
          reject("fail")
        this.setState({
          grupos:[]
         })
        });
      }).then(()=>{
        ReactDOM.render(
          <MDBDataTable
          searchLabel="Buscar"
          responsive
          hover={true}
          data={this.tabledata2()}
        />,
        document.getElementById('tabla2')
      );
      }
        
      ).catch((e)=>{
        toast.error("Error oferta grupos! no existen grupos en este periodo", {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
      });
      this.setState({
        grupos:[]
       })
      }

        
      )
  }
  tabledata() {
    let data = {
      columns: [
        {
          label: "Curso",
          field: "curso",
          sort: "asc",
        },
        {
          label: 'Horario',
          field: 'horario',
          sort:  'asc',
        },
       { label: 'Profesor',
        field: 'profesor',
        sort:  'asc',
      },  
      {
        label: 'Eliminar',
        field: 'delete',
        sort:  'asc',
      }
      ],
      rows: this.state.inscripciones,
    };
    for(let i in data.rows){
      data.rows[i]['curso'] = data.rows[i].grupo.curso.nombre
      data.rows[i]['horario'] = data.rows[i].grupo.horario
      data.rows[i]['profesor'] = data.rows[i].grupo.profesor.nombre
      data.rows[i]['delete'] = 
      <Button variant="secondary" onClick={() => this.openModalDel(data.rows[i]['codigo'])}>
        Eliminar
      </Button> 
      
    } 
    return data;
  }

  tabledata2() {
    let data = {
      columns: [
        {
          label: "Curso",
          field: "curso",
          sort: "asc",
        },
        {
          label: 'Horario',
          field: 'horario',
          sort:  'asc',
        },
       { label: 'Profesor',
        field: 'profesor',
        sort:  'asc',
      },  
      {
        label: 'Matricular',
        field: 'add',
        sort:  'asc',
      }
      ],
      rows: this.state.grupos,
    };
    console.log("global tabla grupos ojo",this.state.grupos)
    for(let i in data.rows){
      data.rows[i]['curso'] = data.rows[i].curso.nombre
      data.rows[i]['profesor'] = data.rows[i].profesor.nombre
      data.rows[i]['add'] = 
      <Button variant="primary" >
        Matricular
      </Button> 
      
    } 
    return data;
  }


  tabledata3() {
    let grupos=[];
    let data = {
      columns: [
        {
          label: "Curso",
          field: "curso",
          sort: "asc",
        },
        {
          label: 'Horario',
          field: 'horario',
          sort:  'asc',
        },
       { label: 'Profesor',
        field: 'profesor',
        sort:  'asc',
      },  
      {
        label: 'Matricular',
        field: 'add',
        sort:  'asc',
      }
      ],
      rows: grupos,
    };
    for(let i in data.rows){
      data.rows[i]['add'] = 
      <Button variant="primary" >
        Matricular
      </Button> 
      
    } 
    return data;
  }
  render() {
    return (
      <div>   
      <div className="d-flex justify-content-end mt-3 mr-3  w-50">
        <input type="text" className="w-100" placeholder="" readOnly  id="ciclo"       
         data-ciclo=""></input>

        <Button
          size="sm"
          onClick={this.openModal}
          variant="success"
          key="AddButton"
          className="w-25"
        >
          <i className="bi bi-plus-square"></i> Selecionar ciclo
        </Button>
      </div>
      <div className='d-flex justify-content-end mt-3 mr-3'>   
            <Button size="sm"    onClick={async() => await this.cargar()}variant="primary" key="AddButton">
              Cargar Datos
            </Button>
            </div> 
      <div  className="d-flex flex-column">
      <div>
      <div className="h2">Cursos a Matricular</div>
      <div id="tabla2">  <MDBDataTable
          searchLabel="Buscar"
          responsive
          hover={true}
          data={this.tabledata3()}
        /> </div>
    
        </div>
        <div>
        <div className="h2">Cursos a Retirar</div>
        <MDBDataTable
          searchLabel="Buscar"
          responsive
          hover={true}
          data={this.tabledata()}
        />
        </div>
      </div>
      <ToastContainer />
      <SelectCycleModal
          show={this.state.show}
          careerID={this.state.careerID}
          closeModal={this.closeModal}
        />
    </div>
    );
  }
}
