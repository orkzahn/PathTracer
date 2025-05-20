package cgg.a08;

import tools.BoundingBox;

public interface Shape {

    public Hit intersect(Ray ray);
    public BoundingBox bounds();

}
