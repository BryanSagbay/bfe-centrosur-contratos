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
            if key != 'templates':
                data[key] = request.form[key]
        
        selected_templates = request.form.getlist('templates')
        if not selected_templates:
            return "Error: No se seleccionaron plantillas"
        
        # Lógica para llenar las plantillas seleccionadas
        template_loader = TemplateLoader()
        generated_files = []
        
        for template_path in selected_templates:
            template = template_loader.load_template(template_path)
            filled_template = template_loader.fill_template(template, data)
            
            # Guardar el documento lleno
            output_base_dir = os.path.join(os.path.dirname(__file__), '..', '..', 'output')
            if template_path.startswith('excel/'):
                output_dir = os.path.join(output_base_dir, 'excel')
                output_filename = f'filled_{os.path.splitext(os.path.basename(template_path))[0]}.xlsx'
            elif template_path.startswith('word/'):
                output_dir = os.path.join(output_base_dir, 'word')
                output_filename = f'filled_{os.path.basename(template_path)}'
            os.makedirs(output_dir, exist_ok=True)
            output_path = os.path.join(output_dir, output_filename)
            template_loader.save_filled_template(filled_template, output_path)
            generated_files.append(output_path)
        
        return f"Documentos generados exitosamente: {', '.join(generated_files)}"
    except Exception as e:
        return f"Error al generar documentos: {str(e)}"