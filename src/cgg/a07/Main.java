package cgg.a07;

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

        var fabric = new DiffuseMaterial(
                new ImageTexture("data/fabric4k.png"),
                new ConstantColor(Color.white),
                new ConstantColor(new Color(1000, 1000, 1000)));

        var fabric2 = new DiffuseMaterial(
                new ImageTexture("data/fabric2.png"),
                new ConstantColor(Color.white),
                new ConstantColor(new Color(1000, 1000, 1000)));

        var mirror = new MirrorMaterial();

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


        var bgEmission = new BackgroundMaterial(new ConstantColor(new Color(0.8, 0.8, 0.8)));

        var AreaLight1 = new BackgroundMaterial(new ConstantColor(Color.red));

        var sun = new DirectionalLight(vec3(1, 1, 1), Color.white);
        var pointLWhiteFront = new PointLight(new Vec3(0, 10, -2), Color.white);

        var bg = new Background(bgEmission);


        var boden = new Plane(1000, diffuseGray);

        List<Shape> flakes = createSphereflake(new Vec3(0, 0.15, 0), 0.15,
                Arrays.asList(mirror, AreaLight1, fabric, fabric2), 5);

        var teppich = new Plane(0.5, fabric2);
        Group flakesGroup = new Group(identity(), Arrays.asList(
                new Group(multiply(move(0, 0, 0), (rotate(Vec3.yAxis, -45))), flakes)));

        Group fun = new Group(multiply(move(0, 0, 0), scale(1, 1, 1)), Arrays.asList(flakesGroup));

        var teppichKugel = new Group(move(0, 0.01, -10), Arrays.asList(
                fun,
                teppich));

        Group root = new Group(identity(), Arrays.asList(
                bg, boden, fillPlane(teppichKugel, 10, 0.5)));

        Mat44 viewDir = multiply(move(0, 5, 0), rotate(Vec3.xAxis, -15));

        var raytracer = new RayTracer(
                new PinHoleCamera(vec2(width, height), 45, viewDir),
                Arrays.asList(pointLWhiteFront),
                root);

        var image = new Image(width, height);

        image.sample(new RandomSampling(raytracer, 1));
        image.writePng("a07-image");
    }

    private static Shape fillPlane(Shape s, int n, double gap) {
        if (n == 0)
            return s;

        var p1 = fillPlane(s, n - 1, gap);
        var p2 = fillPlane(s, n - 1, gap);

        var p3 = fillPlane(s, n - 1, gap);
        var p4 = fillPlane(s, n - 1, gap);
        var size = p1.bounds().size().x() + gap;
        List<Shape> allgr = Arrays.asList(
        new Group(move(-size / 2.0, 0, -size / 2.0), Arrays.asList(p1)),
            new Group(move( size / 2.0, 0, -size / 2.0), Arrays.asList(p2)),
            new Group(move(-size / 2.0, 0, size / 2.0), Arrays.asList(p3)),
            new Group(move(size / 2.0, 0, size / 2.0), Arrays.asList(p4))
        );
        
        return new Group(identity(), allgr);

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
                // new Vec3(0, -offset, 0),
                new Vec3(0, 0, offset),
                new Vec3(0, 0, -offset));

        // Rekursiv aufrufen
        for (Vec3 offsetVec : offsets) {
            Vec3 newCenter = add(center, offsetVec);
            spheres.addAll(createSphereflake(newCenter, childRadius, materials, depth - 1));
        }

        return spheres;
    }

}
