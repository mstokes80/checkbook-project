import {
    redirect,
    useActionData,
    useNavigate,
    useNavigation,
    useParams
  } from 'react-router-dom';
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
        <TransactionForm onCancel={cancelHandler}/>;
    </>);
}

export default CreateTransactionPage;

export async function action({ request }) {
    const data = await request.formData();
    console.log(data);
    // const validationError = await savePost(data);
    // if (validationError) {
    //   return validationError;
    // }
    return redirect('/');
}