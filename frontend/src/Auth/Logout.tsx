import {useNavigate} from "react-router";
import axios from "axios";


const Logout = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        axios.post("/logout")
            .then(() => {
                navigate("/login");
            })
            .catch((error) => console.error("Logout failed:", error));
    }

    return(
        <button onClick={handleLogout} className="logout-button">
            Logout
        </button>
    );
}
export default Logout;