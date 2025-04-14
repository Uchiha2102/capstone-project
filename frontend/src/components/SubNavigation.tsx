import {Link, useLocation} from "react-router-dom";

const SubNavigation = () => {
    const location = useLocation();
    if (location.pathname.includes("/appointments")) {
        return (
            <nav className="sub-nav">
                <ul className="nav-links">
                    <li>
                        <Link
                            to="/appointments/history"
                            className={location.pathname === "/appointments/history" ? "active" : ""}>
                            History
                        </Link>
                    </li>
                    <li>
                        <Link
                            to="/create"
                            className={location.pathname === "/create" ? "active" : ""}>
                            New Appointment
                        </Link>
                    </li>
                </ul>
            </nav>
        );
    }
    return null;
};

export default SubNavigation;