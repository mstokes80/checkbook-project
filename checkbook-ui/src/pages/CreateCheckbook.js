import {
    redirect,
    useActionData,
    useNavigate,
    useNavigation,
  } from 'react-router-dom';

import CheckbookForm from "../components/checkbook/CheckbookForm";

const CreateCheckbook = () => {
  const data = useActionData();

  const navigation = useNavigation();
  console.log(navigation.state);

  const navigate = useNavigate();

  function cancelHandler() {
    navigate('/');
  }
  
  return (
    <>
        {data && data.isError && <p>{data.message}</p>}
        <CheckbookForm onCancel={cancelHandler} />
    </>);
}

export default CreateCheckbook;

export async function action({ request }) {
    const data = await request.formData();
    console.log(data);
    // const validationError = await savePost(data);
    // if (validationError) {
    //   return validationError;
    // }
    return redirect('/');
}