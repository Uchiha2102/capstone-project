import {Link, useLocation} from "react-router-dom";

const SubNavigation = () => {
    const location = useLocation();

    if (location.pathname.includes("/appointments")) {
        return (
            <nav className="sub-nav">
                <ul className="nav-links">
                    <li>
                        <Link to="/appointments"
                              className={location.pathname === "/appointments" ? "active" : ""}>
                            Appointments
                        </Link>
                    </li>
                    <li>
                        <Link to="/create" className={location.pathname === "/create" ? "active" : ""}>
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