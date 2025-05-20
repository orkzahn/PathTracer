package cgg.a02;

import tools.Functions;
import tools.Vec2;
import tools.Vec3;


public class LochKamera {

    private Vec2 res;
    private double winkel;

    public LochKamera(Vec2 res, double winkel){
        this.res = res;
        this.winkel = winkel;
    }

    public Ray generateRay(Vec2 abtastPunkt){
        //Strahl konstruiren
        //Berechnung der Strahlrichtung
        // x = xp - w/2
        // y = yp - h/2
        // z = w/2 / tan(winkel/2)

        Vec3 dir = new Vec3(abtastPunkt.x() - res.x() / 2,
                            -(abtastPunkt.y() - res.y() / 2),
                            -((res.x()/2) / Math.tan(winkel / 2)));

        return new Ray(Vec3.zero, Functions.normalize(dir));
    }

    public Vec2 getRes(){
        return res;
    }

}


/** Fragen in der Uebung
 * Kleines Problem mit der Kamera? -z Ist Blickrichtung, aber bei mir ist irgendwie die Y Achse auch negativ??!?!
 * Gelöst SetPixel war anders bei mir, deswegen habe ich es jetzt hier negiert
 * */