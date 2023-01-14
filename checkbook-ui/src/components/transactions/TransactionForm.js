import { Form, useParams } from "react-router-dom";

const TransactionForm = (props) => {
    const params = useParams();
    const {checkbookId} = params;
    return (
        <div className="row mt-5">
            <div className="col-md-10">
                <Form action={`/checkbook/${checkbookId}/transactions/new-transaction`} method="post">
                    <fieldset className="row mb-3">
                        <legend className="col-form-label col-sm-3 pt-0">Transaction Type:</legend>
                        <div className="col-sm-9">
                            <div className="form-check form-check-inline">
                                <input className="form-check-input" type="radio" id="transactionTypeW" name="transactionType" value="W" checked />
                                <label className="form-check-label" htmlFor="transactionType">Withdrawal</label>
                            </div>
                            <div className="form-check form-check-inline">
                                <input className="form-check-input" type="radio" id="transactionTypeD" name="transactionType" value="D" />
                                <label className="form-check-label" htmlFor="transactionType">Deposit</label>
                            </div>
                        </div>
                    </fieldset>    
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">Description:</label>
                        <input type="text" className="form-control" id="description" name="description" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="amount" className="form-label">Amount:</label>
                        <input type="number" step="0.01" className="form-control" id="amount" name="amount" />
                    </div>
                    <div className="row mb-3">
                        <div className="col-md-4">
                            <input type="date" className="form-control" id="payedDate" name="payedDate" />
                        </div>
                    </div>
                    <div className="text-center">
                        <button className="btn btn-primary">Create</button>
                        <button className="btn btn-secondary ms-3" onClick={props.onCancel}>Cancel</button>
                    </div>
                </Form>
            </div>
        </div>
    );
};

export default TransactionForm;