package hxf.ws.runtime;

public class RuntimeConfiguration {
    private ErrorLevel errorLevel;
    private int latency;
    private boolean allowCrossDomain;
    private String requestCharset;
    private String responseCharset;
    private OutputConfiguration outputConfiguration;
    private ServiceConfiguration serviceConfiguration;

    private RuntimeConfiguration() {
        this.errorLevel = ErrorLevel.NONE;
        this.latency = 0;
        this.allowCrossDomain = true;
        this.requestCharset = "utf-8";
        this.responseCharset = "utf-8";
        this.outputConfiguration = new OutputConfiguration(false, false, false, null);
    }

    public static RuntimeConfiguration createDefault() {
        return new RuntimeConfiguration();
    }

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(ErrorLevel errorLevel) {
        this.errorLevel = errorLevel;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public boolean isAllowCrossDomain() {
        return allowCrossDomain;
    }

    public void setAllowCrossDomain(boolean allowCrossDomain) {
        this.allowCrossDomain = allowCrossDomain;
    }

    public String getRequestCharset() {
        return requestCharset;
    }

    public void setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public void setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
    }

    public OutputConfiguration getOutputConfiguration() {
        return outputConfiguration;
    }

    public void setOutputConfiguration(OutputConfiguration outputConfiguration) {
        this.outputConfiguration = outputConfiguration;
    }

    public ServiceConfiguration getServiceConfiguration() {
        return serviceConfiguration;
    }

    public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }
    
    


}
