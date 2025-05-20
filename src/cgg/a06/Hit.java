package cgg.a06;

import tools.Vec2;
import tools.Vec3;


public record Hit(double t, Vec2 uv, Vec3 point, Vec3 normal, Material material) {

}
