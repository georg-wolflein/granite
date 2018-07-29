package granite.engine.util;

import java.io.InputStream;

public class Resource {

    public static InputStream loadResource(ResourceType type, String name) {
        String typeString = "";
        switch (type) {
            case TEXTURE:
                typeString = "textures";
                break;
            case SHADER:
                typeString = "shaders";
                break;
            case MODEL:
                typeString = "models";
        }
        return Resource.class.getClassLoader().getResourceAsStream(typeString + "/" + name);
    }
}

