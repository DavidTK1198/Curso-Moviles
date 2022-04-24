 
 
import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button } from 'react-bootstrap';

const cookies = new Cookies();

export default class Menu extends Component {
    /*
    Menu class corresponds to the component that is shown to the user
    if the auth-request was accepted by the server.
    */
    componentDidMount() {
        if (!(cookies.get('username', { path: process.env.REACT_APP_AUTH })
            && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
            && cookies.get('ced', { path: process.env.REACT_APP_AUTH })
            ))
            this.props.history.push('/login');
    }

    render() {
        return (
            <div>
                <h1 className="text-center">Menu Principal</h1>
                <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                    <div className="text-center">
                        <Button onClick={() => { document.location = ""; }}>
                            Something
                        </Button>
                    </div>
                    <div className="text-center">
                        <Button onClick={() => {this.props.history.push('/logout')}}>
                            Salir :v
                        </Button>
                    </div>
                </Container>
            </div>
        );
    }
}
