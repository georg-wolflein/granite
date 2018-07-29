package granite.engine.model;

import granite.engine.Engine;
import granite.engine.core.IDestroyable;
import granite.engine.core.IEngineObject;

import java.util.ArrayList;
import java.util.List;

public class ModelManager implements IEngineObject {

    private List<Model> models = new ArrayList<>();
    private List<VAO> vaos = new ArrayList<>();
    private List<Texture> textures = new ArrayList<>();

    public VAO loadVAO(String file) {
        VAO vao = VAO.loadFromFile(file);
        vaos.add(vao);
        return vao;
    }

    public Texture loadTexture(String file) {
        Texture texture = new Texture(file);
        textures.add(texture);
        return texture;
    }

    public Model addModel(VAO vao, Texture texture) {
        Model model = new Model(vao, texture);
        models.add(model);
        return model;
    }

    public List<VAO> getVaos() {
        return vaos;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public List<Model> getModels() {
        return models;
    }

    @Override
    public void destroy() {
        for (IDestroyable vao : vaos) {
            vao.destroy();
        }
        for (IDestroyable texture : textures) {
            texture.destroy();
        }
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {

    }
}
