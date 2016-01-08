package unittesttest

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Title)
class TitleSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {

        setup:
        def obj = new Title(name:"title")

        expect:
        obj.name == "title"
    }
}
