import React, { useRef } from "react";
import { PDFDocument, rgb, StandardFonts } from "pdf-lib";

const PdfEditor: React.FC = () => {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleEditPdf = async () => {
    const fileInput = fileInputRef.current;
    if (!fileInput || !fileInput.files || fileInput.files.length === 0) {
      alert("PDF 파일을 선택하세요.");
      return;
    }

    const file = fileInput.files[0];
    const arrayBuffer = await file.arrayBuffer();

    const pdfDoc = await PDFDocument.load(arrayBuffer);
    const pages = pdfDoc.getPages();
    const firstPage = pages[0];
    const font = await pdfDoc.embedFont(StandardFonts.Helvetica);

    // 텍스트 추가
    firstPage.drawText("React + TypeScript로 추가한 텍스트!", {
      x: 50,
      y: 700,
      size: 18,
      font,
      color: rgb(0, 0, 0),
    });

    const modifiedPdfBytes = await pdfDoc.save();
    const blob = new Blob([modifiedPdfBytes], { type: "application/pdf" });
    const url = URL.createObjectURL(blob);

    // 자동 다운로드
    const a = document.createElement("a");
    a.href = url;
    a.download = "edited.pdf";
    a.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div>
      <h2>PDF 편집기</h2>
      <input type="file" ref={fileInputRef} accept="application/pdf" />
      <button onClick={handleEditPdf}>텍스트 추가 후 다운로드</button>
    </div>
  );
};

export default PdfEditor;
