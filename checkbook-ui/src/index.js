import React from 'react';
import ReactDOM from 'react-dom/client';
import { AuthContextProvider } from './store/auth-context';
import './index.css';
import App from './App';

import '../node_modules/bootstrap-icons/font/bootstrap-icons.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthContextProvider>
      <App />
    </AuthContextProvider>
  </React.StrictMode>
);
