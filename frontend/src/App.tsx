import './CSS/App.css'
import {Navigate, Route, Routes, useLocation} from "react-router-dom";
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
import Login from "./Auth/Login.tsx";
import './CSS/LoginButton.css';
import DocumentsPage from "./components/DocumentsPage.tsx";


function App() {
    const location = useLocation();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        axios.get("/api/auth/me")
            .then(() => setIsLoggedIn(true))
            .catch(() => setIsLoggedIn(false));
    }, []);

    return (
        <>
            <Header/>
            {location.pathname.includes('/appointments') && <SubNavigation/>}
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/" element={isLoggedIn ? <Home/> : <Navigate to="/login" replace/>}/>
                <Route element={<ProtectedRoutes isLoggedIn={isLoggedIn}/>}>
                    <Route path="/appointments" element={<Appointments/>}/>
                    <Route path="/create" element={<CreateAppointment/>}/>
                    <Route path="/edit/:id" element={<EditAppointment/>}/>
                    <Route path="/documents" element={<DocumentsPage/>}/>
                    <Route path="/xray-documents" element={<XRayDocuments/>}/>
                </Route>
            </Routes>
        </>
    );
}

export default App;


