package cgg.a02;

import cgg.Image;
import tools.Color;
import static tools.Functions.*;

public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;


    var cam = new LochKamera(vec2(10, 10),Math.PI / 2);
    System.out.println(cam.generateRay(vec2(0, 0)));
    System.out.println(cam.generateRay(vec2(5, 5)));
    System.out.println(cam.generateRay(vec2(10, 10)));


      var s1 = new Kugel(vec3(0, 0, -2), 1,Color.red);
      var s2 = new Kugel(vec3(0, -1, -2), 1, Color.blue);
      var s3 = new Kugel(vec3(0, 0, 0), 1, Color.cyan);
      var r1 = new Ray(vec3(0, 0, 0), vec3(0, 0, -1));
      var r2 = new Ray(vec3(0, 0, 0), vec3(0, 1, -1));
      System.out.println(s1.intersect(r1));
      System.out.println(s1.intersect(r2));
      System.out.println(s2.intersect(r1));
      System.out.println(s3.intersect(r1));
      
    // This class instance defines the contents of the image.
    //var constant = new ConstantColor(new Color(0.9f, 0 , 0.9f));

    //var constant = new Circle(vec2(0, 0), 25, new Color(0.9,0,0.9));
    //var constant1 = new DrawCircles(vec2(width,height),5,5);
    var cam1 = new LochKamera(vec2(width, height),Math.PI / 2);

    var constant1 = new DrawKugeln(cam1, 10000);
    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++){
      for (int y = 0; y != height; y++){
        image.setPixel(x, y, constant1.getColor(vec2(x, y)));
      }
    }

    // Write the image to disk.
    image.writePng("a02-spheres");
  }
}