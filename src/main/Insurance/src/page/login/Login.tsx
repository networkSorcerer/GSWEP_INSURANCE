import { Box, Button, styled, TextField, Typography } from "@mui/material";
import { useState } from "react";
import AxiosApi from "../../api/AxiosApi";
import { useNavigate } from "react-router-dom";
import GoogleLoginButton from "../../layout/styled/GoogleLoginButton";
import Common from "../../util/Common";
import AxiosInstance from "../../api/AxiosInstance";
import { jwtDecode } from "jwt-decode";

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
interface DecodedGoogleToken {
  email: string;
  name: string;
  sub: string; // Google 고유 사용자 ID
}

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [, setError] = useState("");

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
  const handleGoogleLoginSuccess = async (credentialResponse: any) => {
    if (credentialResponse.credential) {
      const decodedToken = jwtDecode<DecodedGoogleToken>(
        credentialResponse.credential
      );
      const { email, name, sub: providerId } = decodedToken;
      const accessToken = credentialResponse.credential;
      Common.setAccessToken(accessToken);
      try {
        const response = await AxiosInstance.post("/api/auth/google", {
          email,
          name,
          provider: "google",
          providerId,
        });
        const { accessToken, refreshToken } = response.data;
        Common.setAccessToken(accessToken);
        Common.setRefreshToken(refreshToken);
        console.log(jwtDecode(accessToken));
      } catch (err) {
        setError("OAuth 로그인 실패");
      }
    }
  };
  const handleGoogleLoginError = () => {
    setError("구글 로그인 오류: ");
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
      <GoogleLoginButton
        onSuccess={handleGoogleLoginSuccess}
        onError={handleGoogleLoginError}
      />
    </LoginStyle>
  );
};

export default Login;
