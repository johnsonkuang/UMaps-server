import React, {Component} from 'react';

import { useAlert } from 'react-alert'
import Combobox from "react-widgets/lib/Combobox";

import "bootstrap/dist/css/bootstrap.css";
import "react-widgets/dist/css/react-widgets.css";

/**
 * Handles building selection, passes information up to PathApp
 */
class BuildingSelector extends Component {
    render() {
        return(
          <div>
              <div className={"row"}>
                  <div className={"col-md-4"}>
                      <h4>Start: </h4>
                  </div>
                  <div className={"col-md-8"}>
                      <Combobox
                          dropUp
                          busy={this.props.busy}
                          data={this.props.buildingValues}
                          value={this.props.startValue}
                          onSelect={this.props.handleStart}
                      />
                  </div>
              </div>
              <div className={"row"} style={{marginTop: "10px"}}>
                  <div className={"col-md-4"}>
                      <h4 style={{paddingTop: "5px"}}>Destination: </h4>
                  </div>
                  <div className={"col-md-8    "}>
                      <Combobox
                          dropUp
                          busy={this.props.busy}
                          data={this.props.buildingValues}
                          value={this.props.destValue}
                          onSelect={this.props.handleDest}
                      />
                  </div>
              </div>
          </div>
        );
    }
}
//No validation, except for empty list (done server side) dropdown guarantees well-formed input

export default BuildingSelector;