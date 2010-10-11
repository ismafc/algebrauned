/*
 * Point3D.java
 *
 * Created on 28 de mayo de 2005, 11:59
 */

package lib;

/**
 * Stores a three dimensial point
 * @author Isma
 */
public class Point3D extends Object3D {
    
    /** Creates a new instance of Point3D */
    public Point3D() {
        super();
    }
   
    /**
     * Creates a new instance of Point3D with parameters
     * @param nx X value for 3D environment
     * @param ny Y value for 3D environment
     * @param nz Z value for 3D environment
     */
    public Point3D(double nx, double ny, double nz) {
        super(nx, ny, nz);
    }

    /**
     * Creates a new instance of Point3D with parameter
     * @param p Point3D to be used for create new Point3D
     */
    public Point3D(Point3D p) {
        super(p);
    }
    
    /**
     * Creates a new instance of Point3D with parameter
     * @param o Object3D to be used for create new Point3D
     */
    public Point3D(Object3D o) {
        super(o);
    }

    /**
     * Creates a new instance of Point3D with parameter
     * @param v Vector3D to be used for create new Point3D
     */
    public Point3D(Vector3D v) {
        super(v);
    }
    
    /**
     * Creates a new instance of Point3D with parameter
     * @param c Array of doubles used for create new Point3D
     */
    public Point3D(double c[]) {
        super(c);
    }
    
    /**
     * Obtains distance between this Point3D and input Point3D
     * @param p Point3D to calculate distance to
     * @return Double containing distance between 'p' and this point
     */
    public double distance(Point3D p) {
        return Vector3D.length(new Vector3D(this, p));
    }

    /**
     * Obtains squared distance between this Point3D and input Point3D
     * @param p Point3D to calculate squared distance to
     * @return Double containing squared distance between 'p' and this point
     */
    public double distanceSquared(Point3D p) {
        return Vector3D.lengthSquared(new Vector3D(this, p));
    }
    
    /**
     * Obtains midpoint between this Point3D and input Point3D 'p'
     * @param p Point3D to calculate midpoint from
     * @return Point3D containing midpoint between 'p' and this point
     */
    public Point3D midpoint(Point3D p) {
        return new Point3D((x + p.x) / 2.0, 
                           (y + p.y) / 2.0, 
                           (z + p.z) / 2.0);
    }

    /** 
     * returns distance between this point and plane ax+by+cz+d=0 
     * If this point is in side of normal direction value is positive, else it is negative
     * @param a First parameter of plane equation
     * @param b Second parameter of plane equation
     * @param c Third parameter of plane equation
     * @param d Last parameter of plane equation
     * @return Double containing distance between plane and this point
     */
    public double planeDistance(double a, double b, double c, double d) {
        double den = Vector3D.length(new Vector3D(a, b, c));
        return (den == 0.0 ? Double.NaN : (x * a + y * b + z * c + d) / den);
    }

    /** 
     * returns distance between this point and line defined by 'v' and 'a' 
     * @param v Director vector for line
     * @param a Point of line
     * @return Double containing distance between line and this point
     */
    public double lineDistance(Vector3D v, Point3D a) {
        if (v.isNull())
            return Double.NaN;
        Vector3D ap = new Vector3D(a, this);
        return Vector3D.cross(ap, v).length() / v.length();
    }

    /** 
     * Rotates this point 'r' radiants respect X axis
     * @param r X rotation angle in radiants
     */
    public void rotateX(double r) {
        double ny = y * Math.cos(r) - z * Math.sin(r);
        double nz = y * Math.sin(r) + z * Math.cos(r);
        y = ny;
        z = nz;
    }

    /** 
     * Rotates this point 'r' radiants respect Y axis
     * @param r Y rotation angle in radiants
     */
    public void rotateY(double r) {
        double nx = x * Math.cos(r) + z * Math.sin(r);
        double nz = z * Math.cos(r) - x * Math.sin(r);
        x = nx;
        z = nz;
    }

