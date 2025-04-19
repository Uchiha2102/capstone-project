import {useEffect, useState} from "react";
import axios from "axios";
import {PastAppointment} from "../types/PastAppointment.tsx";


const AppointmentHistory = () => {
    const [pastAppointments, setPastAppointments] = useState<PastAppointment[]>([]);

    useEffect(() => {
        axios.get("/api/appointments/history")
            .then((res) => setPastAppointments(res.data))
            .catch(() => console.error("Error loading appointment history."));
    }, []);

    return (
        <div className="history-container">
            <h1 className="history-title">Appointment History</h1>
            <ul className="history-list">
                {pastAppointments.map(({ id, date, time, dentistName, description }) => (
                    <li key={id} className="history-item">
                        <div className="history-info">
                            <div>
                                <strong>Date:</strong> {date}
                            </div>
                            <div>
                                <strong>Time:</strong> {time}
                            </div>
                            <div>
                                <strong>Dentist:</strong> {dentistName}
                            </div>
                            <div>
                                <strong>Description:</strong> {description}
                            </div>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AppointmentHistory;
