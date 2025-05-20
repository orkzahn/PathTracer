package cgg.a07;

import tools.BoundingBox;

public interface Shape {

    public Hit intersect(Ray ray);
    public BoundingBox bounds();

}
