package cgg.a07;

import tools.Color;

public record PhongMaterial(Color kD, Color kS, double kE) implements Material{

    @Override
    public Color baseColor(Hit h) {
        return kD;
    }

    @Override
    public Color specular(Hit h) {
        return kS;
    }

    @Override
    public double shininess(Hit h) {
        return kE;
    }

    @Override
    public Color emission(Hit h) {
        return new Color(0, 0, 0);
    }

    @Override
    public Ray scattered(Ray ray, Hit hit) {
        return null;
    }

}
