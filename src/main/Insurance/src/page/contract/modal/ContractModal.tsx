import { useEffect, useState } from "react";
import AxiosApi from "../../../api/AxiosApi";
import { CancelButton2 } from "../../../layout/styled/Button";
import { ModalStyle } from "../../../layout/styled/ModalStyle";
import DownloadButton from "./DownloadButton";
// interface Insurer {
//   name: string;
//   ceo: string;
//   address: string;
//   phone: string;
//   fax: string;
// }
const ContractModal = (props: {
  close: () => void;
  open: boolean;
  id: number;
  type: boolean;
}) => {
  const { close, open, id } = props;
  const [data, setData] = useState<any>(null);

  useEffect(() => {
    const searchDataById = async () => {
      try {
        const res = await AxiosApi.getDataById(id);
        setData(res.data.data);
        console.log(res.data);
      } catch (e) {
        console.error("데이터 로드 실패", e);
      }
    };
    if (open) {
      searchDataById();
    }
  }, [id, open]);

  return (
    <ModalStyle>
      <div
        className={open ? "modal fade show d-block" : "modal fade"}
        tabIndex={-1}
        aria-labelledby="contractModalLabel"
        aria-hidden={!open}
      >
        {open && data && (
          <>
            <DownloadButton />
            <div
              id="pdf-content"
              style={{
                padding: "40px",
                backgroundColor: "#fffdf7",
                borderRadius: "12px",
                boxShadow: "0 0 12px rgba(0, 0, 0, 0.1)",
                fontFamily: "'Noto Sans KR', sans-serif",
                fontSize: "10px",
                color: "#333",
                maxWidth: "800px",
                margin: "0 auto",
                maxHeight: "900px",
                overflowY: "auto", // 넘치면 세로 스크롤 생김
              }}
            >
              <h1
                style={{
                  color: "#e67627",
                  textAlign: "center",
                  marginBottom: "30px",
                }}
              >
                보험 가입 증명서
              </h1>

              <section>
                <p>
                  <strong>증권번호:</strong> {data.product_code || "정보 없음"}
                </p>
                <p>
                  <strong>보험기간:</strong> {data.start_date || "?"} ~{" "}
                  {data.end_date || "?"}
                </p>
                <p>
                  <strong>보험종목:</strong> {data.product_name || "정보 없음"}
                </p>
              </section>

              <section>
                <h2
                  style={{
                    color: "#e67627",
                    borderBottom: "1px solid #eee",
                    marginTop: 30,
                  }}
                >
                  피보험자
                </h2>
                {data.co}
                {data.insuredParties?.length > 0 ? (
                  data.insuredParties.map((party: any, i: number) => (
                    <p key={i}>
                      {party.name || "이름 없음"} (
                      {party.businessId || "사업자 번호 없음"})
                    </p>
                  ))
                ) : (
                  <p>정보 없음</p>
                )}
              </section>

              <section>
                <p>
                  <strong>주소:</strong> {data.address || "정보 없음"}
                </p>
                <p>
                  <strong>보장내용:</strong>{" "}
                  {data.coverageDetails || "정보 없음"}
                </p>
              </section>

              <section>
                <h2
                  style={{
                    color: "#e67627",
                    borderBottom: "1px solid #eee",
                    marginTop: 30,
                  }}
                >
                  보상한도액
                </h2>
                <p>대인: {data.compensationLimit?.perPerson || "정보 없음"}</p>
                <p>
                  대물: {data.compensationLimit?.propertyDamage || "정보 없음"}
                </p>
                <p>
                  (총 보상한도액:{" "}
                  {data.compensationLimit?.totalCompensationLimit ||
                    "정보 없음"}
                  )
                </p>
              </section>

              <section>
                <p>
                  <strong>가입약관:</strong>{" "}
                  {data.subscriptionTerms || "정보 없음"}
                </p>
                <p>
                  <strong>발행일:</strong> {data.issueDate || "정보 없음"}
                </p>
              </section>

              <section>
                <h2
                  style={{
                    color: "#e67627",
                    borderBottom: "1px solid #eee",
                    marginTop: 30,
                  }}
                >
                  보험사 정보
                </h2>
                <p>HYUNDAI MARINE & FIRE INSURANCE CO., LTD.</p>
                <p>대표: 김정남</p>
                <p>163, Sejong-daero, Jongno-gu, Seoul, Korea 03183</p>
                <p>T. 82-1588-5656 / F. 82-2-732-4806</p>
              </section>

              <section>
                <h3 style={{ color: "#e67627", marginTop: 30 }}>참고사항</h3>
                <ul>
                  {data.importantNotes?.length > 0 ? (
                    data.importantNotes.map((note: string, i: number) => (
                      <li key={i}>{note}</li>
                    ))
                  ) : (
                    <li>없음</li>
                  )}
                </ul>
              </section>
            </div>
            <CancelButton2
              type="button"
              className="btn btn-secondary"
              onClick={close}
              style={{ display: "block", margin: "20px auto 0" }}
            >
              닫기
            </CancelButton2>
          </>
        )}
      </div>
    </ModalStyle>
  );
};

export default ContractModal;
