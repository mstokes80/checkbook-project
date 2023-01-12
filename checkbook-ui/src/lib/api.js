const CHECKBOOK_API = 'https://localhost:8080';

  export async function getAllCheckbooks(token) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks`, 
    {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not fetch checkbook data.');
    }
  
    return data;
  }


  export async function addCheckbook(checkbookData, token) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks`, {
      method: 'POST',
      body: JSON.stringify(checkbookData),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not add checkbook.');
    }
  
    return null;
  }  


  export async function getAllTransactions(checkbookId, token) {
    const response = await fetch(`${CHECKBOOK_API}/checkbooks/${checkbookId}/transactions`, {
      headers: {
        'Authorization': `Bearer ${token}`
      },
    });
    const data = await response.json();
  
    if (!response.ok) {
      throw new Error(data.message || 'Could not retrieve transactions.');
    }
  
    return data;
  }  