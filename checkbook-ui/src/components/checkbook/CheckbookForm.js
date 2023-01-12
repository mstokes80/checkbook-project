import { Form } from "react-router-dom";

const CheckbookForm = (props) => {
    return (
        <div className="row mt-5">
            <div className="col-md-10">
                <Form action="/checkbook/create-checkbook" method="post">
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Checkbook Name</label>
                        <input type="text" className="form-control" id="name" name="name" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="currentAmount" className="form-label">Current Amount</label>
                        <input type="number" className="form-control" id="currentAmount" name="currentAmount" />
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

export default CheckbookForm;