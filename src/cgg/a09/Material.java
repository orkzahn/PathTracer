package cgg.a09;

import tools.Color;

public interface Material {
    Color baseColor(Hit h);   //kd
    Color specular(Hit h);    //ks
    Color emission(Hit h);    //ks
    double shininess(Hit h);  //ke
    Ray scattered(Ray ray, Hit hit);
}
