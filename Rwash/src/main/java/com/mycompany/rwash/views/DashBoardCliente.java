package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class DashBoardCliente extends JFrame {

    private int idCliente;
    private JButton btnVoltar;

    public DashBoardCliente() {
        // 1. Remove a borda padrão do Windows para visual moderno
        setUndecorated(true);
        
        this.idCliente = UsuarioDAO.idClienteLogado; 
        initUI();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // ==========================================
    //       CLASSES DE DESIGN E ESTILO
    // ==========================================

    // Painel Gradiente (Fundo) - Consistente com outras telas
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

    // --- PAINEL DE ECONOMIA ATUALIZADO ---
    class PainelEconomia extends JPanel {
        public PainelEconomia(double totalLitros) {
            setOpaque(false);
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(20, 20, 20, 20));

            // 1. TARIFA MAIS CARA (R$ 18,90 por metro cúbico - Água + Esgoto)
            double tarifaPorMetro = 18.90; 
            double metrosCubicos = totalLitros / 1000.0;
            double economiaReais = metrosCubicos * tarifaPorMetro;

            // 2. PROJEÇÃO ANUAL (Baseado na média atual x 12 meses)
            double litrosAno = totalLitros * 12; // Estimativa simples
            double reaisAno = economiaReais * 12;

            DecimalFormat dfMoeda = new DecimalFormat("R$ #,##0.00");
            DecimalFormat dfLitros = new DecimalFormat("#,##0");

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            // Título
            JLabel lblTitulo = new JLabel("ECONOMIA ATUAL");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18)); // Fonte um pouco maior
            lblTitulo.setForeground(new Color(200, 200, 200));
            add(lblTitulo, gbc);

            // Valor em Reais (Grande Destaque)
            gbc.gridy++;
            gbc.insets = new Insets(10, 0, 5, 0);
            JLabel lblValor = new JLabel(dfMoeda.format(economiaReais));
            lblValor.setFont(new Font("Arial", Font.BOLD, 48)); // Fonte grande
            lblValor.setForeground(new Color(153, 50, 255)); // Roxo Neon
            add(lblValor, gbc);

            // Litros
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 20, 0);
            JLabel lblLitros = new JLabel(dfLitros.format(totalLitros) + " Litros salvos até agora");
            lblLitros.setFont(new Font("Arial", Font.PLAIN, 16));
            lblLitros.setForeground(Color.WHITE);
            add(lblLitros, gbc);

            // --- BLOCO DE PROJEÇÃO (Texto Comparativo) ---
            gbc.gridy++;
            gbc.insets = new Insets(10, 0, 0, 0); // Espaço extra
            
            // Fundo semi-transparente para destacar a projeção
            JPanel panelProjecao = new JPanel();
            panelProjecao.setBackground(new Color(255, 255, 255, 20));
            panelProjecao.setBorder(new EmptyBorder(10, 15, 10, 15));
            panelProjecao.setLayout(new BorderLayout());
            
            String textoProjecao = "<html><center>"
                    + "<span style='font-size:14px; color:#cccccc;'>IMPACTO EM 1 ANO:</span><br>"
                    + "<span style='font-size:18px; color:#ffffff; font-weight:bold;'>" + dfLitros.format(litrosAno) + " Litros</span><br>"
                    + "<span style='font-size:18px; color:#9932FF; font-weight:bold;'>" + dfMoeda.format(reaisAno) + "</span>"
                    + "</center></html>";
            
            JLabel lblProjecao = new JLabel(textoProjecao);
            panelProjecao.add(lblProjecao);
            
            add(panelProjecao, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Fundo translúcido (preto com transparência)
            g2.setColor(new Color(0, 0, 0, 60)); 
            g2.fillRoundRect(10, 10, getWidth()-20, getHeight()-20, 0, 0); 
            g2.dispose();
        }
    }
    
    // Classes para botão estilizado (se necessário, ou use HeaderPersonalizado)
    // ... (Mantendo AnimatedUnderline, wrapHeaderButton, styleHeaderButton se não usar HeaderPersonalizado)
    // No entanto, vamos usar HeaderPersonalizado para consistência.
    
    // --- Header Personalizado para o Dashboard ---
    // (Pode ser uma classe interna ou usar a externa se ela suportar botões extras)
    // Vamos usar a HeaderPersonalizado externa e adicionar o botão "Voltar" nela
    // OU manter o header atual mas com estilo do HeaderPersonalizado (sem borda do windows)

    // ==========================================
    //            CONSTRUÇÃO DA TELA
    // ==========================================

    private void initUI() {
        // Painel Principal com Gradiente
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // --- HEADER ---
        // Aqui usamos um painel customizado para simular o HeaderPersonalizado,
        // mas com o botão "Voltar" específico do Dashboard.
        // Se a classe HeaderPersonalizado for flexível, ótimo. Senão, fazemos assim:
        
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14, 22, 6, 22));

        String nomeCli = "Cliente"; 
        try {
            Usuario u = UsuarioDAO.buscarPorId(idCliente);
            if (u != null) {
                nomeCli = u.getNomeCliente();
            }
        } catch (Exception e) {
            System.err.println("Aviso: Banco OFF.");
        }

        JLabel titleLabel = new JLabel("R-Wash | Dashboard " + nomeCli);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel, BorderLayout.WEST);

        // Botões do Lado Direito (Voltar + Fechar/X)
        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightButtons.setOpaque(false);

        // Botão Voltar (Estilizado como Link ou Botão Roxo)
        btnVoltar = new JButton("Voltar ao Painel");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setForeground(new Color(153, 50, 255));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorder(new EmptyBorder(5, 10, 5, 10));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> acaoVoltar());
        
        // Efeito Hover simples
        btnVoltar.addMouseListener(new MouseAdapter() {
             public void mouseEntered(MouseEvent e) { btnVoltar.setForeground(Color.WHITE); }
             public void mouseExited(MouseEvent e) { btnVoltar.setForeground(new Color(153, 50, 255)); }
        });

        rightButtons.add(btnVoltar);
        
        // Adiciona um separador e o botão X (Fechar) para manter consistência com HeaderPersonalizado
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(2, 20));
        sep.setForeground(new Color(255, 255, 255, 50));
        rightButtons.add(sep);
        
        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Arial", Font.BOLD, 18));
        btnClose.setForeground(Color.WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnClose.setForeground(new Color(255, 80, 80)); }
            public void mouseExited(MouseEvent e) { btnClose.setForeground(Color.WHITE); }
        });
        btnClose.addActionListener(e -> System.exit(0));
        rightButtons.add(btnClose);

        header.add(rightButtons, BorderLayout.EAST);

        // Linha separadora
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 255, 255, 50));
        
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(separator, BorderLayout.SOUTH);

        mainPanel.add(headerWrap, BorderLayout.NORTH);

        // --- GRÁFICOS ---
        JPanel chartsContainer = new JPanel(new GridLayout(2, 2, 20, 20)); 
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
        ArrayList<Double> aguaReutilizada = new ArrayList<>();
        ArrayList<Double> energia = new ArrayList<>();
        
        boolean dadosCarregados = false;

        try (InputStream input = getClass().getResourceAsStream("/dados.csv")) {
            if (input != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String linha = br.readLine(); 

                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 5) {
                        datas.add(partes[0]);
                        turbidez.add(Double.parseDouble(partes[1]));
                        temperatura.add(Double.parseDouble(partes[2]));
                        aguaReutilizada.add(Double.parseDouble(partes[3]));
                        energia.add(Double.parseDouble(partes[4]));
                    }
                }
                dadosCarregados = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!dadosCarregados || datas.isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                datas.add("L" + i);
                double t = Math.random() * 60;
                turbidez.add(t);
                if (t < 20) aguaReutilizada.add(40.0); else aguaReutilizada.add(0.0);
                temperatura.add(30.0);
                energia.add(1.2);
            }
        }

        String[] categorias = datas.toArray(new String[0]);
        double[] vTurbidez = turbidez.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vAgua = aguaReutilizada.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vEnergia = energia.stream().mapToDouble(Double::doubleValue).toArray();

        double totalLitros = 0;
        for (Double lit : aguaReutilizada) {
            totalLitros += lit;
        }

        // 1. Turbidez (Qualidade)
        container.add(new PainelGrafico("Turbidez da Água", "Data", "NTU", categorias, vTurbidez));
        
        // 2. Água Reutilizada (Gráfico)
        container.add(new PainelGrafico("Água Reutilizada", "Data", "Litros", categorias, vAgua));
        
        // 3. PAINEL DE ECONOMIA (Com Projeção Anual)
        container.add(new PainelEconomia(totalLitros));
        
        // 4. Eficiência (Constante)
        container.add(new PainelGrafico("Gasto de Energia", "Data", "kWh", categorias, vEnergia));
    }

    private void acaoVoltar() {
        // Transição suave para o PainelCliente
        Transicao.trocar(this, new PainelCliente()); 
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new DashBoardCliente().setVisible(true));
    }
}