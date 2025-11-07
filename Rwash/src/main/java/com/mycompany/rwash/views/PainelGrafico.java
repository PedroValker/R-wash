/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.views;

import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class PainelGrafico extends JPanel {

     public PainelGrafico(String titulo, String eixoX, String eixoY, String[] categorias, double[] valores) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < categorias.length; i++) {
            dataset.addValue(valores[i], titulo, categorias[i]);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                titulo, eixoX, eixoY, dataset);
        
        chart.setBackgroundPaint(Color.WHITE); // fundo da área do chart
        CategoryPlot plot = chart.getCategoryPlot();

        // Converter HSV para RGB
        float hue = 244f / 360f;
        float saturation = 0.62f;
        float value = 0.22f;
        Color roxoFundo = Color.getHSBColor(hue, saturation, value);

        float valuePlot = 0.35f; 
        Color roxoPlot = Color.getHSBColor(hue, saturation, valuePlot);
        
        float saturationLinha = 0.8f;
        float valueLinha = 0.7f;
        Color roxoLinha = Color.getHSBColor(hue, saturationLinha, valueLinha);
        
       chart.setBackgroundPaint(roxoFundo);  // antes estava Color.BLACK
        plot.setBackgroundPaint(roxoFundo);          // fundo do gráfico
        plot.setDomainGridlinePaint(Color.WHITE); // linhas verticais
        plot.setRangeGridlinePaint(Color.WHITE);  // linhas horizontais

        // Linha do gráfico
       plot.setDomainGridlinePaint(Color.WHITE);
plot.setRangeGridlinePaint(Color.WHITE);
chart.getTitle().setPaint(Color.WHITE);
plot.getDomainAxis().setLabelPaint(Color.WHITE);
plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
plot.getRangeAxis().setLabelPaint(Color.WHITE);
plot.getRangeAxis().setTickLabelPaint(Color.WHITE);

        // Texto dos eixos
        plot.getDomainAxis().setLabelPaint(Color.WHITE);
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        plot.getRangeAxis().setLabelPaint(Color.WHITE);
        plot.getRangeAxis().setTickLabelPaint(Color.WHITE);

        // Título
        chart.getTitle().setPaint(Color.WHITE);

        
        
        
        
        

        ChartPanel painel = new ChartPanel(chart);
        setLayout(new java.awt.BorderLayout());
        add(painel, java.awt.BorderLayout.CENTER);
    }
}