package cgg.a04;
import tools.*;
import static tools.Functions.subtract;

public record Sphere(Vec3 pos, double rad, Material material) {
    
    public Hit intersect(Ray r){
        // Schnitt: Stahl Kugel Quadratische Gleichung in t
        Vec3 origin = Functions.subtract(r.x0(), pos);
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
                Vec3 point = r.calcPoint(t); // Punkt berechnen
                Vec3 normalVec = Functions.divide(Functions.subtract(point, pos), rad); // Normalvektor berechnen
                Vec2 uv = CalculateUV(point);        
                return new Hit(t,uv,point,normalVec,material);
            }
        }

        if(diskriminante == 0){ // 1 Lösungen
            double t = -b / 2 * a;
            if(r.validInterval(t)){ // Bedingung muss im Intervall tmin,tmax liegen sonst kein Treffer!
                Vec3 point = r.calcPoint(t); // Punkt berechnen
                Vec3 normalVec = Functions.divide(Functions.subtract(point, pos), rad); // Normalvektor berechnen
                Vec2 uv = CalculateUV(point);   
                return new Hit(t,uv,point,normalVec,material);
            }
        }

        // Keine Lösung also null
        return null;

    }

    public Vec2 CalculateUV(Vec3 point){
        double u;
        double v;

        Vec3 offsetPoint = subtract(point, pos);

        u = (Math.atan2(offsetPoint.x(),offsetPoint.z()) + (Math.PI)) / (Math.PI * 2);
        v = -((Math.PI - (Math.acos(offsetPoint.y()/rad()))) / (Math.PI));

        return new Vec2(u, v);
    }

}
