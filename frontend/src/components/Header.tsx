import { Link, useLocation } from "react-router-dom";

const Header = () => {
    const location = useLocation();

    return (
        <header className="main-header">
            <nav>
                <ul className="nav-links">
                    <li>
                        <Link to="/" className={location.pathname === "/" ? "active" : ""}>
                            Home
                        </Link>
                    </li>
                    <li>
                        <Link to="/appointments" className={location.pathname.includes("/appointments") ? "active" : ""}>
                            Appointments
                        </Link>
                    </li>
                    <li>
                        <Link to="/documents" className={location.pathname === "/documents" ? "active" : ""}>
                            Documents
                        </Link>
                    </li>
                    <li>
                        <Link to="/xray-documents" className={location.pathname === "/xray-documents" ? "active" : ""}>
                            XRay Documents
                        </Link>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;
