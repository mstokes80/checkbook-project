import { useParams, useNavigate } from "react-router-dom";
import { format } from 'date-fns';
import { deleteTransaction } from "../../lib/api";

const TransactionListItem = (props) => {
    const params = useParams();
    const navigate = useNavigate();
    const isWithdrawal = props.type === 'W';
    //const itemClass =  isWithdrawal ? 'list-group-item-danger' : 'list-group-item-success';
    const handleDelete = () => {
        deleteTransaction(props.id).then(() => {
            navigate(`/checkbook/${params.checkbookId}/transactions`)
        });
    };

    return (
        <div className="card mt-1" style={{'backgroundColor': isWithdrawal ? 'tomato':'lightgreen'}}>
            <div className="card-header">
                <h5 className="mb-1 d-inline">{isWithdrawal ? 'Withdrawal' : 'Deposit'}</h5>
                <i className="bi bi-trash float-end" onClick={handleDelete}></i>
            </div>
            <div className="card-body">
                <h5 className="card-title">{props.description}</h5>
                <small>{format(new Date(props.payedDate), 'MMMM dd, yyyy')}</small>
                <p className="card-text">{isWithdrawal && "-"}${props.amount.toFixed(2)}</p>
            </div>
        </div>
    );
}

export default TransactionListItem;