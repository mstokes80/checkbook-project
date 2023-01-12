import { useLoaderData } from 'react-router-dom';
import CheckbookListItem from './CheckbookListItem';
import { getAllCheckbooks } from '../../lib/api';

const CheckbookListing = () => {
    const checkbooks = useLoaderData();
    return (
        <div className="row">
            <div className="col-sm-12">
                {checkbooks && checkbooks.map(checkbook => <CheckbookListItem key={checkbook.id} id={checkbook.id} name={checkbook.name} currentBalance={checkbook.currentBalance} />)}
                {!checkbooks && <p>No checkbook found!</p>}
            </div>
        </div>
    );
}

export default CheckbookListing;

export function loader() {
    return getAllCheckbooks();
}