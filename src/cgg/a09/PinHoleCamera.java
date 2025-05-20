package cgg.a09;

import tools.Functions;
import tools.Mat44;
import tools.Vec2;
import tools.Vec3;


public record PinHoleCamera(Vec2 imageResolution, double winkel, Mat44 viewingTransformation) {


    public Ray generateRay(Vec2 abtastPunkt){
        //Berechnung der Strahlrichtung
        // x = xp - w/2
        // y = yp - h/2
        // z = w/2 / tan(winkel/2)

        Vec3 dir = new Vec3(abtastPunkt.x() - imageResolution.x() / 2,
                            (abtastPunkt.y() - imageResolution.y() / 2),
                            -((imageResolution.x()/2) / Math.tan(winkel / 2)));

        Vec3 nDir = Functions.normalize(dir);

        //Transformierter Strahl
        Vec3 transformedPoint = Functions.multiplyPoint(viewingTransformation, Vec3.zero);
        Vec3 transformedDirection = Functions.multiplyDirection(viewingTransformation, nDir);

        return new Ray(transformedPoint, transformedDirection, 0,Double.POSITIVE_INFINITY);
    }
}
