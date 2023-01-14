import { Link, useNavigate } from "react-router-dom";

import { deleteCheckbook } from "../../lib/api"

const CheckbookListItem = (props) => {
    const navigate = useNavigate();
    const handleDelete = () => {
        deleteCheckbook(props.id).then(() => {
            navigate('/checkbook');
        });
    };

    return (
        <div className="card mt-1">
            <div className="card-header">
                {props.name}
                <i className="bi bi-trash float-end" onClick={handleDelete}></i>
            </div>
            <div className="card-body">
                <h5 className="card-title">{props.name}</h5>
                <p className="card-text">${props.currentBalance}</p>
                <div className="row justify-content-center">
                    <div className="col-sm-6">
                        <Link to={`/checkbook/${props.id}/transactions`} className="btn btn-primary">View Transactions</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CheckbookListItem;