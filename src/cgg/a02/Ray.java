package cgg.a02;

import tools.Functions;
import tools.Vec3;

public record Ray(Vec3 x0, Vec3 dir){

    private static final double tMin = 0;
    private static final double tMax = Double.POSITIVE_INFINITY;

    public Vec3 calcPoint(double t){
        // Formel Strahlengleichung x0 + t*dir halbgerade
        // Parametische Form wegen dem (t)
        // Punkt auf der Geraden ausrechnen mit Paramter t
        // Beispiel t = 0 dann wird der rechts teil wegfallen wegen 0 multiply = startpunkt
        // Beispiel t = 1 ist die dann die Spitze
        return Functions.add(Functions.multiply(t, dir),x0);
    }

    public boolean validInterval(double t){
        // Paramter t weiter eingeschränkt, alles davor oder danach ist uns egal
        return t >= tMin && t <= tMax; 
    }

}
