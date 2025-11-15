/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Panel reutilizable para:
Reporte (JComboBox): qué informe correr.
Período (combo con “Todos” + periodos)
Institución (combo con “Todas” + instituciones)
Si el rol es Institución, se preselecciona y bloquea su institución.
Estado (combo con “Todos” + estados fijos)
Aplicar (botón que corre el reporte seleccionado)
*/
package edu.formosa.sigef.GUI;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.CatalogoServicio;
import edu.formosa.sigef.LOGICA.CatalogoServicio.EstadoOpt;
import edu.formosa.sigef.LOGICA.ReporteServicio;
import edu.formosa.sigef.LOGICA.UserSession;
import edu.formosa.sigef.LOGICA.dto.AlumnosPorLocalidad;
import edu.formosa.sigef.LOGICA.dto.InstitucionDTO;
import edu.formosa.sigef.LOGICA.dto.PeriodoDTO;
import edu.formosa.sigef.LOGICA.dto.LocalidadDTO;
import edu.formosa.sigef.PERSISTENCIA.ReporteDaoJdbc;
import edu.formosa.sigef.PERSISTENCIA.CatalogoDaoJdbc;
import edu.formosa.sigef.GUI.Exportar.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PanelFiltros extends JPanel {
  public enum TipoReporte {
        LOC("Alumnos por localidad"),
        NIV("Alumnos por nivel"),
        CUR("Alumnos por curso"),
        COND("Alumnos por condición");

        private final String label;
        TipoReporte(String label){ this.label = label; }
        @Override public String toString(){ return label; }
    }

    private final UserSession session;
    private final ReporteServicio reportes;
    private final CatalogoServicio catalogos = new CatalogoServicio(new CatalogoDaoJdbc());
    private final JTable table;
    private final JLabel status;
    private final JComboBox<Object> cmbLocalidad = new JComboBox<>();
    private JLabel lblLocalidad;
    private final JComboBox<TipoReporte> cmbReporte = new JComboBox<>();
    private final JComboBox<Object> cmbPeriodo = new JComboBox<>();
    private final JComboBox<Object> cmbInstitucion = new JComboBox<>();
    private final JComboBox<EstadoOpt> cmbEstado = new JComboBox<>(EstadoOpt.values());
    private final JButton btnAplicar = new JButton("Aplicar");
    /*
    Boton para exportar los datos
    */
    private final JButton btnCsv  = new JButton("CSV");
    private final JButton btnXlsx = new JButton("XLSX");
    private final JButton btnPdf  = new JButton("PDF");

    public PanelFiltros(UserSession session, ReporteServicio reportes, JTable table, JLabel status){
        super(new GridBagLayout());
        this.session = session;
        this.reportes = reportes;
        this.table = table;
        this.status = status;

        buildUI();
        cargarCombos();
        personalizarPorRol();
        hookEvents();
    }

    private void buildUI(){
        setBorder(BorderFactory.createTitledBorder("Filtros"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,8,4,8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        int y = 0;

        // Reporte
        c.gridx=0; c.gridy=y; add(new JLabel("Reporte:"), c);
        c.gridx=1; add(cmbReporte, c); y++;

        // Periodo
        c.gridx=0; c.gridy=y; add(new JLabel("Período:"), c);
        c.gridx=1; add(cmbPeriodo, c); y++;

        // Institución
        c.gridx=0; c.gridy=y; add(new JLabel("Institución:"), c);
        c.gridx=1; add(cmbInstitucion, c); y++;
        // Localidad
        lblLocalidad = new JLabel("Localidad:");
        c.gridx=0; c.gridy=y; add(lblLocalidad, c);
        c.gridx=1; add(cmbLocalidad, c); y++;
        // Estado
        c.gridx=0; c.gridy=y; add(new JLabel("Estado:"), c);
        c.gridx=1; add(cmbEstado, c); y++;

        // Botón
        c.gridx=0; c.gridy=y; c.gridwidth=2;
        btnAplicar.setFocusable(false);
        add(btnAplicar, c);
        
        // Botonera de exportación
        c.gridx=0; c.gridy=++y; c.gridwidth=2;
        JPanel exportBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        exportBar.add(new JLabel("Exportar:"));
        exportBar.add(btnCsv);
        exportBar.add(btnXlsx);
        exportBar.add(btnPdf);
        add(exportBar, c);
    }

    private void cargarCombos(){
        // Reportes según rol se hace luego en personalizarPorRol()
        // Periodos
        DefaultComboBoxModel<Object> mPer = new DefaultComboBoxModel<>();
        mPer.addElement("Todos");
        for (PeriodoDTO p : catalogos.periodos()) mPer.addElement(p);
        cmbPeriodo.setModel(mPer);

        // Instituciones
        DefaultComboBoxModel<Object> mInst = new DefaultComboBoxModel<>();
        mInst.addElement("Todas");
        for (InstitucionDTO i : catalogos.instituciones()) mInst.addElement(i);
        cmbInstitucion.setModel(mInst);
        
        // Localidades
        DefaultComboBoxModel<Object> mLoc = new DefaultComboBoxModel<>();
        mLoc.addElement("Todas");
        for (var l : catalogos.localidades()) mLoc.addElement(l);
        cmbLocalidad.setModel(mLoc);

        // Estado (ya cargado por el constructor con values())
        cmbEstado.setSelectedItem(EstadoOpt.TODOS);
    }

    private void personalizarPorRol(){
        DefaultComboBoxModel<TipoReporte> mRep = new DefaultComboBoxModel<>();
        if (session.esInstitucion() || "INSTITUCION".equalsIgnoreCase(session.getRolCodigo())){
            // Menú Institución
            mRep.addElement(TipoReporte.CUR);
            mRep.addElement(TipoReporte.COND);
            mRep.addElement(TipoReporte.NIV); // (opcional) nivel, pero filtrando su institución
            // Forzar institución fija
            seleccionarInstitucionYBloquear(session.getInstitucionId());
        } else {
            // Menú Departamento
            mRep.addElement(TipoReporte.LOC);
            mRep.addElement(TipoReporte.NIV);
            mRep.addElement(TipoReporte.CUR);
            mRep.addElement(TipoReporte.COND);
            cmbInstitucion.setEnabled(true); // puede elegir
        }
        cmbReporte.setModel(mRep);
        cmbReporte.setSelectedIndex(0);
        cmbReporte.addActionListener(e -> {
        var t = (TipoReporte) cmbReporte.getSelectedItem();
        boolean showLoc = (t == TipoReporte.LOC);
        lblLocalidad.setVisible(showLoc);
        cmbLocalidad.setVisible(showLoc);
        });
        /* disparar estado inicial */
        {
            var t = (TipoReporte) cmbReporte.getSelectedItem();
            boolean showLoc = (t == TipoReporte.LOC);
            lblLocalidad.setVisible(showLoc);
            cmbLocalidad.setVisible(showLoc);
        }
    }
    private Long extraerLocalidad(){
        Object sel = cmbLocalidad.getSelectedItem();

        // Caso 1: no hay selección
        if (sel == null) return null;

        // Caso 2: opción "Todas" → sin filtro
        if (sel instanceof String && ((String) sel).equalsIgnoreCase("Todas")) {
            return null;
        }

        // Caso 3: objeto LocalidadDTO válido → devuelve su ID
        if (sel instanceof LocalidadDTO l) {
            return l.getId();
        }

        // Caso por defecto
        return null;
    }
    private void seleccionarInstitucionYBloquear(Long instId){
        if (instId == null){ // por seguridad
            cmbInstitucion.setEnabled(true);
            return;
        }
        ComboBoxModel<Object> model = cmbInstitucion.getModel();
        for (int i=0; i<model.getSize(); i++){
            Object val = model.getElementAt(i);
            if (val instanceof InstitucionDTO dto && dto.getId()==instId){
                cmbInstitucion.setSelectedIndex(i);
                break;
            }
        }
        cmbInstitucion.setEnabled(false);
        cmbInstitucion.setToolTipText("Restringido por rol a su institución (ID=" + instId + ")");
    }

    private void hookEvents(){
        btnAplicar.addActionListener(e -> ejecutarReporte());
        btnCsv.addActionListener(e -> exportar("csv"));
        btnXlsx.addActionListener(e -> exportar("xlsx"));
        btnPdf.addActionListener(e -> exportar("pdf"));
    }

    private void ejecutarReporte(){
        try{
            Long periodoId = extraerPeriodo();
            Long instId    = extraerInstitucion();
            Long localidadId = extraerLocalidad();
            Integer estado = extraerEstado();

            TipoReporte t = (TipoReporte) cmbReporte.getSelectedItem();
            if (t == null) return;

            switch (t){
                case LOC -> runPorLocalidad(periodoId, instId, localidadId, estado);
                case NIV -> runPorNivel(periodoId, instId, estado);
                case CUR -> runPorCurso(periodoId, instId, estado);
                case COND -> runPorCondicion(periodoId, instId);
            }
        } catch (edu.formosa.sigef.LOGICA.AppExcepcion ex){
            JOptionPane.showMessageDialog(this, "No se pudo ejecutar el reporte: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Long extraerPeriodo(){
         Object sel = cmbPeriodo.getSelectedItem();

        // Caso 1: opción "Todos" seleccionada → sin filtro
        if (sel instanceof String && ((String) sel).equalsIgnoreCase("Todos")) {
            return null;
        }

        // Caso 2: objeto PeriodoDTO válido → devuelve su ID
        if (sel instanceof PeriodoDTO p) {
            return p.getId();
        }

        // Caso por defecto → sin selección
        return null;
    }
    private Long extraerInstitucion(){
       Object sel = cmbInstitucion.getSelectedItem();

        // Caso 1: rol institucional → devuelve solo su institución
        if (!cmbInstitucion.isEnabled()) {
            if (sel instanceof InstitucionDTO i) {
                return i.getId();
            } else {
            return session.getInstitucionId();
            }
        }

        // Caso 2: opción "Todas" seleccionada → devolver null (para que SQL muestre todas)
        if (sel instanceof String && ((String) sel).equalsIgnoreCase("Todas")) {
            return null;
        }

        // Caso 3: institución específica seleccionada
        if (sel instanceof InstitucionDTO i) {
            return i.getId();
        }

        // Caso por defecto
        return null;
    }
    private Integer extraerEstado(){
         Object sel = cmbEstado.getSelectedItem();

        // Caso 1: texto "Todos" → null (para no filtrar por estado)
        if (sel instanceof String && ((String) sel).equalsIgnoreCase("Todos")) {
            return null;
        }

        // Caso 2: opción válida de tipo EstadoOpt
        if (sel instanceof EstadoOpt e) {
        // Si el enum tiene una opción "TODOS", también devolvemos null
            if (e == EstadoOpt.TODOS) return null;
            return e.id();
        }

        // Caso por defecto
        return null;
    }

    /* ======== Ejecutores concretos: usa ReporteService y cargan la JTable ======== */

    private void runPorLocalidad(Long periodoId, Long instId, Long localidadId, Integer estado){
        //Verificación de datos
        //System.out.println("---- DEBUG FILTROS ----");
        //System.out.println("Periodo ID: " + periodoId);
        //System.out.println("Institucion ID: " + instId);
        //System.out.println("Localidad ID: " + localidadId);
        //System.out.println("-----------------------");

        var datos = reportes.cantidadAlumnosPorLocalidad(periodoId, instId, localidadId, estado, session);
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Localidad ID","Localidad","Inscriptos","Cursando","Egresados","Abandono","Total alumnos"}, 0);
        for (var r : datos) {
            model.addRow(new Object[]{
                r.getLocalidadId(),
                r.getLocalidadNombre(),
                r.getInscriptos(),
                r.getCursando(),
                r.getEgresados(),
                r.getAbandono(),
                r.getTotal() });
        }
        aplicarModelo(model, "Reporte por Localidad");
}
    private void runPorNivel(Long periodoId, Long instId, Integer estado) {
    try {
        var datos = reportes.cantidadAlumnosPorNivel(periodoId, instId, estado, session);

        // Definimos las columnas del modelo de tabla
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Nivel ID", "Nivel", "Inscriptos", "Cursando", "Egresados", "Abandono", "Total"}, 0);

        // Cargamos cada fila desde los datos
        for (var r : datos) {
            model.addRow(new Object[]{
                    r.getNivelId(),
                    r.getNivelNombre(),
                    r.getInscriptos(),
                    r.getCursando(),
                    r.getEgreso(),
                    r.getAbandono(),
                    r.getTotal()
            });
        }

        // Aplicamos el modelo a la JTable
        table.setModel(model);
        table.setAutoCreateRowSorter(true);

        int filas = model.getRowCount();
        status.setText("Reporte por Nivel | Filas: " + filas);

        // Si no hay resultados, avisamos
        if (filas == 0) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontraron resultados para los filtros seleccionados.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                "Error al generar el reporte por nivel:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

private void runPorCurso(Long periodoId, Long instId, Integer estado){
    // 1️⃣ Obtener datos con control de sesión
    var datos = reportes.cantidadAlumnosPorCurso(periodoId, instId, estado, session);

    // 2️⃣ Ordenar por total DESC (de mayor a menor cantidad de alumnos)
    var ordenados = ReporteServicio.ordenar(
            datos,
            java.util.Comparator.comparingLong(
                    edu.formosa.sigef.LOGICA.dto.AlumnosPorCurso::getTotal
            ).reversed()
    );

    // 3️⃣ Búsqueda binaria por ID (primero ordeno por ID asc)
    var porIdAsc = ReporteServicio.ordenar(
            datos,
            java.util.Comparator.comparingLong(
                    edu.formosa.sigef.LOGICA.dto.AlumnosPorCurso::getCursoId
            )
    );
    int pos = ReporteServicio.busquedaBinariaPorCursoId(porIdAsc, 12L);
    System.out.println("DEBUG: resultado búsqueda binaria del cursoId=12 -> índice = " + pos);

    // 4️⃣ Crear modelo de tabla con las mismas columnas que el reporte por localidad
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new Object[]{
                "Curso ID", "Curso",
                "Inscriptos", "Cursando", "Egresados", "Abandono", "Total"
            }, 0
    );

    // 5️⃣ Cargar filas con los valores de cada curso
    for (var r : ordenados) {
        model.addRow(new Object[]{
            r.getCursoId(),
            r.getCursoNombre(),
            r.getInscriptos(),
            r.getCursando(),
            r.getEgresados(),
            r.getAbandono(),
            r.getTotal()
        });
    }

    // 6️⃣ Aplicar modelo a la tabla
    aplicarModelo(model, "Reporte por Curso (ordenado por total)");
    }

    private void runPorCondicion(Long periodoId, Long instId){
        var datos = reportes.cantidadPorCondicion(periodoId, instId,session);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Estado ID","Condición","Total alumnos"}, 0);
        for (var r : datos) model.addRow(new Object[]{ r.getEstadoId(), r.getEstado(), r.getTotal() });
        table.setModel(model);
        status.setText("Reporte por Condición | Filas: " + model.getRowCount());
    }
    private void aplicarModelo(javax.swing.table.DefaultTableModel model, String titulo){
    table.setModel(model);
    int filas = model.getRowCount();
    status.setText(titulo + " | Filas: " + filas);

    if (filas == 0) {
        java.awt.Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(
                this,
                "No se encontraron resultados para los filtros seleccionados.",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        // (opcional) habilita el ordenamiento de columnas
        table.setAutoCreateRowSorter(true);
    }
}
    private void exportar(String tipo){
    try {
        // Validación: ¿hay algo para exportar?
        var model = table.getModel();
        if (model == null || model.getRowCount() == 0) {
            java.awt.Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(
                    this,
                    "No hay datos para exportar. Ejecutá un reporte primero.",
                    "Sin datos",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Elegir archivo destino
        var ch = new javax.swing.JFileChooser();
        switch (tipo){
            case "csv"  -> ch.setSelectedFile(new java.io.File("reporte.csv"));
            case "xlsx" -> ch.setSelectedFile(new java.io.File("reporte.xlsx"));
            case "pdf"  -> ch.setSelectedFile(new java.io.File("reporte.pdf"));
        }
        if (ch.showSaveDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION) return;
        java.io.File f = ch.getSelectedFile();

        // Llamar a la utilidad
        Exportacion exp = switch (tipo){
            case "csv"  -> new ExportarCSV();
            case "xlsx" -> new ExportarXlsx();
            case "pdf"  -> new ExportarPDF();
            default -> new ExportarPDF();
        };
        exp.ExportarArchivo(table, f, tipo,session);        
        JOptionPane.showMessageDialog(this, "Exportado: " + f.getAbsolutePath());

    } catch (Exception ex){
        JOptionPane.showMessageDialog(this, "No se pudo exportar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
