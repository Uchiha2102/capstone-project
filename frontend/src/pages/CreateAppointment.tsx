import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";


const CreateAppointment = () => {
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");
    const [dentistName, setDentistName] = useState("");
    const [description, setDescription] = useState("");

    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const newAppointment = {date, time, dentistName, description};

        axios.post("/api/appointments", newAppointment)
            .then(() => {
                alert("Appointment successfully created!");
                setDate("");
                setTime("");
                setDentistName("");
                setDescription("");
            })
            .catch((error) => console.error("Error creating an appointment:", error));
    };

    return (
        <div className="edit-appointment-modal">
            <div className="modal-content">
                <h1>Create New Appointment</h1>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="appointmentDate">Date:</label>
                        <input
                            id="appointmentDate"
                            type="date"
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="appointmentTime">Time:</label>
                        <input
                            id="appointmentTime"
                            type="time"
                            value={time}
                            onChange={(e) => setTime(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="dentistName">Dentist:</label>
                        <input
                            id="dentistName"
                            type="text"
                            value={dentistName}
                            onChange={(e) => setDentistName(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="appointmentDescription">Description:</label>
                        <textarea
                            id="appointmentDescription"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                        ></textarea>
                    </div>
                    <button type="submit">Add Appointment</button>
                    <button type="button" onClick={() => navigate("/appointments")}>
                        Cancel
                    </button>
                </form>
            </div>
        </div>
    );
};
export default CreateAppointment;