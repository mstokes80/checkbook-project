import { Outlet } from 'react-router-dom';
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const CheckbookLayout = () => {
    return (
        <main className="d-flex flex-column min-vh-100">
            <Header />
            <div className="container-fluid">
                <Outlet />
            </div>
            <Footer />
        </main>
    );
}

export default CheckbookLayout;