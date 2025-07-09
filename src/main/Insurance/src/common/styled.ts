import styled from "styled-components";

export const PageNavigateStyled = styled.div`
  width: 100%;
  max-width: 100%;
  overflow-x: auto;
  padding: 1rem;
  background-color: #dce1e6;
  text-align: center;

  ul {
    display: inline-flex;
    justify-content: center;
    flex-wrap: nowrap;
    padding: 0;
    margin: 0 auto;
    list-style: none;
    gap: 4px;
  }

  li {
    padding: 8px;
    margin: 1px;
    border-radius: 4px;
    white-space: nowrap;

    a {
      color: black;
      text-decoration: none;
    }

    &:hover {
      background-color: skyblue;

      a {
        color: white;
      }
    }
  }

  .active {
    background-color: skyblue;
    border: 1px solid black;
    color: white;
    border-radius: 5px;
  }

  .disabled {
    visibility: hidden;
  }

  @media (max-width: 600px) {
    ul {
      justify-content: flex-start;
      width: 100%;
    }
  }
`;
