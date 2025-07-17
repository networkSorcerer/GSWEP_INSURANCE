import { useState } from "react";
import Certificate from "./write/Certificate";
import Gemini from "./SearchGemini/Gemini";

const CertificateMain = () => {
  const [activeTab, setActiveTab] = useState<string>("");

  const handleTabClick = (tab: string) => {
    setActiveTab(tab);
  };

  return (
    <>
      <div
        className="content"
        style={{
          padding: "20px",
          maxWidth: "1200px",
          margin: "0 auto",
        }}
      >
        <h1>가입 증명서 생성</h1>
        <div
          style={{
            display: "flex",
            flexWrap: "wrap",
            justifyContent: "space-between",
            marginBottom: "20px",
          }}
        >
          {["write", "pdf"].map((tab) => (
            <div
              key={tab}
              style={{
                width: "48%",
                marginBottom: "20px",
                padding: "15px",
                backgroundColor: "#fff",
                border: activeTab === tab ? "2px solid #000" : "1px solid #ddd",
                borderRadius: "10px",
                boxShadow: "0 4px 6px rgba(0,0,0,0.1)",
                cursor: "pointer",
                transition: "all 0.3s",
              }}
              onClick={() => handleTabClick(tab)}
            >
              <h4
                style={{
                  textAlign: "center",
                  fontSize: "18px",
                  fontWeight: activeTab === tab ? "bold" : "normal",
                  color: activeTab === tab ? "#000" : "#555",
                }}
              >
                {tab === "write" && "수기 작성"}
                {tab === "pdf" && "AI 증권 분석"}
              </h4>
            </div>
          ))}
        </div>

        {/* 조건부 렌더링은 여기! */}
        {activeTab === "write" && <Certificate />}
        {activeTab === "pdf" && <Gemini />}
      </div>
    </>
  );
};

export default CertificateMain;
