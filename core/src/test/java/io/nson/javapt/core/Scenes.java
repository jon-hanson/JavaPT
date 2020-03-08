package io.nson.javapt.core;

import io.nson.javapt.core.Shapes.*;
import io.nson.javapt.geom.*;
import io.nson.javapt.sdf.*;
import org.typemeta.funcj.json.model.JsValue;

import java.util.*;

import static io.nson.javapt.core.Material.*;

public abstract class Scenes {
    public static void main(String[] args) {
        //SceneIO.save(cornell(), "scenes/cornell.json");
        //SceneIO.save(cornell2(), "scenes/cornell2.json");
        SceneIO.save(cornell3(), "scenes/cornell3.json");
        //SceneIO.save(horizon(), "scenes/horizon.json");
        //SceneIO.save(redGreenBlue(), "scenes/rgb.json");
    }

    public static Scene cornell() {
        final List<Shape> objects =
                Arrays.asList(
                        new Plane("left", diffuse(0.75, 0.25, 0.25), Plane.Axis.X, true, 1),
                        new Plane("right", diffuse(0.25, 0.25, 0.75), Plane.Axis.X, false, 99),
                        new Plane("back", diffuse(0.75, 0.75, 0.75), Plane.Axis.Z, true, 0),
                        new Plane("front", diffuse(RGB.BLACK), Plane.Axis.Z, false, 170),
                        new Plane("bottom", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, true, 0),
                        new Plane("top", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, false, 81.6),

                        new Sphere("mirror", reflective(RGB.WHITE.mult(0.999)), new Point3d(27, 16.5, 47), 16.5),
                        new Sphere("glass", refractive(RGB.WHITE.mult(0.999)), new Point3d(73, 16.5, 78), 16.5),

                        new Sphere("light", emissive(RGB.WHITE.mult(12)), new Point3d(50, 681.6 - 0.27, 81.6), 600.0)
                );

        return new Scene(
            new Camera(
                    Ray.of(
                            new Point3d(50, 52, 295.6),
                            new Vector3d(0, -0.042612, -1)
                    ), 0.5135
            ), objects);
    }

    public static Scene cornell2() {
        final List<Shape> objects =
                Arrays.asList(
                        new Plane("left", diffuse(0.75, 0.25, 0.25), Plane.Axis.X, true, 1),
                        new Plane("right", diffuse(0.25, 0.25, 0.75), Plane.Axis.X, false, 99),
                        new Plane("back", diffuse(0.75, 0.75, 0.75), Plane.Axis.Z, true, 0),
                        new Plane("front", diffuse(RGB.BLACK), Plane.Axis.Z, false, 170),
                        new Plane("bottom", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, true, 0),
                        new Plane("top", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, false, 81.6),

                        new Sphere("mirror", reflective(RGB.WHITE.mult(0.999)),new Point3d(27, 60, 47), 16.5),
                        new Sphere("glass", refractive(RGB.WHITE.mult(0.999)),new Point3d(73, 16.5, 78), 16.5),

                        new Sphere("diff", diffuse(0.75, 0.75, 0.25),new Point3d(27, 16.5, 100), 16.5),

                        new Sphere("light", emissive(RGB.WHITE.mult(12)),new Point3d(50, 681.6 - 0.27, 81.6), 600.0)
                );
        return new Scene(
            new Camera(
                    Ray.of(
                            new Point3d(50, 52, 295.6),
                            new Vector3d(0, -0.042612, -1)
                    ), 0.5135
            ), objects);
    }

