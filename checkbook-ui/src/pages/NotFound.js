import { Link } from "react-router-dom";

const NotFound = () => {
    return (
        <div className="row mt-5 justify-content-center">
            <div className="col-md-6">
                <h1>Page Not Found!</h1>
                <hr />
                <p>The page you are trying to navigate to cannot be found.</p>
                <p>Click <Link to="/">here</Link> to return home.</p>
            </div>
        </div>
    );
}

export default NotFound;