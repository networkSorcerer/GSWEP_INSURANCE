import type { RouteObject } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../page/login/Login";
import Contracts from "../page/contract/Contracts";
import AppLayout from "../layout/applayout/AppLayout";
import Certificate from "../page/certification/Certificate";
import DynamicForm from "../page/practice/DynamicForm";

const routers: RouteObject[] = [
  { path: "/", element: <Login /> },
  { path: "/practice", element: <DynamicForm /> },
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
