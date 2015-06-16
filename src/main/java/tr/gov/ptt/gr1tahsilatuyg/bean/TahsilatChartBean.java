/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.gov.ptt.gr1tahsilatuyg.bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;
import tr.gov.ptt.gr1tahsilatuyg.service.TahsilatBorcService;

@ManagedBean
@SessionScoped
public class TahsilatChartBean {

    private PieChartModel pieChartModel;

    private List<Object[]> chartListe;
    
    private DefaultPieDataset dataset;

    @EJB
    private TahsilatBorcService tahsilatBorcService;

    public TahsilatChartBean() {

        pieChartModel = new PieChartModel();
        dataset = new DefaultPieDataset();
        pieChartModel.setLegendPosition("ne");
        pieChartModel.setShowDataLabels(true);
        pieChartModel.setTitle("Kurum Borç Grafiği");
        pieChartModel.setSliceMargin(5);
        pieChartModel.setDiameter(150);
        chartListe = new ArrayList<Object[]>();

    }

    public List<Object[]> getChartListe() {
        return chartListe;
    }

    public void setChartListe(List<Object[]> chartListe) {
        this.chartListe = chartListe;
    }

    @PostConstruct
    public void doldurChart() {

        chartListe = tahsilatBorcService.chartVerisiGetir();

        for (Object[] chartElement : chartListe) {

            pieChartModel.set(String.valueOf(chartElement[0]), Double.valueOf(chartElement[1].toString()));

        }

    }

    public PieChartModel getPieChartModel() {

        return pieChartModel;

    }

    public StreamedContent getJfreeChart() {
        StreamedContent content = null;
        try {
            
            chartListe = tahsilatBorcService.chartVerisiGetir();

            for (Object[] chartElement : chartListe) {

                dataset.setValue(String.valueOf(chartElement[0]), Double.valueOf(chartElement[1].toString()));

            }
            boolean legend = true, tooltip = true, urls = false;
            JFreeChart chart = ChartFactory.createPieChart("JFreeChart",
                    dataset, legend, tooltip, urls);
            File chartFile = new File("jfreechart");
            int width = 375, height = 300;
            ChartUtilities.saveChartAsPNG(chartFile, chart, width,
                    height);
            content = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return content;
    }
    public void temizleGrafik()
    {
        pieChartModel.clear();
        dataset.clear();        
    }

}
