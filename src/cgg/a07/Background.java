package cgg.a07;

import tools.BoundingBox;
import tools.Vec3;

public record Background(Material material) implements Shape{

    @Override
    public Hit intersect(Ray ray) {
        //Quasi Schnittpunkt faken damit wir immer einen falschen Treffer generieren
        //Wir wissen sofern kein Treffer = Hintergrund also wenn Abstand infinity ist 
        return new Hit(Double.POSITIVE_INFINITY, null,Vec3.zero, ray.dir(), material);
    }

    @Override
    public BoundingBox bounds() {
        return BoundingBox.everything;
    }

}