    /** 
     * Rotates this point 'r' radiants respect Z axis
     * @param r Z rotation angle in radiants
     */
    public void rotateZ(double r) {
        double nx = x * Math.cos(r) - y * Math.sin(r);
        double ny = x * Math.sin(r) + y * Math.cos(r);
        x = nx;
        y = ny;
    }
    
    /** 
     * Translates this point 'o' units (each component translates in one axis)
     * @param o Point3D with storing translation
     */
    public void translate(Object3D o) {
        add(o);
    }

    /** 
     * Translates this point 'nx' units in X axis, 'ny' units in Y axis and 'nz' units in Z axis
     * @param nx Double indicating X axis translation
     * @param ny Double indicating Y axis translation
     * @param nz Double indicating Z axis translation
     */
    public void translate(double nx, double ny, double nz) {
        add(nx, ny, nz);
    }

    /** 
     * Translates this point 'p[0]' units in X axis, 'p[1]' units in Y axis and 'p[2]' units in Z axis
     * @param p Array of doubles indicating X, Y and Z axis translation
     * @return True of translation has been made successfully and False if not (array is less than 3 values length)
     */
    public boolean translate(double p[]) {
        return add(p);
    }
    
    /**
     * Rotates an angle of 'r' radiants arround axis defined by points 'p1' and 'p2'
     * @param r Angle in radiants for rotation
     * @param p1 First point defining rotation axis
     * @param p2 last point defining rotation axis
     * @return True if 'p1' and 'p2' are equals (not axis defined) and False if they are equals
     */
    public boolean rotate(double r, Point3D p1, Point3D p2) {
        if (p1.equals(p2))
            return false;
        Vector3D v = new Vector3D(p1, p2);
        double beta = Math.atan2(v.y, v.z);
        double gamma = Math.atan2(v.x, Math.sqrt(v.y * v.y + v.z * v.z));
        
        v.set(p1);
        v.mul(-1.0);
        translate(v);
        
        rotateX(beta);
        rotateY(-gamma);
        rotateZ(r);
        rotateY(gamma);
        rotateX(-beta);
        
        v.mul(-1.0);
        translate(v);
        
        return true;
    }
    
    /** 
     * Scales this point 'ex' units in X axis, 'ey' units in Y axis and 'ez' units in Z axis
     * This scale transformation is made respect 'p' point
     * @param ex Double indicating X axis scale
     * @param ey Double indicating Y axis scale
     * @param ez Double indicating Z axis scale
     * @param p Point3D indicating base point for this scale tranformation
     */
    public void scale(double ex, double ey, double ez, Point3D p) {
        Vector3D v = new Vector3D(p);
        v.mul(-1.0);
        translate(v);
        
        x *= ex;
        y *= ey;
        z *= ez;
        
        v.mul(-1.0);
        translate(v);
    }
    
    /** 
     * Makes this point symetric respect plane ax+by+cz+d=0
     * @param a first parameter of plane equation
     * @param b second parameter of plane equation
     * @param c third parameter of plane equation
     * @param d last parameter of plane equation
     * @return True if plane is correct and False if not
     */
    public boolean symmetry(double a, double b, double c, double d) {
        Vector3D v = new Vector3D(a, b, c);
        if (v.isNull())
            return false;
        double l2 = v.length() * v.length();
        double dot = Vector3D.dot(v, new Vector3D(this));
        double nx = (x - 2.0 * a * dot + d) / l2;
        double ny = (y - 2.0 * b * dot + d) / l2;
        double nz = (z - 2.0 * c * dot + d) / l2;
        x = nx;
        y = ny;
        z = nz;
        return true;
    }

    /** 
     * Returns if this point is included in plane defined by ax+by+cz+d=0
     * @param a first parameter of plane equation
     * @param b second parameter of plane equation
     * @param c third parameter of plane equation
     * @param d last parameter of plane equation
     * @return True if point is in plane and False if not
     */
    public boolean isInPlane(double a, double b, double c, double d) {
        return (a * x + b * y + c * z + d == 0.0);
    }
    
