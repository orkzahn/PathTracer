package cgg;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import tools.*;
import static tools.Functions.vec2;

public class Image implements tools.Image {

    // ---8<--- missing-implementation
    // TODO Provides storage for the image data. For each pixel in the image
    // three double values are needed to store the pixel components.
    private int width;
    private int height;
    private double[] allPixels;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.allPixels = new double[width * height * 3];
    }

    // TODO Stores the RGB color components for one particular pixel addressed
    // by it's coordinates in the image.#
    public void setPixel(int x, int y, Color color) {
        int index = 3 * ((height - y - 1) * width + x);

        allPixels[index] = color.r();
        allPixels[index + 1] = color.g();
        allPixels[index + 2] = color.b();
    }

    // TODO Retrieves the RGB color components for one particular pixel addressed
    // by it's coordinates in the image.
    public Color getPixel(int x, int y) {
        return Color.black;
    }
    // --->8---

    public void writePng(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePng(String name, double[] data, int width, int height) to
        // write the image data to disk in PNG format.
        ImageWriter.writePng(name, allPixels, width, height);
    }

    public void writeHdr(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePng(String name, double[] data, int width, int height) to
        // write the image data to disk in OpenEXR format.
        ImageWriter.writeHdr(name, allPixels, width, height);
    }

    public int width() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual width of the Image is
        // returned.
        return width;
    }

    public int height() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual height of the Image is
        // returned.
        return height;
    }

    public void sample(Sampler sample) {
        var watch = new StopWatch();

        AtomicInteger progressCounter = new AtomicInteger(0);

        Stream.iterate(0, y -> y != height, y -> y + 1)
                .unordered().parallel()
                .forEach(y -> { Stream.iterate(0, x -> x != width, x -> x + 1)
                            .forEach(x -> { setPixel(x, y, sample.getColor(vec2(x, y)));
                                int currProgress = progressCounter.incrementAndGet();
                                if (currProgress % (width * height / 100) == 0) {
                                    double progress = (currProgress / (double) (width * height)) * 100;
                                    System.out.printf("\rProgress: %.2f%%", progress);
                                }
                            });
                });

        System.out.println("");
        watch.stop("sample");
    }
}
