package io.nson.javapt.core;

public interface Material {

    static Material diffuse(double r, double g, double b) {
        return new Diffuse(new RGB(r, g, b), RGB.BLACK);
    }

    static Material diffuse(RGB colour) {
        return new Diffuse(colour, RGB.BLACK);
    }

    static Material emissive(double r, double g, double b) {
        return new Diffuse(RGB.BLACK, new RGB(r, g, b), true);
    }

    static Material emissive(RGB colour) {
        return new Diffuse(RGB.BLACK, colour, true);
    }

    static Material refractive(double r, double g, double b) {
        return new Refractive(new RGB(r, g, b), RGB.BLACK);
    }

    static Material refractive(RGB colour) {
        return new Refractive(colour, RGB.BLACK);
    }

    static Material reflective(double r, double g, double b) {
        return new Reflective(new RGB(r, g, b), RGB.BLACK);
    }

    static Material reflective(RGB colour) {
        return new Reflective(colour, RGB.BLACK);
    }

    RGB colour();
    
    RGB emColour();
    
    default RGB emission() {
        return emColour();
    }
    
    RGB radiance(
            RNG rng,
            Renderer rdr,
            Ray ray,
            int depth,
            Point3d p,
            Vector3d n,
            Vector3d nl,
            RGB acc,
            RGB att
    );
}
