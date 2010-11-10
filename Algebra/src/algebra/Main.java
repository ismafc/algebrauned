/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algebra;

import java.io.*;
import lib.*;

/**
 *
 * @author Isma
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Introducimos la matriz
        BufferedReader bf = new BufferedReader (new InputStreamReader(System.in));
        System.out.print("filas = ");
        int filas = new Integer(bf.readLine()).intValue();
        System.out.print("columnas = ");
        int columnas = new Integer(bf.readLine()).intValue();
        double[][] d = new double[filas][columnas];
        double[] r = new double[columnas - 1];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                System.out.print("M[" + f + "][" + c + "] = ");
                d[f][c] = new Double(bf.readLine()).doubleValue();
            }
        }
        MatrixMxN matrix = new MatrixMxN(d);
        if (!matrix.gaussJordan())
            System.out.println("Hubo problemas!!!!");
        System.out.println(matrix.toString());
        if (!matrix.solve2x2(r))
            System.out.println("No se pudo resolver el sistema (no es 2x3)" + matrix);
        else {
            if (Double.isNaN(r[0]) && Double.isNaN(r[1]))
                System.out.println("El sistema no tiene solución");
            else if (Double.isInfinite(r[0]) && Double.isInfinite(r[1]))
                System.out.println("El sistema tienen infinitas soluciones");
            else
                System.out.println("La solución es (" +  r[0] + ", " + r[1] + ")");
        }
    }

}
