import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import "../CSS/Appointments.css";


type Appointment = {
    id: string;
    date: string;
    time: string;
    dentistName: string;
    description: string;
};

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


                {appointments.map((appointment) => (
                    <li key={appointment.id} className="appointment-item">
                        <div className="appointment-info">
                            <div className="appointment-date-time">

                                <span className="date">{appointment.date}</span> –{" "}
                                <span className="time">{appointment.time}</span>
                            </div>
                            <div className="appointment-dentist">{appointment.dentistName}</div>
                            <div className="appointment-description">{appointment.description}</div>
                            </div>
                            <div className="appointment-actions">
                            <Link
                                className="button edit-button"
                                to={`/edit/${appointment.id}`}
                            >
                                Edit
                            </Link>
                            <button
                                className="button delete-button"
                                onClick={() => deleteAppointment(appointment.id)}
                            >
                                Delete
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Appointments;
