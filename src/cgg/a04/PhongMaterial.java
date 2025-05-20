package cgg.a04;

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

}
