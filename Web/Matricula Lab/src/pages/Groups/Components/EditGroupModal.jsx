import React, { Component } from 'react';
import axios from 'axios';
import '../../../css/AddGroupModal.css';
import { Modal, Button, Form } from "react-bootstrap";

export default class AddGroupModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
        this.handleSubmit = this.handleSubmit.bind(this);
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
            let options = {
                url: 'http://localhost:8088/Matricula/api/grupos',
                method: 'PUT',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'idEntidad': this.props.idG,
                    'cupo': event.target.cupo.value,
                    'horario': event.target.horario.value
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
        let curso = this.props.curso
        let profesor = this.props.profesor
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
                                    placeholder="Curso"
                                    defaultValue={curso}
                                    className="form-control"
                                    disabled
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar curso.
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
                                    defaultValue={profesor}
                                    className="form-control"
                                    disabled
                                    required
                                />
                                <Form.Control.Feedback type="invalid">
                                    Por favor ingresar profesor.
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