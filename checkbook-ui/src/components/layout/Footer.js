const Footer = (props) => {
    let today = new Date();
    return (
        <footer className="mt-auto">
            <div className="col-md-4 d-flex align-items-center">
                <span className="mb-3 mb-md-0 text-muted">© {today.getFullYear()} Stokes Software Solutions, Inc</span>
            </div>
        </footer>
    );
}

export default Footer;