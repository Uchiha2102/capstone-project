import { useEffect, useState } from "react";
import axios from "axios";


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
        axios.get("/api/appointments")
            .then((response) => setAppointments(response.data))
            .catch((error) => console.error("Fehler beim Laden der Termine:", error));
    }, []);
    
    return (
        <div>
            <h1>Appointments</h1>
            <ul>
                {appointments.map((appointment)=> (
                <li key={appointment.id}>
                Date: {appointment.date}, Time: {appointment.time}, Dentist: {appointment.dentistName}, Description:
                    {appointment.description}
                </li>
                ))}
            </ul>
        </div>
    );
};
export default Appointments;

