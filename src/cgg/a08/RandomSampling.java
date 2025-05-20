package cgg.a08;

import tools.Color;
import static tools.Functions.add;
import static tools.Functions.divide;
import static tools.Functions.random;
import static tools.Functions.vec2;
import tools.Sampler;
import tools.Vec2;

public record RandomSampling(Sampler s, int n) implements  Sampler{

    public Color getColor(Vec2 p) {
        var color = Color.black;
        for (int i = 0; n != i; i++) {
            var rp = vec2(random(), random());
            var c = s.getColor(add(p,rp));
            color = add(color, c);
        }
        return divide(color, n);

    }

}
