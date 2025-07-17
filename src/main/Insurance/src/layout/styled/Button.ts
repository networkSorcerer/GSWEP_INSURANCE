import styled from "styled-components";

// Cancel Button 스타일
export const CancelButton2 = styled.button`
  padding: 10px 20px;
  background-color: #ccc;
  color: #333;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px;

  &:hover {
    background-color: #b3b3b3; /* hover 시 색상 변경 */
  }
`;

export const OrangeButton = styled.button`
  background-color: #ff7043;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 10px 20px;
  font-weight: bold;
  cursor: pointer;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);

  &:hover {
    background-color: #e66030; /* hover 시 색상 변경 */
  }
`;
