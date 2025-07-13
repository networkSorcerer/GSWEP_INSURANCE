import type { RouteObject } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../page/login/Login";
import Contracts from "../page/contract/Contracts";
import AppLayout from "../layout/applayout/AppLayout";
import Certificate from "../page/certification/Certificate";

const routers: RouteObject[] = [
  { path: "/", element: <Login /> },
  {
    path: "/contract",
    element: <AppLayout />,
    children: [
      { index: true, element: <Contracts /> },
      { path: "/contract/:contract_id", element: <Certificate /> },
    ],
  },
];

export const Routers = createBrowserRouter(routers);
