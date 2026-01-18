from flask import Blueprint, request
from utils.templates_manager.template_loader import TemplateLoader
import os

formulario_bp = Blueprint('formulario', __name__)

# Lógica para manejar el formulario
@formulario_bp.route('/submit_form', methods=['POST'])
def submit_form():
    # Obtener datos del formulario
    name = request.form['name']
    cedula = request.form['cedula']
    template_choice = request.form['template_choice']
    
    # Lógica para llenar la plantilla seleccionada
    template_loader = TemplateLoader()
    template = template_loader.load_template(template_choice)
    filled_template = template_loader.fill_template(template, {'name': name, 'cedula': cedula})
    
    # Guardar el documento lleno
    output_dir = 'output'
    if template_choice.endswith('.xlsx'):
        output_dir = os.path.join(output_dir, 'excel')
    elif template_choice.endswith('.docx'):
        output_dir = os.path.join(output_dir, 'word')
    os.makedirs(output_dir, exist_ok=True)
    output_path = os.path.join(output_dir, f'filled_{template_choice}')
    template_loader.save_filled_template(filled_template, output_path)
    
    return f"Formulario procesado con éxito! Documento guardado en {output_path}"