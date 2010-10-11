/*
 * Ray3D.java
 *
 * Created on 10 de junio de 2005, 19:19
 */

package lib;

/**
 * Implements Ray3D Object. Defines a Ray (direction vector plus origin point)
 * It is a line, semiline (Ray) or segment (it depends on 'mint' or 'maxt' definition)
 * r(t) = d * t + o
 * @author Isma
 */
public class Ray3D {
    
    /**
     * Vector containing ray direction
     */
    protected Vector3D d;
    
    /**
     * Point containing ray origin (or point of line)
     */
    protected Point3D o;
    
    /**
     * If defined, stores the first side of segment or side of semiline
     */
    protected double mint;
    
    /**
     * If defined, stores the second side of segment or side of semiline
     */
    protected double maxt;
    
    /**
     * If defined, stores the time associated to line, semiline or segment
     */
    protected double time;
    
    /** 
     * Creates a new instance of Ray3D with default initialization 
     * Default initialization is a Ray (semiline)
     */
    public Ray3D() {
        super();
        o = new Point3D(0.0, 0.0, 0.0);
        d = new Vector3D(0.0, 0.0, 1.0);
        mint = 0.0;
        maxt = Double.MAX_VALUE;
        time = 0.0;
    }

    /** 
     * Creates a new instance of Ray3D with a copy of 'r'
     * @param r Ray used to copy to this object
     */
    public Ray3D(Ray3D r) {
        super();
        if (r != null) {
            o = r.o;
            d = r.d;
            mint = r.mint;
            maxt = r.maxt;
            time = r.time;
        }
        else {
            o = new Point3D(0.0, 0.0, 0.0);
            d = new Vector3D(0.0, 0.0, 1.0);
            mint = 0.0;
            maxt = Double.MAX_VALUE;
            time = 0.0;
        }
    }
    
    /** 
     * Creates a new instance of Ray3D defined by point 'no' and vector 'nd'
     * Default initialization is a Ray (semiline)
     * @param no Origin point for this ray
     * @param nd Direction for this ray
     */
    public Ray3D(Point3D no, Vector3D nd) {
        super();
        o = (no != null) ? no : new Point3D(0.0, 0.0, 0.0);
        d = (nd != null) ? nd : new Vector3D(0.0, 0.0, 1.0);
        mint = 0.0;
        maxt = Double.MAX_VALUE;
        time = 0.0;
    }
    
    /** 
     * Creates a new instance of Ray3D defined by point 'no', vector 'nd',
     * from point 'no'+'nd'*'nmint' to 'no'+'nd'*'nmaxt'
     * @param no Origin point for this ray
     * @param nd Direction for this ray
     * @param nmint Minimum valid point for this ray
     * @param nmaxt Maximum valid point for this ray
     */
    public Ray3D(Point3D no, Vector3D nd, double nmint, double nmaxt) {
        super();
        o = (no != null) ? no : new Point3D(0.0, 0.0, 0.0);
        d = (nd != null) ? nd : new Vector3D(0.0, 0.0, 1.0);
        if (nmint == Double.NaN)
            mint = nmint;
        else
            mint = (nmint < nmaxt) ? nmint : nmaxt;
        if (nmaxt == Double.NaN)
            maxt = nmaxt;
        else
            maxt = (nmaxt > nmint) ? nmaxt : nmint;
        time = 0.0;
    }

    /** 
     * Creates a new instance of Ray3D defined by point 'no', vector 'nd',
     * from point 'no'+'nd'*'nmint' to 'no'+'nd'*'nmaxt' and associated to time 'ntime'
     * @param no Origin point for this ray
     * @param nd Direction for this ray
     * @param nmint Minimum valid point for this ray
     * @param nmaxt Maximum valid point for this ray
     * @param ntime Time associated to this ray
     */
    public Ray3D(Point3D no, Vector3D nd, double nmint, double nmaxt, double ntime) {
        super();
        o = (no != null) ? no : new Point3D(0.0, 0.0, 0.0);
        d = (nd != null) ? nd : new Vector3D(0.0, 0.0, 1.0);
        if (nmint == Double.NaN)
            mint = nmint;
        else
            mint = (nmint < nmaxt) ? nmint : nmaxt;
        if (nmaxt == Double.NaN)
            maxt = nmaxt;
        else
            maxt = (nmaxt > nmint) ? nmaxt : nmint;
        time = ntime;
    }
    
