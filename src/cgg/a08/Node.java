package cgg.a08;

import tools.BoundingBox;

public class Node implements KdTree {
    KdTree left;
    KdTree right;
    BoundingBox bb;

    public Node(KdTree left, KdTree right, BoundingBox BBleft, BoundingBox BBright) {
        this.left = left;
        this.right = right;
        this.bb = BBleft.extend(BBright);
    }

    @Override
    public Hit intersect(Ray ray) {
        if (!bb.intersect(ray.x0(), ray.dir(), ray.tMin(), ray.tMax())) {
            return null;
        }
        
        double t = Double.POSITIVE_INFINITY;
        Hit closestHit = null;

        if (left != null) {
            Hit leftHit = left.intersect(ray);
            if (leftHit != null && leftHit.t() <= t) { // Hit darf nicht null sein und muss kleiner oder gleich unser t sein
                t = leftHit.t(); // unser t auf unseren hit t setzen
                closestHit = leftHit;
            }
        }

        if (right != null) {
            Hit rightHit = right.intersect(ray);
            if (rightHit != null && rightHit.t() <= t) { // Hit darf nicht null sein und muss kleiner oder gleich unser t sein
                t = rightHit.t(); // unser t auf unseren hit t setzen
                closestHit = rightHit;
            }

        
        }

        return closestHit;
    }

    @Override
    public BoundingBox bounds() {
        return bb;
    }
}


