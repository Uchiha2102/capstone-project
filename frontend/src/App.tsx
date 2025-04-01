import './CSS/App.css'
import {Route, Routes} from "react-router-dom";
import Appointments from "./components/Appointments.tsx";
import CreateAppointment from "./components/CreateAppointment.tsx";
import EditAppointment from "./components/EditAppointment.tsx";
import Home from "./components/Home.tsx";
import XRayDocuments from "./components/XRayDocuments.tsx";
import './CSS/XrayImage.css';
import Header from "./components/Header.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import ProtectedRoutes from "./Auth/ProtectedRoutes.tsx";
import SubNavigation from "./components/SubNavigation.tsx";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        axios.get("/api/auth/me")
            .then(() => {
                setIsLoggedIn(true);
            })
            .catch(() => setIsLoggedIn(false));
    }, []);

    return (
        <>
            <Header/>
            {location.pathname.includes('/appointments') && <SubNavigation/>}
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route element={<ProtectedRoutes isLoggedIn={isLoggedIn}/>}>
                    <Route path="/appointments" element={<Appointments/>}/>
                    <Route path="/create" element={<CreateAppointment/>}/>
                    <Route path="/edit/:id" element={<EditAppointment/>}/>
                    <Route path="/xray-documents" element={<XRayDocuments/>}/>
                </Route>
            </Routes>
        </>
    );
}

export default App;


