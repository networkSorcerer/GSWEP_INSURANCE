from flask import Flask, render_template
from routes.pdf_routes import pdf_bp
from flask_cors import CORS # CORS 임포트

app = Flask(__name__)
CORS(app) 
app.register_blueprint(pdf_bp)

@app.route("/")
def index():
    return render_template("upload.html")  # templates/upload.html 불러오기

if __name__ == "__main__":
    app.run(debug=True)
