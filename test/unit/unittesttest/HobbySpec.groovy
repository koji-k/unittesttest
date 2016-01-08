package unittesttest

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Hobby)
class HobbySpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {

        setup:
        def obj = new Hobby(name:"hobby1")

        expect:
        obj.name == "hobby1"
    }
}
