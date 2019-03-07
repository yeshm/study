package study.yeshm.groovy

import spock.lang.Specification
import study.yeshm.groovy.util.GroovyScriptUtil

class AppTest extends Specification {
    def "helloWorld"() {
        setup:
        def map = [
                "name":"Guru"
        ]

        when:
        def result = GroovyScriptUtil.run("Hello.groovy", "helloWorld", map);

        then:
        result == 'Hello world. Guru!'
    }
}
