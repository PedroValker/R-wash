package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.MaquinaDAO;
import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DashBoardCliente extends JFrame {

    private int idCliente;
    private JButton btnVoltar;

    private JComboBox<String> comboMaquinas;
    private LinkedHashMap<Integer, String> maquinasMap;
    private Integer idMaquinaSelecionada; // NOME CORRETO

    private JPanel chartsContainer;

    public DashBoardCliente() {
        setUndecorated(true);
        this.idCliente = UsuarioDAO.idClienteLogado;
        initUI();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            g2.setPaint(new GradientPaint(0, 0,
                    new Color(23, 21, 56), w, h, new Color(60, 0, 120)));
            g2.fillRect(0, 0, w, h);
        }
    }

    class PainelEconomia extends JPanel {
        public PainelEconomia(double totalLitros) {
            setOpaque(false);
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(20, 20, 20, 20));

            double tarifa = 18.90;
            double economia = (totalLitros / 1000) * tarifa;
            double estimadoAno = economia * 12;

            DecimalFormat df = new DecimalFormat("R$ #,##0.00");
            DecimalFormat dfL = new DecimalFormat("#,##0");

            JLabel titulo = new JLabel("ECONOMIA ATUAL");
            titulo.setFont(new Font("Arial", Font.BOLD, 18));
            titulo.setForeground(Color.WHITE);

            JLabel valor = new JLabel(df.format(economia));
            valor.setFont(new Font("Arial", Font.BOLD, 48));
            valor.setForeground(new Color(153, 50, 255));

            JLabel litros = new JLabel(dfL.format(totalLitros) + " Litros reutilizados");
            litros.setFont(new Font("Arial", Font.PLAIN, 16));
            litros.setForeground(Color.WHITE);

            JPanel projeção = new JPanel();
            projeção.setOpaque(false);
            projeção.add(new JLabel("<html><center>PROJEÇÃO ANUAL:<br>"
                    + df.format(estimadoAno) + "</center></html>"));
            projeção.setForeground(Color.WHITE);

            add(titulo);
            add(valor);
            add(litros);
            add(projeção);
        }
    }

    private void initUI() {

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        JPanel header = criarHeader();
        mainPanel.add(header, BorderLayout.NORTH);

        chartsContainer = new JPanel(new GridLayout(2, 2, 20, 20));
        chartsContainer.setOpaque(false);
        chartsContainer.setBorder(new EmptyBorder(25, 25, 25, 25));

        mainPanel.add(chartsContainer, BorderLayout.CENTER);

        carregarMaquinas();

        atualizarGraficos();
    }


    private JPanel criarHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14, 22, 6, 22));

        String nomeCli = "Cliente";
        Usuario u = UsuarioDAO.buscarPorId(idCliente);
        if (u != null) nomeCli = u.getNomeCliente();

        JLabel title = new JLabel("R-Wash | Dashboard - " + nomeCli);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightButtons.setOpaque(false);

        comboMaquinas = new JComboBox<>();
        comboMaquinas.setFont(new Font("Arial", Font.BOLD, 16));
        comboMaquinas.addActionListener(e -> atualizarGraficos());
        rightButtons.add(comboMaquinas);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setForeground(new Color(153, 50, 255));
        btnVoltar.addActionListener(e -> acaoVoltar());
        rightButtons.add(btnVoltar);

        JButton close = new JButton("X");
        close.setContentAreaFilled(false);
        close.setForeground(Color.WHITE);
        close.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { close.setForeground(Color.RED); }
            public void mouseExited(MouseEvent e) { close.setForeground(Color.WHITE); }
        });
        close.addActionListener(e -> System.exit(0));
        rightButtons.add(close);

        header.add(rightButtons, BorderLayout.EAST);

        return header;
    }


    private void carregarMaquinas() {

        maquinasMap = MaquinaDAO.buscarMaquinasPorCliente(idCliente);

        comboMaquinas.removeAllItems();

        maquinasMap.forEach((id, nome) -> comboMaquinas.addItem(nome));

        if (!maquinasMap.isEmpty()) idMaquinaSelecionada = maquinasMap.keySet().iterator().next();
    }


    private void atualizarGraficos() {

   
    chartsContainer.removeAll();

    if (comboMaquinas.getSelectedIndex() >= 0) {
        // Atualiza ID da máquina selecionada baseado no texto do combo
        String nomeSelecionado = (String) comboMaquinas.getSelectedItem();
        idMaquinaSelecionada = maquinasMap.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(nomeSelecionado))
                .map(e -> e.getKey())
                .findFirst()
                .orElse(null);
    }

    // SIMULANDO VALORES DE BD (ate integrar de verdade)
    ArrayList<Double> valores = new ArrayList<>();
    for (int i = 0; i < 10; i++) valores.add(Math.random() * 100);

    chartsContainer.add(criarGrafico("Turbidez", valores));
    chartsContainer.add(criarGrafico("Energia", valores));
    chartsContainer.add(new PainelEconomia(valores.stream().mapToDouble(a -> a).sum()));
    chartsContainer.add(criarGrafico("Temperatura", valores));

    chartsContainer.revalidate();
    chartsContainer.repaint();
    }
private JPanel criarGrafico(String titulo, ArrayList<Double> valores) {

    org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();

    int dia = 1;
    for (Double v : valores) {
        dataset.addValue(v, titulo, "Dia " + dia++);
    }

    org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createLineChart(
            titulo + " - Máquina " + (comboMaquinas.getSelectedItem()),
            "Dias",
            titulo,
            dataset,
            org.jfree.chart.plot.PlotOrientation.VERTICAL,
            false, true, false
    );

    chart.setBackgroundPaint(new Color(23, 21, 56));
    chart.getTitle().setPaint(Color.WHITE);

    org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(60, 0, 120));
    plot.setRangeGridlinePaint(Color.WHITE);
    plot.setDomainGridlinePaint(Color.WHITE);

    org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
    chartPanel.setOpaque(false);
    chartPanel.setPreferredSize(new Dimension(300, 220));

    return chartPanel;
}


    private void acaoVoltar() {
        Transicao.trocar(this, new PainelCliente(idCliente));
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new DashBoardCliente().setVisible(true));
    }
}
