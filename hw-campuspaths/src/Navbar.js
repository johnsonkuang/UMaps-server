import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Link } from "react-router-dom";

import "normalize.css";
import "./navbar.css";
import "bootstrap/dist/css/bootstrap.css";

//Stateless Functional Component
/**
 * Purely cosmetic navbar that uses <Link> to route between app components
 */
class NavBar extends Component {
    render() {
        return (
            <header style={{height: "50px"}}>
                        <input type="checkbox" id="nav-toggle" className="nav-toggle" />
                        <nav>
                            <ul>
                                <li style={{marginTop: "50px;"}}>
                                    <Link
                                        to="/"
                                        className={
                                            "nav-item "
                                        }
                                    >
                                            home
                                    </Link>
                                </li>
                                <li>
                                    <Link
                                        to="/path-app"
                                        className={
                                            "nav-item " + (this.props.active === "RA" ? "active" : "")
                                        }
                                    >
                                        find path
                                    </Link>
                                </li>
                            </ul>
                        </nav>
                        <label htmlFor="nav-toggle" className="nav-toggle-label">
                            <span></span>
                        </label>
            </header>
        );
    }
}

export default NavBar;
