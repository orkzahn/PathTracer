package cgg.a01;

import tools.Color;
import tools.Vec2;

public record Circle(Vec2 pos, double rad, Color col) {


    public Color getColor(Vec2 point) {
        if(coversPoint(point))
            return col;
        return Color.black;
    }

    public boolean coversPoint(Vec2 point){
       // var d = subtract(pos, point);
       // return squaredLength(d) < rad * rad;

        double formel = Math.pow(point.x() - pos.x(), 2) + Math.pow(point.y() - pos.y(), 2);
        double radius = Math.pow(rad, 2);

        return formel < radius;
    }

}
