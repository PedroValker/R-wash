package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DashBoardCliente extends JFrame {

        private int idCliente;
        private JButton btnVoltar;

        // --- CONSTRUTOR SEM PARÂMETROS ---
        public DashBoardCliente() {
            // Pega o ID direto da memória global
            this.idCliente = UsuarioDAO.idClienteLogado; 
            
            initUI();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // 1. Linha animada (Efeito Hover)
    class AnimatedUnderline extends JComponent {
        private Color current = new Color(153, 50, 255);
        private final Color hover = Color.WHITE;
        private final Color normal = new Color(153, 50, 255);
        private Timer timer;

        public AnimatedUnderline(JButton target) {
            setOpaque(false);
            setPreferredSize(new Dimension(100, 3));
            target.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { animateTo(hover); }
                @Override public void mouseExited(MouseEvent e)  { animateTo(normal); }
            });
        }
        private void animateTo(Color target) {
            if (timer != null && timer.isRunning()) timer.stop();
            timer = new Timer(16, ev -> {
                int r = step(current.getRed(), target.getRed());
                int g = step(current.getGreen(), target.getGreen());
                int b = step(current.getBlue(), target.getBlue());
                current = new Color(r,g,b);
                repaint();
                if (r==target.getRed() && g==target.getGreen() && b==target.getBlue()) timer.stop();
            });
            timer.start();
        }
        private int step(int c, int t) {
            if (c < t) return Math.min(c+12, t);
            if (c > t) return Math.max(c-12, t);
            return c;
        }
        @Override protected void paintComponent(Graphics g) {
            g.setColor(current);
            g.fillRect(0,0,getWidth(), getHeight());
        }
    }

    // 2. Método para envolver o botão na animação
    private JPanel wrapHeaderButton(final JButton btn) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(btn);
        wrapper.add(center, BorderLayout.CENTER);

        final AnimatedUnderline underline = new AnimatedUnderline(btn);
        wrapper.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                underline.setPreferredSize(new Dimension(center.getWidth(), 3));
                underline.revalidate();
            }
        });
        wrapper.add(underline, BorderLayout.SOUTH);
        return wrapper;
    }

    // 3. Estilo do texto do botão (Link Roxo)
    private void styleHeaderButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setForeground(new Color(153, 50, 255));
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // 4. Painel Gradiente (Fundo)
    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(23, 21, 56), w, h, new Color(60, 0, 120));
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
        }
    }

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        setTitle("R-Wash | Dashboard de Monitoramento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define o fundo gradiente
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14, 22, 6, 22));

        // Título + Nome do Cliente (COM PROTEÇÃO CONTRA ERRO DE CONEXÃO)
        String nomeCli = "Cliente"; 
        try {
            // Tenta buscar no banco. Se o banco estiver OFF, cai no catch e não trava.
            Usuario u = UsuarioDAO.buscarPorId(idCliente);
            if (u != null) {
                nomeCli = u.getNomeCliente();
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível conectar ao banco de dados para pegar o nome. " + e.getMessage());
        }

        JLabel titleLabel = new JLabel("R-Wash | Dashboard " + nomeCli);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);

        // Botão Voltar (Direita)
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightButtons.setOpaque(false);

        btnVoltar = new JButton("Voltar ao Painel");
        styleHeaderButton(btnVoltar);
        btnVoltar.addActionListener(e -> acaoVoltar());

        rightButtons.add(wrapHeaderButton(btnVoltar));
        header.add(rightButtons, BorderLayout.EAST);

        // Linha separadora
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 255, 255, 50));
        
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(separator, BorderLayout.SOUTH);

        mainPanel.add(headerWrap, BorderLayout.NORTH);

        // --- ÁREA DOS GRÁFICOS ---
        JPanel chartsContainer = new JPanel(new GridLayout(2, 2, 20, 20)); // Grid 2x2 com espaço
        chartsContainer.setOpaque(false);
        chartsContainer.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        carregarGraficos(chartsContainer);

        mainPanel.add(chartsContainer, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void carregarGraficos(JPanel container) {
        ArrayList<String> datas = new ArrayList<>();
        ArrayList<Double> turbidez = new ArrayList<>();
        ArrayList<Double> temperatura = new ArrayList<>();
        ArrayList<Double> consumoAgua = new ArrayList<>();
        ArrayList<Double> consumoEnergia = new ArrayList<>();
        
        boolean dadosCarregados = false;

        // Tenta ler o CSV
        try (InputStream input = getClass().getResourceAsStream("/dados.csv")) {
            if (input != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String linha = br.readLine(); // Pula cabeçalho

                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 5) {
                        datas.add(partes[0]);
                        turbidez.add(Double.parseDouble(partes[1]));
                        temperatura.add(Double.parseDouble(partes[2]));
                        consumoAgua.add(Double.parseDouble(partes[3]));
                        consumoEnergia.add(Double.parseDouble(partes[4]));
                    }
                }
                dadosCarregados = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Se o CSV não existir ou estiver vazio, gera dados aleatórios para teste
        if (!dadosCarregados || datas.isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                datas.add("Dia " + i);
                turbidez.add(Math.random() * 5);
                temperatura.add(20 + Math.random() * 10);
                consumoAgua.add(50 + Math.random() * 20);
                consumoEnergia.add(1 + Math.random());
            }
        }

        String[] categorias = datas.toArray(new String[0]);
        double[] vTurbidez = turbidez.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vTemp = temperatura.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vAgua = consumoAgua.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vEnergia = consumoEnergia.stream().mapToDouble(Double::doubleValue).toArray();

        // Adiciona os painéis gráficos
        container.add(new PainelGrafico("Turbidez (NTU)", "Data", "NTU", categorias, vTurbidez));
        container.add(new PainelGrafico("Temperatura (°C)", "Data", "°C", categorias, vTemp));
        container.add(new PainelGrafico("Consumo Água (L)", "Data", "Litros", categorias, vAgua));
        container.add(new PainelGrafico("Energia (kWh)", "Data", "kWh", categorias, vEnergia));
    }

    private void acaoVoltar() {
        this.dispose(); // Fecha o Dashboard
        new PainelCliente().setVisible(true); // Abre o Painel do Cliente
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new DashBoardCliente().setVisible(true));
    }
}