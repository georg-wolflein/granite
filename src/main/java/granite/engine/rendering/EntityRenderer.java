package granite.engine.rendering;

import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.model.Material;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.shaders.EntityShader;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer extends BatchRenderer<EntityShader> {

    private static final float RENDER_DISTANCE = 1000;

    public EntityRenderer() {
        super(new EntityShader());
    }

    @Override
    public void renderBatch(Mesh mesh, Collection<Model> models, Camera camera, Light light) {
        getShader().bind();
        getShader().loadViewMatrix(camera);
        Material mtl = mesh.getMaterial();
        getShader().loadDiffuseColor(mtl.getDiffuseColor());
        getShader().loadSpecularVariables(mtl.getSpecularColor(), mtl.getSpecularExponent());
        getShader().loadLight(light);
        mesh.bind();
        mesh.enableArrays();
        for (Model model : models) {
            // TODO: render distance check
            getShader().loadTransformationMatrix(model.getAbsoluteTransformation());
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        }
        mesh.disableArrays();
        mesh.unbind();
    }
}
