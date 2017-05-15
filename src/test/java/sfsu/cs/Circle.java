package sfsu.cs;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 *
 * By default radius should be set to zero
 * UML
 ********************
 * Circle
 * ******************
 * radius
 * ******************
 * Circle()
 * Circle(radius)
 * setRadius()
 * getRadius()
 * getArea()
 * getCircumference()
 * ******************
 *
 */


public class Circle {
    String name;
    int radius;


    static int counter = 0;
    //no arg constructor
    public Circle(){
        counter++;
        this.radius = 10;
    }

    //1-arg constructor, radius as input
    public  Circle(int newRadius){
        counter++;
        this.radius = newRadius;
    }

    //
    public void setRadius(int newRadius){
        this.radius = newRadius;
    }

    public int getRadius(){
        return this.radius;
    }

    public double getArea(){
        double area = Math.PI * this.radius * this.radius;
        return area;
    }

    public double getCircumference(){
        double circm = 2 * Math.PI * this.radius;
        return circm;
    }



}
