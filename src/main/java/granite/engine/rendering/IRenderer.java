package granite.engine.rendering;

import granite.engine.core.IDestroyable;
import granite.engine.entities.EntityManager;
import granite.engine.shaders.Shader3D;

public interface IRenderer<S extends Shader3D> extends IDestroyable {

    S getShader();

    EntityManager getEntityManager();

    default void attach(MasterRenderer master) {
        getShader().bind();
        getShader().loadProjectionMatrix(master.getProjectionMatrix());
        getShader().unbind();
    }
}
