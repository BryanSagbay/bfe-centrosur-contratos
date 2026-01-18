from flask import Flask, render_template
from jinja2 import FileSystemLoader
import os

app = Flask(__name__)
app.config['TEMPLATE_FOLDER'] = os.path.join(os.path.dirname(__file__), 'view')
app.jinja_env.loader = FileSystemLoader(app.config['TEMPLATE_FOLDER'])

# Importar blueprints
from routes.formulario import formulario_bp
app.register_blueprint(formulario_bp)

@app.route('/')
def home():
    return render_template('formulario.html')

if __name__ == '__main__':
    app.run(debug=True)