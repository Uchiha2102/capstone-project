import {useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router";
import {Button} from "@mui/material";
import GoogleIcon from '@mui/icons-material/Google';


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

    return (
        <div className="login-page">
            <div className="login-container">
                <Button className="login-button"
                        variant="contained"
                        onClick={login}
                        startIcon={<GoogleIcon/>}>
                    Sign in with Google
                </Button>
            </div>
        </div>
    );
}


