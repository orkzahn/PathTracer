package cgg.a09;

import tools.BoundingBox;
import static tools.Functions.add;
import static tools.Functions.almostEqual;
import static tools.Functions.cross;
import static tools.Functions.dot;
import static tools.Functions.length;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.subtract;
import tools.Vec2;
import tools.Vec3;
import tools.Vertex;

public class Triangle implements Shape{
    Vertex v0;
    Vertex v1;
    Vertex v2;
    BoundingBox bb;

    public Triangle(Vertex v0, Vertex v1, Vertex v2){
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;

        this.bb = BoundingBox.empty;
        this.bb = bb.extend(v0.position());
        this.bb = bb.extend(v1.position());
        this.bb = bb.extend(v2.position());
    }
    

    @Override
    public Hit intersect(Ray ray) {
        if(!bb.intersect(ray.x0(), ray.dir(), ray.tMin(), ray.tMax()))
            return null;

        Vec3 a = v0.position();
        Vec3 b = v1.position();
        Vec3 c = v2.position();

        Vec3 geoNormal = normalize(cross(subtract(b, a),subtract(c,a)));

        double t = dot(subtract(a, ray.x0()), geoNormal) / dot(ray.dir(), geoNormal);
        if (t < 0) {
            return null;
        }
        Vec3 p = ray.calcPoint(t);

        double abc = length(cross(subtract(b, a), subtract(c, a))) / 2;

        double bcp = length(cross(subtract(c, b), subtract(p, b))) / 2;
        double abp = length(cross(subtract(b, a), subtract(p, a))) / 2;
        double cap = length(cross(subtract(a, c), subtract(p, c))) / 2;

        double u = bcp / abc;
        double v = cap / abc;
        double w = abp / abc;

        double check = u+v+w;

        if(!almostEqual(check, 1)){
            return null;
        }

        //Interpolieren
        Vec3 pointInterpolated= add(multiply(u, a), multiply(v, b), multiply(w, c));
        Vec3 normalInterpolated = add(multiply(u, v0.normal()), multiply(v, v1.normal()), multiply(w, v2.normal()));
        Vec2 interpolatedUV = calculateUV(u, v, w);

        return new Hit(t, interpolatedUV, pointInterpolated, normalInterpolated, null);
    }

    public Vec2 calculateUV(double u, double v, double w){
        Vec2 uv0 = v0.uv();
        Vec2 uv1 = v1.uv();
        Vec2 uv2 = v2.uv();

        Vec2 interpolatedUV = add(multiply(u, uv0), multiply(v, uv1), multiply(w, uv2));
        Vec2 negateUV = new Vec2(interpolatedUV.x(), -interpolatedUV.y()); // UV war Kopfüber deswegen

        return negateUV;
    }

    @Override
    public BoundingBox bounds() {
        return bb;
    }

}
