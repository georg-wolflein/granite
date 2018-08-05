package granite.engine.rendering;

import granite.engine.core.IDestroyable;
import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.shaders.Shader3D;

import java.util.Collection;

public interface IRenderer<S extends Shader3D> extends IDestroyable {

    S getShader();

    void renderBatch(Mesh mesh, Collection<Model> models, Camera camera, Light light);

    default void attach(MasterRenderer master) {
        getShader().bind();
        getShader().loadProjectionMatrix(master.getProjectionMatrix());
        getShader().unbind();
    }
}
