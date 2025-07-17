import axios from "axios";
import type { getContractResponse } from "../page/model/contract";
import AxiosInstance from "./AxiosInstance";

const GSWEP_DOMAIN = "";

const AxiosApi = {
  loginApi: async (email: string, password: string) => {
    const response = await axios.post(GSWEP_DOMAIN + "/auth/login", {
      email,
      password,
    });

    // 응답 헤더에서 토큰 꺼내기
    const token =
      response.headers["authorization"] || response.headers["Authorization"];

    const success = !!token && token !== "";
    return { token, success };
  },

  contractApi: async (
    searchKeyword: any,
    page: number,
    size: number
  ): Promise<getContractResponse> => {
    return await AxiosInstance.get(GSWEP_DOMAIN + "/contract/list", {
      params: {
        select: searchKeyword.oname,
        keyword: searchKeyword.sname,
        page: page,
        size: size,
      },
    });
  },
  coursePage: async (page: number, size: number) => {
    const params = {
      page: page,
      size: size,
    };
    return await AxiosInstance.get(GSWEP_DOMAIN + "/contract/count", {
      params,
    });
  },
  getDataById: async (id: number) => {
    const params = {
      id: id,
    };
    return await AxiosInstance.get(GSWEP_DOMAIN + "/contract/id", {
      params,
    });
  },
  getProductCode: async (id: number) => {
    const params = {
      contract_id: id,
    };
    return await AxiosInstance.get(GSWEP_DOMAIN + "/form/find_product_code", {
      params,
    });
  },
  getForm: async (productCode: string) => {
    const params = {
      productCode: productCode,
    };
    return await AxiosInstance.get(GSWEP_DOMAIN + "/form/read_form", {
      params,
    });
  },
  addForm: async (contractId: number, productCode?: string) => {
    return await AxiosInstance.post(GSWEP_DOMAIN + "/form/add_form", {
      contractId: contractId,
      productCode: productCode,
    });
  },
  addField: async (formId: number) => {
    return await AxiosInstance.post(GSWEP_DOMAIN + "/form_fields/add_fields", {
      formId: formId,
    });
  },
  findFormId: async (contractId: number) => {
    const params = {
      contractId: contractId,
    };
    return await AxiosInstance.get(
      GSWEP_DOMAIN + "/form/find_formId_by_ProductCode",
      { params }
    );
  },
  readField: async (formId: number) => {
    const params = {
      formId: formId,
    };
    return await AxiosInstance.get(GSWEP_DOMAIN + "/form_fields/get_fields", {
      params,
    });
  },
  addEmptyAnswer: async (fieldsId: number) => {
    return await AxiosInstance.post(GSWEP_DOMAIN + "/answers/addAnswers", {
      fieldsId,
    });
  },
  deleteField: async (fieldId: number) => {
    console.log("deleteField -> fieldId", fieldId);
    const params = {
      fieldsId: fieldId,
    };
    return await AxiosInstance.post(
      GSWEP_DOMAIN + "/form_fields/delete",
      null,
      {
        params,
      }
    );
  },
  updateField: async (label: any, fieldsId: any) => {
    console.log("saveField -> lable && fieldsId", label, fieldsId);
    return await AxiosInstance.post(
      GSWEP_DOMAIN + "/form_fields/update_fields",
      { label, fieldsId }
    );
  },
  updateAnswer: async (answers: any, fieldsId: any, fieldAnswersId: any) => {
    return await AxiosInstance.post(GSWEP_DOMAIN + "/answers/update_answers", {
      answers,
      fieldsId,
      fieldAnswersId,
    });
  },
};
export default AxiosApi;
