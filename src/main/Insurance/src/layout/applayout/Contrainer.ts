import styled from "styled-components";

export const Container = styled.div`
width: 800px;
  flex: 1;
  position: absolute;
  top: 30%;
  left: 30%;
  margin-top: 50px;
  /* 원하는 크기와 스타일 지정 */
  margin-left: 50px
  justify-content: center;
  align-items: center;
`;

export const FlexWrapper = styled.div`
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
  margin-top: 24px;

  @media (max-width: 768px) {
    flex-direction: column;
  }
`;

export const LeftPanel = styled.div`
  flex: 1;
  min-width: 300px;
`;

export const RightPanel = styled.div`
  flex: 1;
  min-width: 300px;
`;

export const fieldDiv = styled.div`
  width: 600px;
`;


// 스타일 컴포넌트 추가
export const Section = styled.section`
  background: #fffdf7;
  padding: 24px 32px;
  margin-bottom: 32px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgb(255 112 67 / 0.15);
  font-family: "Noto Sans KR", sans-serif;
  color: #4a4a4a;

  p {
    margin: 10px 0;
    font-size: 16px;
  }
  strong {
    color: #ff7043;
    margin-right: 8px;
  }
`;

export const Header = styled.h1`
  color: #ff7043;
  font-weight: 700;
  margin-bottom: 24px;
  font-size: 28px;
  text-align: center;
  font-family: "Noto Sans KR", sans-serif;
`;

export const ButtonWrapper = styled.div`
  text-align: center;
  margin: 40px 0 60px;

  button {
    font-size: 18px;
    padding: 14px 28px;
    box-shadow: 0 3px 8px rgba(255, 112, 67, 0.4);
    transition: all 0.3s ease;
  }
  button:hover {
    background-color: #e66030;
    box-shadow: 0 5px 15px rgba(230, 96, 48, 0.6);
  }
`;