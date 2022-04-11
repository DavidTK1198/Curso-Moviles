import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class Login extends React.Component {
    componentDidMount() {
        console.log("saliendo");
        cookies.remove("username", { path: process.env.REACT_APP_AUTH });
        cookies.remove("full_name", { path: process.env.REACT_APP_AUTH });
        cookies.remove("roles", { path: process.env.REACT_APP_AUTH });
        cookies.remove("token", { path: process.env.REACT_APP_AUTH });
        this.props.history.push("/auth");
    }
    render() {
        return (
            <h1>Redireccionando...</h1>
        );
    }
}