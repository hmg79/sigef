/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/*
Trabajo con polimorfismo para la exportación de los documentos a distintos formatos
*/
package edu.formosa.sigef.GUI.Exportar;

/**
 *
 * @author Héctor Marciano Galli <hectormgalli@gmail.com>
 */

import javax.swing.JTable;
import java.io.File;
import edu.formosa.sigef.LOGICA.UserSession;
public interface Exportacion {
        void ExportarArchivo(JTable tabla, File archivo, String titulo, UserSession session) throws Exception ;
}
