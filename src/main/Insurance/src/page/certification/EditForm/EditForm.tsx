import { useEffect, useState } from "react";
import AxiosApi from "../../../api/AxiosApi";
import { useParams } from "react-router-dom";
import { Button, Input } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import SaveRoundedIcon from "@mui/icons-material/SaveRounded";
import DeleteForeverRoundedIcon from "@mui/icons-material/DeleteForeverRounded";
interface productCode {
  productCode: string;
}
interface fieldType {
  formId: number;
  label: string;
  order: number;
  fieldId: number;
  fieldAnswersResponseDTO: {
    fieldsId: number;
    answers: string;
    id: number;
  };
}
const EditForm = () => {
  const contract_id = useParams<{ contract_id: string }>();
  const [productCode, setProductCode] = useState<productCode>();
  // const [formData, setFormData] = useState();
  const [formId, setFormId] = useState<number>();
  const [listFormId, setListFormId] = useState();
  const [fieldsList, setFieldsList] = useState<fieldType[]>();
  useEffect(() => {
    if (contract_id) {
      GetProductCode(Number(contract_id.contract_id));
    }
  }, [contract_id]);

  // useEffect(() => {
  //   if (productCode) {
  //     GetForm(productCode);
  //   }
  // }, [productCode]);

  const GetProductCode = async (contractId: number) => {
    const res = await AxiosApi.getProductCode(contractId);
    setProductCode(res.data);
  };

  // const GetForm = async (productCode: productCode) => {
  //   // 이것도 그냥 나중에 새로운 증권을 등록 할때 가져 오는 걸로
  //   // 해당 계약의 가입증명서는 바로 그냥 contractId를 통해서 가져올것.
  //   const res = await AxiosApi.getForm(productCode.productCode);
  //   setFormData(res.data);
  // };
  const AddForm = async () => {
    // 3 완전 form부터 만들어야 할때
    const res = await AxiosApi.addForm(
      Number(contract_id.contract_id),
      productCode?.productCode
    );
    setFormId(res.data.latestId);
  };

  useEffect(() => {
    if (formId) {
      AddField(formId);
    }
  }, [formId]);

  const findFormId = async () => {
    const res = await AxiosApi.findFormId(Number(contract_id.contract_id));
    console.log("+클릭", res.data);
    if (res.data.isExist === true) {
      AddField(res.data.formId);
    } else {
      AddForm();
    }
  };

  const AddField = async (formId: number) => {
    console.log("formId 감지  : ", formId);
    const res = await AxiosApi.addField(formId);
    console.log(res.data);
    if (res.data.isSuccess === true) {
      addEmptyAnswer(res.data.fieldId);
    }
  };
  const addEmptyAnswer = async (fieldId: number) => {
    console.log("필드 추가후 바로 id 반환 : ", fieldId);
    const res = await AxiosApi.addEmptyAnswer(fieldId);
    if (res.data === true) {
      getFormId(Number(contract_id.contract_id));
    }
    console.log(res.data);
  };
  useEffect(() => {
    if (contract_id?.contract_id) {
      getFormId(Number(contract_id.contract_id));
    }
  }, [contract_id]);

  const getFormId = async (contractId: number) => {
    const res = await AxiosApi.findFormId(contractId);
    if (res.data.isExist === true) {
      setListFormId(res.data.formId);
      readField(res.data.formId);
    }

    console.log("리스트 출력 시작 getFormId : ", res.data);
  };
  useEffect(() => {
    if (listFormId) {
      readField(listFormId);
    }
  }, [listFormId]);
  const readField = async (listFormId: number) => {
    const res = await AxiosApi.readField(listFormId);
    setFieldsList(res.data);
    console.log("fieldList 응답", res.data);
  };
  const deleteField = async (fieldId: number) => {
    const res = await AxiosApi.deleteField(fieldId);
    console.log(res.data);
    if (res.data === true) {
      getFormId(Number(contract_id.contract_id));
    }
  };
  return (
    <div>
      <div>
        <Button onClick={() => findFormId()}>
          필드 추가
          <AddIcon />
        </Button>
        <div>
          {fieldsList ? (
            fieldsList.map((field, i) => (
              <div key={i}>
                <p>{i + 1}</p>
                <Input value={field.label} placeholder="양식 추가"></Input> :
                {field.fieldAnswersResponseDTO ? (
                  <Input
                    value={field.fieldAnswersResponseDTO.answers}
                    placeholder="양식에 대한 응답"
                  />
                ) : (
                  <Input value="" placeholder="응답 없음" />
                )}
                <button>
                  <SaveRoundedIcon />
                </button>
                <button onClick={() => deleteField(field.fieldId)}>
                  {" "}
                  <DeleteForeverRoundedIcon />
                </button>
              </div>
            ))
          ) : (
            <>
              <p>필드가 존재하지 않습니다.</p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

// 응답을 저장할때는 해당 계약과 연결 되어야 하고
// form 은 상품 코드를 따라서 가져올수 있어야 한다/

export default EditForm;
