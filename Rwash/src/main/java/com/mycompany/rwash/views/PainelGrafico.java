/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.views;

import java.awt.*;
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
        titulo, eixoX, eixoY, dataset,
        org.jfree.chart.plot.PlotOrientation.VERTICAL,
        false,  // <<< DESATIVA A LEGENDA
        true,
        false
);


        // === Cores base ===
        Color roxoClaro = new Color(190, 160, 255); // topo
        Color roxoEscuro = new Color(100, 60, 180); // base
        Color branco = Color.WHITE;

        // === Configuração do gráfico ===
        chart.setBackgroundPaint(roxoClaro); // fundo geral
        CategoryPlot plot = chart.getCategoryPlot();

        // Gradiente para o fundo do gráfico
        GradientPaint gradienteFundo = new GradientPaint(
                0, 0, roxoClaro,          // parte superior
                0, 400, roxoEscuro        // parte inferior
        );
        plot.setBackgroundPaint(gradienteFundo);

        // Linhas da grade
        plot.setDomainGridlinePaint(branco);
        plot.setRangeGridlinePaint(branco);

        // === Cor da linha do gráfico ===
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, branco); // linha branca
        renderer.setDefaultShapesVisible(true); // mostrar pontos
        renderer.setDefaultShapesFilled(true);

        // === Texto e eixos ===
        chart.getTitle().setPaint(branco);
        plot.getDomainAxis().setLabelPaint(branco);
        plot.getDomainAxis().setTickLabelPaint(branco);
        plot.getRangeAxis().setLabelPaint(branco);
        plot.getRangeAxis().setTickLabelPaint(branco);

        // === Painel ===
        ChartPanel painel = new ChartPanel(chart);
        setLayout(new BorderLayout());
        add(painel, BorderLayout.CENTER);
    }
}
