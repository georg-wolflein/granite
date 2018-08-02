package granite.test;

import granite.engine.Engine;
import granite.engine.IGame;
import granite.engine.entities.Camera;
import granite.engine.entities.Entity;
import granite.engine.input.Input;
import granite.engine.model.Model;
import org.joml.Vector3f;
import org.junit.Test;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class EngineTest {

    @Test
    public void runEngine() {

        Engine engine = new Engine(new IGame() {

            Entity entity1, entity2;

            @Override
            public void attach(Engine engine) {
////                TexturedMesh vao = new TexturedMesh(new float[]{0f, 0f, 0f, -.5f, 0f, 0f, -.5f, -.5f, 0f, 0f, -.5f, 0f, 0f, 0f, 1f, -.5f, 0f,
////                        1f, -.5f, -.5f, 1f, 0f, -.5f, 1f}, new float[]{0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
////                        0, 1, 1, 0, 0, 1}, new int[]{0, 1, 2, 0, 2, 3, 0, 1, 4, 4, 1, 5, 4, 5, 6, 4, 6, 7});
////                Texture texture = new Texture("texture1.png");
////                Model model = new Model(vao, texture);
//                TexturedMesh vao = engine.getModelManager().loadVAO("donut.obj");
//                Model model = new Model(vao, null);
//                entity1 = engine.getEntityManager().addEntity(model, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 1);
//                entity2 = engine.getEntityManager().addEntity(model, new Vector3f(0, 1, -1), new Vector3f(0, 1, 0), 1);
                try {
                    Model model = engine.getModelManager().load("test", "cubes.obj");
                    entity1 = engine.getEntityManager().addEntity(model, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 1);
                    entity1 = engine.getEntityManager().addEntity(model, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void update(Engine engine) {
                Input input = engine.getInput();
                Camera camera = engine.getCamera();
                float x = 0, y = 0, z = 0, rx = 0, ry = 0, rz = 0;
                if (input.isPressed(GLFW_KEY_W)) {
                    z = -.02f;
                }
                if (input.isPressed(GLFW_KEY_A)) {
                    x = -.02f;
                }
                if (input.isPressed(GLFW_KEY_S)) {
                    z = .02f;
                }
                if (input.isPressed(GLFW_KEY_D)) {
                    x = .02f;
                }
                if (input.isPressed(GLFW_KEY_UP)) {
                    rx = -1f;
                }
                if (input.isPressed(GLFW_KEY_DOWN)) {
                    rx = 1f;
                }
                if (input.isPressed(GLFW_KEY_LEFT)) {
                    ry = -1f;
                }
                if (input.isPressed(GLFW_KEY_RIGHT)) {
                    ry = 1f;
                }
                camera.move(new Vector3f(x, y, z));
                camera.rotate(new Vector3f(rx, ry, rz));

                entity1.move(new Vector3f(0, 0, -.1f));
                //entity2.rotate(new Vector3f(1, 0, 0));
            }

            @Override
            public void destroy() {

            }
        });

        engine.start();
    }
}
