from flask import Flask
from routes.pdf_routes import pdf_bp

app = Flask(__name__)
app.register_blueprint(pdf_bp)
@app.route("/")
def index():
    return "✅ Flask 서버가 정상적으로 작동 중입니다!"

if __name__ == "__main__":
    app.run(debug=True)
