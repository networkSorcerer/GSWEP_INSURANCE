import fitz  # PyMuPDF
import google.generativeai as genai

genai.configure(api_key="AIzaSyBYLxt1DGoY2RF7v0OxOVz7U66Vn7AQFJw")

def extract_text_from_pdf(pdf_path):
    doc = fitz.open(pdf_path)
    text = ""
    for page in doc:
        text += page.get_text()
    return text

def summarize_with_gemini(text: str):
    model = genai.GenerativeModel("gemini-pro")

    prompt = f"""
다음 문서에서 핵심 정보를 요약해 주세요:

- 보장 대상
- 보장 금액
- 보험 기간
- 보험자 이름

{text[:12000]}
"""
    response = model.generate_content(prompt)
    return response.text
