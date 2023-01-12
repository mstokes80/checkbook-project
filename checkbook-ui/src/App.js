import { useContext } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import CheckbookLayout from './components/layout/CheckbookLayout';

import './App.css';
import CheckbookPage from './pages/CheckbookPage';
import CheckbookForm from './components/checkbook/CheckbookForm';
import Transactions from './pages/Transactions';
import CreateCheckbook from './pages/CreateCheckbook';
import CreateTransactionPage from './pages/CreateTransactionPage';
import LoginPage from './pages/auth/LoginPage';

import AuthContext from './store/auth-context';

import { action as createCheckbookAction } from './pages/CreateCheckbook';
import { action as createTransactionAction } from './pages/CreateTransactionPage';
import { loader as checkbookLoader } from './components/checkbook/CheckbookListing';
import { loader as transactionLoader } from './components/transactions/TransactionList';

function App() {
  const authContext = useContext(AuthContext);
  const router = createBrowserRouter([
    { path: '/', element: <CheckbookLayout />, children: [
      { index: true, element: authContext.isLoggedIn ? <CheckbookPage /> : <LoginPage />},
      { path: '/checkbook', element: authContext.isLoggedIn ? <CheckbookPage /> : <LoginPage />, loader: {checkbookLoader}, children: [
        { path: ':checkbookId', element: <CheckbookForm />},
        { path: ':checkbookId/transactions', element: <Transactions />, loader: {transactionLoader}},
        { path: ':checkbookId/transactions/new-transaction', element: <CreateTransactionPage />, action: createTransactionAction},
        { path: 'create-checkbook', element: <CreateCheckbook />, action: createCheckbookAction}
      ]}
    ]},
  ]);
  return (
    <RouterProvider router={router}></RouterProvider>
  );
}

export default App;
