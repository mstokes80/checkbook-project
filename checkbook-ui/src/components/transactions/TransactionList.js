import { useEffect, useState } from 'react';
import TransactionListItem from './TransactionListItem';
import { getAllTransactions } from '../../lib/api';
import { useLoaderData } from 'react-router-dom';
import { Collapse } from 'react-bootstrap';

const TransactionList = () => {
    const transactions = useLoaderData();
    const [monthValue, setMonthValue] = useState("");
    const [yearValue, setYearValue] = useState("");
    const [transactionTypeFilter, setTransactionTypeFilter] = useState("");
    const [openFilters, setOpenFilters] = useState(false);

    const [filteredTransactions, setFilteredTransactions] = useState([]);
    const [filtersApplied, setFiltersApplied] = useState(false);

    let filterYears = [];
    const currentYear = new Date().getFullYear();
    if(transactions.length > 0) {
        transactions.sort((a,b) => {
            return new Date(a.payedDate) - new Date(b.payedDate);
        });
    
        let basementYear = new Date(transactions[0].payedDate).getFullYear();
        if(basementYear < currentYear){
            for(; basementYear <= currentYear; basementYear++) {
                filterYears.push(basementYear);
            }
        } else {
            filterYears.push(currentYear);
        }
    } else {
        filterYears.push(currentYear);
    }

    useEffect(() => {
        let filteredTransactionsList = [...transactions];
        if(monthValue !== '' && yearValue !== '') {
            filteredTransactionsList = filteredTransactionsList.filter((transaction) => {
                const transactionYear = new Date(transaction.payedDate).getFullYear();
                const transactionMonth = new Date(transaction.payedDate).getMonth();
                return transactionMonth === +monthValue && transactionYear === +yearValue;  
            });
            setFiltersApplied(true);
        }
        if(transactionTypeFilter !== '') {
            filteredTransactionsList = filteredTransactionsList.filter((transaction) => {
                return transaction.transactionType === transactionTypeFilter;
            });
            setFiltersApplied(true);
        }
        setFilteredTransactions(filteredTransactionsList);
    }, [monthValue, yearValue, transactionTypeFilter, transactions]);

    const clearFilterHandler = () => {
        setMonthValue("");
        setYearValue("");
        setTransactionTypeFilter("");
        setFiltersApplied(false);
        setFilteredTransactions([]);
    };
    
    return (
        <div className="row">
            <div className="col-sm-12 mt-3">
                <div className='row mt-3'>
                    <div className='col-sm-12'>
                        <span className='pe-2'>Filter:</span>
                        <button className='btn btn-primary' onClick={() => setOpenFilters(!openFilters)} aria-controls="example-collapse-text" aria-expanded={openFilters}>Show Filters</button>
                    </div>
                    <Collapse in={openFilters}>
                        <div id="example-collapse-text">
                            <div className='row mt-2'>
                                <div className='col-sm-2'>
                                    <span className='pe-2'>Date:</span>
                                </div>    
                                <div className='col-sm-5'>
                                    <select className='form-select' onChange={e => setMonthValue(e.target.value)} value={monthValue}>
                                        <option value="">Select Month</option>
                                        {[1,2,3,4,5,6,7,8,9,10,11,12].map(month => <option key={month} value={month-1}>{month}</option>)}
                                    </select>
                                </div>
                                <div className='col-sm-5'>
                                    <select className='form-select' onChange={e => setYearValue(e.target.value)} value={yearValue}>
                                        <option value="">Select Year</option>
                                        {filterYears.map(year => <option key={year} value={year}>{year}</option>)}
                                    </select>
                                </div>
                            </div>    
                            <div className='row mt-2'>
                                <div className='col-sm-2'>
                                    <span className='pe-2'>Type:</span>
                                </div>
                                <div className='col-sm-10'>
                                    <div className="form-check form-check-inline">
                                        <input className="form-check-input" type="radio" id="inlineRadio1" name="transactionFilter" value="W" onClick={e => setTransactionTypeFilter(e.target.value)} />
                                        <label className="form-check-label" htmlFor="inlineRadio1">Withdrawal</label>
                                    </div>
                                    <div className="form-check form-check-inline">
                                        <input className="form-check-input" type="radio" id="inlineRadio2" name="transactionFilter" value="D" onClick={e => setTransactionTypeFilter(e.target.value)} />
                                        <label className="form-check-label" htmlFor="inlineRadio2">Deposit</label>
                                    </div>
                                </div>    
                            </div>
                            <div className='row mt-2'>
                                <div className='col-sm-12'>
                                    <button className='btn btn-primary' onClick={clearFilterHandler}>Clear Filters</button>
                                </div>
                            </div>    
                        </div>
                    </Collapse>    
                </div>
                {filteredTransactions && filteredTransactions.length > 0 && filteredTransactions.map(transaction => <TransactionListItem key={transaction.id} id={transaction.id} description={transaction.description} amount={transaction.amount} type={transaction.transactionType} payedDate={transaction.payedDate} />)}
                {!filteredTransactions.length > 0 && transactions.length > 0 && !filtersApplied && transactions.map(transaction => <TransactionListItem key={transaction.id} id={transaction.id} description={transaction.description} amount={transaction.amount} type={transaction.transactionType} payedDate={transaction.payedDate} />)}
                {!transactions && <p>No transactions for this checkbook.</p>}
            </div>
        </div>
    );
}

export default TransactionList;

export async function loader({params}) {
    let transactions = await getAllTransactions(params.checkbookId);
    console.log(transactions);
    return transactions;
}