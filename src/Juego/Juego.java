package Juego;

import Graficos.Teclado;

import javax.swing.*;
import java.awt.*;

public class Juego extends Canvas implements Runnable {
    private static final int ancho = 800;
    private static final int alto = 800;
    private static volatile boolean enFuncionamiento = false;
    private static final String nombre = "Juego";
    private static int APS = 0;
    private static int FPS = 0;

    private static JFrame ventana;


    private static Thread thread;

    private Teclado teclado;

    private Juego() {
        setPreferredSize(new Dimension(ancho, alto));

        ventana = new JFrame(nombre);
        teclado = new Teclado();
        addKeyListener(teclado);

        //Cierra la ventana para que no consuma recursos en segundo plano//
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);//para que no pueda modificar el tamaño de la ventana
        ventana.setLayout(new BorderLayout()); //que no haya espacios
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }


    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
    }
// Se ejecuta el Thread

    private synchronized void iniciar() {
        enFuncionamiento = true;
        thread = new Thread(this, "Graficos");
        thread.start();
    }


    private synchronized void detener() {
        enFuncionamiento = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizar() {

        //Actualiza el estado del tecladowwwwwwwwwwww
        teclado.actualizar();
        if (teclado.arriba) {
            System.out.println("Arriba");
        }
        if (teclado.abajo) {
            System.out.println("abajo");
        }
        if (teclado.derecha) {
            System.out.println("Derecha");
        }
        if (teclado.izquierda) {
            System.out.println("Izquierda");
        }
        APS++;

    }

    public void mostrar() {
        FPS++;
    }

    @Override
    public void run() {
        final int NS_segundo = 1000000000;
        final byte APS_objetivo = 60;
        final double NS_Actualizacion = NS_segundo / APS_objetivo;
        long referenciaACTUALIZACION = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        double delta = 0;

        requestFocus();

        while (enFuncionamiento) {
            final long cicloBucle = System.nanoTime();
            tiempoTranscurrido = cicloBucle - referenciaACTUALIZACION;
            referenciaACTUALIZACION = cicloBucle;
            delta += tiempoTranscurrido / NS_Actualizacion;

            while (delta >= 1) {
                actualizar();
                delta--;
            }

            mostrar();
            if (System.nanoTime() - referenciaContador > NS_segundo) {
                ventana.setTitle(nombre + " || APS: " + APS + " ||fps " + FPS);
                APS = 0;
                FPS = 0;
                referenciaContador = System.nanoTime();
            }

        }
    }
}
