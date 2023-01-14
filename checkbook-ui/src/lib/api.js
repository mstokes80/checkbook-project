const CHECKBOOK_API = 'https://localhost:8080';

  export async function getAllCheckbooks() {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks`, 
    {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not fetch checkbook data.');
    }
  
    return data;
  }


  export async function addCheckbook(checkbookData) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks`, {
      method: 'POST',
      body: JSON.stringify(checkbookData),
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not add checkbook.');
    }
  
    return null;
  }  


  export async function deleteCheckbook(checkbookId) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks/${checkbookId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
    });
  
    if (!response.ok) {
      throw new Error('Could not delete checkbook.');
    }
  
    return null;
  }


  export async function getAllTransactions(checkbookId) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks/${checkbookId}/transactions`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not retrieve transactions.');
    }
  
    return data;
  }  


  export async function addTransaction(transactionData, checkbookId) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks/${checkbookId}/transactions`, {
      method: 'POST',
      body: JSON.stringify(transactionData),
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not add transaction.');
    }
  
    return null;
  }  


  export async function deleteTransaction(transactionId) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks/transactions/${transactionId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json'
      },
    });
  
    if (!response.ok) {
      throw new Error('Could not delete transaction.');
    }
  
    return null;
  }  


  export async function createAccount(userData) {
    console.log(userData);
    const response = await fetch(`${CHECKBOOK_API}/api/auth/signup`, {
      method: 'POST',
      body: JSON.stringify(userData),
      headers: {
        'Content-Type': 'application/json'
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not add transaction.');
    }
  
    return data;
  }  