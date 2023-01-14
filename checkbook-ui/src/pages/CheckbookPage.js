import { Link, Outlet } from "react-router-dom";
import CheckbookListing from "../components/checkbook/CheckbookListing";

const CheckbookPage = () => {
    return (
        <div className="row" >
            <div className="col-sm-6 overflow-scroll" style={{height: '90vh'}}>
                <Link to='/checkbook/create-checkbook' className="btn btn-success mt-2">New Checkbook</Link>
                <CheckbookListing />
            </div>
            <div className="col-sm-6 overflow-scroll" style={{height: '90vh'}}>
                <Outlet />
            </div>
        </div>
    );
}

export default CheckbookPage;