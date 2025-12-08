package com.mycompany.rwash.views;

import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class PainelGrafico extends JPanel {

    public PainelGrafico(String titulo, String eixoX, String eixoY, String[] categorias, double[] valores) {
        // Define layout e transparência
        setLayout(new BorderLayout());
        setOpaque(false); 

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < categorias.length; i++) {
            dataset.addValue(valores[i], titulo, categorias[i]);
        }

        // Cria o gráfico
        JFreeChart chart = ChartFactory.createLineChart(
                titulo,
                eixoX,
                eixoY,
                dataset,
                PlotOrientation.VERTICAL,
                false, // <--- LEGENDA ESTÁ DESLIGADA AQUI
                true,  
                false  
        );

        // --- ESTILIZAÇÃO DARK ---
        
        Color textoCor = Color.WHITE;
        Color gridCor = new Color(255, 255, 255, 50); 
        Color linhaCor = new Color(153, 50, 255); 
        Color fundoPlot = new Color(0, 0, 0, 60); 

        // Fundo Transparente
        chart.setBackgroundPaint(null); 
        
        // Configuração do Plot
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(fundoPlot); 
        plot.setOutlineVisible(false); 
        
        // Grades
        plot.setDomainGridlinePaint(gridCor);
        plot.setRangeGridlinePaint(gridCor);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        // Estilo da Linha
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, linhaCor); 
        renderer.setSeriesStroke(0, new BasicStroke(3.0f)); 
        renderer.setDefaultShapesVisible(true); 
        renderer.setDefaultShapesFilled(true);

        // Eixos e Textos
        chart.getTitle().setPaint(textoCor);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));

        // Eixo X
        plot.getDomainAxis().setLabelPaint(textoCor);
        plot.getDomainAxis().setTickLabelPaint(textoCor);
        plot.getDomainAxis().setAxisLinePaint(textoCor);

        // Eixo Y
        plot.getRangeAxis().setLabelPaint(textoCor);
        plot.getRangeAxis().setTickLabelPaint(textoCor);
        plot.getRangeAxis().setAxisLinePaint(textoCor);
        
        // --- CORREÇÃO DO ERRO AQUI ---
        // Só tenta mudar a borda da legenda SE a legenda existir
        if (chart.getLegend() != null) {
            chart.getLegend().setFrame(BlockBorder.NONE);
        }

        // Painel do Chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false); 
        chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        add(chartPanel, BorderLayout.CENTER);
    }
}