package granite.engine.entities;

import granite.engine.model.Color;

public class Light extends LeafEntity {

    private Color color;

    public Light(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}