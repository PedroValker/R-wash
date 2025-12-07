package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * PainelClienteNaoLogado - versão final ajustada
 */
public class PainelClienteNaoLogado extends JFrame {

    private int idCliente;
    private JLabel tituloLabel;
    private JButton btnTopoAdquirir;
    private JButton btnTopoLogin;
    private RoundedButton btnAdquirirPrincipal;
    private JLabel imgLabel;

    public PainelClienteNaoLogado() {
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
    }

    public PainelClienteNaoLogado(int idCliente) {
        this.idCliente = idCliente;
        initUI();
        carregarDadosCliente();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void carregarDadosCliente() {
        Usuario u = UsuarioDAO.buscarPorId(idCliente);
        if (u != null) {
            tituloLabel.setText("R-Wash | " + u.getNomeCliente());
        }
    }

    // Animated underline
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
            if (timer!=null && timer.isRunning()) timer.stop();
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

    // Rounded CTA
    public static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 20));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0,0,getWidth(),getHeight(),28,28);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // Gradient background
    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0,0,new Color(23,21,56), w, h, new Color(60,0,120));
            g2.setPaint(gp);
            g2.fillRect(0,0,w,h);
        }
    }

    // Build UI
    private void initUI() {
        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

        // HEADER (transparent)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(14,22,6,22));

        tituloLabel = new JLabel("R-Wash");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 34));
        tituloLabel.setForeground(Color.WHITE);
        header.add(tituloLabel, BorderLayout.WEST);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 22, 12));
        rightButtons.setOpaque(false);

        btnTopoAdquirir = new JButton("Adquira Agora");
        styleHeaderButton(btnTopoAdquirir);
        // remove any border look
        btnTopoAdquirir.setBorderPainted(false);
        btnTopoAdquirir.setOpaque(false);
        btnTopoAdquirir.setFocusable(false);
        btnTopoAdquirir.setPreferredSize(new Dimension(160,36));

        btnTopoLogin = new JButton("Login");
        styleHeaderButton(btnTopoLogin);
        btnTopoLogin.setBorderPainted(false);
        btnTopoLogin.setOpaque(false);
        btnTopoLogin.setFocusable(false);
        btnTopoLogin.setPreferredSize(new Dimension(110,36));

        rightButtons.add(wrapHeaderButton(btnTopoAdquirir));
        rightButtons.add(Box.createHorizontalStrut(10));
        rightButtons.add(wrapHeaderButton(btnTopoLogin));

        header.add(rightButtons, BorderLayout.EAST);

        // subtle separator under header
        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        // CENTER content
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // ---------------- LEFT: texto ----------------
        String html =
                "<html><div style='width:600px; font-family:Arial; line-height:1.18; text-align: justify;'>"
                        + "<span style='font-size:30px; font-weight:bold; color:#9932FF;'>Transforme</span>"
                        + "<span style='font-size:26px; color:#ffffff; margin-left:8px;'> sua rotina e economize água</span>"
                        + "<br>"
                        + "<span style='font-size:26px; color:#ffffff; margin-left:8px;'>de maneira simples e eficiente.</span>"
                        + "<br>"
                        + "<span style='font-size:26px; color:#ffffff;'>Ao se cadastrar, você começa a fazer a diferença hoje mesmo, contribuindo para</span>"
                        + "<br>"
                        + "<span style='font-size:26px; color:#ffffff;'>um planeta mais verde.</span>"
                        + "<br>"
                        + "<span style='font-size:26px; color:#ffffff;'>Não deixe para amanhã o que você pode</span>"
                        + "<br>"
                        + "<span style='font-size:26px; color:#ffffff;'>mudar agora!</span>"
                        + "</div></html>";

        JLabel textBlock = new JLabel(html);
        textBlock.setVerticalAlignment(SwingConstants.TOP);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        // Ajustei as margens para dar mais espaço
        leftPanel.setBorder(new EmptyBorder(30, 48, 30, 10)); 
        leftPanel.add(textBlock, BorderLayout.NORTH);

        // Botão ADQUIRA — mais baixo
        btnAdquirirPrincipal = new RoundedButton("ADQUIRA AGORA");
        btnAdquirirPrincipal.setBackground(new Color(153, 50, 255));
        btnAdquirirPrincipal.setPreferredSize(new Dimension(300, 75)); // maior
        btnAdquirirPrincipal.addActionListener(e -> {
            setVisible(false);
            // new TelaLogin().setVisible(true); // Certifique-se que TelaLogin existe ou comente
        });

        JPanel ctaHolder = new JPanel(new GridBagLayout());
        ctaHolder.setOpaque(false);
        ctaHolder.setBorder(new EmptyBorder(60, 0, 0, 0)); // <-- abaixado aqui!!!
        ctaHolder.add(btnAdquirirPrincipal);

        leftPanel.add(ctaHolder, BorderLayout.SOUTH);

        // posicione na grid
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6; // Levemente reduzido para dar mais espaço à direita
        gbc.gridwidth = 1; // Ocupa 1 coluna
        gbc.fill = GridBagConstraints.BOTH; // Preenche o espaço disponível
        gbc.anchor = GridBagConstraints.NORTHWEST;
        center.add(leftPanel, gbc);

    // ---------------- RIGHT: imagem MAIOR ----------------
imgLabel = new JLabel();
imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

// 1. Tenta pegar o caminho da imagem (use o nome do arquivo que funcionava antes)
java.net.URL imgUrl = getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg");

// 2. Verifica se achou a imagem para não dar erro
final ImageIcon original;
if (imgUrl != null) {
    original = new ImageIcon(imgUrl);
} else {
    // Se não achar, cria uma imagem vazia ou usa um placeholder para não travar
    System.err.println("ERRO: Imagem não encontrada no caminho especificado.");
    original = new ImageIcon(); 
}

// Lógica de redimensionamento
center.addComponentListener(new ComponentAdapter() {
    @Override
    public void componentResized(ComponentEvent e) {
        // Se a imagem não foi carregada (width <= 0), não faz nada para evitar erro
        if (original.getIconWidth() <= 0) return;

        int totalW = center.getWidth();
        if (totalW <= 0) return;

        int targetW = (int) (totalW * 0.45); 
        targetW = Math.max(300, Math.min(targetW, 520));

        double ratio = (double) original.getIconHeight() / original.getIconWidth();
        int targetH = (int) (targetW * ratio);

        Image scaled = original.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        imgLabel.setIcon(new ImageIcon(scaled));
    }
});
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        // Ajustei as margens para a direita
        rightPanel.setBorder(new EmptyBorder(30, 10, 100, 70)); 
        rightPanel.add(imgLabel, BorderLayout.NORTH);

        // Reset gbc para o painel da direita
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4; // Aumentado para 40%
        gbc.gridwidth = 1; // Ocupa 1 coluna
        gbc.fill = GridBagConstraints.BOTH; // Preenche o espaço disponível
        gbc.anchor = GridBagConstraints.NORTH;
        // Adiciona margem à esquerda da imagem para garantir separação
        gbc.insets = new Insets(0, 20, 0, 0); 
        center.add(rightPanel, gbc);

        main.add(center, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void styleHeaderButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setForeground(new Color(153,50,255));
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PainelClienteNaoLogado().setVisible(true));
    }
}
