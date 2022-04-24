import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import '../../css/Courses.css'
import axios from 'axios';
import { MDBDataTable,  } from 'mdbreact';
import Cookies from 'universal-cookie';
import { Link } from 'react-router-dom';
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/inscripciones/alumno";
export default class Student extends Component {
    constructor(props){
        super(props);
        this.state = {
            inscripciones: []
        }
        this.tabledata = this.tabledata.bind(this);
    }

    componentDidMount() {
        if (!(cookies.get('username', { path: process.env.REACT_APP_AUTH })
        && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
        && cookies.get('ced', { path: process.env.REACT_APP_AUTH })
        ))
        this.props.history.push('/login');
      this.refreshPage();
  }
  refreshPage() {
    let options = {
        url:requestURL+'?ced='+cookies.get('ced'),
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
            inscripciones: response.data,
        });
    }).catch(error => {
      console.log(error);
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
         label: 'Nota',
         field: 'nota',
         sort:  'asc',
       },
   
                 
     ],
     rows: this.state.inscripciones  
     }    
     for(let i in data.rows){
          for(let i in data.rows){
            data.rows[i]['curso'] = 
            <Button variant="info" disabled>
              {data.rows[i].grupo.curso.nombre}
            </Button> 
          }     
     }         
     return data
}    
    render() {
        return (
            <div>       
              <MDBDataTable 
                searchLabel='Buscar'
                responsive
                hover={true}
                data={this.tabledata()}              
              />
            </div>
        );
    }
}
