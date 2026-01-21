from flask import Blueprint, request
from utils.templates_manager.template_loader import TemplateLoader
import os
from docx2pdf import convert
from datetime import datetime  # Cambiado: importar datetime directamente

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
        
        # Validar fecha si existe
        if 'fecha_entrega_documentos' in data and data['fecha_entrega_documentos']:
            datetime.strptime(  # Corregido: ya no necesita datetime.datetime
                data['fecha_entrega_documentos'], '%d-%m-%Y'
            )

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
            else:
                # Manejar caso por defecto
                output_dir = output_base_dir
                output_filename = f'filled_{os.path.basename(template_path)}'
                
            os.makedirs(output_dir, exist_ok=True)
            output_path = os.path.join(output_dir, output_filename)
            template_loader.save_filled_template(filled_template, output_path)
            
            # Convertir DOCX a PDF
            if output_path.endswith('.docx'):
                pdf_path = output_path.replace('.docx', '.pdf')
                convert(output_path, pdf_path)
                generated_files.append(pdf_path)
            else:
                generated_files.append(output_path)
        
        # Crear respuesta HTML con enlaces a los archivos generados
        response_html = "<h2>Documentos generados exitosamente:</h2><ul>"
        for file_path in generated_files:
            file_name = os.path.basename(file_path)
            # Asumiendo que el servidor sirve archivos desde /output
            file_url = f"/output/{os.path.relpath(file_path, os.path.join(os.path.dirname(__file__), '..', '..', 'output'))}"
            response_html += f'<li><a href="{file_url}" target="_blank">{file_name}</a></li>'
        response_html += "</ul>"
        return response_html
    except Exception as e:
        return f"Error al generar documentos: {str(e)}"