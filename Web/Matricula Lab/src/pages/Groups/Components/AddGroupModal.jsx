import React, { Component } from 'react';
import axios from 'axios';
import '../../../css/AddGroupModal.css';
import { Modal, Button, Form, Stack } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
export default class AddGroupModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
        //this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }
    onChange = e => {
        this.setState({ value: e.target.value })
    }
    
    handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let data;
            console.log(this.props.ciclo)
                data = {
                    'curso': {'codigo':event.target.curso.value},
                    'profesor': {'cedula':event.target.profesor.value},
                    'horario': event.target.horario.value,
                    'cupo': event.target.cupo.value,
                    'numero':event.target.num.value,
                    'ciclo':this.props.ciclo
                }
                var url='http://localhost:8088/Matricula/api/grupos/agregar';
                let request = new Request(url, {method: 'POST', headers: { 'Content-Type': 'application/json',
                'Access-Control-Allow-Methods':'*',
                'Access-Control-Allow-Origin':'*'
            },body: JSON.stringify(data),mode:'cors'});
                  (async ()=>{
                    const response = await fetch(request);
                    this.closeModal();
                    this.refreshPage();  
                    toast.success("Se agrego el grupo correctamente!", {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });                         
                })();
            }
            }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;
        let codigo = this.props.codigo
        return (
            <Modal show={render} onHide={() => { closeModal() }} >
                <Modal.Header closeButton>
                    Ingrese los datos del nuevo grupo
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                    <Form.Group>
                            <div className="form-group">
                                <Form.Label>Curso:</Form.Label>
                                <Form.Control
                                    name="curso"
                                    id="curso"
                                    type="text"
                                    placeholder="curso"
                                    className="form-control"
                                    defaultValue={codigo}
                                    disabled
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar cupo.
                                </Form.Control.Feedback>
                            </div>
                    </Form.Group>
                    <Form.Group>
                            <div className="form-group">
                                <Form.Label>Cedula profesor:</Form.Label>
                                <Form.Control
                                    name="profesor"
                                    id="profesor"
                                    type="text"
                                    placeholder="Cedula profesor"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar cupo.
                                </Form.Control.Feedback>
                            </div>
                    </Form.Group>
                    <Form.Group>
                            <div className="form-group">
                                <Form.Label>Cupo:</Form.Label>
                                <Form.Control
                                    name="cupo"
                                    id="cupo"
                                    type="text"
                                    placeholder="Cupo"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar cupo.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Número de grupo:</Form.Label>
                                <Form.Control
                                    name="num"
                                    id="num"
                                    type="text"
                                    placeholder="número de grupo"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor el numero de grupo.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Horario:</Form.Label>
                                <Form.Control
                                    name="horario"
                                    id="horario"
                                    type="text"
                                    placeholder="Horario"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar horario.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <div className='text-center'>
                            <Button className='btn-sfr' type="submit">
                                Guardar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body> 
                <ToastContainer />
            </Modal>
        );
    }
};