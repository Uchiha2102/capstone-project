import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import "../CSS/Appointments.css";
import {Appointment} from "../types/Appointment.tsx";

const Appointments = () => {
    const [appointments, setAppointments] = useState<Appointment[]>([]);

    useEffect(() => {
        fetchAppointments();
    }, []);


    const fetchAppointments = () => {
        axios.get("/api/appointments")
            .then((response) => setAppointments(response.data))
            .catch((error) => console.error("Error loading appointments:", error));
    };

    const deleteAppointment = (id: string) => {
        axios.delete(`/api/appointments/${id}`)
            .then(() => {
                alert("Appointment successfully deleted!");
                fetchAppointments();
            })
            .catch((error) => console.error("Error deleting the appointment:", error));
    };

    return (
        <div className="appointments-container">
            <h1 className="appointments-title">Appointments</h1>
            <ul className="appointments-list">
                {appointments.map(({id, date, time, dentistName, description}) => (
                    <li key={id} className="appointment-item">
                        <div className="appointment-info">
                            <div className="appointment-date-time">
                                <span className="date">{date}</span> – <span className="time">{time}</span>
                            </div>
                            <div className="appointment-dentist">{dentistName}</div>
                            <div className="appointment-description">{description}</div>
                        </div>
                        <div className="appointment-actions">
                            <Link to={`/edit/${id}`} className="button edit-button">Edit</Link>
                            <button className="button delete-button" onClick={() => deleteAppointment(id)}>Delete
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Appointments;