package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class LineCounter implements Runnable {

    private String name;
    private AtomicInteger totalLineas;
    private AtomicInteger totalCaracteres;

    public LineCounter(String name, AtomicInteger totalLineas, AtomicInteger totalCaracteres) {
        this.name = name;
        this.totalLineas = totalLineas;
        this.totalCaracteres = totalCaracteres;
    }

    @Override
    public void run() {
        int contadorLineas = 0;
        int contadorCaracteres = 0;

        try (BufferedReader in = new BufferedReader(new FileReader(name))) {
            String linea;
            long t1 = System.currentTimeMillis();

            while ((linea = in.readLine()) != null) {
                contadorLineas++;
                contadorCaracteres += linea.length();
            }

            System.out.printf("%s: %-50s %,7d %,7d (%d ms)%n",
                    Thread.currentThread().getName(), name,
                    contadorLineas, contadorCaracteres, System.currentTimeMillis() - t1);


            totalLineas.addAndGet(contadorLineas);
            totalCaracteres.addAndGet(contadorCaracteres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
