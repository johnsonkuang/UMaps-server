import React from "react";
import { Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css";
import PathApp from "./PathApp";
import HomeApp from "./HomeApp";

// The Main component renders one of the three provided
// Routes (provided that one matches). Both the /roster
// and /schedule routes will match any pathname that starts
// with /roster or /schedule. The / route will only match
// when the pathname is exactly the string "/"
const Main = () => (
    <main>
        <Switch>
            {/* Home */}
            <Route exact path="/" component={HomeApp} />
            {/* Path Finder */}
            <Route path="/path-app" component={PathApp} />
        </Switch>
    </main>
);

export default Main;
