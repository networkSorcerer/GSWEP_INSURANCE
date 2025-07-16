import headerImage from "../../../images/가입증명서헤더.png";
import footerImage from "../../../images/가입증명서바텀.png";
import { ModalStyle } from "../../../layout/styled/ModalStyle";
import { CancelButton2 } from "../../../layout/styled/Button";
import DownloadButton from "../../contract/modal/DownloadButton";

const CertiModal = (props: {
  close: () => void;
  open: boolean;
  id: string;
  type: boolean;
}) => {
  const { close, open, id } = props;
  return (
    <div>
      {" "}
      <ModalStyle>
        <div
          className={open ? "modal fade show d-block" : "modal fade"}
          tabIndex={-1}
          aria-labelledby="contractModalLabel"
          aria-hidden={!open}
        >
          {open && id && (
            <>
              <DownloadButton />
              <>
                <div
                  id="pdf-content"
                  style={{
                    padding: "40px",
                    backgroundColor: "#fffdf7",
                    borderRadius: "12px",
                    boxShadow: "0 0 12px rgba(0, 0, 0, 0.1)",
                    fontFamily: "'Noto Sans KR', sans-serif",
                    fontSize: "15px",
                    color: "#333",
                    maxWidth: "800px",
                    margin: "0 auto",
                    maxHeight: "900px",
                    overflowY: "auto", // 넘치면 세로 스크롤 생김
                  }}
                >
                  <div>
                    <img
                      src={headerImage}
                      alt="가입증명서 헤더 이미지"
                      width="700px"
                    />
                  </div>
                  <div
                    style={{
                      border: "2px solid orange",
                      borderRadius: "10px",
                      padding: "24px",
                      margin: "24px auto",
                      maxWidth: "800px",
                      backgroundColor: "#fffbe6", // 연한 배경 (선택)
                      boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                      fontFamily: "'Noto Sans KR', sans-serif",
                      lineHeight: 1.6,
                    }}
                  >
                    <section>
                      <h2
                        style={{
                          borderBottom: "1px solid #ccc",
                          paddingBottom: "6px",
                          fontSize: "20px",
                          fontWeight: "normal",
                          color: "#555",
                          marginBottom: "24px",
                        }}
                      >
                        INSURANCE CERTIFICATE
                      </h2>
                      <div
                        id="summary"
                        style={{
                          marginTop: "24px",
                          whiteSpace: "pre-wrap",
                          background: "#fffbe6",
                          padding: "16px",
                          borderRadius: "8px",
                          border: "1px solid #ddd",
                        }}
                      >
                        {id}
                      </div>
                    </section>
                    <section
                      style={{
                        marginTop: "40px",
                        textAlign: "center",
                        color: "#444",
                      }}
                    >
                      <p>HYUNDAI MARINE & FIRE INSURANCE CO., LTD.</p>
                      <p>대표: 정몽윤</p>
                      <p>163, Sejong-daero, Jongno-gu, Seoul, Korea 03183</p>
                      <p>T. 82-1588-5656 / F. 82-2-732-4806</p>
                      <div style={{ marginTop: "20px" }}>
                        <img
                          src={footerImage}
                          alt="가입증명서 푸터 이미지"
                          width="250px"
                          height="100px"
                          style={{ opacity: 0.8 }}
                        />
                      </div>
                    </section>
                  </div>
                </div>
                <div
                  className="print-hidden"
                  style={{ textAlign: "center", marginTop: 24 }}
                >
                  <CancelButton2 onClick={close}>닫기</CancelButton2>
                </div>
              </>
            </>
          )}
        </div>
      </ModalStyle>
    </div>
  );
};

export default CertiModal;
