from flask import Blueprint, request, jsonify
from services.pdf_service import extract_text_from_pdf, summarize_with_gemini

pdf_bp = Blueprint("pdf", __name__, url_prefix="/pdf")

@pdf_bp.route("/summarize", methods=["POST"])
def summarize_pdf():
    file = request.files.get("file")
    if not file:
        return jsonify({"error": "No file uploaded"}), 400

    file_path = f"./uploads/{file.filename}"
    file.save(file_path)

    text = extract_text_from_pdf(file_path)
    summary = summarize_with_gemini(text)

    return jsonify({"summary": summary})
