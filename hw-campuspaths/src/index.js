/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* eslint-disable react/jsx-no-comment-textnodes */
import React, { Component } from "react";
import ReactDOM from "react-dom";

import { BrowserRouter } from "react-router-dom";
import Main from "./main";
import NavBar from "./Navbar";

import "bootstrap/dist/css/bootstrap.min.css";

class App extends Component {
    render() {
        return (
            <div>
                <NavBar />
                <Main />
            </div>
        );
    }
}

ReactDOM.render(
    <BrowserRouter basename="/">
        <App />
    </BrowserRouter>,
    document.getElementById("root")
);

