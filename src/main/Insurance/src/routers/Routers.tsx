import type { RouteObject } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../page/login/Login";
import MainPage from "../page/applayout/MainPage";

const routers: RouteObject[] = [
  { path: "/", element: <Login /> },
  {
    path: "/main",
    element: <MainPage />,
  },
];

export const Routers = createBrowserRouter(routers);
