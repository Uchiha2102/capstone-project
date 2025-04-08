import {useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router";

export default function Login() {
    const navigate = useNavigate();

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/google', '_self')
    }

    useEffect(() => {
        axios.get("/api/auth/me")
            .then(() => navigate("/"))
            .catch(() => console.log("User not logged in"));
    }, []);

    return(
        <div className="login-page">
        <div className="login-container">
    <button className="login-button" onClick={login}>Login with Google</button>
        </div>
        </div>
    );
}
