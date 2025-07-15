from flask import Blueprint, request, jsonify
import os
from services.pdf_service import extract_text_from_pdf, summarize_with_gemini
import uuid 
pdf_bp = Blueprint("pdf", __name__, url_prefix="/pdf")

UPLOAD_FOLDER = "uploads"
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
@pdf_bp.route("/summarize", methods=["POST"])
def summarize_pdf():
    if "file" not in request.files:
        return jsonify({"error": "No file uploaded"}), 400

    file = request.files["file"]
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400

    unique_filename = f"{uuid.uuid4()}-{file.filename}" # 파일 이름 고유화
    file_path = os.path.join(UPLOAD_FOLDER, unique_filename)

    try:
        file.save(file_path)
        text = extract_text_from_pdf(file_path)
        if text is None: # extract_text_from_pdf에서 오류 발생 시 None 반환
            return jsonify({"error": "PDF 텍스트 추출에 실패했습니다."}), 500

        summary = summarize_with_gemini(text)
        if "오류:" in summary: # summarize_with_gemini에서 오류 발생 시 특정 문자열 반환 가정
            return jsonify({"error": summary}), 500 # Gemini 오류 메시지 반환

        return jsonify({"summary": summary})

    except Exception as e:
        # 일반적인 예외 처리 (로그 기록 등)
        return jsonify({"error": f"서버 내부 오류: {str(e)}"}), 500
    finally:
        # 임시 파일이 항상 삭제되도록 보장
        if os.path.exists(file_path):
            os.remove(file_path)
