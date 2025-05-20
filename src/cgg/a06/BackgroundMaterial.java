package cgg.a06;

import tools.Color;
import tools.Sampler;

public record BackgroundMaterial(Sampler texture) implements Material {

    @Override
    public Color baseColor(Hit h) {
        return texture.getColor(h.uv());
    }

    @Override
    public Color specular(Hit h) {
        return Color.black;
    }

    @Override
    public Color emission(Hit h) {
        return texture.getColor(h.uv());
    }
    @Override
    public double shininess(Hit h) {
        return 0;
    }

    @Override
    public Ray scattered(Ray ray, Hit hit) {
        return null;
    }

}
