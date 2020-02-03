package ba.unsa.etf.rpr.t7;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class KorisniciReport extends JFrame {
    private static final long serialVersionUID = 1L;

    public void showReport(Connection conn) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/korisnici.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();
        JasperDesign jasperDesign = JRXmlLoader.load(reportSrcFile);

        JRDesignQuery newQuery = new JRDesignQuery();
        newQuery.setText("SELECT ime, prezime, username, password FROM korisnik");
        jasperDesign.setQuery(newQuery);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("reportsDirPath", reportsDir);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}

