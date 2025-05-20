package cgg.a06;

import tools.Color;
import static tools.Functions.EPSILON;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.subtract;
import tools.Vec3;


public class MirrorMaterial implements Material{

    @Override
    public Color baseColor(Hit h) {
        return new Color(1, 1, 0.7);
    }

    @Override
    public Color specular(Hit h) {
        return Color.white;
    }

    @Override
    public double shininess(Hit h) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color emission(Hit h) {
        return Color.black;
    }

    @Override
    public Ray scattered(Ray ray, Hit hit) {
    Vec3 d = normalize(ray.dir());
    Vec3 n = normalize(hit.normal()); 
    Vec3 r = normalize(reflect(n, d));

    return new Ray(hit.point(), r, EPSILON, Double.POSITIVE_INFINITY);
    }


    public static Vec3 reflect(Vec3 n, Vec3 d){
        return subtract(d, multiply(2.0 * dot(n,d), n));
    }
}
