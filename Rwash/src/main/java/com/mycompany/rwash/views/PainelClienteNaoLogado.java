package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        if (u != null) tituloLabel.setText("R-Wash | " + u.getNomeCliente());
    }

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
            return c < t ? Math.min(c+12, t) : Math.max(c-12, t);
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

    class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0,0,new Color(23,21,56), getWidth(), getHeight(), new Color(60,0,120));
            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());
        }
    }

    private void initUI() {
        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel main = new GradientPanel();
        main.setLayout(new BorderLayout(0,0));
        setContentPane(main);

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
        btnTopoAdquirir.addActionListener(e -> abrirLogin());

        btnTopoLogin = new JButton("Login");
        styleHeaderButton(btnTopoLogin);
        btnTopoLogin.addActionListener(e -> abrirLogin());

        rightButtons.add(wrapHeaderButton(btnTopoAdquirir));
        rightButtons.add(wrapHeaderButton(btnTopoLogin));

        header.add(rightButtons, BorderLayout.EAST);

        JSeparator headerLine = new JSeparator();
        headerLine.setForeground(new Color(255,255,255,60));
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(headerLine, BorderLayout.SOUTH);
        main.add(headerWrap, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel textBlock = new JLabel(
            "<html><center>"
 + "<span style='font-size:30px; font-weight:bold; color:#46B3FF;'>Pequenas atitudes mudam o mundo.</span><br><br>"
 + "<span style='font-size:22px; color:white;'>E se economizar água fosse tão simples quanto usar o chuveiro?</span><br><br>"
 + "<span style='font-size:22px; color:#32FF8A;'>Agora é. Tecnologia inteligente que detecta desperdício e economiza por você.</span>"
 + "</center></html>"

        );
        textBlock.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(20, 40, 20, 20));

        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.insets = new Insets(0,0,30,0);
        leftPanel.add(textBlock, leftGbc);

        btnAdquirirPrincipal = new RoundedButton("ADQUIRA AGORA");
        btnAdquirirPrincipal.setBackground(new Color(153, 50, 255));
        btnAdquirirPrincipal.setPreferredSize(new Dimension(260, 55));
        btnAdquirirPrincipal.addActionListener(e -> abrirLogin());

        leftGbc.gridy = 1;
        leftPanel.add(btnAdquirirPrincipal, leftGbc);

        gbc.gridx = 0;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        center.add(leftPanel, gbc);

        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon original = new ImageIcon(getClass().getResource("/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg"));

        center.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                int newW = Math.max(350, center.getWidth() / 3);
                Image scaled = original.getImage().getScaledInstance(newW, -1, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(scaled));
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0,20,0,40);
        center.add(imgLabel, gbc);

        main.add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private void abrirLogin(){
        this.setVisible(false);
        new TelaLogin().setVisible(true);
    }

    private void styleHeaderButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setForeground(new Color(153,50,255));
        b.setContentAreaFilled(false);
        b.setBorder(new EmptyBorder(6,10,6,10));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainelClienteNaoLogado::new);
    }
}
