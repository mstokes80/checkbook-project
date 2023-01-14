import { Form, Link } from "react-router-dom";

const CreateAccountForm = () => {
 
    return (
        <div className="row mt-5 justify-content-center">
            <div className="col-md-5">
                <Form action="/create-account" method="post">
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email Address</label>
                        <input type="email" className="form-control" id="email" name="email" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">User Name</label>
                        <input type="text" className="form-control" id="username" name="username" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" className="form-control" id="password" name="password" />
                    </div>
                    <div className="text-center">
                        <button className="btn btn-primary">Create</button>
                        <Link className="btn btn-secondary ms-3" to="/login">Login</Link>
                    </div>
                </Form>
            </div>
        </div>
    );
}

export default CreateAccountForm;