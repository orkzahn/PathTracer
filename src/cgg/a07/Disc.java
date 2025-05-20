package cgg.a07;

import tools.BoundingBox;
import static tools.Functions.length;
import tools.Vec3;

public record Disc(double radius, Shape shape) implements Shape {

    @Override
    public Hit intersect(Ray ray) {
        Hit hit = shape.intersect(ray);
        if (hit == null)
            return null;

        Vec3 center = hit.point();

        if (length(center) > Math.pow(radius, 2)) {
            return null;
        }

        return hit;

    }

    @Override
    public BoundingBox bounds() {
        Vec3 min = new Vec3(-radius, 0, -radius);
        Vec3 max = new Vec3(radius, 0, radius);

        return new BoundingBox(min, max);
    }
}
