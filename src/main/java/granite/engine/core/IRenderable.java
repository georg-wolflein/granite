package granite.engine.core;

import granite.engine.rendering.IRenderer;

public interface IRenderable {

    IRenderer getRenderer();

    void setRenderer(IRenderer renderer);

}
