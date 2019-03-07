package study.yeshm.groovy;

import study.yeshm.groovy.util.GroovyScriptUtil;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", "Guru");

        String result = GroovyScriptUtil.run("Hello.groovy", "helloWorld", map);
        System.out.println(result);
    }
}
