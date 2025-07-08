import axios from "axios";
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

  contractApi: async () => {
    const accessToken = localStorage.getItem("accessToken");
    console.log("accessToken", accessToken);
    return await axios.get(GSWEP_DOMAIN + "/contract/list", {
      headers: {
        Authorization: `${accessToken}`,
      },
    });
  },
};
export default AxiosApi;
