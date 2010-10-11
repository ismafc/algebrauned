/*
 * Box3D.java
 *
 * Created on 10 de agosto de 2005, 17:39
 */

package lib;

/**
 * Implements a 3D Box class
 * @author Isma
 */
public class Box3D {
    
    /** 
     * First corner of box (minimum values for x, y and z)
     */
    protected Point3D pMin;
    
    /** 
     * Second corner of box (maximum values for x, y and z)
     */
    protected Point3D pMax;
    
    /** 
     * Creates a new instance of Box3D 
     */
    public Box3D() {
        super();
        pMin = new Point3D(Double.NEGATIVE_INFINITY, 
                           Double.NEGATIVE_INFINITY, 
                           Double.NEGATIVE_INFINITY);
        pMax = new Point3D(Double.POSITIVE_INFINITY, 
                           Double.POSITIVE_INFINITY, 
                           Double.POSITIVE_INFINITY);
    }

    /** 
     * Creates a new instance of Box3D with a clone of 'b'
     * @param b Box to copy to
     */
    public Box3D(Box3D b) {
        super();
        if (b != null) {
            pMin = b.pMin;
            pMax = b.pMax;
        }
        else {
            pMin = new Point3D(Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY);
            pMax = new Point3D(Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY);            
        }
    }

    /** 
     * Creates a new instance of Box3D defined by two points
     * @param p1 First corner point
     * @param p2 Second corner point
     */
    public Box3D(Point3D p1, Point3D p2) {
        super();
        if (p1 == null || p2 == null) {
            pMin = new Point3D(Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY);
            pMax = new Point3D(Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY);            
        }
        else {
            pMin = new Point3D((p1.x < p2.x) ? p1.x : p2.x, 
                               (p1.y < p2.y) ? p1.y : p2.y,
                               (p1.z < p2.z) ? p1.z : p2.z);
            pMax = new Point3D((p1.x > p2.x) ? p1.x : p2.x, 
                               (p1.y > p2.y) ? p1.y : p2.y,
                               (p1.z > p2.z) ? p1.z : p2.z);
        }
    }

    /** 
     * Creates a new instance of Box3D defined by a point and a vector (diagonal)
     * @param p corner of box
     * @param v Vector of diagonal
     */
    public Box3D(Point3D p, Vector3D v) {
        super();
        if (p == null || v == null) {
            pMin = new Point3D(Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY, 
                               Double.NEGATIVE_INFINITY);
            pMax = new Point3D(Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY, 
                               Double.POSITIVE_INFINITY);            
        }
        else {
            Point3D p2 = Point3D.add(p, v);
            pMin = new Point3D((p.x < p2.x) ? p.x : p2.x, 
                               (p.y < p2.y) ? p.y : p2.y,
                               (p.z < p2.z) ? p.z : p2.z);
            pMax = new Point3D((p.x > p2.x) ? p.x : p2.x, 
                               (p.y > p2.y) ? p.y : p2.y,
                               (p.z > p2.z) ? p.z : p2.z);
        }
    }
    
    /** 
     * Initializes this box with point 'p'
     * @param p Point that initializes the box
     */
    public void set(Point3D p) {
        pMin = new Point3D(p);
        pMax = new Point3D(p);
    }
    
    /** 
     * Makes this box greater to contain this box and provided object 'o' (ussually a point)
     * @param o Object to contain
     */
    public void union(Object3D o) {
        pMin.x = (o.x < pMin.x) ? o.x : pMin.x;
        pMin.y = (o.y < pMin.y) ? o.y : pMin.y;
        pMin.z = (o.z < pMin.z) ? o.z : pMin.z;
        pMax.x = (o.x > pMax.x) ? o.x : pMax.x;
        pMax.y = (o.y > pMax.y) ? o.y : pMax.y;
        pMax.z = (o.z > pMax.z) ? o.z : pMax.z;
    }

    /** 
     * Makes this box greater to contain this box and provided box 'b'
     * @param b Box to contain
     */
    public void union(Box3D b) {
        pMin.x = (b.pMin.x < pMin.x) ? b.pMin.x : pMin.x;
        pMin.y = (b.pMin.y < pMin.y) ? b.pMin.y : pMin.y;
        pMin.z = (b.pMin.x < pMin.z) ? b.pMin.z : pMin.z;
        pMax.x = (b.pMax.x > pMax.x) ? b.pMax.x : pMax.x;
        pMax.y = (b.pMax.y > pMax.y) ? b.pMax.y : pMax.y;
        pMax.z = (b.pMax.x > pMax.z) ? b.pMax.z : pMax.z;
    }
    
