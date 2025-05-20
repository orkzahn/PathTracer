package cgg.a08;

import java.util.List;
import tools.*;
import static tools.Functions.add;
import static tools.Functions.divide;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.negate;
import static tools.Functions.reflect;
import static tools.Functions.subtract;

public record RayTracer(PinHoleCamera cam, List<LightSource> lights, Shape scene) implements Sampler {

    private static Color ambientIntensity = Color.black;

    public RayTracer(PinHoleCamera cam, List<LightSource> lights, Shape scene) {
        this.cam = cam;
        this.lights = lights;
        this.scene = scene;
        init();
    }

    @Override
    public Color getColor(Vec2 p) {
        Ray ray = cam.generateRay(p);
        return recusiveShading(ray, 10);
    }

    public void init() {
        for (var light : lights)
            ambientIntensity = add(ambientIntensity, light.infoFor(Vec3.zero).incomingIntensity());
        ambientIntensity = divide(ambientIntensity, lights().size());
    }

    public Color recusiveShading(Ray ray, int depth) {
        if (ray == null || depth == 0) {
            return Color.black;
        }

        var hit = scene.intersect(ray);

        if (hit == null)
            return Color.black;

        var material = hit.material();
        if(material == null)
            return Color.black;
            
        var albedo = material.baseColor(hit);
        var emission = material.emission(hit);


        var secRay = material.scattered(ray, hit);
        
        //var direct = phong(hit.point(), hit.normal(), ray.dir(), material, hit);
        
        var direct = phong2(hit,ray);

        var global = recusiveShading(secRay, depth - 1);

        return add( emission, direct,  multiply(albedo, global));
    }


        public Color phong2(Hit hit, Ray ray) {
        double tMax = 0;
        double tMin = 1 * Math.pow(10, -5); // tmin = e, verhindert Shadow-Acne

        var n =  hit.normal();
        var kD = hit.material().baseColor(hit);
        var intensity = multiply(0.1, multiply(kD, ambientIntensity));
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
            Ray shadowRay = new Ray(offsetPoint, s, 0, Double.POSITIVE_INFINITY); // Ursprung immer unser letzter Treffer
            boolean insideShadow = false;
            
            tMax = info.distance();

            Hit shadowHit = scene.intersect(shadowRay);
            if (shadowHit != null && shadowHit.t() > tMin && shadowHit.t() <= tMax) { // tMin Checken um Selbsttreffer zu vermeiden und schauen ob t kleiner ist tMax wichtig für PointLight
                insideShadow = true;
            }

            if(!insideShadow){
            var r = reflect(s,n);
            var v = negate(ray.dir());
            var L = info.incomingIntensity();
            var kS = material.specular(hit);
            var kE = hit.material().shininess(hit);
            

            if(light instanceof PointLight){ // Intensität von Pointlight war zu wenig :(
                L  = multiply(1, L);
            }

    

            if(light instanceof DirectionalLight){ // Intensität von Pointlight war zu wenig :(
                L  = multiply(5, L);
            }



            var diffuse = multiply(kD, multiply(L, dot(n, s)));

            var specular = multiply(kS, multiply(L, Math.pow(dot(r, v), kE)));

            if(dot(n, s) < 0){
                specular = Color.black;
            }

            intensity = add(intensity,diffuse,specular);
            intensity = Functions.clamp(intensity);

        }
    }
        return intensity;
    }  

    public static Vec3 reflect2(Vec3 n, Vec3 d){
        return subtract(d, multiply(2.0 * dot(d,n), n));
    }

}
