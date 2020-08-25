import React, {Component} from 'react';
import { Frame } from "framer";
import { IoIosArrowUp, IoIosArrowDown} from "react-icons/io";

/**
 * This is a purely cosmetic home page that stores no state and performs no functions
 *
 * there are four animations:
 * - a vertical sliding bar
 * - a horizontal sliding bar
 * - a stagger effect when letters appear
 * - a color change effect on the <h1></h1>
 */


class HomeApp extends Component {
    render() {
        const string = Array.from("uw huskymap");
        // Add staggering effect to the children of the container
        const containerVariants = {
            before: {x: "-100%"},
            after: { transition: {
                        delay: 0.2,
                        when: "beforeChildren",
                        staggerChildren: 0.06,
                        ease: "easeOut",
                        duration: 0.9 },
                      x: 0
                    },
        };

        // Variants for animating each letter
        const letterVariants = {
            before: {
                opacity: 0,
                y: 20,
                transition: {
                    type: "spring",
                    damping: 16,
                    stiffness: 200,
                },
            },
            after: {
                opacity: 1,
                y: 0,
                transition: {
                    type: "spring",
                    damping: 16,
                    stiffness: 200,
                },
            },
        };

        const divStyle ={
            width: "30%",
            left: "-10%",
            position: "absolute",
            zIndex: 99999,
            height: "100vh",
            display: "inline-block",
            background: "#363C74",
            transform: "skew(-15deg)"
        }

        return(
            <div>
                <Frame
                    style={divStyle}
                    height={"100%"}
                    initial={{ y: "-100%" }}
                    animate={{ y: 0 }}
                    transform={"skewX(-15deg)"}
                    transition={{
                        ease: "easeOut",
                        duration: 0.5,
                    }}
                    skew={-10}
                >
                </Frame>
                <Frame
                    center={ "y" }
                    height={ 100 }
                    width={ "100%" }
                    background={ "#E8D3A2" }
                    style={{
                        fontFamily: "Encode Sans, sans-serif",
                        fontWeight: "bold",
                        letterSpacing: "-0.04em",
                        fontSize: 100,
                        color: "#FFF",
                        display: "flex", // Set the display value to flex
                        justifyContent: "center", // Center all children elements on the x axis
                    }}
                    variants={ containerVariants }
                    initial={ "before" }
                    animate={ "after" }
                >
                    {string.map((letter, index) => (
                        <Frame
                            key={ index }
                            width={ "auto" } // Set the width to the width of the letter
                            height={ 26 } // Set the height to the height of the text
                            background={ "" }
                            style={{ position: "relative" }} // Position elements
                            variants={ letterVariants }
                        >
                            {letter === " " ? "\u00A0" : letter}
                        </Frame>
                    ))}
                </Frame>
                <div className={"container"} style={{paddingTop: "110vh", height: "100vh"}}>
                    <Frame
                        animate={{ color: "#85F" }}
                        transition={{
                            duration: 1,
                            yoyo: Infinity,
                        }}
                        color={"#0CF"}
                        background={""}
                        width={"100%"}
                    >
                        <h1>hi there! welcome to uw husky map (this is a cool animation)</h1>
                    </Frame>
                </div>
                <div className={"container"} style={{marginTop: "50px"}}>
                    <p>
                        on the "find path" page you will find the path-finder app. a few things to take note of:
                    </p>
                    <p>
                        the location selection menu can be accessed via the button in the lower left corner. it looks kind
                        of like this <button className={"btn btn-outline-info"}><IoIosArrowUp/></button>
                    </p>
                    <br />
                    <p>
                        input start and destination buildings and press the map icon to find the path. reset via the refresh
                        icon. once a path has been found, you can email it to yourself via the email field that shows up
                    </p>
                </div>
            </div>
        );
    }
}

export default HomeApp;