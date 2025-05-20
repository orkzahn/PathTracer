package cgg.a09;

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
import tools.Vertex;
import tools.Wavefront;

public class Main {

    public static void main(String[] args) {
        int width = 1500;
        int height = 1500;

        var pengiunDiffuse = new DiffuseMaterial(
                new ImageTexture("data/test.png"),
                new ImageTexture("data/wowemission.png"),
                new ConstantColor(new Color(1000, 1000, 1000)));

        var roboterDiffuse = new DiffuseMaterial(
                    new ImageTexture("data/test.png"),
                    new ConstantColor(Color.white),
                    new ConstantColor(new Color(1000, 1000, 1000)));

        var blank = new DiffuseMaterial(
                    new ConstantColor(Color.gray),
                        new ConstantColor(Color.white),
                        new ConstantColor(new Color(1000, 1000, 1000)));

        var mirror = new MirrorMaterial();
        var bgEmission = new BackgroundMaterial(new ConstantColor(new Color(1, 1, 1)));

        var sun = new DirectionalLight(vec3(-2, 5, 0), Color.white);
        var pointLWhiteFront = new PointLight(new Vec3(0, 10, -3), Color.green);

        var bg = new Background(bgEmission);
        var boden = new Plane(100000, mirror);

            
        var pengiunMesh = triangleToMesh("data/test.obj", roboterDiffuse);
        var robotMesh =  triangleToMesh("data/rob.obj", roboterDiffuse);

        var pengiunGroup = new Group(multiply(move(0,0,-395),scale(10,10,10) ), Arrays.asList(pengiunMesh));
        var roboterGroup = new Group(multiply(move(80,0,-50),scale(0.4,0.4,0.4) ), Arrays.asList(robotMesh));


        var home = new Group(rotate(Vec3.yAxis, 180), Arrays.asList(pengiunMesh));


        var pengiunStressTest = fillPlane(home, 6, 2);
        var robotStressTest = fillPlane(roboterGroup, 3, 2);


        
        Group root = new Group(identity(), Arrays.asList(
                 boden,bg,home));
                 
       //Mat44 viewDir = multiply(multiply(rotate(Vec3.yAxis,-40), rotate(Vec3.xAxis, -19)), move(0,40,-65));
        Mat44 viewDir = multiply(move(0,7,20), rotate(Vec3.xAxis, -20));
        var raytracer = new RayTracer(
                new PinHoleCamera(vec2(width, height), 45, viewDir),
                Arrays.asList(pointLWhiteFront),
                root);

        var image = new Image(width, height);

        image.sample(new RandomSampling(raytracer, 1));

        image.writePng("a09-image");
    }

    public static TriangleMesh triangleToMesh(String obj, Material mat){
        List<Triangle> triangles = new ArrayList<>();
        List<Wavefront.MeshData> dataList = Wavefront.loadMeshData(obj);

             for (Wavefront.MeshData meshData : dataList) {
                System.out.println("triangles: " + meshData.triangles().size());
                for (Wavefront.TriangleData triangle : meshData.triangles()) {
                    Vertex v0 = new Vertex(triangle.v0().position(),triangle.v0().normal(), triangle.v0().uv());
                    Vertex v1 = new Vertex(triangle.v1().position(),triangle.v1().normal(), triangle.v1().uv());
                    Vertex v2 = new Vertex(triangle.v2().position(),triangle.v2().normal(), triangle.v2().uv());

                    Triangle triangle2 = new Triangle(v0, v1, v2);
                    triangles.add(triangle2);
                }
            }

            return new TriangleMesh(triangles, mat);

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

}
