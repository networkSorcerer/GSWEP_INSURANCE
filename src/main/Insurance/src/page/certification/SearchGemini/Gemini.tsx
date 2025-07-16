import React, { useState, type FormEvent } from "react";
import { OrangeButton } from "../../../layout/styled/Button";
import { ButtonWrapper } from "../../../layout/applayout/Contrainer";
import CertiModal from "../modal/CertiModal";
const Gemini: React.FC = () => {
  const [summary, setSummary] = useState<string>("");
  const [file, setFile] = useState<File | null>(null);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!file) {
      setSummary("PDF 파일을 선택해주세요.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch("http://localhost:5000/pdf/summarize", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) throw new Error("요약 실패");

      const result = await response.json();
      setSummary(result.summary || "요약 결과 없음");
    } catch (error) {
      setSummary("요약 실패: " + (error as Error).message);
    }
  };
  const [certiModal, setCertiModal] = useState(false);

  const CertiModalState = () => {
    setCertiModal(true);
  };
  const closeModal = () => {
    setCertiModal(false);
  };
  return (
    <>
      <div
        style={{
          fontFamily: "sans-serif",
          padding: "40px",
          backgroundColor: "#f9f9f9",
          minHeight: "100vh",
        }}
      >
        <h1 style={{ color: "#ff7043" }}>PDF 분석</h1>
        <form
          onSubmit={handleSubmit}
          style={{
            background: "white",
            padding: "20px",
            borderRadius: "8px",
            boxShadow: "0 0 12px rgba(0, 0, 0, 0.1)",
          }}
        >
          <input
            type="file"
            accept="application/pdf"
            required
            onChange={(e) => setFile(e.target.files?.[0] || null)}
            style={{ marginBottom: "16px" }}
          />
          <br />
          <OrangeButton type="submit">AI 분석 시작</OrangeButton>
        </form>
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
          {summary}
        </div>
        <ButtonWrapper>
          <OrangeButton onClick={() => CertiModalState()}>
            가입 증명서 확인
          </OrangeButton>
        </ButtonWrapper>
      </div>
      <CertiModal
        open={certiModal}
        close={closeModal}
        type={true}
        id={summary}
      />
    </>
  );
};

export default Gemini;
