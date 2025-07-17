import type { RouteObject } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../page/login/Login";
import Contracts from "../page/contract/Contracts";
import AppLayout from "../layout/applayout/AppLayout";
import DynamicForm from "../page/practice/DynamicForm";
import CertificateMain from "../page/certification/CertificateMain";

const routers: RouteObject[] = [
  { path: "/", element: <Login /> },
  { path: "/practice", element: <DynamicForm /> },
  {
    path: "/contract",
    element: <AppLayout />,
    children: [
      { index: true, element: <Contracts /> },
      { path: "/contract/:contract_id", element: <CertificateMain /> },
    ],
  },
];

export const Routers = createBrowserRouter(routers);
