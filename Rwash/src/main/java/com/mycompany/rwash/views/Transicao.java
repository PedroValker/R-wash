package com.mycompany.rwash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Transicao {

    // Velocidade da animação (quanto menor, mais rápido)
    private static final int DELAY = 10; 
    // Quanto de opacidade muda por passo (0.0 a 1.0)
    private static final float STEP = 0.05f; 

    /**
     * Fecha a tela atual suavemente e abre a próxima suavemente.
     * @param atual A tela que será fechada (pode ser null se for a primeira)
     * @param proxima A tela que será aberta
     */
    public static void trocar(final JFrame atual, final JFrame proxima) {
        // Configura a próxima tela para começar invisível
        if (proxima != null) {
            proxima.setOpacity(0.0f);
            proxima.setVisible(true);
        }

        // Se tiver tela atual, faz o Fade OUT (Sair)
        if (atual != null) {
            Timer timerOut = new Timer(DELAY, null);
            timerOut.addActionListener(new ActionListener() {
                float opacity = 1.0f;

                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity -= STEP;
                    if (opacity <= 0.0f) {
                        opacity = 0.0f;
                        atual.setOpacity(opacity);
                        timerOut.stop();
                        atual.dispose(); // Fecha a tela antiga
                        
                        // Inicia o Fade IN da próxima tela
                        if (proxima != null) {
                            iniciarFadeIn(proxima);
                        }
                    } else {
                        atual.setOpacity(opacity);
                    }
                }
            });
            timerOut.start();
        } else {
            // Se não tem tela atual, apenas abre a próxima
            if (proxima != null) {
                iniciarFadeIn(proxima);
            }
        }
    }

    private static void iniciarFadeIn(JFrame frame) {
        Timer timerIn = new Timer(DELAY, null);
        timerIn.addActionListener(new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += STEP;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    frame.setOpacity(opacity);
                    timerIn.stop();
                } else {
                    frame.setOpacity(opacity);
                }
            }
        });
        timerIn.start();
    }
}