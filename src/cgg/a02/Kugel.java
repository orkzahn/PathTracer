package cgg.a02;
import tools.*;

public record Kugel(Vec3 pos, double rad, Color col) {
    
    public Hit intersect(Ray r){
        // Schnitt: Stahl Kugel Quadratische Gleichung in t
        Vec3 origin = Functions.subtract(r.x0(), pos);  // x0 von c abziehen, damit der Kreis im Ursprung ist, damit das c keine Rolle spielt
        double a = Functions.dot(r.dir(), r.dir());
        double b = 2 * Functions.dot(origin, r.dir());
        double c = Functions.dot(origin,origin) - Math.pow(rad, 2);
        
        //Diskriminante
        double diskriminante = Math.pow(b, 2) - 4 * a * c;


        if(diskriminante > 0){ // 2 Lösungen
            // Lösung berechnen wie in den Formeln
            // (-b +- Wurzel(b^2 -4ac) / 2a)

            double t1 = (-b - Math.sqrt(diskriminante)) / (2*a);
            double t2 = (-b + Math.sqrt(diskriminante)) / (2*a);
            double t = 0;

            if(t1 < 0)
                t = t2;
            else if(t2 < 0)
                t = t1;
            else
                t = Math.min(t1, t2); // Kleinsten Strahlparameter zuerst

            if(r.validInterval(t)){ // Bedingung muss im Intervall tmin,tmax liegen sonst kein Treffer!
                Vec3 x = r.calcPoint(t); // Punkt berechnen
                Vec3 normalVec = Functions.divide(Functions.subtract(x, pos), rad); // Normalvektor berechnen, an der oberfläche, in welche richtung ist die oberflöche an diesem punkt orientiert
                return new Hit(t,x,normalVec,col);
            }
        }

        if(diskriminante == 0){ // 1 Lösungen
            double t = -b / 2 * a;
            if(r.validInterval(t)){ // Bedingung muss im Intervall tmin,tmax liegen sonst kein Treffer!
                Vec3 x = r.calcPoint(t); // Punkt berechnen
                Vec3 normalVec = Functions.divide(Functions.subtract(x, pos), rad); // Normalvektor berechnen
                return new Hit(t,x,normalVec,col);
            }
        }

        // Keine Lösung also null
        return null;

    }



}
