package cgg.a04;

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

}
