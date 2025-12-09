package com.mycompany.rwash.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Transicao {

    // Velocidade da animação (quanto menor, mais rápido)
    private static final int DELAY = 10; 
    // Quanto a opacidade muda por passo (0.0 a 1.0)
    private static final float STEP = 0.05f; 

    /**
     * Troca telas com fade in/out automaticamente.
     * @param atual Tela atual (pode ser null)
     * @param proxima Nova tela a ser aberta
     */
    public static void trocar(final JFrame atual, final JFrame proxima) {

        // Configura a próxima tela antes de mostrar
        if (proxima != null) {
            prepararTela(proxima);
            proxima.setOpacity(0.0f);
            proxima.setVisible(true);
        }

        // Se existe tela atual → anima saída
        if (atual != null) {
            prepararTela(atual);

            Timer timerOut = new Timer(DELAY, null);
            timerOut.addActionListener(new ActionListener() {
                float opacity = 1.0f;

                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity -= STEP;
                    if (opacity <= 0.0f) {
                        atual.setOpacity(0.0f);
                        timerOut.stop();
                        atual.dispose();

                        if (proxima != null) {
                            iniciarFadeIn(proxima);
                        }
                    } else {
                        atual.setOpacity(opacity);
                    }
                }
            });
            timerOut.start();
        } 
        // Se não existe tela atual → apenas fade in na nova
        else if (proxima != null) {
            iniciarFadeIn(proxima);
        }
    }

    /** Garante que o JFrame permita animação */
    private static void prepararTela(JFrame frame) {
        if (frame.isDisplayable()) {
            frame.dispose();
        }
        frame.setUndecorated(true);
    }

    /** Gradualmente mostra a nova tela */
    private static void iniciarFadeIn(JFrame frame) {
        Timer timerIn = new Timer(DELAY, null);
        timerIn.addActionListener(new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += STEP;
                if (opacity >= 1.0f) {
                    frame.setOpacity(1.0f);
                    timerIn.stop();
                } else {
                    frame.setOpacity(opacity);
                }
            }
        });
        timerIn.start();
    }
}
