package cgg.a03;

import tools.Functions;
import tools.Vec2;
import tools.Vec3;


public record PinHoleCamera(Vec2 imageResolution, double winkel) {


    public Ray generateRay(Vec2 abtastPunkt){
        //Berechnung der Strahlrichtung
        // x = xp - w/2
        // y = yp - h/2
        // z = w/2 / tan(winkel/2)

        Vec3 dir = new Vec3(abtastPunkt.x() - imageResolution.x() / 2,
                            -(abtastPunkt.y() - imageResolution.y() / 2),
                            -((imageResolution.x()/2) / Math.tan(winkel / 2)));

        return new Ray(Vec3.zero, Functions.normalize(dir));
    }
}
