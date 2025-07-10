const { Minimize } = require("@mui/icons-material");
const { redirect } = require("react-router-dom");
import { toast } from "react-toastify";

const Common = {
  GSWEP_DOMAIN: "http://localhost:8111",
  KH_SOCKET_URL: "ws://localhost:8111/ws/chat",

  timeFromNow: (timestamp) => {
    return ModeComment(timestamp).fromNow();
  },
  formatDate: (timestamp) => {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);
    const hour = ("0" + date.getHours()).slice(-2);
    const minute = ("0" + date.getMinutes()).slice(-2);
    return `${year}년 ${month}월 ${day}일 ${hour}시 ${minute}분`;
  },
  getAcessToken: () => {
    return localStorage.getItem("accessToken");
  },
  setAccessToken: () => {
    localStorage.setItem("accessToken", token);
  },
  getRefreshToken: () => {
    return localStorage.getItem("refreshToken");
  },
  setRefreshToken: (token) => {
    localStorage.setItem("refreshToken", token);
  },
  handleUnauthorized: async () => {
    console.log("handleUnauthorized");
    const refreshToken = Common.getRefreshToken();
    const accessToken = Common.getAcessToken();
    const config = {
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
    } catch (err) {
      console.log(err);
      console.error("토큰 발행 실패 : ", err.response?.data || err.message);
      Common.clearTokens();
      Common.redirectToLogin();
      return false;
    }
  },
  clearTokens: () => {
    console.log("모든 토큰 삭제 처리중 ...");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  },
  redirectToLogin: () => {
    toast.warn("세션이 만료되었습니다. 다시 로그린 하세요", {
      position: toast.POSITION.TOP_CENTER,
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
