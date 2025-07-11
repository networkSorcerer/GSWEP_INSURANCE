import axios from "axios";
import type { getContractResponse } from "../page/model/contract";
import AxiosInstance from "./AxiosInstance";

const GSWEP_DOMAIN = "http://localhost:8111";

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
    console.log("api searchKey", searchKeyword);
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
};
export default AxiosApi;
