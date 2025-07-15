import styled from "styled-components";

export const Container = styled.div`
  flex: 1;
  position: absolute;
  top: 30%;
  left: 30%;

  /* 원하는 크기와 스타일 지정 */
  margin_left: 50px
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
