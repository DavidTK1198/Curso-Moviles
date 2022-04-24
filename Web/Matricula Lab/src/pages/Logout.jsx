/*
Authentication Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from 'universal-cookie';
import axios from "axios";
import {TripleMaze } 
from 'react-spinner-animated';
import 'react-spinner-animated/dist/index.css';
const cookies = new Cookies();
const requestURL = "http://localhost:8088/Matricula/api/login";
export default class Logout extends React.Component {

    
    
    async componentDidMount() {
        cookies.remove("username", { path: process.env.REACT_APP_AUTH });
        cookies.remove("roles", { path: process.env.REACT_APP_AUTH });
        cookies.remove("ced", { path: process.env.REACT_APP_AUTH });
        await this.logout();
        this.props.history.push("/auth");
        localStorage.removeItem("logged");
        window.location.reload(false);
    }
    render() {
        return (
            <TripleMaze text={"Redireccionando..."}
    center={true} width={"150px"} height={"150px"}/>
        );
    }
    logout(){
        return new Promise((resolve,reject)=>{
        let options = {
          url: requestURL,
          method: 'DELETE',
          header: {
              'Access-Control-Allow-Origin': '*',
              'Access-Control-Allow-Methods':'*',
          }
        }
      axios(options)
          .then(response => {
            setTimeout(() => {  resolve("ok") }, 2000);
          }).catch(error => {
              console.log(error);
              reject(error)
          });
        }
        )
    }
      
}