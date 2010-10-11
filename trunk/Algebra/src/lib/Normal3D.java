/*
 * Normal3D.java
 *
 * Created on 15 de septiembre de 2005, 20:38
 */

package lib;

/**
 * Stores a three dimensional normal
 * @author Isma
 */
public class Normal3D extends Vector3D {
    
    /** Creates a new instance of Normal3D */
    public Normal3D() {
        super();
    }

    /** 
     * Creates a new instance of Normal3D with a copy of defined input normal 'n'
     * @param n Normal to copy to
     */
    public Normal3D(Normal3D n) {
        super(n);
    }
    
    /** 
     * Creates a new instance of Normal3D with a copy of defined input vector 'v'
     * @param v Vector to copy to
     */
    public Normal3D(Vector3D v) {
        super(v);
    }
    
    /** 
     * Creates a new instance of Normal3D with a copy of defined input point 'p'
     * @param p Point to copy to
     */
    public Normal3D(Point3D p) {
        super(p);
    }
    
    /** 
     * Creates a new instance of Normal3D with a copy of defined input three components object 'o'
     * @param o Three components object to copy to
     */
    public Normal3D(Object3D o) {
        super(o);
    }
    
    /** 
     * Creates a new instance of Normal3D initialized with defined parameters
     * @param nx X component of normal
     * @param ny Y component of normal
     * @param nz Z component of normal
     */
    public Normal3D(double nx, double ny, double nz) {
        super(nx, ny, nz);
    }
    
    /** 
     * Creates a new instance of Normal3D with a copy of input array of doubles 'c'
     * @param c Array of doubles with at least three elements with components to copy to
     */
    public Normal3D(double c[]) {
        super(c);
    }
    
    /** 
     * Creates a new instance of Normal3D defined by an origin and an end point
     * @param p1 Origin point for normal
     * @param p2 End point for normal
     */
    public Normal3D(Point3D p1, Point3D p2) {
        super(p2.x - p1.x, p2.y - p2.y, p2.z - p2.z);
    }
    
}
