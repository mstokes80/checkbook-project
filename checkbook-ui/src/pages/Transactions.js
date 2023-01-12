import { Link } from "react-router-dom";
import React from "react";
import TransactionList from "../components/transactions/TransactionList";

const Transactions = () => {

    return (        
        <div className="row">
            <div className="col-sm-10">
                <Link className="btn btn-primary mt-2" to="new-transaction">New Transaction</Link>
                <TransactionList />
            </div>
        </div>
    );

}

export default Transactions;