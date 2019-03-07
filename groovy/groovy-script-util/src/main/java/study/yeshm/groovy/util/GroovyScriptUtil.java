package study.yeshm.groovy.util;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GroovyScriptUtil <T> {

    private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    private static final String BASE_PATH = "src/main/groovy/study/yeshm/groovy/";

    public static <T> T run(String location, String method, Map map) {
        Class groovyClass = getGroovyByLocation(location);
        final Binding binding = new Binding(map);

        Script script = InvokerHelper.createScript(groovyClass, binding);
        T result;
        if (method != null && !method.isEmpty()) {
            result = (T) script.invokeMethod(method, null);
        } else {
            result = (T) script.run();
        }
        return result;
    }

    private static Class getGroovyByLocation(String location) {
        Class gc = loadGroovy(location);
        return gc;
    }

    private static synchronized Class loadGroovy(String location) {
        String fullFileName = BASE_PATH + location;
        String groovyText = getLocationText(fullFileName);
        Class gc = compileGroovy(groovyText, location);

        return gc;
    }

    private static String getLocationText(String location) {
        File localFile = new File(location);
        try {
            URL locationUrl = localFile.toURI().toURL();
            InputStream inputStream = locationUrl.openStream();
            String text = getStreamText(inputStream);
            return text;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getStreamText(InputStream is) {
        if (is == null) return null;
        Reader r = null;
        try {
            r = new InputStreamReader(new BufferedInputStream(is), StandardCharsets.UTF_8);

            StringBuilder sb = new StringBuilder();
            char[] buf = new char[4096];
            int i;
            while ((i = r.read(buf, 0, 4096)) > 0) sb.append(buf, 0, i);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error getting stream text", e);
        } finally {
            try {
                if (r != null) r.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error in close after reading text from stream");
            }
        }
    }

    private void test(){
        //log.wa
    }

    private static synchronized Class compileGroovy(String script, String className) {
        boolean hasClassName = className != null && !className.isEmpty();

        // the simple approach, groovy compiles internally and don't save to disk/etc
        return hasClassName ? groovyClassLoader.parseClass(script, className) : groovyClassLoader.parseClass(script);
    }
}
