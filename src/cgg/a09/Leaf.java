
package cgg.a09;

import java.util.List;
import tools.BoundingBox;

public class Leaf implements KdTree{
    
    List<Triangle> triangles;
    BoundingBox bb;
    
    public Leaf(List<Triangle> triangles){
        this.triangles = triangles;
        this.bb = BoundingBox.empty;
        for (Triangle t : triangles) {
            this.bb = this.bb.extend(t.bounds());
        }
    
    }

    @Override
    public Hit intersect(Ray ray) {
    if (!bb.intersect(ray.x0(), ray.dir(), ray.tMin(), ray.tMax())) {
        return null;
    }
    
    double t = Double.POSITIVE_INFINITY;
    Hit closestHit = null;
    
    for (Triangle triangle : triangles) {
        Hit hit = triangle.intersect(ray);
        if (hit != null && hit.t() <= t) {
            t = hit.t();
            closestHit = hit;
        }
    }
    
    return closestHit;
    }

    @Override
    public BoundingBox bounds() {
        return bb;
    }

}
