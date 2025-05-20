package cgg.a07;

import tools.Color;
import static tools.Functions.*;
import tools.Vec3;

public record PointLight(Vec3 position, Color lightColor) implements LightSource {

    public double calctMax(Vec3 x){
        return length(subtract(position, x));
    }
  
    public Color incoming(Vec3 x) {
        var L = divide(lightColor, Math.pow(length(subtract(position, x)), 2));  
       return L;
    }

    @Override
    public Info infoFor(Vec3 x) {
        return new Info(incoming(x), normalize(subtract(position, x)), calctMax(x));
    }

}