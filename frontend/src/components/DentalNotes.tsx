import {useEffect, useState } from "react";
import axios from "axios";
import {DentalNote} from "../types/DentalNote.tsx";



const DentalNotes = () => {
    const [notes, setNotes] = useState<DentalNote[]>([]);
    const [tooth, setTooth] = useState("");
    const [note, setNote] = useState("");

    useEffect(() => {
        axios.get("/api/dental-notes")
            .then((res) => setNotes(res.data))
            .catch((err) => console.error("Error fetching notes:", err));
    }, []);

    const handleCreateNote = () => {
        axios.post("/api/dental-notes", { tooth, note })
            .then((res) => setNotes([...notes, res.data]))
            .catch((err) => console.error("Error creating note:", err));
    };

    const handleDeleteNote = (id: string) => {
        axios.delete(`/api/dental-notes/${id}`)
            .then(() => setNotes(notes.filter((n) => n.id !== id)))
            .catch((err) => console.error("Error deleting note:", err));
    };

    return (
        <div className="wrapper">
            <div className="dental-notes-container">
                <h2 className="dental-notes-title">My Teeth Notes</h2>
                <div className="dental-notes-form">
                    <input
                        className="dental-input"
                        type="text"
                        placeholder="Tooth (e.g., 41)"
                        value={tooth}
                        onChange={(e) => setTooth(e.target.value)}
                    />
                    <input
                        className="dental-input"
                        type="text"
                        placeholder="Note"
                        value={note}
                        onChange={(e) => setNote(e.target.value)}
                    />
                    <button
                        className="dental-add-btn"
                        onClick={handleCreateNote}
                    >
                        Add Note
                    </button>
                </div>
                <ul className="dental-notes-list">
                    {notes.map((n) => (
                        <li key={n.id} className="dental-note-item">
                            <strong>{n.tooth}: </strong>
                            {n.note}
                            <button
                                className="dental-delete-btn"
                                onClick={() => handleDeleteNote(n.id)}
                            >
                                Delete
                            </button>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="container-home-image">
                <h3>FDI Dental notation </h3>
                <img
                    src="/dental-zahnschema-fdi.jpg"
                    alt="Dental-Zahnschema-FDI"
                />
                <h4>www.MedicalGraphics.de</h4>
                <h5>CC BY-ND 4.0</h5>
            </div>
        </div>
    );
};

export default DentalNotes;



