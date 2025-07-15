import fitz  # PyMuPDF
import google.generativeai as genai
import os # Import the os module to access environment variables

# --- 1. API 키 설정 (가장 상단에 위치) ---
# 여기에 발급받은 실제 Gemini API 키를 직접 입력하세요.
# 이 방법은 개발 단계에서 편리하지만, 보안상 민감한 정보가 코드에 직접 노출됩니다.
# 서비스 배포 시에는 os.environ.get("GOOGLE_API_KEY")와 같은 환경 변수 사용을 강력히 권장합니다.
genai.configure(api_key="AIzaSyBYLxt1DGoY2RF7v0OxOVz7U66Vn7AQFJw")

# --- 2. PDF 텍스트 추출 함수 ---
def extract_text_from_pdf(pdf_path):
    """
    PDF 파일에서 텍스트를 추출합니다.
    """
    try:
        doc = fitz.open(pdf_path)
        text = ""
        for page in doc:
            text += page.get_text()
        doc.close()
        return text
    except Exception as e:
        print(f"PDF 텍스트 추출 중 오류 발생: {e}")
        return None # 오류 발생 시 None 반환하여 상위 함수에서 처리하도록 함

# --- 3. Gemini 요약 함수 ---
def summarize_with_gemini(text: str):
    """
    Gemini 모델을 사용하여 텍스트를 요약합니다.
    """
    if not text:
        return "요약할 텍스트가 없습니다."

    # !!! 여기서 모델 이름을 확인된 정확한 이름으로 변경해야 합니다. !!!
    # 예를 들어, list_available_models() 실행 결과 'models/gemini-pro'가 나오면 그대로,
    # 다른 이름이 나오면 그 이름으로 변경하세요.
    try:
        model = genai.GenerativeModel("models/gemini-1.5-pro") # <--- 이 부분 확인 및 수정 필요!
        # model = genai.GenerativeModel("models/gemini-pro") # 이렇게 전체 경로를 시도해볼 수도 있습니다.

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
    except Exception as e:
        # API 호출 중 발생한 오류를 더 명확하게 반환
        return f"요약에 실패했습니다. 오류: {e}"

# --- 4. 사용 가능한 모델 목록 출력 함수 (테스트/디버깅용) ---
def list_available_models():
    """
    현재 API 키로 접근 가능한 Gemini 모델 목록을 출력합니다.
    """
    print("\n--- 사용 가능한 Gemini 모델 목록 ---")
    try:
        # genai.list_models()는 이터레이터를 반환합니다.
        models = list(genai.list_models()) # 모든 모델을 리스트로 변환
        found_content_models = False
        for m in models:
            if "generateContent" in m.supported_generation_methods:
                print(f"- 모델 이름: {m.name}, 지원 메서드: {m.supported_generation_methods}")
                found_content_models = True
        if not found_content_models:
            print("generateContent를 지원하는 모델을 찾을 수 없습니다.")
    except Exception as e:
        print(f"모델 목록을 가져오는 중 오류 발생: {e}")
    print("----------------------------------\n")


# --- 5. 스크립트 직접 실행 시 동작하는 부분 (하나만 유지) ---
if __name__ == "__main__":
    # 1. 사용 가능한 모델 목록을 먼저 확인합니다.
    # 이 결과를 보고 summarize_with_gemini 함수 내의 모델 이름을 수정해야 합니다.
    list_available_models()

    # 2. PDF 요약 예제 코드 (테스트용)
    pdf_file = "../uploads/temp.pdf" # 실제 PDF 파일 경로로 변경하세요.
    print(f"'{pdf_file}' 에서 텍스트를 추출 중...")
    try:
        extracted_text = extract_text_from_pdf(pdf_file)

        if extracted_text:
            print("\n텍스트 추출 완료. Gemini로 요약 시작...")
            summary = summarize_with_gemini(extracted_text)
            print("\n--- 요약 결과 ---")
            print(summary)
        else:
            print("텍스트 추출에 실패하여 요약을 진행할 수 없습니다.")
    except FileNotFoundError:
        print(f"오류: 파일 '{pdf_file}'을(를) 찾을 수 없습니다. PDF 파일이 존재하는지 확인하세요.")
    except Exception as e:
        print(f"예상치 못한 오류 발생: {e}")