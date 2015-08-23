package hxf.ws;


import hxf.ws.adapters.*;
import hxf.ws.runtime.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by kumait on 12/3/14.
 */
public abstract class HXFServlet extends HttpServlet {
    protected RuntimeConfiguration runtimeConfiguration;
    
    @Override
    public void init() throws ServletException {
        super.init();

        runtimeConfiguration = RuntimeConfiguration.createDefault();

        this.setup();

        String configFilePath = getServletContext().getRealPath("WEB-INF/" + ConfigurationReader.FILE_NAME);
        ConfigurationReader.init(new File(configFilePath));
    }

    protected abstract void setup();

    private String getPostBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ( (line = reader.readLine()) != null) {
            sb.append(line);
        }
        String postBody = sb.toString();
        return postBody;
    }

    private void writeDefaultPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("HXF Services version 1.1");
        resp.getWriter().flush();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConfigurationLoader.applyConfiguration(getServletName(), ConfigurationReader.getConfiguration(), runtimeConfiguration);
        
        if (runtimeConfiguration.getRequestCharset() != null) {
            req.setCharacterEncoding(runtimeConfiguration.getRequestCharset());
        }

        if (runtimeConfiguration.getResponseCharset() != null) {
            resp.setCharacterEncoding(runtimeConfiguration.getResponseCharset());
        }

        if (runtimeConfiguration.isAllowCrossDomain()) {
            resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            resp.addHeader("Access-Control-Allow-Origin", "*");
        }

        InputAdapter inputAdapter = null;
        String input = null;
        if (req.getMethod().equals("POST")) {
            input = getPostBody(req);
            if (input == null || input.length() == 0) {
                writeDefaultPage(req, resp);
                return;
            }
            inputAdapter = new JsonInputAdapter();
        } else if (req.getMethod().equals("GET")) {
            if (req.getQueryString() == null) {
                writeDefaultPage(req, resp);
                return;
            }
            String decQueryString = URLDecoder.decode(req.getQueryString(), runtimeConfiguration.getRequestCharset());

            input = decQueryString;
            inputAdapter = new QueryStringInputAdapter();
        } else {
            super.service(req, resp);
            return;
        }


        String format = req.getParameter("f");
        OutputAdapter outputAdapter = null;
        try {
            outputAdapter = OutputAdapterFactory.create(runtimeConfiguration, format);
        } catch (Exception e) {
            throw new ServletException("Unable to create output adapter: " + e.getMessage());
        }
        
        ServiceRuntime serviceRuntime = new ServiceRuntime(runtimeConfiguration, inputAdapter, outputAdapter);
        String result = serviceRuntime.execute(input);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");

        resp.getWriter().write(result);
        resp.getWriter().flush();
    }
}
