package org.example.ui;

import org.example.service.ReporteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

public class Formulario extends JFrame {

    private JTextField txtColaborador;
    private JTextField txtCedula;
    private JTextField txtArea;
    private JTextField txtCargo;
    private JTextField txtReemplazo;
    private JTextField txtFecha;
    private JTextField txtDireccion;
    private JTextField txtLugarTrabajo;
    private JTextField txtRecibidoPor;
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JTextField txtCiudad;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtResponsableDTH;
    private JTextField analista_th;
    private JTextField trabajadora_social;
    private JTextField remuneraciones;
    private JTextField seguros;
    private JTextField seguridad;
    private JTextField riesgo;
    private JTextField coordinador_calidad;
    private JTextField departamento_medico;
    private JTextField ingeniero_calidad;
    private JTextField tecnologia;
    private JTextField distribucion;

    private Map<String, JCheckBox> checkBoxReportes;

    // Paleta de colores profesional y moderna
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246); // Blue-500
    private static final Color PRIMARY_HOVER = new Color(37, 99, 235); // Blue-600
    private static final Color PRIMARY_PRESSED = new Color(29, 78, 216); // Blue-700
    private static final Color SECONDARY_COLOR = new Color(99, 102, 241); // Indigo-500
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94); // Green-500
    private static final Color SUCCESS_HOVER = new Color(22, 163, 74); // Green-600
    private static final Color WARNING_COLOR = new Color(249, 115, 22); // Orange-500
    private static final Color WARNING_HOVER = new Color(234, 88, 12); // Orange-600

    private static final Color BACKGROUND_START = new Color(249, 250, 251); // Gray-50
    private static final Color BACKGROUND_END = new Color(243, 244, 246); // Gray-100
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39); // Gray-900
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128); // Gray-500
    private static final Color BORDER_COLOR = new Color(229, 231, 235); // Gray-200
    private static final Color FOCUS_BORDER = new Color(147, 197, 253); // Blue-300
    private static final Color INPUT_BG_FOCUS = new Color(239, 246, 255); // Blue-50

    public Formulario() {
        setTitle("Sistema de Gestión Documental");
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(900, 800));

        // Panel principal con gradiente profesional
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_START, 0, h, BACKGROUND_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header moderno y profesional
        JPanel headerPanel = crearHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de contenido con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel datosCard = crearCardDatosPersonales();
        contentPanel.add(datosCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel reportesCard = crearCardReportes();
        contentPanel.add(reportesCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Scrollbar moderna
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(156, 163, 175);
                this.trackColor = BACKGROUND_START;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer con botón principal
        JPanel footerPanel = crearFooter();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JLabel titulo = new JLabel("Sistema de Gestión Documental");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(TEXT_PRIMARY);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Complete la información y seleccione los documentos a generar");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(TEXT_SECONDARY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Badge con diseño limpio
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgePanel.setOpaque(false);
        JLabel badge = new JLabel("VERSIÓN 2.0");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(PRIMARY_COLOR);
        badge.setOpaque(true);
        badge.setBackground(new Color(239, 246, 255));
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 1, true),
                new EmptyBorder(5, 14, 5, 14)
        ));
        badgePanel.add(badge);

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(badgePanel);

        return panel;
    }

    private JPanel crearCardDatosPersonales() {
        JPanel card = crearCardConSombra("Información del Colaborador");
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 25, 8, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 1;

        // Sección Personal
        agregarSeparador(card, "DATOS PERSONALES", row++, gbc);
        txtColaborador = agregarCampoModerno(card, "Nombre Completo", row++, gbc, true);
        txtNombres = agregarCampoModerno(card, "Nombres", row++, gbc, true);
        txtApellidos = agregarCampoModerno(card, "Apellidos", row++, gbc, true);
        txtCedula = agregarCampoModerno(card, "Cédula", row++, gbc, true);
        txtDireccion = agregarCampoModerno(card, "Dirección", row++, gbc, true);
        txtCiudad = agregarCampoModerno(card, "Ciudad", row++, gbc, true);

        row++;
        agregarSeparador(card, "INFORMACIÓN LABORAL", row++, gbc);
        txtCargo = agregarCampoModerno(card, "Cargo", row++, gbc, true);
        txtArea = agregarCampoModerno(card, "Área / Departamento", row++, gbc, true);
        txtLugarTrabajo = agregarCampoModerno(card, "Lugar de Trabajo", row++, gbc, true);
        txtReemplazo = agregarCampoModerno(card, "Reemplazo", row++, gbc, false);
        txtRecibidoPor = agregarCampoModerno(card, "Recibido por", row++, gbc, true);
        txtResponsableDTH = agregarCampoModerno(card, "Responsable DTH", row++, gbc, true);

        row++;
        agregarSeparador(card, "VISTOS BUENOS / ÁREAS RESPONSABLES", row++, gbc);

        analista_th = agregarCampoModerno(card, "Analista de Talento Humano", row++, gbc, false);
        trabajadora_social = agregarCampoModerno(card, "Trabajador/a Social", row++, gbc, false);
        remuneraciones = agregarCampoModerno(card, "Analista de Remuneraciones", row++, gbc, false);
        seguros = agregarCampoModerno(card, "Jefe de Sección de Seguros", row++, gbc, false);
        seguridad = agregarCampoModerno(card, "Supervisor de Seguridad y Salud en el Trabajo", row++, gbc, false);
        riesgo = agregarCampoModerno(card, "Asistente de Seguridad y Salud en el Trabajo", row++, gbc, false);
        coordinador_calidad = agregarCampoModerno(card, "Coordinador del Sistema de Evaluación de Desempeño", row++, gbc, false);
        departamento_medico = agregarCampoModerno(card, "Departamento Médico", row++, gbc, false);
        ingeniero_calidad = agregarCampoModerno(card, "Ingeniero de Calidad", row++, gbc, false);
        tecnologia = agregarCampoModerno(card, "Tecnología", row++, gbc, false);
        distribucion = agregarCampoModerno(card, "Superintendente de Reparaciones", row++, gbc, false);

        row++;
        agregarSeparador(card, "FECHA DEL DOCUMENTO", row++, gbc);

        // Panel de fecha mejorado
        JPanel fechaPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        fechaPanel.setOpaque(false);

        txtDia = crearCampoFecha();
        txtMes = crearCampoFecha();
        txtAnio = crearCampoFecha();

        fechaPanel.add(crearPanelConLabel(txtDia, "Día"));
        fechaPanel.add(crearPanelConLabel(txtMes, "Mes"));
        fechaPanel.add(crearPanelConLabel(txtAnio, "Año"));

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        card.add(fechaPanel, gbc);
        gbc.gridwidth = 1;

        txtFecha = agregarCampoModerno(card, "Fecha Completa", row++, gbc, false);
        txtFecha.setEditable(true);
        txtFecha.setBackground(new Color(249, 250, 251));

        // Inicializar fechas
        LocalDate hoy = LocalDate.now();
        txtDia.setText(String.valueOf(hoy.getDayOfMonth()));
        txtMes.setText(hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")));
        txtAnio.setText(String.valueOf(hoy.getYear()));
        txtFecha.setText(
                hoy.getDayOfMonth() + " de " +
                        hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) +
                        " de " + hoy.getYear()
        );

        // 🔹 ACTIVAR SINCRONIZACIÓN
        sincronizarFechaCompleta();

        return card;
    }

    private void agregarSeparador(JPanel panel, String texto, int row, GridBagConstraints gbc) {
        JPanel separatorPanel = new JPanel(new BorderLayout(15, 0));
        separatorPanel.setOpaque(false);
        separatorPanel.setBorder(new EmptyBorder(20, 0, 15, 0));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(PRIMARY_COLOR);
        label.setPreferredSize(new Dimension(280, 20));

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);
        separator.setBackground(BORDER_COLOR);

        separatorPanel.add(label, BorderLayout.WEST);
        separatorPanel.add(separator, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(separatorPanel, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel crearPanelConLabel(JTextField campo, String label) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(TEXT_SECONDARY);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }

    private JTextField crearCampoFecha() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setForeground(TEXT_PRIMARY);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                new EmptyBorder(14, 12, 14, 12)
        ));
        txt.setBackground(Color.WHITE);

        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(FOCUS_BORDER, 2, true),
                        new EmptyBorder(14, 12, 14, 12)
                ));
                txt.setBackground(INPUT_BG_FOCUS);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                        new EmptyBorder(14, 12, 14, 12)
                ));
                txt.setBackground(Color.WHITE);
            }
        });

        return txt;
    }

    private JPanel crearCardReportes() {
        JPanel card = crearCardConSombra("Selección de Documentos");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        checkBoxReportes = new LinkedHashMap<>();

        String[] reportes = {
                "Constancia de Entrega de Documentos",
                "Acta de Entrega de Cargo",
                "Acta de Recepción de Reglamento",
                "Autorización de Caución",
                "Carnet con Descuento",
                "Carnet sin Descuento",
                "Inducción Genérica"
        };

        String[] archivos = {
                "ConstanciaEntregaDocumentos.jrxml",
                "ActaEntregaDescriptivoCargo.jrxml",
                "ActaRecepcionReglamento.jrxml",
                "AutorizacionCaucion.jrxml",
                "CarnetIdentificacionConDescuento.jrxml",
                "CarnetIdentificacionSinDescuento.jrxml",
                "InduccionGenerica.jrxml"
        };

        JPanel checkboxContainer = new JPanel(new GridLayout(0, 2, 20, 15));
        checkboxContainer.setOpaque(false);
        checkboxContainer.setBorder(new EmptyBorder(10, 25, 25, 25));

        for (int i = 0; i < reportes.length; i++) {
            JCheckBox checkBox = crearCheckBoxModerno(reportes[i]);
            checkBoxReportes.put(archivos[i], checkBox);
            checkboxContainer.add(checkBox);
        }

        card.add(checkboxContainer);

        // Panel de acciones
        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        accionesPanel.setOpaque(false);
        accionesPanel.setBorder(new EmptyBorder(5, 25, 20, 25));

        JButton btnSeleccionarTodos = crearBotonSecundario("Seleccionar Todos", SUCCESS_COLOR, SUCCESS_HOVER);
        JButton btnDeseleccionarTodos = crearBotonSecundario("Limpiar Selección", WARNING_COLOR, WARNING_HOVER);

        btnSeleccionarTodos.addActionListener(e ->
                checkBoxReportes.values().forEach(cb -> cb.setSelected(true))
        );

        btnDeseleccionarTodos.addActionListener(e ->
                checkBoxReportes.values().forEach(cb -> cb.setSelected(false))
        );

        accionesPanel.add(btnSeleccionarTodos);
        accionesPanel.add(btnDeseleccionarTodos);
        card.add(accionesPanel);

        return card;
    }

    private JPanel crearCardConSombra(String titulo) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra profesional
                g2.setColor(new Color(0, 0, 0, 6));
                for (int i = 0; i < 4; i++) {
                    g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 20, 20);
                }

                // Fondo del card
                g2.setColor(CARD_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(25, 0, 25, 0));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setBorder(new EmptyBorder(0, 25, 25, 25));
        card.add(lblTitulo);

        return card;
    }

    private JTextField agregarCampoModerno(JPanel card, String label, int row,
                                           GridBagConstraints gbc, boolean required) {
        JLabel lbl = new JLabel(label + (required ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_PRIMARY);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        card.add(lbl, gbc);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(TEXT_PRIMARY);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));
        txt.setBackground(Color.WHITE);

        // Efectos de interacción
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(FOCUS_BORDER, 2, true),
                        new EmptyBorder(11, 15, 11, 15)
                ));
                txt.setBackground(INPUT_BG_FOCUS);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(12, 16, 12, 16)
                ));
                txt.setBackground(Color.WHITE);
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        card.add(txt, gbc);

        return txt;
    }

    private JCheckBox crearCheckBoxModerno(String texto) {
        JCheckBox checkBox = new JCheckBox() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(new Color(249, 250, 251));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        checkBox.setText(texto);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setOpaque(false);
        checkBox.setForeground(TEXT_PRIMARY);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.setFocusPainted(false);
        checkBox.setSelected(true);
        checkBox.setBorder(new EmptyBorder(14, 16, 14, 16));

        return checkBox;
    }

    private JButton crearBotonSecundario(String texto, Color color, Color hoverColor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(color);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(10, 24, 10, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        return btn;
    }

    private JPanel crearFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        panel.setOpaque(false);

        JButton btnGenerar = new JButton("GENERAR DOCUMENTOS") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color c1, c2;
                if (getModel().isPressed()) {
                    c1 = PRIMARY_PRESSED;
                    c2 = SECONDARY_COLOR.darker();
                } else if (getModel().isRollover()) {
                    c1 = PRIMARY_HOVER;
                    c2 = SECONDARY_COLOR;
                } else {
                    c1 = PRIMARY_COLOR;
                    c2 = SECONDARY_COLOR;
                }

                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setContentAreaFilled(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setBorder(new EmptyBorder(18, 60, 18, 60));
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> generar());

        panel.add(btnGenerar);
        return panel;
    }

    private void generar() {
        // Validación de campos obligatorios
        if (txtColaborador.getText().trim().isEmpty()
                || txtCedula.getText().trim().isEmpty()
                || txtArea.getText().trim().isEmpty()
                || txtCargo.getText().trim().isEmpty()
                || txtLugarTrabajo.getText().trim().isEmpty()
                || txtRecibidoPor.getText().trim().isEmpty()
                || txtDia.getText().trim().isEmpty()
                || txtMes.getText().trim().isEmpty()
                || txtAnio.getText().trim().isEmpty()
                || txtNombres.getText().trim().isEmpty()
                || txtApellidos.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtCiudad.getText().trim().isEmpty()
                || txtResponsableDTH.getText().trim().isEmpty()) {

            mostrarMensajeModerno(
                    "Por favor complete todos los campos obligatorios marcados con *",
                    "Campos Requeridos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<String> reportesSeleccionados = new ArrayList<>();
        checkBoxReportes.forEach((archivo, checkBox) -> {
            if (checkBox.isSelected()) {
                reportesSeleccionados.add(archivo);
            }
        });

        if (reportesSeleccionados.isEmpty()) {
            mostrarMensajeModerno(
                    "Debe seleccionar al menos un documento para generar",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("colaborador", txtColaborador.getText().trim());
        params.put("cedula", txtCedula.getText().trim());
        params.put("area_departamento", txtArea.getText().trim());
        params.put("reemplazo", txtReemplazo.getText().trim());
        params.put("fecha_entrega_documentos", txtFecha.getText());
        params.put("direccion", txtDireccion.getText().trim());
        params.put("lugar_trabajo", txtLugarTrabajo.getText().trim());
        params.put("recibido_por", txtRecibidoPor.getText().trim());
        params.put("dia", txtDia.getText().trim());
        params.put("mes", txtMes.getText().trim());
        params.put("anio", txtAnio.getText().trim());
        params.put("cuidad_colaborador", txtCiudad.getText().trim());
        params.put("colaborador_nombres", txtNombres.getText().trim());
        params.put("colaborador_apellidos", txtApellidos.getText().trim());
        params.put("cargo", txtCargo.getText().trim());
        params.put("responsable_DTH", txtResponsableDTH.getText().trim());
        params.put("analista_th", analista_th.getText().trim());
        params.put("trabajadora_social", trabajadora_social.getText().trim());
        params.put("remuneraciones", remuneraciones.getText().trim());
        params.put("seguros", seguros.getText().trim());
        params.put("seguridad", seguridad.getText().trim());
        params.put("riesgo", riesgo.getText().trim());
        params.put("coordinador_calidad", coordinador_calidad.getText().trim());
        params.put("departamento_medico", departamento_medico.getText().trim());
        params.put("ingeniero_calidad", ingeniero_calidad.getText().trim());
        params.put("tecnologia", tecnologia.getText().trim());
        params.put("distribucion", distribucion.getText().trim());

        ReporteService.generarDocumentosSeleccionados(reportesSeleccionados, params);

        mostrarMensajeModerno(
                "Se han generado " + reportesSeleccionados.size() + " documento(s) exitosamente ✓",
                "Generación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarMensajeModerno(String mensaje, String titulo, int tipo) {
        UIManager.put("OptionPane.background", CARD_COLOR);
        UIManager.put("Panel.background", CARD_COLOR);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    private void sincronizarFechaCompleta() {
        txtDia.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }

            private void actualizar() {
                String dia = txtDia.getText().trim();
                String mes = txtMes.getText().trim();
                String anio = txtAnio.getText().trim();

                if (!dia.isEmpty() && !mes.isEmpty() && !anio.isEmpty()) {
                    txtFecha.setText(dia + " de " + mes + " de " + anio);
                }
            }
        });

        txtMes.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }

            private void actualizar() {
                String dia = txtDia.getText().trim();
                String mes = txtMes.getText().trim();
                String anio = txtAnio.getText().trim();

                if (!dia.isEmpty() && !mes.isEmpty() && !anio.isEmpty()) {
                    txtFecha.setText(dia + " de " + mes + " de " + anio);
                }
            }
        });

        txtAnio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizar(); }

            private void actualizar() {
                String dia = txtDia.getText().trim();
                String mes = txtMes.getText().trim();
                String anio = txtAnio.getText().trim();

                if (!dia.isEmpty() && !mes.isEmpty() && !anio.isEmpty()) {
                    txtFecha.setText(dia + " de " + mes + " de " + anio);
                }
            }
        });
    }

}