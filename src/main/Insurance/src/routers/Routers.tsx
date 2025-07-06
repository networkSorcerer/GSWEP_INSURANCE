import { RouteObject, createBrowserRouter } from "react-router-dom";
import Login from "../page/login/Login";

const routers: RouteObject[] = [{ path: "/", element: <Login /> }];

export const Routers = createBrowserRouter(routers);
