import axios from "axios";
import type { getContractResponse } from "../page/model/contract";

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
    const accessToken = localStorage.getItem("accessToken");
    console.log("accessToken", accessToken);
    console.log("api searchKey", searchKeyword);
    return await axios.get(GSWEP_DOMAIN + "/contract/list", {
      headers: {
        Authorization: `${accessToken}`,
      },
      params: {
        select: searchKeyword.oname,
        keyword: searchKeyword.sname,
        page: page,
        size: size,
      },
    });
  },
  coursePage: async (page: number, size: number) => {
    const accessToken = localStorage.getItem("accessToken");

    const params = {
      page: page,
      size: size,
    };
    return await axios.get(GSWEP_DOMAIN + "/contract/count", {
      headers: {
        Authorization: `${accessToken}`,
      },
      params,
    });
  },
};
export default AxiosApi;
