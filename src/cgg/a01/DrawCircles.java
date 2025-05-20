package cgg.a01;


import static tools.Functions.vec2;

import java.util.ArrayList;
import java.util.Collections;

import tools.Color;
import tools.Vec2;

public class DrawCircles {

    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private Vec2 res;
    private double xAmount;
    private double yAmount;

    public DrawCircles(Vec2 res, double xAmount, double yAmount){
        this.res = res;
        this.xAmount = xAmount;
        this.yAmount = yAmount;
        generateRandomCircles();
    }

    public void generateRandomCircles(){
        for (int i = 0; i < xAmount; i++) {
            for (int j = 0;  j < yAmount; j++) {
                circles.add(new Circle(vec2(Math.random() * res.x(), Math.random()*res.y()), Math.random()*200, new Color(Math.random(), Math.random(), Math.random())));

            }
        }

        Collections.sort(circles, (circle1, circle2) -> Double.compare(circle1.rad(), circle2.rad())); // Liste nach Radius sortieren
    }
    
    public Color getColor(Vec2 point) {
        for (Circle circle : circles) {
            if(circle.coversPoint(point)){
                return circle.col();
            }
          
        }
        return Color.black;
    }
}
