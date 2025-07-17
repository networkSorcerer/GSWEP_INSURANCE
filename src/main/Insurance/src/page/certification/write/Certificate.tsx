import styled from "styled-components";
import { useParams } from "react-router-dom";
import AxiosApi from "../../../api/AxiosApi";
import { useEffect, useState } from "react";
import EditForm from "../EditForm/EditForm";
import {
  ButtonWrapper,
  Container,
  Header,
  Section,
} from "../../../layout/applayout/Contrainer";
import ContractModal from "../modal/ContractModal";
import { OrangeButton } from "../../../layout/styled/Button";

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
