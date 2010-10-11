/*
 * Vector3D.java
 *
 * Created on 27 de mayo de 2005, 23:21
 */

package lib;

/**
 * Stores a three dimensional vector
 * v1 . v2 = |v1| * |v2| * cos(v1, v2) 
 * |v1 ^ v2| = |v1| * |v2| * sin(v1, v2)
 * @author Isma
 */
public class Vector3D extends Object3D {
    
    /** Creates a new instance of Vector3D */
    public Vector3D() {
        super();
    }

    /** 
     * Creates a new instance of Vector3D initialized with defined parameters
     * @param nx X component of vector
     * @param ny Y component of vector
     * @param nz Z component of vector
     */
    public Vector3D(double nx, double ny, double nz) {
        super(nx, ny, nz);
    }

    /** 
     * Creates a new instance of Vector3D with a copy of defined input vector 'v'
     * @param v Vector to copy to
     */
    public Vector3D(Vector3D v) {
        super(v);
    }
    
    /** 
     * Creates a new instance of Vector3D with a copy of defined input point 'p'
     * @param p Point to copy to
     */
    public Vector3D(Point3D p) {
        super(p);
    }
    
    /** 
     * Creates a new instance of Vector3D with a copy of defined input three components object 'o'
     * @param o Three components object to copy to
     */
    public Vector3D(Object3D o) {
        super(o);
    }

    /** 
     * Creates a new instance of Vector3D with a copy of input array of doubles 'c'
     * @param c Array of doubles with at least three elements with components to copy to
     */
    public Vector3D(double c[]) {
        super(c);
    }
    
