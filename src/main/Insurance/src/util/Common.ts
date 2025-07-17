import axios, { type AxiosRequestConfig } from "axios";
import { toast, type ToastPosition } from "react-toastify";
import moment from "moment";
import "react-toastify/dist/ReactToastify.css";

moment.locale("ko"); // 한글 설정 적용

const Common = {
  GSWEP_DOMAIN: "",
  KH_SOCKET_URL: "ws://localhost:8111/ws/chat",

  timeFromNow: (timestamp: Date | string | number): string => {
    return moment(timestamp).fromNow();
  },

  formatDate: (timestamp: string | number | Date): string => {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);
    const hour = ("0" + date.getHours()).slice(-2);
    const minute = ("0" + date.getMinutes()).slice(-2);
    return `${year}년 ${month}월 ${day}일 ${hour}시 ${minute}분`;
  },

  getAccessToken: (): string | null => {
    return localStorage.getItem("accessToken");
  },

  setAccessToken: (token: string): void => {
    localStorage.setItem("accessToken", token);
  },

  getRefreshToken: (): string | null => {
    return localStorage.getItem("refreshToken");
  },

  setRefreshToken: (token: string): void => {
    localStorage.setItem("refreshToken", token);
  },

  handleUnauthorized: async (): Promise<boolean> => {
    console.log("handleUnauthorized");

    const refreshToken = Common.getRefreshToken();
    const accessToken = Common.getAccessToken();

    const config: AxiosRequestConfig = {
      headers: {
        Authorization: `${accessToken}`,
      },
    };

    try {
      const res = await axios.post(
        `${Common.GSWEP_DOMAIN}/auth/refresh`,
        refreshToken,
        config
      );
      const { accessToken: newAccessToken, refreshToken: newRefreshToken } =
        res.data;

      Common.setAccessToken(newAccessToken);
      Common.setRefreshToken(newRefreshToken);
      return true;
    } catch (error: any) {
      console.log(error);
      console.error("토큰 발행 실패 : ", error.response?.data || error.message);
      Common.clearTokens();
      Common.redirectToLogin();
      return false;
    }
  },

  clearTokens: (): void => {
    console.log("모든 토큰 삭제 처리중 ...");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  },

  redirectToLogin: (): void => {
    toast.warn("세션이 만료되었습니다. 다시 로그인 하세요", {
      position: "top-center" as ToastPosition,
      autoClose: 3000,
      hideProgressBar: true,
      closeOnClick: true,
      pauseOnHover: false,
      draggable: true,
      theme: "colored",
    });

    setTimeout(() => {
      window.location.href = "/";
    }, 3500);
  },
};

export default Common;
