import React, { Component } from 'react';
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import { Link } from 'react-router-dom';
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
    let query = new URLSearchParams(this.props.location.search);
    console.log(query.get('codigo'))
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
            rows: this.state.courses   
            }    
            for(let i in data.rows){
              let cName = data.rows[i]['nombre'];
              data.rows[i]['nombre'] = <Link to={{ pathname: "/curso", search: `?id=${data.rows[i]['id']}` }}>
              {cName}</Link> 
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
