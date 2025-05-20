package cgg.a03;

import cgg.Image;
import java.util.ArrayList;
import tools.Color;
import static tools.Functions.*;

public class Main {

    public static void main(String[] args) {
        int width = 1024;
        int height = 1024;

        var cam = new PinHoleCamera(vec2(width, height), Math.PI / 2);

        ArrayList<Kugel> alleKugeln = new ArrayList<>();
        ArrayList<LightSource> allLights = new ArrayList<>();

        //allLights.add(new DirectionalLight(vec3(1,1,0.7), Color.white));
        //allLights.add(new DirectionalLight(vec3(1,1,0.7), Color.red));
        //allLights.add(new DirectionalLight(vec3(-1,1,0.7), Color.green));
        //allLights.add(new DirectionalLight(vec3(0,1,0), Color.blue));

        allLights.add(new PointLight(vec3(0, 10, -10), Color.green));
        allLights.add(new PointLight(vec3(0, 0, -10), Color.magenta));
        allLights.add(new PointLight(vec3(0, 10, -20), Color.red));

        //alleKugeln.add(new Kugel(vec3(0, 0, -10), 4, new Color(0.8, 0.8, 0.8)));
        alleKugeln.add(new Kugel(vec3(0, -29994, -800), 30000, new Color(0.8, 0.8, 0.8)));

        alleKugeln.add(new Kugel(vec3(4, 0, -25), 4, Color.white));
        alleKugeln.add(new Kugel(vec3(-4, 0, -25), 4, Color.white));
        alleKugeln.add(new Kugel(vec3(5, -1, -20), 3, Color.red));
        alleKugeln.add(new Kugel(vec3(-5, -1, -20), 3, Color.red));
        alleKugeln.add(new Kugel(vec3(6, -2, -15), 2, Color.blue));
        alleKugeln.add(new Kugel(vec3(-6, -2, -15), 2, Color.blue));
        alleKugeln.add(new Kugel(vec3(7, -3, -12), 1, Color.green));
        alleKugeln.add(new Kugel(vec3(-7, -3, -12), 1, Color.green));

        var scene = new DrawKugeln(alleKugeln);
        var image = new Image(width, height);

        image.sample(new RayTracer(cam, scene, allLights));

        // Write the image to disk.
        image.writePng("a03-spheres");
    }
}
