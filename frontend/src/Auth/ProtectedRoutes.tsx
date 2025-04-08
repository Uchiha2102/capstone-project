import {Navigate, Outlet} from "react-router-dom";

type Props = {
    isLoggedIn: boolean
}

export default function ProtectedRoutes({isLoggedIn}: Readonly<Props>) {
    if (isLoggedIn === null) {
        return <div>...Loading</div>;
    }
    return isLoggedIn ? <Outlet/> : <Navigate to="/login"/>;
}
