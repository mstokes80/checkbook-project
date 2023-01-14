import { useContext } from "react";
import { Navigate } from "react-router-dom";
import AuthContext from "../../store/auth-context";
import LoginForm from "../../components/auth/LoginForm";

const LoginPage = () => {
    const authContext = useContext(AuthContext);
    if(authContext.isLoggedIn){
        return <Navigate to="/" />
    }
    return (<LoginForm />);
}

export default LoginPage;