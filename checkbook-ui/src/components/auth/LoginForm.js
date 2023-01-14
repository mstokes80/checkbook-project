import { useRef, useContext } from "react";
import { Link, redirect } from "react-router-dom";
import AuthContext from "../../store/auth-context";

const LoginForm = () => {
    
    const usernameRef = useRef();
    const passwordRef = useRef();
    const authContext = useContext(AuthContext);

    const submitFormHandler = (event) => {
        event.preventDefault();
        const enteredUsername = usernameRef.current.value;
        const enteredPassword = passwordRef.current.value;
        fetch('https://localhost:8080/api/auth/signin', 
        {
            method: 'POST',
            body: JSON.stringify({ username: enteredUsername, password: enteredPassword}),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((resp) => {
            if(resp.ok){
                return resp.json();
            } else {
                throw Error("Unable to authenticate using the provided credentials.")
            }
        }).then((data) => {
            authContext.login(data.token, data.expirationDate);
            redirect("/checkbook")
        }).catch(err => {
            alert(err);
        }); 
    };
    return (
        <div className="row mt-5 justify-content-center">
            <div className="col-md-5">
                <form onSubmit={submitFormHandler}>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">User Name</label>
                        <input type="text" className="form-control" id="username" ref={usernameRef} />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" className="form-control" id="password" ref={passwordRef} />
                    </div>
                    <div className="text-center">
                        <button className="btn btn-primary">Login</button>
                        <Link className="btn btn-secondary ms-3" to="/create-account">Create Account</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginForm;