import {useNavigate} from "react-router";
import axios from "axios";


const Logout = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        axios.post("/api/auth/logout")
            .then(() => {
                console.log("Successfully logged out");
                navigate("/login");
            })
            .catch((error) => console.error("Logout failed:", error.response || error));
    };
    return (
        <button onClick={handleLogout} className="logout-button">
            Logout
        </button>
    );
};

export default Logout;