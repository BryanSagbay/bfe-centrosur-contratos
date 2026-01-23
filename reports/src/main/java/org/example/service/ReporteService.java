package org.example.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ReporteService {

    private static final String RUTA_REPORTES = "/reports/";

    /**
     * Genera todos los documentos disponibles
     */
    public static void generarDocumentos(Map<String, Object> params) {
        generarReporte("ConstanciaEntregaDocumentos.jrxml", params);
        generarReporte("ActaEntregaDescriptivoCargo.jrxml", params);
        generarReporte("ActaRecepcionReglamento.jrxml", params);
        generarReporte("AutorizacionCaucion.jrxml", params);
        generarReporte("CarnetIdentificacionConDescuento.jrxml", params);
        generarReporte("CarnetIdentificacionSinDescuento.jrxml", params);
        generarReporte("InduccionGenerica.jrxml", params);
    }

    /**
     * Genera solo los documentos seleccionados por el usuario
     * @param reportesSeleccionados Lista de nombres de archivos .jrxml a generar
     * @param params Parámetros para los reportes
     */
    public static void generarDocumentosSeleccionados(
            List<String> reportesSeleccionados,
            Map<String, Object> params) {

        int exitosos = 0;
        int fallidos = 0;
        StringBuilder errores = new StringBuilder();

        for (String nombreReporte : reportesSeleccionados) {
            try {
                generarReporte(nombreReporte, params);
                exitosos++;
            } catch (Exception e) {
                fallidos++;
                errores.append("- ").append(nombreReporte)
                        .append(": ").append(e.getMessage()).append("\n");
                System.err.println("Error al generar " + nombreReporte + ": " + e.getMessage());
            }
        }

        System.out.println("Reportes generados exitosamente: " + exitosos);
        if (fallidos > 0) {
            System.err.println("Reportes con error: " + fallidos);
            System.err.println("Detalles:\n" + errores);
        }
    }

    /**
     * Genera un reporte individual
     * @param nombreReporte Nombre del archivo .jrxml
     * @param params Parámetros para el reporte
     */
    public static void generarReporte(String nombreReporte, Map<String, Object> params) {
        try (InputStream archivo =
                     ReporteService.class.getResourceAsStream(RUTA_REPORTES + nombreReporte)) {

            if (archivo == null) {
                throw new RuntimeException("No se encontró el reporte: " + nombreReporte);
            }

            JasperReport report = JasperCompileManager.compileReport(archivo);

            JasperPrint print = JasperFillManager.fillReport(
                    report,
                    params,
                    new JREmptyDataSource()
            );

            // Mostrar el reporte en el visor
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setTitle("Reporte: " + nombreReporte.replace(".jrxml", ""));
            viewer.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte: " + nombreReporte, e);
        }
    }

    /**
     * Exporta un reporte a PDF
     * @param nombreReporte Nombre del archivo .jrxml
     * @param params Parámetros para el reporte
     * @param rutaDestino Ruta donde se guardará el PDF
     */
    public static void exportarAPDF(String nombreReporte,
                                    Map<String, Object> params,
                                    String rutaDestino) {
        try (InputStream archivo =
                     ReporteService.class.getResourceAsStream(RUTA_REPORTES + nombreReporte)) {

            if (archivo == null) {
                throw new RuntimeException("No se encontró el reporte: " + nombreReporte);
            }

            JasperReport report = JasperCompileManager.compileReport(archivo);
            JasperPrint print = JasperFillManager.fillReport(
                    report,
                    params,
                    new JREmptyDataSource()
            );

            JasperExportManager.exportReportToPdfFile(print, rutaDestino);
            System.out.println("Reporte exportado a: " + rutaDestino);

        } catch (Exception e) {
            throw new RuntimeException("Error al exportar el reporte: " + nombreReporte, e);
        }
    }
}