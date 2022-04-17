import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form, OverlayTrigger, Tooltip, Stack } from "react-bootstrap";

export default class AddIncidentModal extends Component {
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
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_API_URL + `/IncidenceManager/Insert`,
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
                    Ingrese los datos de la nueva incidencia
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
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Descripción:</label>
                            </Stack>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" style={{ height: '150px' }} required />
                        </div>
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