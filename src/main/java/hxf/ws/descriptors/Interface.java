package hxf.ws.descriptors;

import java.util.List;

/**
 * Created by kumait on 12/8/14.
 */
public class Interface {
    private String name;
    private String description;
    private List<Method> methods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
