package org.example.ui.panels;

import org.example.styles.AppColors;
import org.example.styles.AppStyles;
import org.example.service.ReporteService;
import org.example.ui.components.ComponentFactory;
import org.example.utils.DateUtils;
import org.example.utils.ValidationUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Panel para gestión de certificaciones
 */
public class CertificacionesPanel extends JPanel {

    // Campos del colaborador
    private JTextField txtColaborador;
    private JTextField txtCedula;
    private JTextField txtCargo;
    private JTextField txtArea;
    private JTextField txtFechaIngreso;
    private JTextField txtSalario;

    // Campos de fecha
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JTextField txtFecha;

    // Campos adicionales
    private JTextField txtMotivo;
    private JTextField txtDestino;

    // Checkboxes de certificaciones
    private Map<String, JCheckBox> checkBoxCertificaciones;

    public CertificacionesPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
    }

    private void initComponents() {
        // Panel principal con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Agregar cards
        contentPanel.add(createEmployeeDataCard());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(createCertificationsCard());

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createEmployeeDataCard() {
        JPanel card = ComponentFactory.createCardPanel("Datos del Colaborador");
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 25, 8, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 1;

        // Información básica
        addSectionSeparator(card, "INFORMACIÓN BÁSICA", row++, gbc);
        txtColaborador = addLabeledField(card, "Nombre Completo", row++, gbc, true);
        txtCedula = addLabeledField(card, "Cédula", row++, gbc, true);
        txtCargo = addLabeledField(card, "Cargo", row++, gbc, true);
        txtArea = addLabeledField(card, "Área / Departamento", row++, gbc, true);
        txtFechaIngreso = addLabeledField(card, "Fecha de Ingreso", row++, gbc, false);
        txtSalario = addLabeledField(card, "Salario (opcional)", row++, gbc, false);

        row++;
        addSectionSeparator(card, "INFORMACIÓN ADICIONAL", row++, gbc);
        txtMotivo = addLabeledField(card, "Motivo de la Certificación", row++, gbc, false);
        txtDestino = addLabeledField(card, "Destinatario / Entidad", row++, gbc, false);

        row++;
        addSectionSeparator(card, "FECHA DE EMISIÓN", row++, gbc);
        addDateFields(card, row++, gbc);
        txtFecha = addLabeledField(card, "Fecha Completa", row++, gbc, false);
        txtFecha.setEditable(true);
        txtFecha.setBackground(AppColors.INPUT_BG_DISABLED);

        initializeDates();
        setupDateSynchronization();

        return card;
    }

    private void addDateFields(JPanel panel, int row, GridBagConstraints gbc) {
        JPanel fechaPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        fechaPanel.setOpaque(false);

        txtDia = ComponentFactory.createDateTextField();
        txtMes = ComponentFactory.createDateTextField();
        txtAnio = ComponentFactory.createDateTextField();

        fechaPanel.add(createLabeledPanel(txtDia, "Día"));
        fechaPanel.add(createLabeledPanel(txtMes, "Mes"));
        fechaPanel.add(createLabeledPanel(txtAnio, "Año"));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(fechaPanel, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel createLabeledPanel(JTextField field, String labelText) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(AppStyles.FONT_SUBSECTION);
        label.setForeground(AppColors.TEXT_SECONDARY);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void initializeDates() {
        txtDia.setText(DateUtils.getCurrentDay());
        txtMes.setText(DateUtils.getCurrentMonth());
        txtAnio.setText(DateUtils.getCurrentYear());
        txtFecha.setText(DateUtils.formatCurrentFullDate());
    }

    private void setupDateSynchronization() {
        javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateFullDate(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateFullDate(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateFullDate(); }

            private void updateFullDate() {
                String dia = txtDia.getText().trim();
                String mes = txtMes.getText().trim();
                String anio = txtAnio.getText().trim();

                if (!dia.isEmpty() && !mes.isEmpty() && !anio.isEmpty()) {
                    txtFecha.setText(DateUtils.formatFullDate(dia, mes, anio));
                }
            }
        };

        txtDia.getDocument().addDocumentListener(listener);
        txtMes.getDocument().addDocumentListener(listener);
        txtAnio.getDocument().addDocumentListener(listener);
    }

    private JPanel createCertificationsCard() {
        JPanel card = ComponentFactory.createCardPanel("Tipo de Certificación");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        checkBoxCertificaciones = new LinkedHashMap<>();

        String[] certificaciones = {
                "LISTA DE VERIFICACIÓN DEL PROCESO",
                "ACUERDO DE CUMPLIMIENTO PARA PERSONAS",
                "SOLICITUD PARA LA CERTIFICACIÓN DE PERSONAS"
        };

        String[] archivos = {
                "C01ListaVerificacionProceso.jrxml",
                "C02SolicitudCertificacionPersona.jrxml",
                "C09AcuerdoCumplimiento.jrxml"
        };

        JPanel checkboxContainer = new JPanel();
        checkboxContainer.setLayout(new BoxLayout(checkboxContainer, BoxLayout.Y_AXIS));
        checkboxContainer.setOpaque(false);
        checkboxContainer.setBorder(new EmptyBorder(10, 25, 25, 25));

        for (int i = 0; i < certificaciones.length; i++) {
            JCheckBox checkBox = ComponentFactory.createModernCheckBox(certificaciones[i]);
            checkBox.setSelected(false); // Por defecto no seleccionados
            checkBoxCertificaciones.put(archivos[i], checkBox);
            checkboxContainer.add(checkBox);

            if (i < certificaciones.length - 1) {
                checkboxContainer.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        card.add(checkboxContainer);
        card.add(createActionButtons());

        // Agregar nota informativa
        card.add(createInfoNote());

        return card;
    }

    private JPanel createInfoNote() {
        JPanel notePanel = new JPanel(new BorderLayout(10, 0));
        notePanel.setOpaque(false);
        notePanel.setBorder(new EmptyBorder(10, 25, 20, 25));
        notePanel.setBackground(new Color(239, 246, 255));

        JLabel iconLabel = new JLabel("ℹ");
        iconLabel.setFont(new Font(AppStyles.FONT_FAMILY, Font.BOLD, 18));
        iconLabel.setForeground(AppColors.PRIMARY_COLOR);

        JLabel noteLabel = new JLabel("<html><i>Seleccione el tipo de certificación según sus necesidades. " +
                "Puede generar múltiples certificados a la vez.</i></html>");
        noteLabel.setFont(new Font(AppStyles.FONT_FAMILY, Font.ITALIC, 12));
        noteLabel.setForeground(AppColors.TEXT_SECONDARY);

        notePanel.add(iconLabel, BorderLayout.WEST);
        notePanel.add(noteLabel, BorderLayout.CENTER);

        return notePanel;
    }

    private JPanel createActionButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(5, 25, 20, 25));

        JButton btnSelectAll = ComponentFactory.createSecondaryButton(
                "Seleccionar Todos",
                AppColors.SUCCESS_COLOR,
                AppColors.SUCCESS_HOVER
        );

        JButton btnClearAll = ComponentFactory.createSecondaryButton(
                "Limpiar Selección",
                AppColors.WARNING_COLOR,
                AppColors.WARNING_HOVER
        );

        btnSelectAll.addActionListener(e ->
                checkBoxCertificaciones.values().forEach(cb -> cb.setSelected(true)));

        btnClearAll.addActionListener(e ->
                checkBoxCertificaciones.values().forEach(cb -> cb.setSelected(false)));

        panel.add(btnSelectAll);
        panel.add(btnClearAll);

        return panel;
    }

    private JPanel createFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        panel.setOpaque(false);

        JButton btnGenerar = ComponentFactory.createPrimaryButton("GENERAR CERTIFICACIONES");
        btnGenerar.addActionListener(e -> generateCertifications());

        panel.add(btnGenerar);
        return panel;
    }

    private void addSectionSeparator(JPanel panel, String text, int row, GridBagConstraints gbc) {
        JPanel separatorPanel = new JPanel(new BorderLayout(15, 0));
        separatorPanel.setOpaque(false);
        separatorPanel.setBorder(new EmptyBorder(20, 0, 15, 0));

        JLabel label = new JLabel(text);
        label.setFont(AppStyles.FONT_SUBSECTION);
        label.setForeground(AppColors.PRIMARY_COLOR);
        label.setPreferredSize(new Dimension(280, 20));

        JSeparator separator = new JSeparator();
        separator.setForeground(AppColors.BORDER_COLOR);
        separator.setBackground(AppColors.BORDER_COLOR);

        separatorPanel.add(label, BorderLayout.WEST);
        separatorPanel.add(separator, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(separatorPanel, gbc);
        gbc.gridwidth = 1;
    }

    private JTextField addLabeledField(JPanel panel, String labelText, int row,
                                       GridBagConstraints gbc, boolean required) {
        JLabel label = new JLabel(labelText + (required ? " *" : ""));
        label.setFont(AppStyles.FONT_LABEL);
        label.setForeground(AppColors.TEXT_PRIMARY);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        panel.add(label, gbc);

        JTextField textField = ComponentFactory.createModernTextField();

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        panel.add(textField, gbc);

        return textField;
    }

    private void generateCertifications() {
        // Validar campos obligatorios
        if (!validateRequiredFields()) {
            return;
        }

        // Obtener certificaciones seleccionadas
        List<String> selectedCerts = getSelectedCertifications();
        if (selectedCerts.isEmpty()) {
            showMessage(
                    "Debe seleccionar al menos una certificación para generar",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Preparar parámetros
        Map<String, Object> params = buildParameters();

        // Generar certificaciones
        ReporteService.generarDocumentosSeleccionados(selectedCerts, params);

        showMessage(
                "Se han generado " + selectedCerts.size() + " certificación(es) exitosamente ✓",
                "Generación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private boolean validateRequiredFields() {
        if (!ValidationUtils.isNotEmpty(txtColaborador) ||
                !ValidationUtils.isNotEmpty(txtCedula) ||
                !ValidationUtils.isNotEmpty(txtCargo) ||
                !ValidationUtils.isNotEmpty(txtArea) ||
                !ValidationUtils.isNotEmpty(txtDia) ||
                !ValidationUtils.isNotEmpty(txtMes) ||
                !ValidationUtils.isNotEmpty(txtAnio)) {

            showMessage(
                    "Por favor complete todos los campos obligatorios marcados con *",
                    "Campos Requeridos",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    private List<String> getSelectedCertifications() {
        List<String> selected = new ArrayList<>();
        checkBoxCertificaciones.forEach((archivo, checkBox) -> {
            if (checkBox.isSelected()) {
                selected.add(archivo);
            }
        });
        return selected;
    }

    private Map<String, Object> buildParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("colaborador", txtColaborador.getText().trim());
        params.put("cedula", txtCedula.getText().trim());
        params.put("cargo", txtCargo.getText().trim());
        params.put("area_departamento", txtArea.getText().trim());
        params.put("fecha_ingreso", txtFechaIngreso.getText().trim());
        params.put("salario", txtSalario.getText().trim());
        params.put("dia", txtDia.getText().trim());
        params.put("mes", txtMes.getText().trim());
        params.put("anio", txtAnio.getText().trim());
        params.put("fecha_emision", txtFecha.getText());
        params.put("motivo", txtMotivo.getText().trim());
        params.put("destinatario", txtDestino.getText().trim());
        return params;
    }

    private void showMessage(String message, String title, int type) {
        UIManager.put("OptionPane.background", AppColors.CARD_COLOR);
        UIManager.put("Panel.background", AppColors.CARD_COLOR);
        UIManager.put("OptionPane.messageForeground", AppColors.TEXT_PRIMARY);
        JOptionPane.showMessageDialog(this, message, title, type);
    }
}