package hxf.ws.descriptors;

import java.util.List;

/**
 * Created by kumait on 12/8/14.
 */
public class Service {
    private String name;
    private String description;
    private String platform;
    private String url;
    private List<Interface> interfaces;

    public Service(String name) {
        this.platform = "JAVA";
        this.name = name;
    }

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces = interfaces;
    }
}
