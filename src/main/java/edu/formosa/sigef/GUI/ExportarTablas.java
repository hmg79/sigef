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

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.*;
public class ExportarTablas {

     public static void toCSV(JTable table, File file) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
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

    public static void toXLSX(JTable table, File file) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Reporte");
            TableModel m = table.getModel();
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

    public static void toPDF(JTable table, File file, String title) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage((new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())));
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float margin = 36;
                float startY = page.getMediaBox().getHeight() - margin;
                float x = margin;
                float y = startY;

                // título
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(x, y);
                cs.showText(title);
                cs.endText();

                y -= 24;
                float rowH = 16;
                TableModel m = table.getModel();
                float pageW = page.getMediaBox().getWidth() - 2*margin;
                float colW = pageW / Math.max(1, m.getColumnCount());

                // encabezados
                cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
                float colX = x;
                for (int c=0;c<m.getColumnCount();c++){
                    cs.beginText(); cs.newLineAtOffset(colX+2, y); cs.showText(m.getColumnName(c)); cs.endText();
                    colX += colW;
                }
                y -= rowH;

                // filas
                cs.setFont(PDType1Font.HELVETICA, 10);
                for (int r=0;r<m.getRowCount();r++){
                    if (y < margin + rowH) {
                        // nueva página
                        cs.close();
                        PDPage np = new PDPage((new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())));
                        doc.addPage(np);
                        try (PDPageContentStream cs2 = new PDPageContentStream(doc, np)) {
                            y = np.getMediaBox().getHeight() - margin;
                            colX = x;
                            for (int c=0;c<m.getColumnCount();c++){
                                cs2.beginText(); cs2.newLineAtOffset(colX+2, y); cs2.showText(m.getColumnName(c)); cs2.endText();
                                colX += colW;
                            }
                            y -= rowH;
                        }
                        continue;
                    }
                    colX = x;
                    for (int c=0;c<m.getColumnCount();c++){
                        String val = String.valueOf(m.getValueAt(r,c));
                        cs.beginText(); cs.newLineAtOffset(colX+2, y); cs.showText(val); cs.endText();
                        colX += colW;
                    }
                    y -= rowH;
                }
            }
            doc.save(file);
        }
    }

}
