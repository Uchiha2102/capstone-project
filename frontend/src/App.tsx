import './App.css'
import {BrowserRouter as Router, Route, Routes, Link} from "react-router-dom";
import Appointments from "./pages/Appointments.tsx";
import CreateAppointment from "./pages/CreateAppointment.tsx";
import EditAppointment from "./pages/EditAppointment.tsx";


const App = () => {
    return (
        <Router>
            <nav>
                <Link to="/">Appointments</Link> | <Link to="/create">New Appointment</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Appointments/>}/>
                <Route path="/create" element={<CreateAppointment/>}/>
                <Route path="/edit/:id" element={<EditAppointment/>}/>
            </Routes>
        </Router>
    );
};

export default App;

