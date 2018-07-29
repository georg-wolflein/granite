package granite.engine.model;

import granite.engine.core.IDestroyable;

public class Model implements IDestroyable {

    private VAO vao;
    private Texture texture;

    public Model(VAO vao, Texture texture) {
        this.vao = vao;
        this.texture = texture;
    }

    public VAO getVao() {
        return vao;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void destroy() {
        getVao().destroy();
        getTexture().destroy();
    }
}
