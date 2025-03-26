import './App.css'
import {BrowserRouter as Router, Route, Routes, Link} from "react-router-dom";
import Appointments from "./pages/Appointments.tsx";
import CreateAppointment from "./pages/CreateAppointment.tsx";

const App = () => {
    return (
        <Router>
            <nav>
                <Link to="/">Appointments</Link> | <Link to="/create">New Appointment</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Appointments />} />
                <Route path="/create" element={<CreateAppointment />} />
            </Routes>
        </Router>
    );
};

export default App;

