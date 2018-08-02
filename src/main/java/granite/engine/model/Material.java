package granite.engine.model;

import com.mokiat.data.front.parser.MTLMaterial;
import granite.engine.core.IDestroyable;

public class Material extends MTLMaterial implements IDestroyable {

    public Material(MTLMaterial other) {
        super(other);
        setName(other.getName());
    }

    @Override
    public void destroy() {

    }
}
