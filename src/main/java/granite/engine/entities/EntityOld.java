package granite.engine.entities;

import granite.engine.model.Model;
import org.joml.Vector3f;

public class EntityOld extends Entity {

    private Model model;
    private float scale;

    public EntityOld(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(position, rotation);
        this.model = model;
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
