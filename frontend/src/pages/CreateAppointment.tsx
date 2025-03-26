import React, {useState} from "react";
import axios from "axios";


const CreateAppointment = () => {
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");
    const [dentistName, setDentistName] = useState("");
    const [description, setDescription] = useState("");

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
        <div>
            <h1>Create New Appointment</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Date</label>
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Time: </label>
                    <input
                        type="time"
                        value={time}
                        onChange={(e) => setTime(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Dentist: </label>
                    <input
                        type="text"
                        value={dentistName}
                        onChange={(e) => setDentistName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Description: </label>
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>
                <button type="submit"> Add Appointment</button>
            </form>
        </div>
    );
};
export default CreateAppointment;