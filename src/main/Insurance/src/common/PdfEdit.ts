import { PDFDocument, rgb, StandardFonts } from "pdf-lib";

const uploadInput = document.getElementById("pdf-upload") as HTMLInputElement;
const editBtn = document.getElementById("edit-btn") as HTMLButtonElement;

editBtn.addEventListener("click", async () => {
  if (!uploadInput.files || uploadInput.files.length === 0) {
    alert("PDF 파일을 선택하세요.");
    return;
  }

  const file = uploadInput.files[0];
  const arrayBuffer = await file.arrayBuffer();

  const pdfDoc = await PDFDocument.load(arrayBuffer);
  const pages = pdfDoc.getPages();
  const firstPage = pages[0];
  const font = await pdfDoc.embedFont(StandardFonts.Helvetica);

  firstPage.drawText("타입스크립트에서 추가한 텍스트!", {
    x: 50,
    y: 700,
    size: 20,
    font,
    color: rgb(0, 0, 0),
  });

  const pdfBytes = await pdfDoc.save();
  const blob = new Blob([pdfBytes], { type: "application/pdf" });
  const url = URL.createObjectURL(blob);

  const a = document.createElement("a");
  a.href = url;
  a.download = "edited.pdf";
  a.click();
});
