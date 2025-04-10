import {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import axios from "axios";


const Home = () => {
    const [appointmentsCount, setAppointmentsCount] = useState<number | null>(null);
    const [documentsCount, setDocumentsCount] = useState<number | null>(null);
    
    const navigate = useNavigate();
    
    useEffect(() => {
        axios.get("/api/appointments")
            .then((res) => setAppointmentsCount(res.data.length)) 
            .catch(() => console.error("Fehler beim Laden der Termine."));

      
        axios.get("/api/documents")
            .then((res) => setDocumentsCount(res.data.length))
            .catch(() => console.error("Fehler beim Laden der Dokumente."));
    }, []); 

    return (
            <div className="home-container">
                <div className="summary-section">
                    {appointmentsCount !== null && (
                        <div className="summary-item">
                            <strong>Appointments:</strong> You have {appointmentsCount} upcoming appointments.
                        </div>
                    )}
                    {documentsCount !== null && (
                        <div className="summary-item">
                            <strong>Documents:</strong> You have {documentsCount} saved documents.
                        </div>
                    )}
                </div>

            <div className="quick-links">
                <button
                    onClick={() => navigate("/create")}
                    className="btn btn-create"
                >
                    Create New Appointment
                </button>

                <button
                    onClick={() => navigate("/documents")}
                    className="btn btn-documents">
                    View Documents
                </button>

                <button
                    onClick={() => navigate("/xray-documents")}
                    className="btn btn-xray">
                    View X-ray Images
                </button>
            </div>
            <p className="info-text">
                - Keep important notes and X-rays organized. <br/>
                - Manage your dental health effortlessly. <br/>
                - Schedule and track dental visits efficiently.<br />
            </p>
        </div>
    );
};

export default Home;
