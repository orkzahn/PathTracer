package cgg.a05;

import cgg.Image;
import cgg.a01.ConstantColor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tools.Color;
import static tools.Functions.*;
import tools.ImageTexture;
import tools.Mat44;
import tools.Vec3;

public class Main {

    public static void main(String[] args) {
        int width = 1024;
        int height = 1024;

        var venus = new TexturedPhongMaterial(
                        new ImageTexture("data/venus.jpg"),
                        new ConstantColor(Color.white),
                        new ConstantColor(new Color(1000, 1000, 1000)));  

        var moon = new TexturedPhongMaterial(
                            new ImageTexture("data/moon.jpg"),
                            new ConstantColor(Color.white),
                            new ConstantColor(new Color(1000, 1000, 1000)));  

        var earth = new TexturedPhongMaterial(
                                new ImageTexture("data/earth.png"),
                                new ConstantColor(Color.white),
                                new ConstantColor(new Color(1000, 1000, 1000)));  


        var floor = new Sphere(vec3(0, -1001, -5), 1000,
                        new PhongMaterial(color(0.8,0.8,0.8), Color.black, 0));

        var globe3 = new Sphere(vec3(-0.5,-0.5,-5), 0.5,earth);
        var globe4 = new Sphere(vec3(0,0.4,-5), 0.5,moon);
        var globe5 = new Sphere(vec3(0.5,-0.5,-5), 0.5,venus);


        var sun = new DirectionalLight(vec3(1,1,1), Color.white);
        var pointLBlue = new PointLight(new Vec3(20, 1, -40), Color.blue);
        var pointLRed = new PointLight(new Vec3(0, 1, -10), Color.red);
        var pointLGreen = new PointLight(new Vec3(13, 1, -14), Color.green);
        var pointLMagenta = new PointLight(new Vec3(0,15,-20), Color.magenta);
        var pointLYellow = new PointLight(new Vec3(5,5,-28), Color.yellow);
        var pointLWhite = new PointLight(new Vec3(-5,12,0), Color.white);



        List<Shape> kugeln = new ArrayList<>();
        kugeln.add(globe3);
        kugeln.add(globe4);
        kugeln.add(globe5);


        Group frontalTriangle = new Group(identity(), Arrays.asList(
              new Group(identity(), kugeln),
                new Group(multiply(move(1.5,-0.1,0),rotate(Vec3.zAxis, 180)), kugeln),
                new Group(multiply(move(-1.5,-0.1,0),rotate(Vec3.zAxis, -180)), kugeln)
        ));

        Group alotTrianglesGroup = new Group(identity(), Arrays.asList(
            new Group(identity(), frontalTriangle.getShapes()),
                new Group(move(0,0,-10), frontalTriangle.getShapes()),
                new Group(multiply(move(0,0,-10), rotate(Vec3.yAxis, 90)), frontalTriangle.getShapes()),
                new Group(multiply(move(0,0,-10), rotate(Vec3.yAxis, 270)), frontalTriangle.getShapes())
        ));

        Group ringGroup = new Group(identity(), Arrays.asList(
            new Group(multiply(move(0,-0.2,-2),scale(0.8,0.8,0.8)), alotTrianglesGroup.getShapes()),
                new Group(multiply(multiply(move(0,0,-15), scale(0.1,0.1,0.1)), rotate(Vec3.xAxis, 90)), alotTrianglesGroup.getShapes()),
                new Group(multiply(multiply(move(0,-5,-15), scale(0.6,0.6,0.6)), rotate(Vec3.xAxis, 90)), alotTrianglesGroup.getShapes()),
                new Group(multiply(move(20,2.5,-7),scale(4,4,4)), alotTrianglesGroup.getShapes()),
                new Group(multiply(move(20,0,-7),rotate(Vec3.yAxis, 45)), alotTrianglesGroup.getShapes())
        ));

        Group root = new Group(identity(), Arrays.asList(
            ringGroup,floor));


        //Mat44 viewDir = multiply(move(-10,5,0), multiply(rotate(Vec3.yAxis, -40), rotate(Vec3.xAxis, -20)));
        Mat44 viewDir = multiply(move(-8,4,-5), multiply(rotate(Vec3.yAxis, -55), rotate(Vec3.xAxis, -20)));



        var raytracer = new RayTracer(
                            new PinHoleCamera(vec2(width, height), 45,viewDir), 
                            Arrays.asList( pointLRed,pointLGreen,pointLBlue,pointLMagenta,pointLYellow,pointLWhite),
                            root);
                        

        var image = new Image(width, height);

        image.sample(new RandomSampling(raytracer, 10));
        image.writePng("a05-image");
    }
}
