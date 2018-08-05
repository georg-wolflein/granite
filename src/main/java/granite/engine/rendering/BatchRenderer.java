package granite.engine.rendering;

import granite.engine.shaders.Shader3D;

public abstract class BatchRenderer<S extends Shader3D> implements IRenderer<S> {

    private S shader;

    public BatchRenderer(S shader) {
        this.shader = shader;
    }

    @Override
    public S getShader() {
        return shader;
    }

    @Override
    public void destroy() {
        shader.destroy();
    }
}
