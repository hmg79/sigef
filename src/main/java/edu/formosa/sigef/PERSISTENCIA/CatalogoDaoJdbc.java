/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
    Logica de comunicación con la base de datos para realizar el catalogo
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.dto.InstitucionDTO;
import edu.formosa.sigef.LOGICA.dto.PeriodoDTO;
import edu.formosa.sigef.LOGICA.dto.LocalidadDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CatalogoDaoJdbc implements CatalogoDAO{
  @Override
    public List<PeriodoDTO> listarPeriodos() {
        String sql =
            "SELECT id, " +
            "       CONCAT(anio, IF(cuatrimestre IS NULL, ' Anual', CONCAT(' C', cuatrimestre))) AS nombre " +
            "FROM periodo " +
            "ORDER BY anio DESC, cuatrimestre";
        try (Connection cn = DB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            List<PeriodoDTO> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new PeriodoDTO(rs.getLong("id"), rs.getString("nombre")));
            }
            return out;
        } catch (SQLException e) {
            throw new PersistenciaExcepciones("Error listando periodos", e);
        }

    }

    @Override
    public List<InstitucionDTO> listarInstituciones() {
        String sql = "SELECT id, nombre FROM institucion ORDER BY nombre";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<InstitucionDTO> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new InstitucionDTO(rs.getLong("id"), rs.getString("nombre")));
            }
            return out;
        } catch (SQLException e) {
            throw new PersistenciaExcepciones("Error listando instituciones", e);
        }
    }
    @Override
        public List<LocalidadDTO> listarLocalidades() {
            String sql = "SELECT id, nombre FROM localidad ORDER BY nombre";
            try (Connection cn = DB.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            List<LocalidadDTO> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(new LocalidadDTO(rs.getLong("id"), rs.getString("nombre")));
                }
                return out;
            } catch (SQLException e) {
        throw new PersistenciaExcepciones("Error listando localidades", e);
        }
    }
}
