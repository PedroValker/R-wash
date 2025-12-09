package com.mycompany.rwash.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HeaderPersonalizado extends JPanel {

    private int pX, pY;

    public HeaderPersonalizado(JFrame parent, String titulo) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 20, 10, 20)); // Margens

        // 1. Título
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.WEST);

        // 2. Botão Fechar (X)
        JButton btnClose = new JButton("X");
        btnClose.setFont(new Font("Arial", Font.BOLD, 20));
        btnClose.setForeground(Color.WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efeito Hover no X (Fica vermelho)
        btnClose.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnClose.setForeground(new Color(255, 80, 80)); }
            @Override public void mouseExited(MouseEvent e) { btnClose.setForeground(Color.WHITE); }
        });

        // Ação de Fechar
        btnClose.addActionListener(e -> System.exit(0)); // Fecha o programa todo
        
        // Painel para alinhar o botão à direita
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(btnClose);
        add(rightPanel, BorderLayout.EAST);

        // 3. Lógica para Arrastar a Janela (Drag and Drop)
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Pega a posição inicial do mouse
                pX = me.getX();
                pY = me.getY();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                // Define a nova posição da janela baseada no movimento do mouse
                parent.setLocation(parent.getLocation().x + me.getX() - pX,
                                   parent.getLocation().y + me.getY() - pY);
            }
        });
    }
}