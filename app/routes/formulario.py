from flask import Blueprint, request
from utils.templates_manager.template_loader import TemplateLoader
import os

formulario_bp = Blueprint('formulario', __name__)

# Lógica para manejar el formulario
@formulario_bp.route('/submit_form', methods=['POST'])
def submit_form():
    try:
        # Obtener todos los datos del formulario
        data = {}
        for key in request.form:
            data[key] = request.form[key]
        
        template_choice = data.get('template_choice')
        if not template_choice:
            return "Error: No se seleccionó plantilla"
        
        # Lógica para llenar la plantilla seleccionada
        template_loader = TemplateLoader()
        template = template_loader.load_template(template_choice)
        filled_template = template_loader.fill_template(template, data)
        
        # Guardar el documento lleno
        output_dir = 'output'
        if template_choice.endswith('.xlsx'):
            output_dir = os.path.join(output_dir, 'excel')
        elif template_choice.endswith('.docx'):
            output_dir = os.path.join(output_dir, 'word')
        os.makedirs(output_dir, exist_ok=True)
        output_path = os.path.join(output_dir, f'filled_{template_choice}')
        template_loader.save_filled_template(filled_template, output_path)
        
        return f"Documento generado exitosamente: {output_path}"
    except Exception as e:
        return f"Error al generar documento: {str(e)}"