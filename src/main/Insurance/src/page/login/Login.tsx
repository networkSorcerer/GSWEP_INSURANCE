import { LoginStyled } from "../../styled/Login";

const Login = () => {
  return (
    <div>
      <LoginStyled className="main">
        <h1 className="logo">Insurance</h1>
        <LoginStyled className="container">
          <input type="text" placeholder="ID" id="id" className="account" />
          <input
            type="password"
            placeholder="Password"
            id="password"
            className="account"
          />
          <button id="login" className="account">
            login
          </button>
          <p id="alert" className="account">
            {" "}
          </p>
        </LoginStyled>
      </LoginStyled>{" "}
    </div>
  );
};

export default Login;