    /** 
     * Initializes the origin point for this ray with parameter (only if 'no' is not null)
     * @param no new origin point for this ray
     */
    public void setOrigin(Point3D no) {
        if (no != null)
            o = no;
    }
    
    /** 
     * Initializes the vector direction for this ray with parameter (only if 'nd' is not null)
     * @param nd new vector direction for this ray
     */
    public void setDirection(Vector3D nd) {
        if (nd != null)
            d = nd;
    }
    
    /** 
     * Initializes the minimum side of semiline or segment with parameter
     * @param nmint new minimum side for this ray
     */
    public void setMint(double nmint) {
        if (nmint == Double.NaN || nmint <= maxt)
            mint = nmint;
        else {
            mint = maxt;
            maxt = nmint;
        }
    }
    
    /** 
     * Initializes the maximum side of semiline or segment with parameter
     * @param nmaxt new maximum side for this ray
     */
    public void setMaxt(double nmaxt) {
        if (nmaxt >= mint || nmaxt == Double.NaN)
            maxt = nmaxt;
        else {
            maxt = mint;
            mint = nmaxt;
        }
    }
    
    /** 
     * Initializes the time associated to this ray with parameter
     * @param ntime new maximum side for this ray
     */
    public void setTime(double ntime) {
        time = ntime;
    }
    
    /** 
     * Obtains Point of this line, semiline or segment
     * @param t Parameter to search the point in vectorial equation
     * @return Point found. If Point found is outside segment or semiline returns null
     */
    public Point3D getPoint(double t) {
        if (o == null || d == null)
            return null;
        
        if ((mint != Double.NaN && t < mint) || 
            (maxt != Double.NaN && t > maxt))
            return null;
        
        Vector3D v = Vector3D.mul(d, t);
        return Point3D.add(v, o);
    }
    
    /** 
     * Compares this ray with ray 'r'
     * @param r Ray to compare with
     * @return True if both lines, semilines or segments are equals and False if not
     */
    public boolean equals(Ray3D r) {
        if (r == null)
            return false;
        
        // Direction
        if (d.cross(r.d).length() != 0.0)
            return false;
        
        if (mint != r.mint || maxt != r.maxt)
            return false;
        
        if (mint == Double.NaN && maxt == Double.NaN)
            return isIn(r.o);
        else
            return o.equals(r.o);
    }
    
    /** 
     * Returns if parameter 'p' is in this line. This function doesn't 
     * checks 'mint' or 'maxt'. This object is an infinite line.
     * @param p Point that we have to check
     * @return True if point is in this line and False if not
     */
    public boolean isIn(Object3D p) {
        double tx = (d.x == 0.0) ? Double.NaN : (p.x - o.x) / d.x;
        double ty = (d.y == 0.0) ? Double.NaN : (p.y - o.y) / d.y;
        double tz = (d.z == 0.0) ? Double.NaN : (p.z - o.z) / d.z;
        if (tx != Double.NaN && ty != Double.NaN && tz != Double.NaN)
            return tx == ty && ty == tz;
        if (tx == Double.NaN && ty != Double.NaN && tz != Double.NaN)
            return ty == tz;
        if (tx != Double.NaN && ty == Double.NaN && tz != Double.NaN)
            return tx == tz;
        if (tx != Double.NaN && ty != Double.NaN && tz == Double.NaN)
            return tx == ty;
        return true;
    }
    
    /** 
     * Returns a String representation for this object
     * @return String representing this object
     */
    public String toString() {
        return o + " + t" + d;
    }    
}
