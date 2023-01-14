import { Link, useNavigate } from "react-router-dom";

import { deleteCheckbook } from "../../lib/api"

const CheckbookListItem = (props) => {
    const navigate = useNavigate();
    let formattedBalance = (Math.round(props.currentBalance * 100) / 100).toFixed(2);
    const handleDelete = () => {
        if(window.confirm("Are you sure you want to delete this checkbook and all of it's transactions?")){
            deleteCheckbook(props.id).then(() => {
                navigate('/checkbook');
            });
        }
    };

    return (
        <div className="card shadow mt-3">
            <div className="card-header">
                {props.name}
                <i className="bi bi-trash float-end" style={{'cursor':'pointer'}} onClick={handleDelete}></i>
            </div>
            <div className="card-body">
                <h5 className="card-title">{props.name}</h5>
                <p className="card-text">${formattedBalance}</p>
                <div className="row justify-content-center">
                    <div className="col-sm-6">
                        <Link to={`/checkbook/${props.id}/transactions`} className="btn btn-success">View Transactions</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CheckbookListItem;