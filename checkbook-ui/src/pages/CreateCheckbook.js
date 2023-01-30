import {
    redirect,
    useActionData,
    useNavigate,
  } from 'react-router-dom';

import { addCheckbook } from '../lib/api';

import CheckbookForm from "../components/checkbook/CheckbookForm";

const CreateCheckbook = () => {
  const data = useActionData();
  const navigate = useNavigate();

  function cancelHandler() {
    navigate('/checkbook');
  }
  
  return (
    <>
        {data && data.isError && <p>{data.message}</p>}
        <CheckbookForm onCancel={cancelHandler} />
    </>);
}

export default CreateCheckbook;

export async function action({ request, params }) {
    const data = await request.formData();
    await addCheckbook({ name: data.get("name"), currentBalance: data.get("currentBalance")}).catch((error) => {
      alert(error.message);
    });
    return redirect(`/checkbook`);
}