    /** 
     * Creates a new instance of Vector3D defined by an origin and an end point
     * @param p1 Origin point for vector
     * @param p2 End point for vector
     */
    public Vector3D(Point3D p1, Point3D p2) {
        super(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
    }
    
    /** 
     * Calculates dot product between this vector and input vector 'v'
     * @param v Vector to make dot product with
     * @return Double containing dot product
     */
    public double dot(Vector3D v) {
        return x * v.x + y * v.y + z * v.z;
    }
    
    /** 
     * Calculates cross product between this vector and input vector 'v'
     * @param v Vector to make cross product with
     * @return Vector containing cross product. Result value is orthogonal to this and 'v' vector
     */
    public Vector3D cross(Vector3D v) {
        return new Vector3D(y * v.z - z * v.y, 
                            z * v.x - x * v.z, 
                            x * v.y - y * v.x);
    }

    /** 
     * Transform the vector in another with same direction but with a specific length
     * @param length new length for vector
     */
    public void resize(double length) {
	normalize();
        mul(length);
    }

    /** 
     * Transform the vector in another with same direction but length of 1.0
     * @return True if transformation can be done and False if length of vector is 0.0
     */
    public boolean normalize() {
        if (isNull())
            return false;
        double length = length();
        x /= length;
        y /= length;
        z /= length;
        return true;
    }
    
    /** 
     * Returns if this vector is normalized or not (length = 1)
     * @return True if length is 1.0 and False if not
     */
    public boolean isNormalized() {
        return length() == 1.0;
    }

    /** 
     * Returns if this vector is nearly (plus/minus epsilon) normalized or not (length = 1)
     * @param epsilon is the margin to considere the vector normalized (usually a little value)
     * @return True if length is 1.0 (plus/minus epsilon) and False if not
     */
    public boolean isNormalized(double epsilon) {
        epsilon = (epsilon >= 0) ? epsilon : -epsilon;
        double l = length();
        return ((l - epsilon) <= 1.0) && ((l + epsilon) >= 1.0);
    }
    
    /** 
     * Returns angle in radiants between this vector and input vector 'v' (always positive value)
     * @param v is second vector to compare with
     * @return Double with angle between two vectors in radiants. If some vector have length of 0.0 then NAN is returned
     */
    public double angle(Vector3D v) {
        if (isNull() || v.isNull())
            return Double.NaN;
        double l2 = length() * v.length();
        return Math.asin(cross(v).length() / l2);
    }
      
    /** 
     * Constructs an orthogonal coordinate system.
     * This vector will be the first vector for new orthogonal coordinate system.
     * If This vector is null (0,0,0) then v1 and v2 will be invalidated to (NAN,NAN,NAN)
     * @param v1 is the second vector for new coordinate system
     * @param v2 is the third vector for new coordinate system
     */
    public void coordinateSystem(Vector3D v1, Vector3D v2) {
        if (isNull()) {
            v1.invalidate();
            v2.invalidate();
        }
        else {
            if (Math.abs(x) > Math.abs(y)) {
                double invLen = 1.0 / Math.sqrt(x * x + z * z);
                v1.set(-z * invLen, 0.0, x * invLen);
            }
            else {
                double invLen = 1.0 / Math.sqrt(y * y + z * z);
                v1.set(0.0, z * invLen, -y * invLen);
            }
            v2.set(cross(v1));
        }
    }

    /** 
     * Initializes this vector with another orthogonal to 'v1' and 'v2'
     * @param v1 First vector orthogonal to searched vector
     * @param v2 First vector orthogonal to searched vector
     * @return True if this vector can be calculated and False if not
     */
    public boolean normal(Vector3D v1, Vector3D v2) {
        set(v1.cross(v2));
        return normalize();
    }
    
    /** 
     * Returns a new vector with 'o1' - 'o2'
     * @param o1 First 3D object
     * @param o2 Second 3D object to substract to 'o1'
     * @return Vector with result of the substraction
     */
    static public Vector3D sub(Object3D o1, Object3D o2) {
        return new Vector3D(o1.x - o2.x, o1.y - o2.y, o1.z - o2.z);
    }
    
    /** 
     * Returns a new vector with 'o1' + 'o2'
     * @param o1 First 3D object
     * @param o2 Second 3D object to add to 'o1'
     * @return Vector with result of the addition
     */
    static public Vector3D add(Object3D o1, Object3D o2) {
        return new Vector3D(o1.x + o2.x, o1.y + o2.y, o1.z + o2.z);
    }

    /** 
     * Returns a new vector with 'o' / 'd'. If d = 0.0 returns a vector initialized to (NAN,NAN,NAN)
     * @param o Three components object that will be divided by double 'd'
     * @param d Value to divide to 'o'
     * @return Vector with result of the division
     */
    static public Vector3D div(Object3D o, double d) {
        if (d == 0)
            return new Vector3D(Double.NaN, Double.NaN, Double.NaN);
        return new Vector3D(o.x / d, o.y / d, o.z / d);
    }

    /** 
     * Returns a new vector with 'o' * 'd'
     * @param o Three components object that will be multiplied by double 'd'
     * @param d Value to multiply to 'o'
     * @return Vector with result of the multiplication
     */
    static public Vector3D mul(Object3D o, double d) {
        return new Vector3D(o.x * d, o.y * d, o.z * d);
    }
    
    /** 
     * Calculates dot product between 'v1' and 'v2'
     * @param v1 First vector to make dot product
     * @param v2 Second vector to make dot product
     * @return Double containing dot product
     */
    static public double dot(Vector3D v1, Vector3D v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    /** 
     * Calculates cross product between 'v1' and 'v2'
     * @param v1 First vector to make cross product
     * @param v2 Second vector to make corss product
     * @return Vector containing dot product
     */
    static public Vector3D cross(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.y * v2.z - v1.z * v2.y, 
                            v1.z * v2.x - v1.x * v2.z, 
                            v1.x * v2.y - v1.y * v2.x);
    }
    
    /** 
     * Calculates length ^ 2 of vector 'v'
     * @param v Vector to calculate its length ^ 2
     * @return Double containing v length ^ 2
     */
    static public double lengthSquared(Vector3D v) {
        return v.lengthSquared();
    }
    
    /** 
     * Returns angle in radiants between vectors 'v1' and 'v2' (always positive value)
     * @param v1 is first vector to compare with
     * @param v2 is second vector to compare with
     * @return Double with angle between two vectors in radiants. If some vector have length of 0.0 then NAN is returned
     */
    static public double angle(Vector3D v1, Vector3D v2) {
        if (v1.isNull() || v2.isNull())
            return Double.NaN;
        double l2 = v1.length() * v2.length();
        if (l2 == 0.0)
            return Double.NaN;
        return Math.asin(cross(v1, v2).length() / l2);
    }
    
    /** 
     * Constructs an orthogonal coordinate system.
     * vector 'v1' will be the first vector for new orthogonal coordinate system.
     * If vector 'v1' is null (0,0,0) then v2 and v3 will be invalidated to (NAN,NAN,NAN)
     * @param v1 is the first vector for new coordinate system
     * @param v2 is the second vector for new coordinate system
     * @param v3 is the third vector for new coordinate system
     */
    static public void coordinateSystem(Vector3D v1, Vector3D v2, Vector3D v3) {
        if (v1.isNull()) {
            v2.invalidate();
            v3.invalidate();
        }
        else {
            if (Math.abs(v1.x) > Math.abs(v1.y)) {
                double invLen = 1.0 / Math.sqrt(v1.x * v1.x + v1.z * v1.z);
                v2.set(-v1.z * invLen, 0.0, v1.x * invLen);
            }
            else {
                double invLen = 1.0 / Math.sqrt(v1.y * v1.y + v1.z * v1.z);
                v2.set(0.0, v1.z * invLen, -v1.y * invLen);
            }
            v3.set(Vector3D.cross(v1, v2));
        }
    }
}
