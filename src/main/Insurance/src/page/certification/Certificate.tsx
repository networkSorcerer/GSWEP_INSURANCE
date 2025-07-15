import styled from "styled-components";
import { useParams } from "react-router-dom";
import AxiosApi from "../../api/AxiosApi";
import { useEffect, useState } from "react";
import EditForm from "./EditForm/EditForm";
import { Container } from "../../layout/applayout/Contrainer";
import ContractModal from "../contract/modal/ContractModal";
import { OrangeButton } from "../../layout/styled/Button";

type id = {
  contract_id: string;
};
interface dataType {
  contract_no: string;
  start_date: string;
  end_date: string;
  product_name: string;
  co: string;
}

// 스타일 컴포넌트 추가
const Section = styled.section`
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

const Header = styled.h1`
  color: #ff7043;
  font-weight: 700;
  margin-bottom: 24px;
  font-size: 28px;
  text-align: center;
  font-family: "Noto Sans KR", sans-serif;
`;

const ButtonWrapper = styled.div`
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

const Certificate = () => {
  const contract_id = useParams<id>();
  const [data, setData] = useState<dataType>();

  useEffect(() => {
    if (contract_id) {
      DetailContract(Number(contract_id.contract_id));
    }
  }, [contract_id]);

  const DetailContract = async (id: number) => {
    const res = await AxiosApi.getDataById(id);
    setData(res.data.data);
  };

  const [certiModal, setCertiModal] = useState(false);
  const [contractId, setContractId] = useState(0);

  const CertiModalState = () => {
    setCertiModal(true);
    setContractId(Number(contract_id.contract_id));
  };
  const closeModal = () => {
    setCertiModal(false);
  };
  return (
    <Container>
      <Header>가입 증명서 정보</Header>
      {data ? (
        <Section>
          <p>
            <strong>증권번호:</strong> {data.contract_no || "정보 없음"}
          </p>
          <p>
            <strong>보험기간:</strong>{" "}
            {data.start_date?.substring(0, 10) || "?"} ~{" "}
            {data.end_date?.substring(0, 10) || "?"}
          </p>
          <p>
            <strong>보험종목:</strong> {data.product_name || "정보 없음"}
          </p>
          <p>
            <strong>피보험자:</strong> {data.co || "정보 없음"}
          </p>
        </Section>
      ) : (
        <p>해당 정보가 없습니다.</p>
      )}

      <EditForm />

      <ButtonWrapper>
        <OrangeButton onClick={() => CertiModalState()}>
          가입 증명서 확인
        </OrangeButton>
      </ButtonWrapper>

      <ContractModal
        open={certiModal}
        close={closeModal}
        type={true}
        id={contractId}
      />
    </Container>
  );
};

export default Certificate;
