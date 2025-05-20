package cgg.a05;

import tools.Color;
import tools.Vec3;

public interface LightSource {
    // color Incoming intensity and distance
    record Info(Color incomingIntensity, Vec3 directionToLight, double distance){}
    Info infoFor(Vec3 x);
}
