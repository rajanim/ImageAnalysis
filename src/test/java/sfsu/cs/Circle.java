package sfsu.cs;

import java.util.Scanner;

class Circle {
    String name;
    int radius;
    static int count = 1;

    Circle() {
        count = count + 2;
    }
}
class TestCircle {
    public static void main(String [] args) {

        Circle c0 = new Circle();
        c0.name = "oval";
        c0.radius = 5;

        Circle c1 = new Circle();
        c1.name = "curved";
        c1.radius = 15;

        Circle c2 = new Circle();
        c2.name = "elliptical";
        c2.radius = 20;

        System.out.println(c0.count);
        System.out.println(c1.count);

        System.out.println("Names: " + c0.name + " " + c1.name + " " + c2.name);
        System.out.println(Circle.count);
        c1 = c2;
        c1.radius = 13;
        c0.radius = c2.radius + c0.radius;

        System.out.println("radius = " + c0.radius + " " + c1.radius + " " + c2.radius);

    }

    public static void main2(String[] args) {
        Scanner input = new Scanner(System.in);

        int number, max;
        number = input.nextInt();
        max = number;
        while (number != 0) {
            number = input.nextInt();
            if (number > max)
                max = number;
        }
        System.out.println("max is " + max);
        System.out.println("number " + number);
    }

}