import { useEffect, useState } from "react";
import AxiosApi from "../../../api/AxiosApi";
import { useParams } from "react-router-dom";
interface productCode {
  productCode: string;
}
const EditForm = () => {
  const contract_id = useParams<string>();
  const [productCode, setProductCode] = useState<productCode>();
  const [formData, setFormData] = useState();

  useEffect(() => {
    if (contract_id) {
      GetProductCode(Number(contract_id.contract_id));
    }
  }, [contract_id]);

  useEffect(() => {
    if (productCode) {
      GetForm(productCode);
    }
  }, [productCode]);

  const GetProductCode = async (contractId: number) => {
    const res = await AxiosApi.getProductCode(contractId);
    setProductCode(res.data);
  };

  const GetForm = async (productCode: productCode) => {
    const res = await AxiosApi.getForm(productCode.productCode);
    setFormData(res.data);
  };
  return <div>{formData ? <>영구 있다</> : <>영구 없다</>}</div>;
};

export default EditForm;