    /** 
     * Transforms this point to a new frame dsefined by vectors 'e1', 'e2' and 'e3'
     * and origin point 'w'
     * @param e1 First director vector of target frame
     * @param e2 Second director vector of target frame
     * @param e3 Third director vector of target frame
     * @param w Origin point of target frame
     */
    public void freeBaseChange(Vector3D e1, Vector3D e2, Vector3D e3, Point3D w) {
        double nx = e1.x * x + e2.x * y + e3.x * z + w.x;
        double ny = e1.y * x + e2.y * y + e3.y * z + w.y;
        double nz = e1.z * x + e2.z * y + e3.z * z + w.z;
        x = nx;
        y = ny;
        z = nz;        
    }
    
    /**
     * Obtains barycenter for input points (three points)
     * @param p1 First point
     * @param p2 Second point
     * @param p3 Third point
     * @return Point containing barycenter for input points. If the three points define a segment the result is the midpoint.
     */
    static public Point3D barycenter(Point3D p1, Point3D p2, Point3D p3) {
        return new Point3D((p1.x + p2.x + p3.x) / 3.0,
                           (p1.y + p2.y + p3.y) / 3.0,
                           (p1.z + p2.z + p3.z) / 3.0);
    }
    
    /**
     * Obtains distance between two input points
     * @param p1 First point
     * @param p2 Second point
     * @return Double containing distance between two input points
     */
    static public double distance(Point3D p1, Point3D p2) {
        return Vector3D.length(new Vector3D(p1, p2));
    }

    /**
     * Obtains squared distance between two input points
     * @param p1 First point
     * @param p2 Second point
     * @return Double containing squared distance between two input points
     */
    static public double distanceSquared(Point3D p1, Point3D p2) {
        return Vector3D.lengthSquared(new Vector3D(p1, p2));
    }
    
    /**
     * Obtains midpoint between this input points 'p1' and 'p2'
     * @param p1 First point
     * @param p2 Last point
     * @return Point3D containing midpoint between 'p1' and 'p2'
     */
    static public Point3D midpoint(Point3D p1, Point3D p2) {
        return new Point3D((p1.x + p2.x) / 2.0, 
                           (p1.y + p2.y) / 2.0, 
                           (p1.z + p2.z) / 2.0);
    }
    
    /** 
     * Returns a new point with 'p1' + 'p2'
     * @param p1 First point
     * @param p2 Second point to add to 'p1'
     * @return Vector with result of the addition
     */
    static public Point3D add(Object3D p1, Object3D p2) {
        return new Point3D(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }
    
    /** 
     * Returns a new point with 'o1' - 'o2'
     * @param o1 First 3D object
     * @param o2 Second 3D object to substract to 'o1'
     * @return Point with result of the substraction
     */
    static public Point3D sub(Object3D o1, Object3D o2) {
        return new Point3D(o1.x - o2.x, o1.y - o2.y, o1.z - o2.z);
    }
    
    /** 
     * Returns a new point with 'o' / 'd'. If d = 0.0 returns a point initialized to (NAN,NAN,NAN)
     * @param o Three components object that will be divided by double 'd'
     * @param d Value to divide to 'o'
     * @return Vector with result of the division
     */
    static public Point3D div(Object3D o, double d) {
        if (d == 0)
            return new Point3D(Double.NaN, Double.NaN, Double.NaN);
        return new Point3D(o.x / d, o.y / d, o.z / d);
    }
    
    /** 
     * Returns a new point with 'o' * 'd'
     * @param o Three components object that will be multiplied by double 'd'
     * @param d Value to multiply to 'o'
     * @return Point with result of the multiplication
     */
    static public Point3D mul(Object3D o, double d) {
        return new Point3D(o.x * d, o.y * d, o.z * d);
    }
}
