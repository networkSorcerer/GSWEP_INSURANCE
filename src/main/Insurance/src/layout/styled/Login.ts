import styled from "styled-components";

export const LoginStyled = styled.div`
  .main {
    width: 250px;
    height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid lightgrey;
    border-radius: 5px;
  }

  .logo {
    margin-top: 0px;
    margin-bottom: 40px;
  }

  #login {
    width: 100%;
    background-color: skyblue;
    border-color: transparent;
    color: white;
  }

  .account {
    display: block;
    margin-bottom: 3px;
    padding: 3px;
    border: 1px solid lightgray;
    border-radius: 3px;
  }

  #alert {
    border-color: transparent;
  }
`;
