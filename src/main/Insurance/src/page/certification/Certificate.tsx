import { useParams } from "react-router-dom";
import AxiosApi from "../../api/AxiosApi";
import { useEffect, useState } from "react";
import EditForm from "./EditForm/EditForm";

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

  return (
    <div>
      {" "}
      {data ? (
        <section>
          <p>
            <strong>증권번호:</strong> {data.contract_no || "정보 없음"}
          </p>
          <p>
            <strong>보험기간:</strong> {data.start_date || "?"} ~{" "}
            {data.end_date || "?"}
          </p>
          <p>
            <strong>보험종목:</strong> {data.product_name || "정보 없음"}
          </p>
          <p>
            <strong>보험종목:</strong> {data.co || "정보 없음"}
          </p>
        </section>
      ) : (
        <p>해당 정보가 없습니다.</p>
      )}
      <EditForm />
    </div>
  );
};

export default Certificate;
