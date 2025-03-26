import {useNavigate, useParams} from "react-router";
import {useEffect, useState} from "react";
import axios from "axios";


type Appointment = {
    id: string;
    date: string;
    time: string;
    dentistName: string;
    description: string;
}

const EditAppointment = () => {
    const {id} = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [appointment, setAppointment] = useState<Appointment | null>(null);

    useEffect(() => {

        if (id) {
            axios.get(`/api/appointments/${id}`)
                .then((response) => setAppointment(response.data))
                .catch((err) => console.error("Error loading the appointment", err));
        }
    }, [id]);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (appointment) {
            axios.put(`/api/appointments/${id}`, appointment)
                .then(() => {
                    alert("Appointment successfully updated!");
                    navigate("/");
                })
                .catch((err) => console.error("Error updating the appointment:", err));
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        if (appointment) {
            setAppointment({...appointment, [e.target.name]: e.target.value});
        }
    };
    if (!appointment) {
        return <p> Loading the appointment...</p>
    }

    return (
        <div>
            <h1>Edit Appointment</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Date: </label>
                    <input
                        type="date"
                        name="date"
                        value={appointment.date}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Time: </label>
                    <input
                        type="time"
                        name="time"
                        value={appointment.time}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Dentist: </label>
                    <input
                        type="text"
                        name="dentistName"
                        value={appointment.dentistName}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Description: </label>
                    <textarea
                        name="description"
                        value={appointment.description}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Save changes</button>
            </form>
        </div>
    );
};
export default EditAppointment;
