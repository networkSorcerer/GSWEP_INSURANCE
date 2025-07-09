import type { RouteObject } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../page/login/Login";
import Contracts from "../page/contract/Contracts";
import AppLayout from "../layout/applayout/AppLayout";

const routers: RouteObject[] = [
  { path: "/", element: <Login /> },
  {
    path: "/main",
    element: <AppLayout />,
    children: [{ index: true, element: <Contracts /> }],
  },
];

export const Routers = createBrowserRouter(routers);
