package cgg.a08;

import java.util.List;
import tools.BoundingBox;
import static tools.Functions.invert;
import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;
import static tools.Functions.normalize;
import static tools.Functions.transpose;
import tools.Mat44;
import tools.Vec3;

public class Group implements Shape{

    private Mat44 toWorld;
    private Mat44 invertedToWorld;
    private Mat44 transposedWorld;
    private BoundingBox bbox = BoundingBox.empty;

    private List<Shape> shapes;

    public Group(Mat44 toWorld, List<Shape> shapes){
        this.toWorld = toWorld;
        this.invertedToWorld = invert(toWorld);
        this.transposedWorld = transpose(invertedToWorld);
        this.shapes = shapes;

        BoundingBox box = BoundingBox.empty;  

        for (int i = 0; i < shapes.size(); i++) {
            box = box.extend(shapes.get(i).bounds().transform(toWorld));
        }
        this.bbox = box;  
    }

    public Hit intersect(Ray ray){
        if(!bbox.intersect(ray.x0(), ray.dir(), ray.tMin(), ray.tMax())){
            return null;
        }

        double t = Double.POSITIVE_INFINITY;
        Hit closestHit = null;

        // Lokales Koordinatensystem
        Ray localTransformRay = new Ray(
                            multiplyPoint(invertedToWorld, ray.x0()), 
                            multiplyDirection(invertedToWorld, ray.dir()),ray.tMin(),ray.tMax());

        for (Shape s : shapes) { // Durch alle shapes iterieren und den am nächsten hit ermitteln
            Hit point = s.intersect(localTransformRay);

            if (point != null && point.t() <= t) { // Hit darf nicht null sein und muss kleiner oder gleich unser t sein
                t = point.t(); // unser t auf unseren hit t setzen
                closestHit = point;
            }
        }

        if (closestHit != null) { // Sichergehen das hit nicht null ist, return closestHit
            Vec3 transformNormal = multiplyDirection(transposedWorld, closestHit.normal());
            transformNormal = normalize(transformNormal);
            Vec3 transformPoint = multiplyPoint(toWorld, closestHit.point());
            closestHit = new Hit(closestHit.t(), closestHit.uv(), transformPoint, transformNormal, closestHit.material());
            return closestHit;
        }   

        return null;
    }

    public List<Shape> getShapes(){
        return shapes;
    }

    @Override
    public BoundingBox bounds() {
        return bbox;
    }

}
