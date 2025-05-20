package cgg.a04;

import java.util.List;
import tools.*;
import static tools.Functions.add;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.negate;
import static tools.Functions.reflect;

public record RayTracer(PinHoleCamera cam, List<LightSource> lights, DrawKugeln scene)implements Sampler{

    @Override
    public Color getColor(Vec2 p) {
        Ray ray = cam.generateRay(p);
        Hit closestHit = scene.intersect(ray);

        if(closestHit != null)
            return shade(closestHit, ray);

        return Color.black;
    }


    public Color shade(Hit hit, Ray ray) {
        double tMax = 0;
        double tMin = 1 * Math.pow(10, -5); // tmin = e, verhindert Shadow-Acne

        var n =  hit.normal();
        var intensity = Color.black;
        var material = hit.material();


        for (LightSource light : lights) {

            // checken ob irgendwas dazwischen ist Oberfläche und Lichtpunkt
            // Neuen Schattenstrahl von dem Trefferpunkt zu der Lichtquelle
            // falls dazwischen etwas getroffen wird = Lichtquelle keinen Einfluss

            var info = light.infoFor(hit.point());
            var s = info.directionToLight();

            /*
             * Trotz tMin = 1*10^-5 hatte ich Artefakte? 
             * Anscheinend muss man einen kleinen offset einbauen, wegen floating point imprecision?? 
             * Sonst hat man immer noch acne
             */
            Vec3 offsetPoint = add(hit.point(), multiply(tMin, hit.normal()));
            Ray shadowRay = new Ray(offsetPoint, s); // Ursprung immer unser letzter Treffer
            boolean insideShadow = false;
            
            tMax = info.distance();

            for (Sphere kg : scene.alleKugeln()) { // Durch alle Kugeln iterieren und schauen ob shadowRay trifft
                Hit shadowHit = kg.intersect(shadowRay); // Wenn shadowHit != null haben wir mindestens 1 treffer
                if (shadowHit != null && shadowHit.t() > tMin && shadowHit.t() <= tMax) { // tMin Checken um Selbsttreffer zu vermeiden und schauen ob t kleiner ist tMax wichtig für PointLight
                    insideShadow = true;
                    intensity = add(intensity, multiply(material.baseColor(hit), multiply(0.1, info.incomingIntensity())));
                    break;
                }
            }

            if(!insideShadow){
            var r = reflect(s,n);
            var v = negate(ray.dir());
            var L = info.incomingIntensity();
            var kD = material.baseColor(hit);
            var kS = material.specular(hit);
            var kE = material.shininess(hit);
            

            if(light instanceof PointLight){ // Intensität von Pointlight war zu wenig :(
                L  = multiply(20, L);
            }
        
            var ambient = multiply(kD, L);
            ambient = multiply(0.1, ambient);


            var diffuse = multiply(kD, multiply(L, dot(n, s)));

            var specular = multiply(kS, multiply(L, Math.pow(dot(r, v), kE)));

            if(dot(n, s) < 0){
                specular = Color.black;
            }

            intensity = add(intensity,ambient,diffuse,specular);
            intensity = Functions.clamp(intensity);

        }
    }
        return intensity;
    }   
}