    public static Scene cornell3() {
        final SDFAlgebra<JsValue> sdfAlg = SDFSerialise.INSTANCE;
        final List<Shape> objects =
                Arrays.asList(
                        new Plane("left", diffuse(0.75, 0.25, 0.25), Plane.Axis.X, true, 1),
                        new Plane("right", diffuse(0.25, 0.25, 0.75), Plane.Axis.X, false, 99),
                        new Plane("back", diffuse(0.75, 0.75, 0.75), Plane.Axis.Z, true, 0),
                        new Plane("front", diffuse(RGB.BLACK), Plane.Axis.Z, false, 170),
                        new Plane("bottom", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, true, 0),
                        new Plane("top", diffuse(0.75, 0.75, 0.75), Plane.Axis.Y, false, 81.6),

                        new Sphere("mirror", reflective(RGB.WHITE.mult(0.999)),new Point3d(27, 60, 47), 16.5),
                        new Sphere("glass", refractive(RGB.WHITE.mult(0.999)),new Point3d(73, 16.5, 78), 16.5),

                        new SDFShape("diff", diffuse(0.75, 0.75, 0.25),
                                new DeferredSDF(
                                        sdfAlg.subtract(
                                            sdfAlg.displace(sdfAlg.sphere(16.5), new Vector3d(27, 16.5, 100)),
                                            sdfAlg.displace(sdfAlg.sphere(10), new Vector3d(30, 16.5, 100))
                                        )
                                )
                        ),

                        new Sphere("light", emissive(RGB.WHITE.mult(12)),new Point3d(50, 681.6 - 0.27, 81.6), 600.0)
                );
        return new Scene(
                new Camera(
                        Ray.of(
                                new Point3d(50, 52, 295.6),
                                new Vector3d(0, -0.042612, -1)
                        ), 0.5135
                ), objects);
    }
    public static Scene horizon() {
        final double W = 100.0;
        final double W2 = W * 2.0 / 3.0;
        final double D = W / 2.0;
        final double R = W / 4.0;
        final double R2 = W * 16.0;
        final Point3d centre = new Point3d(0.0, R, 0.0);

        final RGB sky = new RGB(135.0/256, 206.0/256, 250.0/256);

        final List<Shape> objects =
                Arrays.asList(
                        new Plane("ground", diffuse(RGB.WHITE.mult(0.999)), Plane.Axis.Y, true, 0.0),
                        new Sphere("refl", reflective(RGB.WHITE.mult(0.999)),new Point3d(0.0, W2, -W2), W2),
                        new Sphere("lglass", refractive(0.75, 0.25, 0.25),new Point3d(-W2, R, W), R),
                        new Sphere("mglass", refractive(0.25, 0.75, 0.25),new Point3d(0, R, W * (3.0 / 2.0)), R),
                        new Sphere("rglass", refractive(0.25, 0.25, 0.75),new Point3d(W2, R, W), R),
                        new Sphere("light", emissive(RGB.WHITE.mult(12.0)), centre.add(Vector3d.Y_UNIT.mult(W * 2)), R),
                        new Sphere("sky", diffuse(sky),new Point3d(0.0, W * 3 + R2, 0.0), R2)
                );

        final Point3d cam = new Point3d(0.0, W * 1.5, 4 * W);
        final Point3d lookAt = new Point3d(0.0, R, 0.0);

        return new Scene(
                new Camera(
                        Ray.of(
                                cam,
                                lookAt.sub(cam)
                        ), 0.7
                ), objects);
    }

    public static Scene redGreenBlue() {
        final double cos30 = Math.cos(Math.PI / 6.0);
        final double sin30 = Math.sin(Math.PI / 6.0);

        final double W = 100.0;
        final double W2 = W * 2.0 / 3.0;
        final double D = W / 2.0;
        final double R = W / 4.0;
        final double R2 = W * 16.0;

        final RGB sky = new RGB(135.0/256, 206.0/256, 250.0/256);

        final double circR = W2;

        final Point3d pL = new Point3d(circR * -cos30, R, circR * sin30);
        final Point3d pM = new Point3d(0, R, -circR);
        final Point3d pR = new Point3d(circR * cos30, R, circR * sin30);

        final List<Shape> objects =
                Arrays.asList(
                        new Plane("ground", diffuse(RGB.WHITE.mult(0.999)), Plane.Axis.Y, true, 0.0),

                        new Sphere("lglass", refractive(0.75, 0.25, 0.25), pL, R),
                        new Sphere("mglass", refractive(0.25, 0.75, 0.25), pM, R),
                        new Sphere("rglass", refractive(0.25, 0.25, 0.75), pR, R),

                        new Sphere("llight", emissive(RGB.WHITE.mult(12.0)), pL.mult(2.0), R / 2.0),
                        new Sphere("mlight", emissive(RGB.WHITE.mult(12.0)), pM.mult(2.0), R / 2.0),
                        new Sphere("rlight", emissive(RGB.WHITE.mult(12.0)), pR.mult(2.0), R / 2.0),

                        //Sphere("light", emissive(RGB.WHITE * 12.0),new Point3d(0.0, W * 2, 0.0), R),

                        new Sphere("sky", diffuse(sky),new Point3d(0.0, W * 3 + R2, 0.0), R2)
                );

        final Point3d cam = new Point3d(0.0, W * 2.5, 1.5 * W);
        final Point3d lookAt = new Point3d(0.0, R, 0.0);

        return new Scene(
                new Camera(
                        Ray.of(
                                cam,
                                lookAt.sub(cam)
                        ), 0.6
                ), objects);
    }
}
