import { Box, Button, styled, TextField, Typography } from "@mui/material";
import { useState } from "react";
import AxiosApi from "../../api/AxiosApi";
import { useNavigate } from "react-router-dom";

const LoginStyle = styled(Box)({
  position: "absolute",
  flexDirection: "column",
  alignItems: "center",
  display: "flex",
  maxWidth: "300px",
  padding: "10px",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
});

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const LoginApi = async () => {
    console.log("email", email);
    console.log("password", password);
    try {
      const res = await AxiosApi.loginApi(email, password);
      console.log("main으로 가니? ", res.success);
      localStorage.setItem("accessToken", res.token);
      if (res.success === true) {
        navigate("/main");
      }
      console.log("res", res);
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <LoginStyle>
      <Typography variant="h3" className="logo">
        Insurance
      </Typography>
      <TextField
        type="text"
        placeholder="ID"
        id="id"
        className="account"
        onChange={(e) => setEmail(e.target.value)}
      />
      <TextField
        type="password"
        placeholder="Password"
        id="password"
        className="account"
        onChange={(e) => setPassword(e.target.value)}
      />
      <Button
        variant="contained" // 또는 'outlined'
        color="warning"
        id="login"
        sx={{ width: "225px" }}
        onClick={LoginApi}
      >
        login
      </Button>
    </LoginStyle>
  );
};

export default Login;
