import { useContext } from "react";
import { Link, NavLink } from "react-router-dom";
import AuthContext from "../../store/auth-context";

const Header = () => {
  const authContext = useContext(AuthContext);
  const isLoggedIn = authContext.isLoggedIn;

  const logoutHandler = () => {
    authContext.logout();
  }
  
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">Checkbook Balancer</Link>
        <div className="collapse navbar-collapse justify-content-end me-5">
          <ul className="navbar-nav me-1 mb-lg-0">
            <li className="nav-item">
              <NavLink className={({isActive}) => isActive ? 'nav-link active':'nav-link'} aria-current="page" to="/checkbook">Checkbooks</NavLink>
            </li>
            {isLoggedIn && 
              <li className="nav-item">
                <button className="btn btn-link nav-link" aria-current="page" onClick={logoutHandler} style={{border: 0}}>Logout</button>
              </li>
            }
            {!isLoggedIn && 
              <li className="nav-item">
                <NavLink className={({isActive}) => isActive ? 'nav-link active':'nav-link'} aria-current="page" to="/">Login</NavLink>
              </li>
            }
          </ul>
        </div>
      </div>
    </nav>);
}

export default Header;