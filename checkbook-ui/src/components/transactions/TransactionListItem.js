const TransactionListItem = (props) => {
    const isWithdrawal = props.type === 'W';
    const itemClass =  isWithdrawal ? 'list-group-item-danger' : 'list-group-item-success';
    return (
        <li className={`list-group-item mt-3 ${itemClass}`}>
            <div className="ms-2 me-auto">
                <div>
                    <div className="d-flex w-100 justify-content-between">
                        <h5 className="mb-1">{isWithdrawal ? 'WITHDRAWAL' : 'DEPOSIT'}</h5>
                        <small>{props.payedDate}</small>
                    </div>
                    
                    <p>{props.description}</p>
                    <p>{isWithdrawal && "-"}${props.amount.toFixed(2)}</p>
                </div>
            </div>
        </li>
    );
}

export default TransactionListItem;