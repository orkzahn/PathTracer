package cgg.a06;

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
        int width = 3840;
        int height = 2160;

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

        var fabric = new DiffuseMaterial(
                                    new ImageTexture("data/fabric4k.png"),
                                    new ConstantColor(Color.white),
                                    new ConstantColor(new Color(1000, 1000, 1000)));  

        var fabric2 = new DiffuseMaterial(
                                        new ImageTexture("data/fabric2.png"),
                                        new ConstantColor(Color.white),
                                        new ConstantColor(new Color(1000, 1000, 1000)));  

        
        var mirror = new MirrorMaterial();
        var phongWhite =new PhongMaterial(Color.white, Color.white, 1000.0);
        var phongBlack =new PhongMaterial(Color.black, Color.white, 1000.0);
        var diffuseGray = new DiffuseMaterial(
            new ConstantColor(Color.gray),
            new ConstantColor(Color.white),
            new ConstantColor(new Color(1000, 1000, 1000)));

        var diffuseblack = new DiffuseMaterial(new ConstantColor(Color.magenta), 
                                               new ConstantColor(Color.white),
                                               new ConstantColor(new Color(1000, 1000, 1000)));

        var diffuse = new DiffuseMaterial(new ConstantColor(Color.blue), 
        new ConstantColor(Color.white),
        new ConstantColor(new Color(1000, 1000, 1000)));

        //var bgEmission = new BackgroundMaterial(new ConstantColor(new Color(0, 0.2, 1)));
        var bgEmission = new BackgroundMaterial(new ConstantColor(new Color(0,0.0,0.0)));

        var AreaLight1 = new BackgroundMaterial(new ConstantColor(Color.red));
        var AreaLight2 = new BackgroundMaterial(new ConstantColor(new Color(1, 0.65, 0)));
        var AreaLight3 = new BackgroundMaterial(new ConstantColor(Color.green));


        var eyesEmission = new BackgroundMaterial(new ConstantColor(Color.magenta));

        var sun = new DirectionalLight(vec3(1,1,1), Color.white);
        var pointLWhiteFront = new PointLight(new Vec3(0,10,-2), Color.white);


        var bg = new Background(bgEmission);

        var floor = new Sphere(vec3(0, -1001, -5), 1000,
        diffuseGray);

        var globe3 = new Sphere(vec3(-0.5,-0.5,-5), 0.5,earth);
        var globe4 = new Sphere(vec3(0,0.4,-5), 0.5,moon);
        var globe5 = new Sphere(vec3(0.5,-0.5,-5), 0.5,venus);
        var sphereMirror = new Sphere(vec3(0,1,-7), 2,mirror);
        var sphereMirror2 = new Sphere(vec3(0,1,2.8), 2,mirror);
        var sphereMirror3 = new Sphere(vec3(5,1,-2), 2,mirror);
        var sphereMirror4 = new Sphere(vec3(-5,1,-2), 2,mirror);


        var body = new Sphere(vec3(0,0,-5), 0.5,diffuse);
        var upperBody = new Sphere(vec3(0,0.6,-5), 0.4,diffuse);
        var rEye = new Sphere(vec3(0.2,0.8,-4.7), 0.05,AreaLight2);
        var lEye = new Sphere(vec3(-0.2,0.8,-4.7), 0.05,AreaLight2);

        var emissionLight = new Sphere(vec3(-1,-0.8,-4), 0.2,AreaLight1);
        var emissionLight2 = new Sphere(vec3(1,-0.8,-4), 0.2,AreaLight1);
        var emissionLight3 = new Sphere(vec3(0,0,-30), 10,AreaLight2);
        var emissionLight4 = new Sphere(vec3(-1,-0.8,0), 0.2,AreaLight3);
        var emissionLight5 = new Sphere(vec3(1,-0.8,0), 0.2,AreaLight3);
        var emissionLight6 = new Sphere(vec3(-0.25,1.3,-1), 0.05,AreaLight1);

        var sphereFlakes2 = new Sphere(vec3(-0.25,1.3,-1.6), 0.05,AreaLight1);




        List<Shape> mirrors = Arrays.asList(sphereMirror,sphereMirror2,sphereMirror3,sphereMirror4);
        List<Shape> areaLights = Arrays.asList(emissionLight,emissionLight2,emissionLight3,emissionLight4,emissionLight5);
        List<Shape> kugeln = Arrays.asList(body, upperBody,lEye,rEye);
        List<Shape> flakes = createSphereflake(new Vec3(0, 0, -4), 0.3, Arrays.asList(mirror,AreaLight1,fabric,fabric2), 5);
        List<Shape> spheresPlane = createPlanePattern(vec3(0,0,0), 0.5, 1, 10);

        Group mirrorsGroup = new Group(identity(), mirrors);
        Group areaLightsGroup = new Group(identity(), areaLights);


        Group snowManGroup = new Group(identity(), Arrays.asList(
                new Group(move(0.7,-0.6,0),kugeln),
                new Group(multiply(move(-0.7,-0.6,0), rotate(Vec3.zAxis, 90)),kugeln)
        ));

        Group spheresGroup = new Group(multiply(move(-10,20,0),rotate(Vec3.xAxis, 90)), spheresPlane);

        Group snowManGroup2 = new Group(identity(), Arrays.asList(
            new Group(multiply(move(0.25,1.08,-1.55), scale(0.03,0.03,0.03)),kugeln),
            new Group(multiply(move(-0.25,1.08,-1.55), scale(0.03,0.03,0.03)),kugeln)

    ));

        Group flakesGroup = new Group(identity(), Arrays.asList(
            //new Group(multiply(move(3.3,0.5,0), rotate(Vec3.yAxis, 45)), flakes),
                 //new Group(multiply(move(-0.5,0.5,0), rotate(Vec3.yAxis, 45)), flakes)
                 new Group(multiply(move(-2.82,1,0.8), (rotate(Vec3.yAxis, -45))), flakes)
       ));
       
       Group fun = new Group(multiply(move(0,0.9,-1),scale(0.2,0.2,0.2)), Arrays.asList(flakesGroup,snowManGroup2));



        Group root = new Group(identity(), Arrays.asList(
            bg,snowManGroup2,flakesGroup,spheresGroup,fun));

        //Mat44 viewDir = multiply(move(0,2,1), rotate(Vec3.xAxis, -20));
        Mat44 viewDir = multiply(move(0,1.25,-1), rotate(Vec3.xAxis, -15));


        var raytracer = new RayTracer(
                            new PinHoleCamera(vec2(width, height), 45,viewDir), 
                            Arrays.asList(pointLWhiteFront),
                            root);
                        

        var image = new Image(width, height);

        image.sample(new RandomSampling(raytracer, 10));
        image.writePng("a06-image");
    }


    private static List<Shape> createSphereflake(Vec3 center, double radius, List<Material> materials, int depth) {
        List<Shape> spheres = new ArrayList<>();
        Material material = materials.get(0);

        if (depth == 0) {
            return spheres;
        }
        
        switch (depth) {
            case 1:
            material = materials.get(1);
                break;
            case 2:
                material = materials.get(3);
                break;
            case 3:
                material = materials.get(0);
                break;
            case 4:
                material = materials.get(2);
                break;
            case 5:
                material = materials.get(0);
                break;
            case 6:
                material = materials.get(1);
                break;
            default:
                break;
        }

        spheres.add(new Sphere(center, radius, material));

    
        // Radius für den nächsten verkleinern
        double childRadius = radius * 0.3;
        double offset = radius + childRadius; // Offset für den Abstand
    
        // Positionen für die neue Kugel festlegen -> 6 Punkte an einer Kugel
        List<Vec3> offsets = Arrays.asList(
            new Vec3(offset, 0, 0),
                new Vec3(-offset, 0, 0),
                new Vec3(0, offset, 0),
                new Vec3(0, -offset, 0),
                new Vec3(0, 0, offset),
                new Vec3(0, 0, -offset)
        );

        // Rekursiv aufrufen
        for (Vec3 offsetVec : offsets) {
            Vec3 newCenter = add(center, offsetVec);
            spheres.addAll(createSphereflake(newCenter, childRadius, materials, depth-1));
        }
    
        return spheres;
    }

    private static List<Shape> createPlanePattern(Vec3 center, double radius, int spacing, int amount){
        List<Shape> spheres = new ArrayList<>();

        int rows = amount;
        int cols = amount;
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = center.x() + col * (2 * radius + spacing);
                double y = center.y();
                double z = center.z() + row * (2 * radius + spacing);
                Vec3 position = new Vec3(x, y, z);

                spheres.add(new Sphere(position, radius,  emmissionMaterial(Color.white)));
            }
        }
        
        return spheres;
    }

    private static Material emmissionMaterial(Color color){
        return new BackgroundMaterial(new ConstantColor(color));
    }

    private static Material diffuseMaterial(String filename){
        return new DiffuseMaterial(new ImageTexture(filename), 
        new ConstantColor(Color.white),
        new ConstantColor(new Color(1000, 1000, 1000)));
    }

    private static Material MirrorMaterial(){
        return new MirrorMaterial();

    }
}
