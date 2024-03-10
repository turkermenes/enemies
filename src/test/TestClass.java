package test;

import java.awt.*;
import java.util.ArrayList;

public class TestClass {

    public static void main(String[] args) {
        ArrayList<Point> noktalar = new ArrayList<>();
        noktalar.add(new Point(15, 69));
        System.out.println(noktalar.contains(new Point(15, 69)));


    }
}
