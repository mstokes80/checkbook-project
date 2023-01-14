import {
    redirect,
    useActionData,
    useNavigate,
    useNavigation,
    useParams
  } from 'react-router-dom';
import { addTransaction } from '../lib/api';  
import TransactionForm from "../components/transactions/TransactionForm";

const CreateTransactionPage = () => {
  const data = useActionData();
  const params = useParams();
  const {checkbookId} = params;
  const navigation = useNavigation();
  console.log(navigation.state);

  const navigate = useNavigate();

  function cancelHandler() {
    navigate(`/checkbook/${checkbookId}/transactions`);
  }
  
    return (
    <>
        {data && data.isError && <p>{data.message}</p>}
        <TransactionForm onCancel={cancelHandler}/>
    </>);
}

export default CreateTransactionPage;

export async function action({ request, params }) {
    const data = await request.formData();
    const validationError = await addTransaction(
      { 
        transactionType: data.get("transactionType"),
        description: data.get("description"), 
        amount: data.get("amount"), 
        payedDate: data.get("payedDate") 
      }, params.checkbookId);
    if (validationError) {
      return validationError;
    }
    return redirect(`/checkbook/${params.checkbookId}/transactions`);
}