/*
SFR Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import {Navbar} from "react-bootstrap";
import '../css/Footer.css';
export default function Footer() {
    return (
        <div>
            <Navbar className="Footer">       
            <p className="align-middle text_center">
                ©{new Date().getFullYear()}. Universidad Nacional de Costa Rica
            </p>
            </Navbar>
        </div>
    );
}