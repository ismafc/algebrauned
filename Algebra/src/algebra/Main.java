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
        BufferedReader bf = new BufferedReader (new InputStreamReader(System.in));
        double[][] d = new double[2][3];
        double[] r = new double[2];
        System.out.print("a11 = ");
        d[0][0] = new Double(bf.readLine()).doubleValue(); // a11
        System.out.print("a12 = ");
        d[0][1] = new Double(bf.readLine()).doubleValue(); // a12
        System.out.print("b1 = ");
        d[0][2] = new Double(bf.readLine()).doubleValue(); // b1
        System.out.print("a21 = ");
        d[1][0] = new Double(bf.readLine()).doubleValue(); // a21
        System.out.print("a22 = ");
        d[1][1] = new Double(bf.readLine()).doubleValue(); // a22
        System.out.print("b2 = ");
        d[1][2] = new Double(bf.readLine()).doubleValue(); // b2
        MatrixNxM matrix = new MatrixNxM(d);
        if (!matrix.solve2x2(r))
            System.out.println("No se pudo resolver el sistema " + matrix);
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
