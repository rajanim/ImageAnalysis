package sfsu.cs;

/**
 * Created by rajanishivarajmaski1 on 5/12/17.
 */
public class TestCircle {

    public static void main(String[] args) {

        Circle cir = new Circle();
        System.out.println(cir.getRadius());
        System.out.println(cir.getArea());

        cir.setRadius(20);
        System.out.println(cir.getRadius());
        System.out.println(cir.getArea());
        System.out.println(cir.getCircumference());


        Circle cir2 = new Circle(15);
        System.out.println(cir2.getArea());




    }
}
