package granite.test;

import granite.engine.Engine;
import granite.engine.entities.Entity;
import granite.engine.model.Model;
import granite.engine.util.math.PolarVector3f;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Globe extends Entity {

    private class Face {
        private Vector3f x1, x2, y1, y2;

        public Face(Vector3f x1, Vector3f x2, Vector3f y1, Vector3f y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    public Globe(Engine engine) {
        float r = 10;
        int segments = 50;
        int slices = 50;
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
