package cgg.a09;

import tools.Functions;
import tools.Vec3;

public record Ray(Vec3 x0, Vec3 dir, double tMin, double tMax){

    public Vec3 calcPoint(double t){
        // Formel Strahlengleichung x0 + t*dir
        return Functions.add(Functions.multiply(t, dir),x0);
    }

    public boolean validInterval(double t){
        // Paramter t weiter eingeschränkt 
        return t >= tMin && t <= tMax; 
    }

}
