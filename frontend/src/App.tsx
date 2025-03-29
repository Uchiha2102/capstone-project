import './App.css'
import {BrowserRouter as Router, Route, Routes, Link, useLocation} from "react-router-dom";
import Appointments from "./pages/Appointments.tsx";
import CreateAppointment from "./pages/CreateAppointment.tsx";
import EditAppointment from "./pages/EditAppointment.tsx";
import Home from "./components/Home.tsx";
import XRayDocuments from "./components/XRayDocuments.tsx";
import './CSS/XrayImage.css';
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

const App = () => {
    return (
        <Router>
            <Header/>
            <SubNavigation/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/appointments" element={<Appointments/>}/>
                <Route path="/create" element={<CreateAppointment/>}/>
                <Route path="/edit/:id" element={<EditAppointment/>}/>
                <Route path="/xray-documents" element={<XRayDocuments/>}/>
            </Routes>
        </Router>
    );
};

export default App;