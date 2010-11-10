/*
 * MatrixMxN.java
 *
 * Created on 13 de agosto de 2005, 14:05
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lib;

/**
 * Implements a M x N double values matrix (M rows and N columns)
 * @author Isma
 */
public class MatrixMxN {

    /** 
     * Contains number of rows for this matrix (by default 4)
     */
    private int rows = 4;
    
    /** 
     * Contains number of columns for this matrix (by default 4)
     */
    private int columns = 4;
    
    /** 
     * Contains The inverse matrix for this matrix.
     * This variable is calculated in 'inverse' function and is erased when
     * the matrix changes in some way.
     */
    private MatrixMxN inverseMatrix = null;
    
    /** 
     * Contains the doubles that allows to this matrix (row, column)
     * stored in a M x N array of doubles
     */
    protected double[][] values = new double[rows][columns];
    
    /**
     * 
     * Creates a new instance of MatrixMxN
     * It's initialized to identity 4 x 4 matrix
     */
    public MatrixMxN() {
        super();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] = (i == j) ? 1.0 : 0.0;
    }
    
    /**
     * 
     * Creates a new instance of MatrixMxN initialized with parameter
     * @param nvalues array of M x N double used to initialized matrix
     */
    public MatrixMxN(double[][] nvalues) {
        super();
        if (nvalues.length > 0) {
            if (nvalues[0].length > 0) {
                rows = nvalues.length;
                columns = nvalues[0].length;
                values = new double[rows][columns];
                for (int i = 0; i < rows; i++)
                    System.arraycopy(nvalues[i], 0, values[i], 0, columns);
            }
        }
    }
    
    /**
     * 
     * Creates a new instance of MatrixMxN initialized with parameter
     * @param m the new matrix used to initialize this
     */
    public MatrixMxN(MatrixMxN m) {
        super();
        rows = m.rows;
        columns = m.columns;
        values = new double[rows][columns];
        for (int i = 0; i < rows; i++)
            System.arraycopy(m.values[i], 0, values[i], 0, columns);
    }

    /**
     * 
     * Creates a new instance of MatrixMxN initialized with parameters
     * Matrix is initialized to identity
     * @param nrows Number of rows of new matrix
     * @param ncolumns Number of columns of new matrix
     */
    public MatrixMxN(int nrows, int ncolumns) {
        super();
        rows = (nrows <= 0) ? rows : nrows;
        columns = (ncolumns <= 0) ? columns : ncolumns;
        values = new double[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] = (i == j) ? 1.0 : 0.0;
    }
    
    /** 
     * Checks if 'matrix' is equal to this matrix
     * @param m The matrix to compare with
     * @return True if both matrix are equals and False if not
     */
    public boolean equals(MatrixMxN m) {
        if (rows != m.rows || columns != m.columns)
            return false;
        for (int i = 0; i < m.rows; i++)
            for (int j = 0; j < m.columns; j++)
                if (values[i][j] != m.values[i][j])
                    return false;
        return true;
    }
    
    /** 
     * Transpose this matrix
     */
    public void transpose() {
        double[][] v = new double [columns][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                v[j][i] = values[i][j];
        values = v;
        inverseMatrix = null;
    }
    
    /** 
     * Calculates the inverse of this matrix if this is not calculated and it's possible.
     * If it's calculated then return it directly
     * @return Returns a N x N matrix containing the inverse of this matrix
     */
    public MatrixMxN getInverse() {
        if (inverseMatrix != null)
            return inverseMatrix;
        double d = determinant(values);
        if (d == 0.0 || d == Double.NaN)
            return null;
        inverseMatrix = new MatrixMxN(rows, columns);
        // Transposed adjunts matrix divided by determinant
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                inverseMatrix.set(j, i, cofactor(values, i, j) / d);
        return inverseMatrix;
    }

    /** 
     * Inverts this matrix.
     * @return True if matrix in invertible and False if not
     */
    public boolean invert() {
        if (inverseMatrix == null)
            inverseMatrix = getInverse();
        if (inverseMatrix == null)
            return false;
        MatrixMxN m = new MatrixMxN(this);
        set(inverseMatrix);
        inverseMatrix = m;
        return true;
    }
    
    /** 
     * Initializes the value located in ('i', 'j') with value 'v'
     * @param i Row of target value
     * @param j Column of target value
     * @param v New value in (i, j)
     */
    public void set(int i, int j, double v) {
        if (i >= 0 && i < rows && j >= 0 && j < columns) {
            values[i][j] = v;
            inverseMatrix = null;
        }
    }

    /** 
     * Copies matrix 'm' in this matrix (clonation)
     * @param m Matrix to be copied in
     */
    public void set(MatrixMxN m) {
        rows = m.rows;
        columns = m.columns;
        values = m.values;
        inverseMatrix = m.inverseMatrix;
    }

    /**
     * Intercambia dos filas de la matriz. Modifica la matriz.
     * @param f1 índice de la fila a intercambiar
     * @param f2 índice de la fila a intercambiar
     * @return Falso si los índices de las filas que se desean intercambiar son incorrectos. Cierto si es posible.
     */
    public boolean swap(int f1, int f2) {
        if (f1 < 0 || f2 < 0 || f1 >= rows || f2 >= rows || f1 == f2)
            return false;
        double v;
        for (int c = 0; c < columns; c++) {
            v = values[f1][c];
            values[f1][c] = values[f2][c];
            values[f2][c] = v;
        }
        return true;
    }

    /**
     * Busca el índice de la última fila con el valor de la columna 'c' diferente de 0
     * Se empieza a partir de la última fila y se acaba en la fila 'lf'.
     * @param lf índice de la fila hasta la que debemos buscar
     * @param c índice de la columna en la que debemos buscar
     * @return Entero con el índice de la fila encontrada (si no se encuentra o no es posible buscarlo se devuelve 'lf')
     */
    private int findLastRowDiff0(int lf, int c) {
        if (lf < 0 || lf >= rows || c < 0 || c >= columns)
            return lf;
        int f = rows - 1;
        while (f >= lf) {
            if (values[f][c] != 0.0)
                break;
            f--;
        }
        return f;
    }

    /**
     * Reordena las filas a partir de la fila 'fila' en función de los elementos
     * de la columna 'columna'. Pone todas las filas con el valor de esa columna a 0 al final.
     * Se supone que los índices 'fila' y 'columna' están dentro del rango de la matriz
     * @param fila índice de la fila a partir de la que debemos buscar ceros y ponerlos al final
     * @param columna en la que debemos buscar los ceros.
     * @return Falso si todos los valores a partir de la fila 'fila' en la columna 'columna' son 0. Cierto si hay algun valor diferente de cero.
     */
    private boolean reordenacionCeros(int fila, int columna) {
        boolean todoCeros = true;
        while (fila < rows) {
            if (values[fila][columna] != 0.0)
                todoCeros = false;
            else {
                int nfila = findLastRowDiff0(fila, columna);
                if (nfila > fila) {
                    // Hemos encontrado un valor diferente de 0 en la
                    // fila 'nfila' y la columna 'columna'.
                    swap(fila, nfila);
                    todoCeros = false;
                }
                else
                    // Sólo quedan valores a 0 el las filas inferiores a 'fila'
                    // en la columna 'columna'. Hemos terminado.
                    break;
            }
            fila++;
        }
        return todoCeros;
    }

    /**
     * Transforma el elemento (fila, columna) en pivote (1)
     * Se supone que los índices 'fila' y 'columna' están dentro del rango de la matriz
     * @param fila índice de la fila a partir de la que debemos buscar ceros y ponerlos al final
     * @param columna en la que debemos buscar los ceros.
     * @return Falso si valor de esa posición es cero. Cierto en caso contrario.
     */
    private boolean pivote(int fila, int columna) {
        double divisor = values[fila][columna];
        if (divisor == 0.0)
            return false;
        values[fila][columna] = 1.0;
        for (int c = columna + 1; c < columns; c++)
            values[fila][c] /= divisor;
        return true;
    }

    /**
     * Se supone que el índice índices 'columna' está dentro del rango de la matriz
     * Esta función hace ceros los elementos de la columna dada a partir de la fila dada
     * @param fila índice de la fila a partir de la que debemos hacer ceros ceros (se hacen a partir del pivote de la fila anterior)
     * @param columna en la que debemos hacer los ceros.
     * @return Falso si valor de la fila anterior en la columna dada no es pivote o el índice 'fila' está fuera de rango.
     */
    private boolean hacerCeros(int fila, int columna) {
        if (fila <= 0 || fila >= rows)
            return false;
        // No es pivote
        if (values[fila - 1][columna] != 1.0)
            return false;
        // Transformamos todas las filas a partir de 'fila' hacia abajo
        for (int f = fila; f < rows; f++) {
            double factor = -values[f][columna];
            values[f][columna] = 0.0;
            // Transformamos toda la fila 'f' a partir de la columna 'columna + 1'
            for (int c = columna + 1; c < columns; c++) {
                values[f][c] = values[fila - 1][c] * factor + values[f][c];
                // Solventar error de precisión cuando debería dar cero y no da cero
                values[f][c] = (Math.abs(values[f][c]) < Matlib.EPSILON) ? 0.0 : values[f][c];
            }
        }
        return true;
    }

    /**
     * Diagonaliza la matriz por debajo aplicando el método de eliminación gaussiana.
     * Modifica la matriz.
     * @return Cierto siempre
     */
    public boolean eliminacionGaussiana() {
        int fila = 0;
        int columna = 0;
        while (fila < rows && columna < columns) {
            if (reordenacionCeros(fila, columna)) {
                columna++;
                continue;
            }
            pivote(fila, columna);
            hacerCeros(fila + 1, columna);
            fila++;
            columna++;
        }
        return true;
    }

    /**
     * Busca el pivote (1) dentro de la fila especificada y antes de la columna especificada
     * @param fila índice de la fila en la que debemos buscar el pivote (1)
     * @param columna máxima en la que debe estar el pivote (1).
     * @return el índice de la columna en la que se encuentra el pivote (1) dentro de la fila. Si todos los valores son 0 devuelve -1, si el primer valor no es un pivote devuelve Integer.MAX_VALUE y si los parametros de entrada no son correctos devuelve Integer.MIN_VALUE
     */
    private int buscarPivote(int fila, int columna) {
        if (fila < 0 || fila >= rows || columna < 0 || columna >= columns)
            return Integer.MIN_VALUE;
        for (int c = 0; c < columna; c++) {
            if (values[fila][c] == 1.0)
                return c;
            else if(values[fila][c] != 0.0)
                return Integer.MAX_VALUE;
        }
        return -1;
    }

    private boolean hacerCerosInverso(int fila, int columna) {
        if (fila < 0 || fila >= rows - 1 || columna < 0 || columna >= columns)
            return false;
        // No es pivote
        if (values[fila + 1][columna] != 1.0)
            return false;
        // Transformamos todas las filas desde 'fila' hacia arriba
        for (int f = fila; f >= 0; f--) {
            double factor = -values[f][columna];
            values[f][columna] = 0.0;
            // Transformamos toda la fila 'f' a partir de la columna 'columna + 1'
            for (int c = columna + 1; c < columns; c++) {
                values[f][c] = values[fila + 1][c] * factor + values[f][c];
                // Solventar error de precisión cuando debería dar cero y no da cero
                values[f][c] = (Math.abs(values[f][c]) < Matlib.EPSILON) ? 0.0 : values[f][c];
            }
        }
        return true;
    }

    /**
     * Pasa la matriz a su forma escalonada reducida por filas y pivote por el método de Gauss-Jordan
     * @return Cierto si puedo realizarse, Falso si no pude realizarse la operación.
     */
    public boolean gaussJordan() {
        if (!eliminacionGaussiana())
            return false;
        int fila = rows - 1;
        int columna = columns - 1;
        while (fila > 0 && columna >= 0) {
            int ncolumna = buscarPivote(fila, columna);
            if (ncolumna == Integer.MAX_VALUE)
                return false;
            else if (ncolumna < 0)
                fila--;
            else {
                if (!hacerCerosInverso(fila - 1, ncolumna))
                    return false;
                fila--;
                columna = ncolumna;
            }
        }
        return true;
    }

    /**
     * Resuelve el sistema de dos ecuaciones con dos incógnitas y devuelve el
     * resultado en el vector 'd' de dos posiciones.
     * Si no tiene solución se devuelve en 'd' (Double.NaN, Double.NaN)
     * Si tiene infinitas soluciones se devuelve en 'd' (Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
     * Si tiene una solución se devuelve en 'd' (x, y)
     * @param d vector donde se devuelve el resultado
     * @return Cierto si la matriz y el vector de resultados tienen las dimensiones correctas (2x3 y 2 respectivamente)
     */
    public boolean solve2x2(double[] d) {
        // Primero comprobamos que podemos analizar el sistema y almacenar los resultados
        if (d.length < 2)
            return false;
        if (rows != 2 || columns != 3)
            return false;
        double b1 = values[0][2];
        double b2 = values[1][2];
        double a11 = values[0][0];
        double a21 = values[1][0];
        double a12 = values[0][1];
        double a22 = values[1][1];
        double a = a11 * a22 - a21 * a12;
        if (a != 0.0) {
            // Una única solución
            d[0] = (b1 * a22 - b2 * a12) / a;
            if (a12 != 0.0)
                d[1] = (b1 - a11 * d[0]) / a12;
            else
                d[1] = (b2 - a21 * d[0]) / a22;
        }
        else {
            // O infinitas o ninguna
            if (b2 * a11 == b1 * a21 && b2 * a12 == b1 * a22) {
                // Infinitas soluciones (la misma recta)
                d[0] = Double.POSITIVE_INFINITY;
                d[1] = Double.POSITIVE_INFINITY;
            }
            else {
                // No tiene solución (rectas paralelas)
                d[0] = Double.NaN;
                d[1] = Double.NaN;
            }
        }
        return true;
    }
    
    /** 
     * Multiplies this matrix by value 'v'
     * @param v Double with value to multiply to this matrix
     */
    public void mul(double v) {
        if (v != 1.0) {
            for (int i = 0; i < values.length; i++)
                for (int j = 0; j < values[i].length; j++)
                    values[i][j] *= v;
            inverseMatrix = null;
        }
    }

    /**
     * Multiplies 'm1' by escalar 'v' and returns a matrix with the result of this operation
     * @param m1 Matrix to be multiplied
     * @param v Scalar to multiply to matrix
     * @return A matrix with m1.v
     */
    static public MatrixMxN mul(MatrixMxN m1, double v) {
        MatrixMxN m = new MatrixMxN(m1);
        m.mul(v);
        return m;
    }

    /** 
     * Multiplies this matrix by 'm' matrix (composition)
     * @param m Matrix to compose to this matrix
     * @return True if multiplication is possible and False if not
     */
    public boolean mul(MatrixMxN m) {
        if (columns != m.rows || columns == 0 || rows == 0 || m.columns == 0 || m.rows == 0)
            return false;
        double[][] v = new double[rows][m.columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                v[i][j] = 0.0;
                for (int k = 0; k < columns; k++)
                    v[i][j] += values[i][k] * m.values[k][j];
            }
        }
        values = v;
        inverseMatrix = null;
        return true;
    }

    /** 
     * Multiplies 'm1' by 'm2' and returns a matrix with the result of this operation,
     * that is, the compositition of both matrix
     * @param m1 Frist matrix to composite
     * @param m2 Second matrix to composite
     * @return A matrix with m1.m2 or null if multiplication is not possible
     */
    static public MatrixMxN mul(MatrixMxN m1, MatrixMxN m2) {
        MatrixMxN m = new MatrixMxN(m1);
        if (m.mul(m2))
            return m;
        else
            return null;
    }
    
    /** 
     * Makes this matrix null (all values will be 0)
     */
    public void toNull() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] = 0.0;
        inverseMatrix = null;
    }
    
    /** 
     * Makes this matrix the identity matrix (all values will be 0 unless diagonal values to 1)
     * @return True if this matrix is squared and False if not
     */
    public boolean toIdentity() {
        if (rows != columns)
            return false;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] = (i == j) ? 1.0 : 0.0;
        inverseMatrix = null;
        return true;
    }

    /** 
     * Makes this matrix a translation defined by 'o'
     * @param o 3D Object defining the translation in each axe
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toTranslation(Object3D o) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        values[0][3] = o.x;
        values[1][3] = o.y;
        values[2][3] = o.z;
        inverseMatrix = null;
        return true;
    }

    /** 
     * Makes this matrix a scaling defined by 'o'
     * @param o 3D Object defining the scaling in each axe
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toScale(Object3D o) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        values[0][0] = o.x;
        values[1][1] = o.y;
        values[2][2] = o.z;
        inverseMatrix = null;
        return true;
    }
    
    /** 
     * Makes this matrix a rotation in X axis 'r' radiants
     * @param r Angle in radiants to rotate
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toRotationX(double r) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        values[1][1] = Math.cos(r);
        values[1][2] = -Math.sin(r);
        values[2][1] = Math.sin(r);
        values[2][2] = Math.cos(r);
        inverseMatrix = null;
        return true;
    }

    /** 
     * Makes this matrix a rotation in Y axis 'r' radiants
     * @param r Angle in radiants to rotate
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toRotationY(double r) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        values[0][0] = Math.cos(r);
        values[0][2] = Math.sin(r);
        values[2][0] = -Math.sin(r);
        values[2][2] = Math.cos(r);
        inverseMatrix = null;
        return true;
    }

    /** 
     * Makes this matrix a rotation in Z axis 'r' radiants
     * @param r Angle in radiants to rotate
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toRotationZ(double r) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        values[0][0] = Math.cos(r);
        values[0][1] = -Math.sin(r);
        values[1][0] = Math.sin(r);
        values[1][1] = Math.cos(r);
        inverseMatrix = null;
        return true;
    }
    
    /** 
     * Makes this matrix a rotation by 'r' radiants arround 'o' axis (ussually a vector)
     * If axis is a null vector this matrix is transformed to identity
     * @param r Angle in radiants to rotate
     * @param o Three dimensional object representing the axis (vector)
     * @return True if this is a 4 x 4 matrix and False if not
     */
    public boolean toRotation(double r, Object3D o) {
        if (rows != 4 || columns != 4)
            return false;
        toIdentity();
        Vector3D a = new Vector3D(o);
        a.normalize();

        double s = Math.sin(r);
        double c = Math.cos(r);

        values[0][0] = a.x * a.x + (1.0 - a.x * a.x) * c;
        values[0][1] = a.x * a.y * (1.0 - c) - a.z * s;
        values[0][2] = a.x * a.z * (1.0 - c) + a.y * s;

        values[1][0] = a.x * a.y * (1.0 - c) + a.z * s;
        values[1][1] = a.y * a.y + (1.0 - a.y * a.y) * c;
        values[1][2] = a.y * a.z * (1.0 - c) - a.x * s;

        values[2][0] = a.x * a.z * (1.0 - c) - a.y * s;
        values[2][1] = a.y * a.z * (1.0 - c) + a.x * s;
        values[2][2] = a.z * a.z + (1.0 - a.z * a.z) * c;

        //
        // Is the same that:
        //
        // double beta = Math.atan2(o.y, o.z);
        // double gamma = Math.atan2(o.x, Math.sqrt(o.y * o.y + o.z * o.z));
        
        // toRotationX(-beta);
        // MatrixMxN m = new MatrixMxN();
        // m.toRotationY(gamma);
        // mul(m);
        // m.toRotationZ(r);
        // mul(m);
        // m.toRotationY(-gamma);
        // mul(m);
        // m.toRotationX(beta);
        // mul(m);
        //
        
        inverseMatrix = null;
        
        return true;
    }    
    
    /** 
     * Makes this matrix a look-at transformation matrix.
     * Gives a transformation between camera space and world space.
     * All parameters are in world coordinates.
     * @param pos Camera position
     * @param look Position being looked at from the camera
     * @param up Orients camera along viewing direction
     * @return True if construction is possible and False if not
     */
    public boolean lookAt(Point3D pos, Point3D look, Vector3D up) {
        if (rows != 4 || columns != 4)
            return false;
        Vector3D upNormalized = new Vector3D(up);
        if (!upNormalized.normalize())
            return false;
        
        values[0][3] = pos.x;
        values[1][3] = pos.y;
        values[2][3] = pos.z;
        values[3][3] = 1;
        
        Vector3D dir = new Vector3D(pos, look);
        Vector3D right = Vector3D.cross(dir, upNormalized);
        Vector3D newUp = Vector3D.cross(right, dir);
        
        values[0][0] = right.x;
        values[1][0] = right.y;
        values[2][0] = right.z;
        values[3][0] = 0;
        values[0][1] = newUp.x;
        values[1][1] = newUp.y;
        values[2][1] = newUp.z;
        values[3][1] = 0;
        values[0][2] = dir.x;
        values[1][2] = dir.y;
        values[2][2] = dir.z;
        values[3][2] = 0;
        
        inverseMatrix = null;
        
        return true;
    }
    
    /** 
     * Transforms the 3D object 'o' applying this matrix transformation
     * @param o Three dimensional object to be transformed
     * @return True if transformation is possible and False if not
     */
    public boolean transform(Object3D o) {
        if (rows != 4 || columns != 4)
            return false;
        double[] col = { o.x, o.y, o.z, 0.0};
        double[] result = {0.0, 0.0, 0.0, 0.0};
        if (o instanceof Point3D) {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                   result[i] += values[i][j] * col[j];
            if (result[3] != 1.0 && result[3] != 0.0) {
                result[0] /= result[3];
                result[1] /= result[3];
                result[2] /= result[3];
            }
        }
        else if (o instanceof Normal3D) {
            MatrixMxN m = getInverse();
            result[0] = m.values[0][0] * o.x + m.values[1][0] * o.y + m.values[2][0] * o.z;
            result[1] = m.values[0][1] * o.x + m.values[1][1] * o.y + m.values[2][1] * o.z;
            result[2] = m.values[0][2] * o.x + m.values[1][2] * o.y + m.values[2][2] * o.z;
        }
        else {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                   result[i] += values[i][j] * col[j];            
        }
        o.x = result[0];
        o.y = result[1];
        o.z = result[2];
        return true;
    }

    /** 
     * Transforms the 3D ray 'r' applying this matrix transformation
     * @param r Three dimensional ray to be transformed
     * @return True if transformation is possible and False if not
     */
    public boolean transform(Ray3D r) {
        if (!transform(r.o))
            return false;
        if (!transform(r.d))
            return false;
        return false;
    }

    /** 
     * Transforms the 3D box 'b' applying this matrix transformation
     * @param b Three dimensional box to be transformed
     */        
    public void transform(Box3D b) {
        Point3D o = b.pMin;
        Vector3D x = new Vector3D(o, new Point3D(b.pMax.x, o.y, o.z));
        Vector3D y = new Vector3D(o, new Point3D(o.x, b.pMax.y, o.z));
        Vector3D z = new Vector3D(o, new Point3D(o.x, o.y, b.pMax.z));
        transform(o);
        transform(x);
        transform(y);
        transform(z);
        b.set(o);
        b.union(Point3D.add(o, x));
        b.union(Point3D.add(o, y));
        b.union(Point3D.add(o, z));
        b.union(Point3D.add(Point3D.add(o, z),  x));
        b.union(Point3D.add(Point3D.add(o, z),  y));
        b.union(Point3D.add(Point3D.add(Point3D.add(o, x),  y), z));
    }
    
    /** 
     * This function tells us if this matrix transformation changes handedness
     * @return True if this matrix swaps handedness and False if not
     */
    public boolean swapsHandedness() {
        return (cofactor(values, 3, 3) < 0.0);
    }

    /** 
     * Divides this matrix by 'v' value
     * @param v Double with value to divide to this matrix
     */
    public void div(double v) {
        if (v != 0 && v != 1.0) {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < columns; j++)
                    values[i][j] /= v;
            inverseMatrix = null;
        }
    }
    
    /** 
     * Adds the matrix 'm' to this matrix
     * @param m Matrix to add to this matrix
     * @return True if addition is possible and False if not
     */
    public boolean add(MatrixMxN m) {
        if (rows != m.rows || columns != m.columns)
            return false;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] += m.values[i][j];
        inverseMatrix = null;
        return true;
    }

    /**
     * Adds the matrix 'm1' to matrix 'm2' and returns a new matrix with result
     * if it is posible
     * @param m1 First matrix to add
     * @param m2 Second matrix to add
     * @return Matrix if addition is possible and null if not
     */
    static public MatrixMxN add(MatrixMxN m1, MatrixMxN m2) {
        MatrixMxN m = new MatrixMxN(m1);
        if (m.add(m2))
            return m;
        else
            return null;
    }

    /** 
     * Substracts the matrix 'm' to this matrix
     * @param m Matrix to substract to this matrix
     * @return True if substraction is possible and False if not
     */
    public boolean sub(MatrixMxN m) {
        if (rows != m.rows || columns != m.columns)
            return false;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                values[i][j] -= m.values[i][j];
        inverseMatrix = null;
        return true;
    }
    
    /** 
     * Calculates the determinant for this 4 x 4 matrix
     * @return Double with determinant for this matrix
     */
    public double determinant() {
        return determinant(values);
    }
    
    /** 
     * Calculates the determinant for a N x N matrix stored in 'v'.
     * If v is not squared or null then returns NaN
     * @param v Two dimensional array of doubles with matrix stored by rows
     * @return Double with determinant for 'v' matrix (or NaN if is not possible to calculate it)
     */
    static public double determinant(double v[][]) {
        // If number of rows is 0 the returns NaN
        if (v.length == 0)
            return Double.NaN;
        // If 'v' is not and squared matrix returns NaN
        if (v.length != v[0].length)
            return Double.NaN;
        if (v.length == 1)
            return v[0][0];
        else if (v.length == 2)
            return v[0][0] * v[1][1] - v[0][1] * v[1][0];
        else {
            double d = 0.0;
            for (int i = 0; i < v.length; i++)
                d += v[i][0] * cofactor(v, i, 0);
            return d;
        }
    }
    
    /** 
     * Calculates the adjunt value for (i, j) position in a N x N matrix stored in 'v'.
     * @param v Two dimensional array of doubles with matrix stored by rows. It must be a squared matrix.
     * @param i Row of 'v' to calculate the cofactor
     * @param j Column of 'v' to calculate the cofactor
     * @return Double with cofactor for (i, j) position of 'v' matrix, that is the determinant of minor of 'v' matrix for (i, j) position with sign determined by (-1)^(i + j)
     */
    static private double cofactor(double v[][], int i, int j) {
        double[][] subv = new double[v.length - 1][v.length - 1];
        for (int ni = 0; ni < v.length; ni++) {
            for (int nj = 0; nj < v[ni].length; nj++) {
                if (ni != i && nj != j)
                    subv[(ni < i) ? ni : ni - 1][(nj < j) ? nj : nj - 1] = v[ni][nj];
            }
        }
        return ((((i + j) % 2) == 0) ? 1.0 : -1.0) * determinant(subv);
    }
    
    /** 
     * Returns a String representation for this object
     * @return String representing this object
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < rows; i++) {
            str += "(";
            for (int j = 0; j < columns; j++)
                str += values[i][j] + ((j < columns - 1) ? ", " : "");
            str += ")" + ((i < rows - 1) ? " - " : "");
        }
        return str;
    }
}
