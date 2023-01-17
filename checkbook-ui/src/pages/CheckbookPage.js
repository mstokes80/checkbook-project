import { useContext } from "react";
import { Link, Outlet, Navigate } from "react-router-dom";
import CheckbookListing from "../components/checkbook/CheckbookListing";

import AuthContext from "../store/auth-context";

const CheckbookPage = () => {
    const auth = useContext(AuthContext);
    if(!auth.isLoggedIn){
        <Navigate to="/login" />
    }
    return (
        <div className="row gx-5" >
            <div className="col-md-12">
                <div className="row">
                    <div className="col-md-12 mt-4">
                        <h1>Manage Checkbooks</h1>
                        <hr />
                    </div>
                </div>
                <div className="row">
                    <div className="col-sm-6 overflow-scroll" style={{height: '90vh'}}>
                        <Link to='/checkbook/create-checkbook' className="btn btn-primary mt-4 shadow">New Checkbook</Link>
                        <CheckbookListing />
                    </div>
                    <div className="col-sm-6 overflow-scroll" style={{height: '90vh'}}>
                        <Outlet />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CheckbookPage;