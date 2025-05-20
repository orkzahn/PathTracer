package cgg.a08;

import java.util.List;
import tools.BoundingBox;
import static tools.Functions.divide;
import static tools.Functions.subtract;
import tools.Vec3;



public class TriangleMesh implements Shape {
    KdTree triangleTree;
    Material material;
    BoundingBox bb;

    TriangleMesh(List<Triangle> tris, Material material) {
        this.bb = BoundingBox.empty;
        for (Triangle triangle : tris) {
            bb = bb.extend(triangle.bounds());
        }

        this.material = material;
        triangleTree = construct(tris);

    }

    // recursivly constructs a k-d-tree
    public KdTree construct(List<Triangle> tris) {
        if (tris.size() < 3) {
            return new Leaf(tris);
        }

        BoundingBox currentBB = BoundingBox.empty;
        for (Triangle t : tris) {
            currentBB = currentBB.extend(t.bounds());
        }

        BoundingBox splitLeft = currentBB.splitLeft();
        BoundingBox splitRight = currentBB.splitRight();

        List<List<Triangle>> bothParts = split(tris, currentBB);

        return new Node(construct(bothParts.get(0)), construct(bothParts.get(1)), splitLeft,splitRight);
    }

    //Von BoundingBox teils übernommen und etwas angepasst um die längste Achse zu finden <.< 
    public int longest(BoundingBox box){
        Vec3 size2 = subtract(
            divide(subtract(box.max(), Vec3.zero), 2),
            divide(subtract(box.min(), Vec3.zero), 2)
        );
        if (size2.x() >= size2.y() && size2.x() >= size2.z()) {
            return 0;

        } else if (size2.y() >= size2.x() && size2.y() >= size2.z()) {
            return 1;

        } else {
            return 2;
        }
    }

    public List<List<Triangle>> split(List<Triangle> triangles, BoundingBox box){
        int axis = longest(box);

        switch (axis) {
            case 0:
                triangles.sort((t1, t2) -> Double.compare(t1.bb.center().x(), t2.bb.center().x()));
                break;
            case 1:
                triangles.sort((t1, t2) -> Double.compare(t1.bb.center().y(), t2.bb.center().y()));
                break;
            case 2:
                triangles.sort((t1, t2) -> Double.compare(t1.bb.center().z(), t2.bb.center().z()));
                break;
            default:
                break;
        }

        int split = triangles.size() / 2;
        List<Triangle> t1 = triangles.subList(0, split);
        List<Triangle> t2 = triangles.subList(split, triangles.size());

        return List.of(t1, t2);
    }

    @Override
    public Hit intersect(Ray ray) {
        if (!bb.intersect(ray.x0(), ray.dir(), ray.tMin(), ray.tMax())) {
            return null;
        }

        Hit hit = triangleTree.intersect(ray);
        if (hit != null)
            return new Hit(hit.t(), hit.uv(), hit.point(), hit.normal(), material);

        return null;
    }

    @Override
    public BoundingBox bounds() {
        return bb;
    }

}
