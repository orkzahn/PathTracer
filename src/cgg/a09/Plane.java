package cgg.a09;

import tools.BoundingBox;
import tools.Vec2;
import tools.Vec3;

public record Plane(double size, Material material) implements Shape {

    public Hit intersect(Ray ray) {

        if (ray.dir().y() == 0) {
            return null;
        }

        double t = -((ray.x0().y()) / ray.dir().y());

        if (!ray.validInterval(t)) {
            return null;
        }

        Vec3 point = ray.calcPoint(t);
        double halfSize = size / 2;

        if (point.x() < -halfSize || point.x() > halfSize || point.z() < -halfSize || point.z() > halfSize) {
            return null;
        }

        Vec3 normal = new Vec3(0, 1, 0);
        if (ray.x0().y() < 0) {
            normal = new Vec3(0, -1, 0);
        }

        double u = (point.x() + halfSize) / size;
        double v = (point.z() + halfSize) / size;

        Vec2 uv = new Vec2(u, v);

        return new Hit(t, uv, point, normal, material);
    }

    @Override
    public BoundingBox bounds() {
        double halfSize = size / 2;
        Vec3 min = new Vec3(-halfSize, 0, -halfSize);
        Vec3 max = new Vec3(halfSize, 0, halfSize);

        return new BoundingBox(min, max);
    }

}
