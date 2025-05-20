package cgg.a04;

import tools.Color;

public interface Material {
    Color baseColor(Hit h);   //kd
    Color specular(Hit h);    //ks
    double shininess(Hit h);  //ke
}
