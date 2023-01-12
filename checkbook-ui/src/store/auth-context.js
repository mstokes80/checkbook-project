import React, { useState, useEffect, useCallback } from "react";

let logoutTimer;

const AuthContext = React.createContext({
    token: '',
    isLoggedIn: false,
    login: (token) => {},
    logout: () => {}
});

const calculateExpTime = (expTime) => {
    const currentTime = new Date().getTime();
    const adjustedTime = new Date(expTime).getTime();
    const remainingDuration = adjustedTime - currentTime;

    return remainingDuration;
};

const restrieveStoredToken = () => {
    const storedToken = localStorage.getItem('token');
    const storedExpirationTime = localStorage.getItem('expirationTime');
    const remainingTime = calculateExpTime(storedExpirationTime);
    if(remainingTime <= 360000){
        localStorage.removeItem('token');
        localStorage.removeItem('expirationTime');
        return null;
    } else {
        return {
            token: storedToken, duration: remainingTime
        };
    }
}

export const AuthContextProvider = (props) => {
    const tokenData = restrieveStoredToken();
    let initialToken;
    if(tokenData) {
        initialToken = tokenData.token;
    }
    const [token, setToken] = useState(initialToken);
    const userIsLoggedIn = !!token;
    const loginHandler = (token, expTime) => {
        setToken(token);
        localStorage.setItem('token', token);
        localStorage.setItem('expirationTime', expTime);
        const remainingTime = calculateExpTime(expTime);
        logoutTimer = setTimeout(logoutHandler, remainingTime);
    };
    const logoutHandler = useCallback(() => {
        setToken(null);
        localStorage.removeItem('token');
        localStorage.removeItem('expirationTime');
        if(logoutTimer) {
            clearTimeout(logoutTimer);
        }
    },[]);

    useEffect(() => {
        if(tokenData){
            logoutTimer = setTimeout(logoutHandler, tokenData.duration);
        }
    }, [tokenData])

    const contextValue = {
        token: token,
        isLoggedIn: userIsLoggedIn,
        login: loginHandler,
        logout: logoutHandler
    }

    return <AuthContext.Provider value={contextValue}>{props.children}</AuthContext.Provider>
};

export default AuthContext;