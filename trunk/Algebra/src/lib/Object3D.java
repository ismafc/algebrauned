/*
 * Object3D.java
 *
 * Created on 25 de mayo de 2005, 19:06
 */

package lib;

/**
 * Stores a Object with three components (X, Y and Z). It will be usefull for Points, Vector, Normals, etc.
 * @author Isma
 * @version 1.0
 */
public class Object3D {
    
    /**
     * X value in 3D environment
     */
    protected double x;
    
    /**
     * Y value in 3D environment
     */
    protected double y;
    
    /**
     * Z value in 3D environment
     */
    protected double z;

    /**
     * Creates a new instance of Object3D
     * All variables initialized to 0
     */
    public Object3D() {
        super();
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    /**
     * Creates a new instance of Object3D with parameters
     * @param nx X value for 3D environment
     * @param ny Y value for 3D environment
     * @param nz Z value for 3D environment
     */
    public Object3D(double nx, double ny, double nz) {
        super();
        x = nx;
        y = ny;
        z = nz;
    }

    /**
     * Creates a new instance of Object3D with object
     * @param o Object to clone
     */
    public Object3D(Object3D o) {
        super();
        if (o != null) {
            x = o.x;
            y = o.y;
            z = o.z;
        }
    }

    /**
     * Creates a new instance of Object3D with array
     * @param c Array of 3 or more values (x, y and z)
     */
    public Object3D(double c[]) {
        super();
        if (c != null) {
            if (c.length >= 3) {
                x = c[0];
                y = c[1];
                z = c[2];
            }
        }
    }
    
    /**
     * Initializes this Object3D with input values
     * @param nx Double for x field
     * @param ny Double for y field
     * @param nz Double for z field
     */
    public void set(double nx, double ny, double nz) {
        x = nx;
        y = ny;
        z = nz;
    }

    /**
     * Initializes this Object3D with input value
     * @param o Object3D to copy in this Object
     */
    public void set(Object3D o) {
        x = o.x;
        y = o.y;
        z = o.z;
    }
    
    /**
     * Initializes this Object3D with input value
     * @param c Array of three doubles with x, y and z values (in this order)
     * @return True if input array 'c' has three values and False if not
     */
    public boolean set(double c[]) {
        if (c.length >= 3) {
            x = c[0];
            y = c[1];
            z = c[2];
            return true;
        }
        return false;
    }
    
    /**
     * Initializes x value of this Object3D with input value
     * @param nx Double with new x value
     */
    public void setX(double nx) { x = nx; }

    /**
     * Initializes y value of this Object3D with input value
     * @param ny Double with new y value
     */
    public void setY(double ny) { y = ny; }

    /**
     * Initializes z value of this Object3D with input value
     * @param nz Double with new z value
     */
    public void setZ(double nz) { z = nz; }
    
    /**
     * Obtains values of this Object3D in input parameter 'c'
     * @param c Array of three values where we will store x, y and z values (in this order)
     * @return True if input array 'c' has three values and False if not
     */
    public boolean get(double c[]) {
        if (c.length >= 3) {
            c[0] = x;
            c[1] = y;
            c[2] = z;
            return true;
        }
        return false;
    }

    /**
     * Adds input Object3D 'o' to this object. It consists on add x value to x value,
     * y value to y value and z value to z value
     * @param o Object3D to add to this object
     */
    public void add(Object3D o) {
        x += o.x;
        y += o.y;
        z += o.z;
    }

    /**
     * Substracts input Object3D 'o' from this object. It consists on substract x value from x value,
     * y value from y value and z value from z value
     * @param o Object3D to substract to this object
     */
    public void sub(Object3D o) {
        x -= o.x;
        y -= o.y;
        z -= o.z;
    }
    
    /**
     * Divides this Object3D by input value 'd'. It consists on divide x, y and z by this value
     * @param d Double with value to divide
     * @return True if division can be done and False if not (division by 0)
     */
    public boolean div(double d) {
        if (d == 0.0)
            return false;
        x /= d;
        y /= d;
        z /= d;
        return true;
    }

    /**
     * Divides this Object3D by input Object3D 'o'. It consists on divide x by x, y by y and z by z
     * @param o Object3D with value to divide
     * @return True if division can be done and False if not (some division by 0)
     */
    public boolean div(Object3D o) {
        if (o.x == 0.0 || o.y == 0.0 || o.z == 0.0)
            return false;
        x /= o.x;
        y /= o.y;
        z /= o.z;
        return true;
    }
    
    /**
     * Multiplies this Object3D by input value 'd'. It consists on multiply x, y and z by this value
     * @param d Double with value to multiply
     */
    public void mul(double d) {
        x *= d;
        y *= d;
        z *= d;
    }
    
    /**
     * Multiplies this Object3D by input Object3D 'o'. It consists on multiply x by x, y by y and z by z
     * @param o Object3D with value to multiply
     */
    public void mul(Object3D o) {
        x *= o.x;
        y *= o.y;
        z *= o.z;
    }
    
    /**
     * Adds input values to this object. It consists on add nx value to x value,
     * ny value to y value and nz value to z value
     * @param nx Double fo add to x value of this object
     * @param ny Double fo add to y value of this object
     * @param nz Double fo add to z value of this object
     */
    public void add(double nx, double ny, double nz) {
        x += nx;
        y += ny;
        z += nz;
    }

    /**
     * Substracts input values from this object. It consists on substracy nx from x value,
     * ny value from y value and nz value from z value
     * @param nx Double fo substract to x value of this object
     * @param ny Double fo substract to y value of this object
     * @param nz Double fo substract to z value of this object
     */
    public void sub(double nx, double ny, double nz) {
        x -= nx;
        y -= ny;
        z -= nz;
    }

    /**
     * Multiplies input values by this object. It consists on multipliy nx by x value,
     * ny value by y value and nz value by z value
     * @param nx Double fo multiply by x value of this object
     * @param ny Double fo multiply by y value of this object
     * @param nz Double fo multiply by z value of this object
     */
    public void mul(double nx, double ny, double nz) {
        x *= nx;
        y *= ny;
        z *= nz;
    }

    /**
     * Divides input values by this object. It consists on divide nx by x value,
     * ny value by y value and nz value by z value
     * @param nx Double fo divide by x value of this object
     * @param ny Double fo divide by y value of this object
     * @param nz Double fo divide by z value of this object
     * @return True if division can be done and False if not (division by 0)
     */
    public boolean div(double nx, double ny, double nz) {
        if (nx == 0.0 || ny == 0.0 || nz == 0.0)
            return false;
        x /= nx;
        y /= ny;
        z /= nz;
        return true;
    }

    /**
     * Adds input array 'c' to this object. It consists on add x value to x[0] value,
     * y value to c[1] value and z value to c[2] value
     * @param c Array of double to add to this object
     * @return True if array length is more than three and False if not
     */
    public boolean add(double c[]) {
        if (c.length < 3)
            return false;
        x += c[0];
        y += c[1];
        z += c[2];
        return true;
    }
    
    /**
     * Asks if this Object3D is null (0.0)
     * @return True if all values are 0.0 and False if not
     */
    public boolean isNull() {
        return x == 0.0 && y == 0.0 && z == 0.0;
    }
    
    /**
     * Asks if this Object3D is near null (0.0 +/- some defined value)
     * @param epsilon Tolerance value
     * @return True if all values are between 0.0 and epsilon, and False if not
     */
    public boolean isNull(double epsilon) {
        epsilon = (epsilon >= 0) ? epsilon : -epsilon;
        return x < epsilon && x > -epsilon && 
               y < epsilon && y > -epsilon &&
               z < epsilon && z > -epsilon;
    }
    
    /**
     * Asks if this Object3D is valid or not
     * @return True if all values are NaN and False if not
     */
    public boolean isInvalid() {
        return x == Double.NaN || y == Double.NaN || z == Double.NaN;
    }
    
    /**
     * Invalidates this Object3D initializing all values to NaN values
     */
    public void invalidate() {
        x = Double.NaN;
        y = Double.NaN;
        z = Double.NaN;
    }
    
    /**
     * Asks if this Object3D is equal than input value (x,y and z are equals one by one)
     * @param o Object to compare to this Object3D
     * @return True if both objects are equals and False if not. 
     */
    public boolean equals(Object3D o) {
        return x == o.x && y == o.y && z == o.z;
    }

    /**
     * Asks if this Object3D is equal than input value with input tolerance
     * x,y and z must be equals one by one with epsilon tolerance
     * @param o Object to compare to this Object3D
     * @param epsilon Tolerance value
     * @return True if both objects are equals and False if not. 
     */
    public boolean equals(Object3D o, double epsilon) {
        epsilon = (epsilon >= 0) ? epsilon : -epsilon;
        return x > o.x - epsilon && x < o.x + epsilon && 
               y > o.y - epsilon && y < o.y + epsilon && 
               z > o.z - epsilon && z < o.z + epsilon;
    }   
    
    /**
     * Asks for a String representation of this Object3D (x,y,z)
     * @return String representing this Object3D 
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /** 
     * Calculates the length of this object ^ 2 (sometimes is not necessary to
     * calculate length to know some properties about vectors (3D objects)
     * @return Double containing the length of object ^ 2
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }
    
    /** 
     * Calculates the length of this object. No errors are possible because
     *'lengthSquared' is always greater or equal than 0
     * @return Double containing the length of object
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    
    /** 
     * Calculates length of object 'o'
     * @param o Three components object to calculate its length
     * @return Double containing o length
     */
    static public double length(Object3D o) {
        return o.length();
    }
    
}
