# Lógica para cargar y gestionar plantillas de Excel y Word

from openpyxl import load_workbook
from docx import Document
import os

class TemplateLoader:
    def __init__(self, template_dir=None):
        if template_dir is None:
            template_dir = os.path.join(os.path.dirname(__file__), '..', '..', '..', 'template')
        self.template_dir = template_dir

    def load_template(self, template_path):
        full_path = os.path.join(self.template_dir, template_path)
        if template_path.endswith('.xlsx'):
            return load_workbook(full_path)
        elif template_path.endswith('.docx'):
            return Document(full_path)
        else:
            raise ValueError("Unsupported template format")

    def fill_template(self, template, data):
        if isinstance(template, load_workbook):
            # Llenar Excel
            sheet = template.active
            for key, value in data.items():
                # Asumir que hay placeholders como {{name}}, {{cedula}}
                for row in sheet.iter_rows():
                    for cell in row:
                        if cell.value and isinstance(cell.value, str):
                            cell.value = cell.value.replace('{{' + key + '}}', str(value))
            return template
        elif isinstance(template, Document):
            # Llenar Word
            for paragraph in template.paragraphs:
                for key, value in data.items():
                    if '{{' + key + '}}' in paragraph.text:
                        paragraph.text = paragraph.text.replace('{{' + key + '}}', str(value))
            return template
        else:
            raise ValueError("Unsupported template type")

    def save_filled_template(self, filled_template, output_path):
        if isinstance(filled_template, load_workbook):
            filled_template.save(output_path)
        elif isinstance(filled_template, Document):
            filled_template.save(output_path)
