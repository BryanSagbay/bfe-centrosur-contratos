package org.example.utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para validación de formularios
 */
public class ValidationUtils {

    /**
     * Valida que un campo de texto no esté vacío
     */
    public static boolean isNotEmpty(JTextField field) {
        return field != null && !field.getText().trim().isEmpty();
    }

    /**
     * Válida múltiples campos obligatorios
     */
    public static ValidationResult validateRequiredFields(JTextField... fields) {
        List<String> emptyFields = new ArrayList<>();

        for (JTextField field : fields) {
            if (!isNotEmpty(field)) {
                emptyFields.add("Campo requerido");
            }
        }

        return new ValidationResult(emptyFields.isEmpty(), emptyFields);
    }

    /**
     * Valida que al menos un checkbox esté seleccionado
     */
    public static boolean atLeastOneSelected(List<JCheckBox> checkBoxes) {
        return checkBoxes.stream().anyMatch(JCheckBox::isSelected);
    }

    /**
     * Clase interna para resultado de validación
     */
    public static class ValidationResult {
        private final boolean valid;
        private final List<String> errors;

        public ValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }

        public String getErrorMessage() {
            return String.join("\n", errors);
        }
    }

    private ValidationUtils() {
    }
}