package cgg.a02;

import java.util.ArrayList;
import java.util.Random;
import tools.*;
import static tools.Functions.vec3;

public class DrawKugeln implements Sampler {
    private ArrayList<Kugel> alleKugeln = new ArrayList<>();
    private ArrayList<Hit> validHits = new ArrayList<>();

    private int amount;
    private LochKamera cam;
    private Vec2 res;

    public DrawKugeln(LochKamera cam, int totalAmount){
        this.amount = totalAmount;
        this.cam = cam;
        this.res = cam.getRes();
        //generateTestKugel();
        generateKugeln2();
    }
    public void generateTestKugel(){
        Color randomCol = new Color(Math.random(), Math.random(), Math.random());
        alleKugeln.add(new Kugel(vec3(-5,  0, -20), 4, Color.red));
        alleKugeln.add(new Kugel(vec3(0,  0, -20), 4, Color.blue));
        alleKugeln.add(new Kugel(vec3(7,  0, -40), 4, Color.green));
    }

    public void generateKugeln(){
        for (int i = 0; i < amount/2; i++) {
            for (int j = 0;  j < amount/2; j++) {
                Color randomCol = new Color(Math.random(), Math.random(), Math.random());
                Random rWidth = new Random();
                Random rHeight = new Random();
                Random rZ = new Random();
                int randomNumber = rWidth.nextInt(200 - (-200) +1) - 200;
                int randomNumber1 = rHeight.nextInt((200 - (-200)) +1) + (-200);
                int randomNumber2 = rZ.nextInt(-20 - (-200) +1) - 200;
                alleKugeln.add(new Kugel(vec3(randomNumber,  randomNumber1, randomNumber2), 4 * Math.random() * 2, randomCol));
            }
        }
    }

    public void generateKugeln2(){
        int widthHalf = (int)res.x() / 2;
        int heightHalf = (int)res.y() / 2;

        for (int i = 0; i < amount; i++) {
            Color randomCol = new Color(Math.random(), Math.random(), Math.random());
            Random rWidth = new Random();
            Random rHeight = new Random();
            Random rZ = new Random();
            int randomWidth = rWidth.nextInt(widthHalf- (-widthHalf) +1) - widthHalf;
            int randomHeight = rHeight.nextInt((heightHalf - (-heightHalf)) +1) + (-heightHalf);
            int randomZ = rZ.nextInt(-200 - (-400) +1) - 400;
            alleKugeln.add(new Kugel(vec3(randomWidth,  randomHeight, randomZ), 4 * Math.random() * 2, randomCol));
        }
    }

    public Color getColor(Vec2 p) {
        Ray ray = cam.generateRay(p);
        /* 
        for (Kugel kg : alleKugeln) {
            Hit hit = kg.intersect(ray);
            if(hit != null){  // checken ob wir mindestens eine Lösung haben
                validHits.add(hit);
                //Color col = shade(hit);
                //Color col = shade(hit);
                return new Color(hit.normal().x(), hit.normal().y(), hit.normal().z());
            }
        }*/

        // Problem davor -> Ich hab direkt die erste Kugel genommen und sofort die Farbe returned deswegen waren Sie immer verdeckt.

        double t = Double.POSITIVE_INFINITY;
        Hit closestHit = null;  

        for (Kugel kg : alleKugeln) {   // Durch alle Kugeln iterieren und den am nächsten hit ermitteln
            Hit point = kg.intersect(ray);
            if(point != null && point.t() <= t){ // Hit darf nicht null sein und muss kleiner oder gleich unser t sein 
                t = point.t(); // unser t auf unseren hit t setzen
                closestHit = point; 
            }
        }

        if(closestHit != null){ // Sichergehen das hit nicht null ist, return closestHit
            return shade(closestHit);

        }
        return Color.black;
    }

    static Color shade(Hit hit) {
        Vec3 lightDir = Functions.normalize((vec3(1, 1, 0.7)));
        Color ambient = Functions.multiply(0.1, hit.col());
        Color diffuse = Functions.multiply(0.9 * Math.max(0, Functions.dot(lightDir, hit.normal())), hit.col());
        return Functions.add(ambient, diffuse);
    }

}