    /** 
     * Checks if this box and 'b' box overlaps
     * @param b Box compare with
     * @return True if 'b' and this box overlaps and False if not
     */
    public boolean overlaps(Box3D b) {
        boolean x = (pMax.x >= b.pMin.x && pMin.x <= b.pMax.x);
        boolean y = (pMax.y >= b.pMin.y && pMin.y <= b.pMax.y);
        boolean z = (pMax.z >= b.pMin.z && pMin.z <= b.pMax.z);
        return x && y && z;
    }
    
    /** 
     * Checks if provided object 'o' (ussually a point) is inside this box
     * @param o Object to check (ussually a point)
     * @return True if 'o' is inside this box and False if not
     */
    public boolean inside(Object3D o) {
        return (o.x >= pMin.x && o.x <= pMax.x && 
                o.y >= pMin.y && o.y <= pMax.y &&
                o.z >= pMin.z && o.z <= pMax.z);
    }
    
    /** 
     * Expand this box 'delta' value in all directions
     * @param delta value to expand
     */
    public void expand(double delta) {
        pMin.sub(delta, delta, delta);
        pMin.add(delta, delta, delta);
    }
    
    /** 
     * Calculates the box volume
     * @return double containing the box volume
     */
    public double volume() {
        Vector3D v = Vector3D.sub(pMax, pMin);
        return v.x * v.y * v.z;
    }
    
    /** 
     * Returns the minimum box containing provided box 'b' and object 'o' (ussually a point)
     * @param b Box to contain
     * @param o Object to contain
     * @return The minimum box containing 'b' and 'o'
     */
    static public Box3D union(Box3D b, Object3D o) {
        Point3D pMin = new Point3D((o.x < b.pMin.x) ? o.x : b.pMin.x,
                                   (o.y < b.pMin.y) ? o.y : b.pMin.y,
                                   (o.x < b.pMin.z) ? o.z : b.pMin.z);
        Point3D pMax = new Point3D((o.x > b.pMax.x) ? o.x : b.pMax.x,
                                   (o.y > b.pMax.y) ? o.y : b.pMax.y,
                                   (o.x > b.pMax.z) ? o.z : b.pMax.z);
        return new Box3D(pMin, pMax);
    }    
    
    /** 
     * Returns the minimum box containing the two provided boxes 'b1' and 'b2'
     * @param b1 First box to contain
     * @param b2 Second box to contain
     * @return The minimum box containing 'b1' and 'b2'
     */
    static public Box3D union(Box3D b1, Box3D b2) {
        Point3D pMin = new Point3D((b1.pMin.x < b2.pMin.x) ? b1.pMin.x : b2.pMin.x,
                                   (b1.pMin.y < b2.pMin.y) ? b1.pMin.y : b2.pMin.y,
                                   (b1.pMin.x < b2.pMin.z) ? b1.pMin.z : b2.pMin.z);
        Point3D pMax = new Point3D((b1.pMax.x > b2.pMax.x) ? b1.pMax.x : b2.pMax.x,
                                   (b1.pMax.y > b2.pMax.y) ? b1.pMax.y : b2.pMax.y,
                                   (b1.pMax.x > b2.pMax.z) ? b1.pMax.z : b2.pMax.z);
        return new Box3D(pMin, pMax);
    }
    
    /** 
     * Checks if provided boxes overlaps
     * @param b1 First box to check
     * @param b2 Second box to check
     * @return True if 'b1' and 'b2' overlaps and False if not
     */
    static public boolean overlaps(Box3D b1, Box3D b2) {
        return b1.overlaps(b2);
    }
    
    /** 
     * Checks if provided object 'o' (ussually a point) is inside box 'b'
     * @param b Box to check
     * @param o Object to check (ussually a point)
     * @return True if 'o' is inside 'b' and False if not
     */
    static public boolean inside(Box3D b, Object3D o) {
        return b.inside(o);
    }
    
    /** 
     * Compares this box with box 'b'
     * @param b Box to compare with
     * @return True if this both and 'b' are equals and False if not
     */
    public boolean equals(Box3D b) {
        if (b == null)
            return false;
        return pMin.equals(b.pMin) && pMax.equals(b.pMax);
    }
    
    /** 
     * Returns a String representation for this object
     * @return String representing this object
     */
    public String toString() {
        return "[" + pMin + ", " + pMax + "]";
    }    
    
}
