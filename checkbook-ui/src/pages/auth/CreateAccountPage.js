import { useContext } from 'react';
import { redirect } from 'react-router-dom';
import CreateAccountForm from '../../components/auth/CreateAccountForm'
import { createAccount } from '../../lib/api';

import AuthContext from '../../store/auth-context';

let authContext;

const CreateAccountPage = () => {
  authContext = useContext(AuthContext);
  return (<CreateAccountForm />);
}

export default CreateAccountPage;

export async function action({request}) {
    const data = await request.formData();

    await createAccount(
      { 
        username: data.get("username"),
        password: data.get("password"), 
        email: data.get("email"), 
      }
    )
      .then(data => {
        authContext.login(data.token, data.expirationDate);
      })
      .catch(err => {
        return err;
      });
      

    return redirect(`/login`);
}
