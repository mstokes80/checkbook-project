import TransactionListItem from './TransactionListItem';
import { getAllTransactions } from '../../lib/api';
import { useLoaderData } from 'react-router-dom';

const TransactionList = () => {
    const transactions = useLoaderData();
    
    return (
        <div className="row">
            <div className="col-sm-12">
                <ul className="list-group">
                    {transactions && transactions.map(transaction => <TransactionListItem key={transaction.id} id={transaction.id} description={transaction.description} amount={transaction.amount} type={transaction.transactionType} payedDate={transaction.payedDate} />)}
                    {!transactions && <p>No transactions for this checkbook.</p>}
                </ul>
            </div>
        </div>
    );
}

export default TransactionList;

export function loader() {
    return getAllTransactions();
}