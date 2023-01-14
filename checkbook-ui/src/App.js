import { useContext } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import CheckbookLayout from './components/layout/CheckbookLayout';

import CheckbookPage from './pages/CheckbookPage';
import CheckbookForm from './components/checkbook/CheckbookForm';
import Transactions from './pages/Transactions';
import CreateCheckbook from './pages/CreateCheckbook';
import CreateTransactionPage from './pages/CreateTransactionPage';
import LoginPage from './pages/auth/LoginPage';
import WelcomePage from './pages/WelcomePage';
import NotFound from './pages/NotFound';
import CreateAccountPage from './pages/auth/CreateAccountPage';

import AuthContext from './store/auth-context';

import { action as createCheckbookAction } from './pages/CreateCheckbook';
import { action as createTransactionAction } from './pages/CreateTransactionPage';
import { action as createAccountAction } from './pages/auth/CreateAccountPage';
import { loader as checkbookLoader } from './components/checkbook/CheckbookListing';
import { loader as transactionLoader } from './components/transactions/TransactionList';

import './App.css';
import './scss/custom.scss'

function App() {
  const authContext = useContext(AuthContext);
  const router = createBrowserRouter([
    { path: '/', element: <CheckbookLayout />, children: [
      { index: true, element: authContext.isLoggedIn ? <WelcomePage /> : <LoginPage />},
      { path: '/checkbook', element: authContext.isLoggedIn ? <CheckbookPage /> : <LoginPage />, loader: authContext.isLoggedIn ? checkbookLoader : null, children: [
        { path: ':checkbookId', element: <CheckbookForm />},
        { path: ':checkbookId/transactions', element: <Transactions />, loader: authContext.isLoggedIn ? transactionLoader : null},
        { path: ':checkbookId/transactions/new-transaction', element: <CreateTransactionPage />, action: createTransactionAction},
        { path: 'create-checkbook', element: <CreateCheckbook />, action: createCheckbookAction}
      ]},
      { path: '/login', element: <LoginPage />},
      { path: '/create-account', element: <CreateAccountPage />, action: createAccountAction},
      { path: '*', element: <NotFound />}
    ]},
  ]);
  return (
    <RouterProvider router={router}></RouterProvider>
  );
}

export default App;
