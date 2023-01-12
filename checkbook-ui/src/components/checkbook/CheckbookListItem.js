import { Link } from "react-router-dom";

const CheckbookListItem = (props) => {
    return (
        <div className="card mt-3">
            <div className="card-header">
                {props.name}
            </div>
            <div className="card-body">
                <h5 className="card-title">{props.name}</h5>
                <p className="card-text">{props.currentBalance}</p>
                <div className="row">
                    <div className="col-sm-6 text-end">
                        <Link to={`/checkbook/${props.id}`} className="btn btn-primary">Edit Checkbook</Link>
                    </div>
                    <div className="col-sm-6">
                        <Link to={`/checkbook/${props.id}/transactions`} className="btn btn-primary">View Transactions</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CheckbookListItem;