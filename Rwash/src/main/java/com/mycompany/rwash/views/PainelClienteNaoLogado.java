package com.mycompany.rwash.views;

import com.mycompany.rwash.DAO.UsuarioDAO;
import com.mycompany.rwash.Model.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PainelClienteNaoLogado extends JFrame {

    private int idCliente;

    // =============== COMPONENTES ORIGINAIS ===============
    private javax.swing.JButton btnAdquirirProduto;
    private javax.swing.JButton btnAdquirirProduto2;
    private javax.swing.JButton btnLogin;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;

    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;

    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;

    // ================== CONSTRUTORES ==================
    public PainelClienteNaoLogado() {
        initUI();
        setExtendedState(MAXIMIZED_BOTH);
    }

    public PainelClienteNaoLogado(int idClienteLogado) {
        this.idCliente = idClienteLogado;
        initUI();
        carregarDadosCliente();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void carregarDadosCliente() {
        Usuario cliente = UsuarioDAO.buscarPorId(idCliente);
        if (cliente != null) {
            jLabel5.setText("R-Wash | " + cliente.getNomeCliente());
        }
    }

    // ======================= UI COMPLETA =======================
    private void initUI() {

        setTitle("R-Wash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // ==============================
        //        CABEÇALHO
        // ==============================
        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(23, 21, 56));
        jPanel2.setLayout(new BorderLayout());

        // Lado esquerdo: LOGO
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);

        jLabel5 = new JLabel("R-Wash");
        jLabel5.setFont(new Font("Arial", Font.BOLD, 36));
        jLabel5.setForeground(Color.WHITE);
        leftPanel.add(jLabel5);

        // Lado direito: BOTÕES
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 10));
        rightPanel.setOpaque(false);

        btnAdquirirProduto2 = new JButton("Adquira Agora");
        btnAdquirirProduto2.setForeground(new Color(153, 50, 255));
        btnAdquirirProduto2.setFont(new Font("Arial", Font.BOLD, 22));
        btnAdquirirProduto2.setBorder(null);
        btnAdquirirProduto2.setContentAreaFilled(false);
        btnAdquirirProduto2.addActionListener((e) -> abrirCadastro());

        jSeparator2 = new JSeparator(SwingConstants.VERTICAL);
        jSeparator2.setPreferredSize(new Dimension(2, 30));
        jSeparator2.setForeground(Color.WHITE);

        btnLogin = new JButton("Login");
        btnLogin.setForeground(new Color(153, 50, 255));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 22));
        btnLogin.setBorder(null);
        btnLogin.setContentAreaFilled(false);
        btnLogin.addActionListener((e) -> abrirLogin());

        rightPanel.add(btnAdquirirProduto2);
        rightPanel.add(jSeparator2);
        rightPanel.add(btnLogin);

        jPanel2.add(leftPanel, BorderLayout.WEST);
        jPanel2.add(rightPanel, BorderLayout.EAST);

        jSeparator1 = new JSeparator();
        jSeparator1.setForeground(Color.WHITE);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(new Color(23, 21, 56));
        topWrapper.add(jPanel2, BorderLayout.CENTER);
        topWrapper.add(jSeparator1, BorderLayout.SOUTH);

        add(topWrapper, BorderLayout.NORTH);


        // ==============================
        //        CORPO DA TELA
        // ==============================
        jPanel4 = new GradientPanel();
        jPanel4.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        // ============== BLOCO DE TEXTO CORRIGIDO ==============
        JPanel textoPanel = new JPanel();
        textoPanel.setOpaque(false);
        textoPanel.setLayout(new BoxLayout(textoPanel, BoxLayout.Y_AXIS));

        JLabel t1 = new JLabel("Transforme");
        JLabel t6 = new JLabel("sua rotina e economize água de");
        JLabel t2 = new JLabel("maneira simples e eficiente. Ao se cadastrar,");
        JLabel t3 = new JLabel("você começa a fazer a diferença hoje mesmo,");
        JLabel t4 = new JLabel("contribuindo para um planeta mais verde. Não");
        JLabel t5 = new JLabel("deixe para amanhã o que você pode mudar agora!");

        t1.setFont(new Font("Arial", Font.BOLD, 34));
        t1.setForeground(new Color(153, 50, 255));

        Font f = new Font("Arial", Font.PLAIN, 28);
        t2.setFont(f); t3.setFont(f); t4.setFont(f); t5.setFont(f); t6.setFont(f);
        t2.setForeground(Color.WHITE);
        t3.setForeground(Color.WHITE);
        t4.setForeground(Color.WHITE);
        t5.setForeground(Color.WHITE);
        t6.setForeground(Color.WHITE);

        t1.setBorder(new EmptyBorder(0, 0, 5, 0));
        t2.setBorder(new EmptyBorder(0, 0, 5, 0));
        t3.setBorder(new EmptyBorder(0, 0, 5, 0));
        t4.setBorder(new EmptyBorder(0, 0, 5, 0));
        t6.setBorder(new EmptyBorder(0, 0, 5, 0));

        textoPanel.add(t1);
        textoPanel.add(t6);
        textoPanel.add(t2);
        textoPanel.add(t3);
        textoPanel.add(t4);
        textoPanel.add(t5);
        

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 60, 0, 0);
        jPanel4.add(textoPanel, gbc);


        // ===================== BOTÃO ADQUIRA =====================
        btnAdquirirProduto = new JButton("ADQUIRA AGORA");
        btnAdquirirProduto.setFont(new Font("Arial", Font.BOLD, 22));
        btnAdquirirProduto.setBackground(new Color(153, 50, 255));
        btnAdquirirProduto.setForeground(Color.WHITE);
        btnAdquirirProduto.setFocusPainted(false);
        btnAdquirirProduto.addActionListener((e) -> abrirCadastro());

        gbc.gridy++;
        gbc.insets = new Insets(40, 60, 0, 0);
        jPanel4.add(btnAdquirirProduto, gbc);


        // ===================== IMAGEM DA MÁQUINA =====================
        jLabel4 = new JLabel(new ImageIcon(getClass().getResource(
                "/lavadora-de-roupas-maquina-de-lavar-roupas-maquina-com-porta-frontal-frigelar-blog-da-frigelar.jpg"
        )));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.insets = new Insets(50, 120, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        jPanel4.add(jLabel4, gbc);


        add(jPanel4, BorderLayout.CENTER);

        pack();
    }

    // ======================== AÇÕES =========================
    private void abrirLogin() {
        setVisible(false);
        new TelaLogin().setVisible(true);
    }

    private void abrirCadastro() {
        setVisible(false);
        new TelaCadastro().setVisible(true);
    }


    // ======================== MAIN =========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PainelClienteNaoLogado().setVisible(true));
    }
}
