package cgg.a09;

import tools.Color;
import static tools.Functions.EPSILON;
import static tools.Functions.add;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.vec3;
import tools.Sampler;
import tools.Vec3;

public record DiffuseMaterial(Sampler kD, Sampler kS, Sampler kE) implements Material{

    @Override
    public Color baseColor(Hit h) {
        return kD.getColor(h.uv());
    }

    @Override
    public Color specular(Hit h) {
        return kS.getColor(h.uv());
    }

    @Override
    public Color emission(Hit h) {
        return multiply(kS.getColor(h.uv()),1);
    }

    @Override
    public double shininess(Hit h) {
        return kE.getColor(h.uv()).r();
    }

    @Override 
    public Ray scattered(Ray ray, Hit hit) {
        Vec3 randomDir = new Vec3(3,3, 3);

        while(Math.pow(randomDir.x(), 2) + Math.pow(randomDir.y(), 2) + Math.pow(randomDir.z(), 2) >= 1){
            //Einheitsquadrat -1 bis 1
            double x = (Math.random()*2)-1;
            double y = (Math.random()*2)-1;
            double z = (Math.random()*2)-1;
            randomDir = vec3(x,y,z);
        }

        Vec3 secondDirection = normalize(add(hit.normal(), randomDir));

        return new Ray(hit.point(), secondDirection, EPSILON, ray.tMax());
    }

}