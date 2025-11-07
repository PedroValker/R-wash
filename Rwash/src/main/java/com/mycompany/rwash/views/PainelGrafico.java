/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.views;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class PainelGrafico extends JPanel {

     public PainelGrafico(String titulo, String eixoX, String eixoY, String[] categorias, double[] valores) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < categorias.length; i++) {
            dataset.addValue(valores[i], titulo, categorias[i]);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                titulo, eixoX, eixoY, dataset);

        ChartPanel painel = new ChartPanel(chart);
        setLayout(new java.awt.BorderLayout());
        add(painel, java.awt.BorderLayout.CENTER);
    }
}