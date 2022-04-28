import React, { Component } from 'react';
//import axios from 'axios';
import '../../../css/AddGroupModal.css';
import { Modal, Button, Form, Stack } from "react-bootstrap";

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
    /*
    handleSubmit = (event) => {
        let query = new URLSearchParams(this.props.location.search);
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: 'http://localhost:8088/Matricula/api/grupos/listar?ciclo='+ query.get('ciclo') + '&codigo=' + query.get('codigo'),
                method: 'POST',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'name': event.target.name.value,
                    'description': event.target.description.value,
                    'entryDate': this.state.startDate.getTime(),
                    'affectation': event.target.affectation.value,
                    'cause': event.target.cause.value,
                    'risk': event.target.risk.value,
                    'planID': this.props.planID
                }
            }

            axios(options)
                .then(response => {
                    this.props.closeModal();
                    this.props.refreshPage();
                }).catch(error => {
                    console.log(error);
                    toast.error("ID de la incidencia ya se encuentra registrado en el sistema.", {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                });
        }
    }*/

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;
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
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar cupo.
                                </Form.Control.Feedback>
                            </div>
                    </Form.Group>
                    <Form.Group>
                            <div className="form-group">
                                <Form.Label>Profesor:</Form.Label>
                                <Form.Control
                                    name="profesor"
                                    id="profesor"
                                    type="text"
                                    placeholder="Profesor"
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
            </Modal>
        );
    }
};