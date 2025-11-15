/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Implementación de exportación a archivos PDF, aplicando polimorfismo
*/
package edu.formosa.sigef.GUI.Exportar;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.GUI.ExportarTablas;
import edu.formosa.sigef.LOGICA.UserSession;
import javax.swing.JTable;
import java.io.File;
public class ExportarPDF implements Exportacion {

    @Override
    public void ExportarArchivo(JTable tabla, File archivo, String titulo, UserSession session) throws Exception {
        ExportarTablas.toPDF(tabla, archivo, titulo, session);
    }
}
