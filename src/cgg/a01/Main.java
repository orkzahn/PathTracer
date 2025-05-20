
package cgg.a01;

import cgg.Image;
import static tools.Functions.*;

public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    //var constant = new ConstantColor(new Color(0.9f, 0 , 0.9f));

    //var constant = new Circle(vec2(0, 0), 25, new Color(0.9,0,0.9));
    var constant1 = new DrawCircles(vec2(width,height),5,5);
    
    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++){
      for (int y = 0; y != height; y++){
        image.setPixel(x, y, constant1.getColor(vec2(x, y)));
      }
    }

    // Write the image to disk.
    image.writePng("a01-discs");
  }
}
