package cgg.a04;

import cgg.Image;
import cgg.a01.ConstantColor;
import java.util.Arrays;
import tools.Color;
import static tools.Functions.*;
import tools.ImageTexture;
import tools.Vec3;

public class Main {

    public static void main(String[] args) {
        int width = 1024;
        int height = 1024;

        var earth = new TexturedPhongMaterial(
                        new ImageTexture("data/earth.png"),
                        new ConstantColor(Color.white),
                        new ConstantColor(new Color(1000, 1000, 1000)));  

        var checker = new TexturedPhongMaterial(
                            new ImageTexture("data/checker.png"),
                            new ConstantColor(Color.white),
                            new ConstantColor(new Color(1000, 1000, 1000)));  

        var floor = new Sphere(vec3(0, -1001, -5), 1000,
                        new PhongMaterial(color(0.8,0.8,0.8), Color.black, 0));

        var globe = new Sphere(vec3(4,2,-15), 3,earth);
        var globe2 = new Sphere(vec3(-4,2,-15), 3,checker);
        var globe3 = new Sphere(vec3(-1,-.5,-5), 0.5,earth);
        var globe4 = new Sphere(vec3(1,-.5,-5), 0.5,checker);

        var sun = new DirectionalLight(vec3(1,1,1), Color.white);
        var pointLRed = new PointLight(new Vec3(1, 5, -13), Color.blue);
        var pointLWhite = new PointLight(new Vec3(1, 2, 0), Color.magenta);

        var scene = new DrawKugeln(Arrays.asList(floor,globe,globe2,globe3,globe4));


        var raytracer = new RayTracer(
                            new PinHoleCamera(vec2(width, height), 45), 
                            Arrays.asList(pointLRed, pointLWhite),
                            scene);
                        

        var image = new Image(width, height);

        image.sample(new RandomSampling(raytracer, 1000));
        image.writePng("a04-image");
    }
}
