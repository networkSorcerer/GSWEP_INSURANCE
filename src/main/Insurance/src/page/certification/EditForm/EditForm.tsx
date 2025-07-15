import { useEffect, useState } from "react";
import AxiosApi from "../../../api/AxiosApi";
import { useParams } from "react-router-dom";
import { Button, Input, ThemeProvider } from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import SaveRoundedIcon from "@mui/icons-material/SaveRounded";
import DeleteForeverRoundedIcon from "@mui/icons-material/DeleteForeverRounded";
import type { fieldType } from "../../model/field";
import theme from "../../../layout/styled/theme";
import { OrangeButton } from "../../../layout/styled/Button";

interface productCode {
  productCode: string;
}

const EditForm = () => {
  const contract_id = useParams<{ contract_id: string }>();
  const [productCode, setProductCode] = useState<productCode>();
  const [formId, setFormId] = useState<number>();
  const [listFormId, setListFormId] = useState<number>();
  const [fieldsList, setFieldsList] = useState<fieldType[]>();

  // 🔧 필드/응답값 상태를 ID별로 개별 관리
  const [fieldValues, setFieldValues] = useState<Record<number, string>>({});
  const [answerValues, setAnswerValues] = useState<Record<number, string>>({});

  useEffect(() => {
    if (contract_id?.contract_id) {
      GetProductCode(Number(contract_id.contract_id));
      getFormId(Number(contract_id.contract_id));
    }
  }, [contract_id]);

  const GetProductCode = async (contractId: number) => {
    const res = await AxiosApi.getProductCode(contractId);
    setProductCode(res.data);
  };

  const AddForm = async () => {
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
    if (res.data.isExist === true) {
      AddField(res.data.formId);
    } else {
      AddForm();
    }
  };

  const AddField = async (formId: number) => {
    const res = await AxiosApi.addField(formId);
    if (res.data.isSuccess === true) {
      addEmptyAnswer(res.data.fieldId);
    }
  };

  const addEmptyAnswer = async (fieldId: number) => {
    const res = await AxiosApi.addEmptyAnswer(fieldId);
    if (res.data === true) {
      getFormId(Number(contract_id.contract_id));
    }
  };

  const getFormId = async (contractId: number) => {
    const res = await AxiosApi.findFormId(contractId);
    if (res.data.isExist === true) {
      setListFormId(res.data.formId);
      readField(res.data.formId);
    }
  };

  useEffect(() => {
    if (listFormId) {
      readField(listFormId);
    }
  }, [listFormId]);

  const readField = async (listFormId: number) => {
    const res = await AxiosApi.readField(listFormId);
    setFieldsList(res.data);

    // 💡 초기값 설정 (fieldValues, answerValues)
    const newFieldValues: Record<number, string> = {};
    const newAnswerValues: Record<number, string> = {};

    res.data.forEach((field: fieldType) => {
      newFieldValues[field.fieldId] = field.label;
      if (field.fieldAnswersResponseDTO) {
        newAnswerValues[field.fieldAnswersResponseDTO.id] =
          field.fieldAnswersResponseDTO.answers;
      }
    });

    setFieldValues(newFieldValues);
    setAnswerValues(newAnswerValues);
  };

  const deleteField = async (fieldId: number) => {
    const res = await AxiosApi.deleteField(fieldId);
    if (res.data === true) {
      getFormId(Number(contract_id.contract_id));
    }
  };

  const saveField = async (fieldId: number) => {
    const value = fieldValues[fieldId];
    if (value !== undefined) {
      const res = await AxiosApi.updateField(value, fieldId);
      console.log("필드 저장:", res.data);
    }
  };

  const saveAnswers = async (fieldsId: number, answerId: number) => {
    const value = answerValues[answerId];
    if (value !== undefined) {
      const res = await AxiosApi.updateAnswer(value, fieldsId, answerId);
      console.log("답변 저장:", res.data);
    }
  };

  return (
    <div>
      <OrangeButton onClick={findFormId} style={{ marginBottom: "30px" }}>
        필드 추가
        <AddIcon />
      </OrangeButton>

      <div>
        {fieldsList && fieldsList.length > 0 ? (
          fieldsList.map((field, i) => (
            <div key={field.fieldId} style={{ marginBottom: "16px" }}>
              <p>{i + 1}번 필드</p>
              <p>필드명</p>
              <Input
                value={fieldValues[field.fieldId] ?? ""}
                placeholder="필드명 입력"
                onChange={(e) =>
                  setFieldValues((prev) => ({
                    ...prev,
                    [field.fieldId]: e.target.value,
                  }))
                }
              />{" "}
              :
              {field.fieldAnswersResponseDTO ? (
                <>
                  <Input
                    value={answerValues[field.fieldAnswersResponseDTO.id] ?? ""}
                    placeholder="응답 입력"
                    onChange={(e) =>
                      setAnswerValues((prev) => ({
                        ...prev,
                        [field.fieldAnswersResponseDTO.id]: e.target.value,
                      }))
                    }
                  />
                </>
              ) : (
                <Input value="" placeholder="응답 없음" disabled />
              )}
              <>
                <ThemeProvider theme={theme}>
                  <Button
                    onClick={() => {
                      saveField(field.fieldId);
                      if (field.fieldAnswersResponseDTO) {
                        saveAnswers(
                          field.fieldAnswersResponseDTO.fieldsId,
                          field.fieldAnswersResponseDTO.id
                        );
                      }
                    }}
                  >
                    <SaveRoundedIcon />
                  </Button>

                  <Button onClick={() => deleteField(field.fieldId)}>
                    <DeleteForeverRoundedIcon />
                  </Button>
                </ThemeProvider>
              </>
            </div>
          ))
        ) : (
          <p>필드가 존재하지 않습니다.</p>
        )}
      </div>
    </div>
  );
};

export default EditForm;
