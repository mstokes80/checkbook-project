import { Link } from "react-router-dom";

const WelcomePage = () => {

    return (
        <div className="row mt-5 justify-content-center">
            <div className="col-md-6">
                <h1>Welcome</h1>
                <hr />
                <p>This application was created by Matt Stokes for demonstration purposes. 
                    The Checkbook Balancer app allows the user to create and balance multiple checkbooks and track transactions against those checkbooks.
                </p>
                <p>The following technologies were used to create this checkbook balancer app.</p>
                <ul>
                    <li>Java 17</li>
                    <li>Spring Boot</li>
                    <li>Spring Security</li>
                    <li>JWT</li>
                    <li>JPA</li>
                    <li>REST API</li>
                    <li>REACT</li>
                    <li>DOCKER</li>
                </ul>
                <p>Click <Link to="/checkbook/create-new">here</Link> to get started by creating a new checkbook.</p>
            </div>
        </div>
    );

}

export default WelcomePage;