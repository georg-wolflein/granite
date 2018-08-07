package granite.test;

import granite.engine.Engine;
import granite.engine.entities.Entity;
import granite.engine.model.Model;
import granite.engine.util.math.PolarVector3f;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Globe extends Entity {

    public Globe(Engine engine) {
        float r = 10;
        int segments = 100;
        int slices = 100;
        List<PolarVector3f> points = new LinkedList<>();

        for (int segment = 0; segment < segments; segment++) {
            double alpha = 2 * Math.PI / segments * segment;
            for (int slice = 0; slice < slices; slice++) {
                double beta = 2 * Math.PI / slices * slice;
                points.add(new PolarVector3f(r, (float) alpha, (float) beta));
            }
        }


        try {
            Model m = Model.load("dot.obj", engine.getRenderer().getEntityRenderer());
            for (PolarVector3f v : points) {
                Entity e = new Entity();
                e.addChild(new Model(m));
                e.move(v.toCartesian());
                engine.getScene().addChild(e);
            }
        } catch (IOException e) {
            System.out.println("err");
        }
    }
}
