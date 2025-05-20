package cgg.a08;

import tools.Color;
import tools.Sampler;

public record TexturedPhongMaterial(Sampler kD, Sampler kS, Sampler kE) implements Material {

    @Override
    public Color baseColor(Hit h) {
        return kD.getColor(h.uv());
    }

    @Override   
    public Color specular(Hit h) {
        return kS.getColor(h.uv());
    }

    @Override
    public double shininess(Hit h) {
        return kE.getColor(h.uv()).r();
    }

    @Override
    public Color emission(Hit h) {
        return Color.black;
    }

    @Override
    public Ray scattered(Ray ray, Hit hit) {
        return null;
    }

}
