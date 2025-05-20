package cgg.a03;

import java.util.ArrayList;

public record DrawKugeln(ArrayList<Kugel> alleKugeln){

    public Hit intersect(Ray ray){
        double t = Double.POSITIVE_INFINITY;
        Hit closestHit = null;

        for (Kugel kg : alleKugeln) { // Durch alle Kugeln iterieren und den am nächsten hit ermitteln
            Hit point = kg.intersect(ray);
            if (point != null && point.t() <= t) { // Hit darf nicht null sein und muss kleiner oder gleich unser t sein
                t = point.t(); // unser t auf unseren hit t setzen
                closestHit = point;
            }
        }

        if (closestHit != null) { // Sichergehen das hit nicht null ist, return closestHit
            return closestHit;
        }

        return null;
    }
}
