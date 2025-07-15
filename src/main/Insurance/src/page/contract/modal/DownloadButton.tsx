import html2pdf from "html2pdf.js";
import { CancelButton2 } from "../../../layout/styled/Button";

const DownloadButton = () => {
  const handleDownload = () => {
    const element = document.getElementById("pdf-content");
    if (!element) return;

    html2pdf()
      .set({
        filename: "가입증명서.pdf",
        html2canvas: { scale: 2 },
        jsPDF: { unit: "mm", format: "a4", orientation: "portrait" },
      })
      .from(element)
      .save();
  };

  return <CancelButton2 onClick={handleDownload}>PDF 다운로드</CancelButton2>;
};

export default DownloadButton;
