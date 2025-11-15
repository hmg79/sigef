/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Interface de exportación de tablas
*/
package edu.formosa.sigef.GUI;

/**
 *
 * @author HÉCTOR M GALLI
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import edu.formosa.sigef.LOGICA.UserSession;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.*;
public class ExportarTablas {

     public static void toCSV(JTable table, File file, String title, UserSession session) throws IOException {
        
         try (FileWriter fw = new FileWriter(file)) {
            // Encabezado SIGEF
            fw.write("SIGEF – Sistema de Gestión Educativa\n");
            fw.write("Usuario: " + (session != null ? session.getUsername() : "N/D") +
                " (" + (session != null ? session.getRolCodigo() : "N/D") + ")\n");
            fw.write("Institución: " + (session != null && session.getInstitucionId() != null ? session.getInstitucionId() : "Todas") + "\n");
            fw.write("Fecha: " + java.time.LocalDateTime.now().toString() + "\n");
            fw.write("Reporte: " + title + "\n\n");
            
             
            TableModel m = table.getModel();
            // encabezados
            for (int c=0;c<m.getColumnCount();c++){
                fw.write(m.getColumnName(c));
                if (c < m.getColumnCount()-1) fw.write(",");
            }
            fw.write("\n");
            // filas
            for (int r=0;r<m.getRowCount();r++){
                for (int c=0;c<m.getColumnCount();c++){
                    fw.write(String.valueOf(m.getValueAt(r,c)));
                    if (c < m.getColumnCount()-1) fw.write(",");
                }
                fw.write("\n");
            }
        }
    }

    public static void toXLSX(JTable table, File file, String title, UserSession session) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Reporte");
            TableModel m = table.getModel();
            int rowNum = 0;

            // Encabezado SIGEF
            Row header = sheet.createRow(rowNum++);
            header.createCell(0).setCellValue("SIGEF – Sistema de Gestión Educativa");
            sheet.createRow(rowNum++).createCell(0).setCellValue(
                "Usuario: " + (session != null ? session.getUsername() : "N/D") +
                " (" + (session != null ? session.getRolCodigo() : "N/D") + ")"
            );
            sheet.createRow(rowNum++).createCell(0).setCellValue(
                "Institución: " + (session != null && session.getInstitucionId() != null ? session.getInstitucionId() : "Todas")
            );
            sheet.createRow(rowNum++).createCell(0).setCellValue(
                "Fecha: " + java.time.LocalDateTime.now().toString()
            );
            sheet.createRow(rowNum++).createCell(0).setCellValue(
                "Reporte: " + title
            );
            rowNum++; // deja una línea vacía

            // encabezado de columnas
            // encabezado
            Row hr = sheet.createRow(0);
            for (int c=0;c<m.getColumnCount();c++) {
                hr.createCell(c).setCellValue(m.getColumnName(c));
            }
            // filas
            for (int r=0;r<m.getRowCount();r++){
                Row row = sheet.createRow(r+1);
                for (int c=0;c<m.getColumnCount();c++){
                    row.createCell(c).setCellValue(String.valueOf(m.getValueAt(r,c)));
                }
            }
            for (int c=0;c<m.getColumnCount();c++) sheet.autoSizeColumn(c);
            try (FileOutputStream out = new FileOutputStream(file)) { wb.write(out); }
        }
    }

    public static void toPDF(JTable table, File file, String title, UserSession session) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage((new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())));
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float margin = 36;
                float startY = page.getMediaBox().getHeight() - margin;
                float x = margin;
                float y = startY;

                // === ENCABEZADO ===
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
                cs.newLineAtOffset(x, y);
                cs.showText("SIGEF – Sistema de Gestión Educativa");
                cs.endText();

                y -= 14;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 9);
                cs.newLineAtOffset(x, y);
                cs.showText("Usuario: " + (session != null ? session.getUsername() : "N/D")
                    + " (" + (session != null ? session.getRolCodigo() : "N/D") + ")");
                cs.endText();

                y -= 12;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 9);
                cs.newLineAtOffset(x, y);
                cs.showText("Institución: " +
                    (session != null && session.getInstitucionId() != null ?
                        session.getInstitucionId() : "Todas"));
                cs.endText();

                y -= 12;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 9);
                cs.newLineAtOffset(x, y);
                cs.showText("Fecha: " + java.time.LocalDateTime.now().toString());
                cs.endText();

                y -= 22;

                // === TÍTULO DEL REPORTE ===
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 15);
                cs.newLineAtOffset(x, y);
                cs.showText(title);
                cs.endText();

                y -= 20;
                float rowH = 14;
                TableModel m = table.getModel();
                float pageW = page.getMediaBox().getWidth() - 2 * margin;

                // 🔹 Ajuste automático del ancho de columnas (menos ancho por columna si son muchas)
                float colW = (pageW - (m.getColumnCount() * 3)) / Math.max(1, m.getColumnCount());
                float fontSize = (m.getColumnCount() > 6) ? 7.5f : 9f; // reduce fuente si hay muchas columnas

                // === ENCABEZADOS ===
                cs.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                float colX = x;
                for (int c = 0; c < m.getColumnCount(); c++) {
                    cs.beginText();
                    cs.newLineAtOffset(colX + 2, y);
                    String name = m.getColumnName(c);
                // recorta texto largo
                if (name.length() > 15) name = name.substring(0, 15) + "…";
                    cs.showText(name);
                    cs.endText();
                    colX += colW;
                }

                y -= rowH;
                cs.setFont(PDType1Font.HELVETICA, fontSize);

                // === FILAS ===
                for (int r = 0; r < m.getRowCount(); r++) {
                    if (y < margin + rowH) {
                    // salto de página
                    cs.close();
                    PDPage np = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
                    doc.addPage(np);
                    try (PDPageContentStream cs2 = new PDPageContentStream(doc, np)) {
                        y = np.getMediaBox().getHeight() - margin;
                        colX = x;
                        cs2.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                    for (int c = 0; c < m.getColumnCount(); c++) {
                        cs2.beginText();
                        cs2.newLineAtOffset(colX + 2, y);
                        String name = m.getColumnName(c);
                            if (name.length() > 15) name = name.substring(0, 15) + "…";
                                cs2.showText(name);
                                cs2.endText();
                                colX += colW;
                            }
                        y -= rowH;
                    }
                continue;
            }

            colX = x;
            for (int c = 0; c < m.getColumnCount(); c++) {
                String val = String.valueOf(m.getValueAt(r, c));
                if (val.length() > 18) val = val.substring(0, 18) + "…"; // evita superposición
                cs.beginText();
                cs.newLineAtOffset(colX + 2, y);
                cs.showText(val);
                cs.endText();
                colX += colW;
            }
            y -= rowH;
        }
    }
    }
}
}    



