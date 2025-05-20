package cgg.a06;

import tools.Color;
import static tools.Functions.normalize;
import tools.Vec3;

public record DirectionalLight(Vec3 lightDir, Color lightColor) implements LightSource {

    @Override
    public Info infoFor(Vec3 x) {
        return new Info(lightColor, normalize(lightDir), Double.POSITIVE_INFINITY);
    }

}
