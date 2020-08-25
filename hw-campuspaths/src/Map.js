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

import React, {Component} from 'react';
import "./Map.css";

class Map extends Component {

    constructor(props) {
        super(props);
        this.canvas = React.createRef();
    }

    componentDidUpdate() {
        //redraw the canvas everytime the props receive an update
        this.redraw();
    }

    drawBackgroundImage(canvas, ctx) {
        if (this.props.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry
            canvas.width = this.props.width;
            canvas.height = this.props.height;
            ctx.drawImage(this.props.backgroundImage, 0, 0);
        }
    }

    redraw = () => {
        let canvas = this.canvas.current;
        let ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, this.props.width, this.props.height);
        this.drawBackgroundImage(canvas, ctx);

        //Draw Paths
        let path = this.props.path;
        path.forEach(element => {
           let start = element["start"];
           let end = element["end"];
           this.drawPath(ctx, start, end);
        });
    }

    //draws a line based on start and end coordinates, color fixed at red
    drawPath = (ctx, start, end) => {
        ctx.lineWidth = 7;
        ctx.strokeStyle = "red";
        ctx.beginPath();
        ctx.moveTo(start["x"], start["y"]);
        ctx.lineTo(end["x"], end["y"]);
        ctx.stroke();
    }

    render() {
        return (
            <div style ={{paddingTop: "52px", paddingBottom: "10px"}}>
                <canvas ref={this.canvas}/>
            </div>
        )
    }
}

export default Map;