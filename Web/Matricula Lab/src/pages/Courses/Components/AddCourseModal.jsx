import React, { Component } from 'react';
import axios from 'axios';
import '../../../css/AddGroupModal.css';
import { Modal, Button, Form } from "react-bootstrap";

export default class AddCourseModal extends Component {
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
        let query = new URLSearchParams(this.props.location.search);
        console.log(query.get('codigo'))
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: "",
                method: 'POST',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'codigo': event.target.name.value,
                    'nombre': event.target.codigo.value,
                    'hsemanales': event.target.hsemanales.value,
                    'creditos': event.target.creditos.value,
                    'idCarrera': query.get('codigo')
                }
            }

            axios(options)
                .then(response => {
                    this.props.closeModal();
                    this.props.refreshPage();
                }).catch(error => {
                    console.log(error);
                });
        }
    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <Modal show={render} onHide={() => { closeModal() }} >
                <Modal.Header closeButton>
                    Ingrese los datos del nuevo curso
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Nombre:</Form.Label>
                                <Form.Control
                                    name="name"
                                    id="name"
                                    type="text"
                                    placeholder="Nombre"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar nombre.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Código:</Form.Label>
                                <Form.Control
                                    name="codigo"
                                    id="codigo"
                                    type="text"
                                    placeholder="Código"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar nombre.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Créditos:</Form.Label>
                                <Form.Control
                                    name="creditos"
                                    id="creditos"
                                    type="text"
                                    placeholder="Créditos"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar nombre.
                                </Form.Control.Feedback>
                            </div>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>Horas Semanales:</Form.Label>
                                <Form.Control
                                    name="hsemanales"
                                    id="hsemanales"
                                    type="text"
                                    placeholder="Horas Semanales"
                                    className="form-control"
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar nombre.
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