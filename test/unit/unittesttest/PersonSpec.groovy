package unittesttest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * hasManyなドメインを持っているドメインのテストのサンプル
 * 大前提として、ドメインでsaveメソッドを使っていないので、ドメインで持っている別ドメインなどのプロパティがnullでconstraintsに違反している場合でも問題ない。
 * 注意点として、ドメインを修正した場合は、インタラクティブモードを抜けたほうがいい。
 */
@TestFor(Person)
@Mock([Person, Hobby]) // addToHobbiesなどのGORM特有の機能を利用する場合、テストしようとしているPersonドメインもMockに指定しておく
class PersonSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "ベーシック"() {
        // このテストの場合は、GORM特有の機能を利用していないので、@MockにPersonを指定する必要はない。
        given:
        Person obj = new Person(name:"koji")

        expect:
        obj.name == "koji"
    }

    void "hasManyなテスト"() {
        // addToHobbiesというGORM特有の機能を利用するので、クラスの@MockにPersonの指定が必要。
        given:
        Person obj = new Person()
        obj.addToHobbies(new Hobby()).addToHobbies(new Hobby())

        expect:
        2 == obj.hobbies.size()
    }

    void "ドメインの中で宣言したメソッドのテスト"() {
        given:
        Person obj = new Person()
        obj.addToHobbies((new Hobby(name:"programming"))).addToHobbies(new Hobby(name:"walk"))

        expect:
        "programming,walk" == obj.writeHobbies()
    }

    @Unroll
    void "constraintsのテスト(name:#name, age:#age, result:#result)"() {

        setup:
        Person obj = new Person(name:name, age:age)
        obj.title = new Title()

        expect:
        result == obj.validate()

        where:
        name | age || result
        ""  | 30  || false
        "k"  | 30  || true
        "ko"  | 30  || true
        "koj"  | 30  || true
        "koji"  | 30  || true
        "koji!"  | 30  || true
        "koji!!"  | 30  || false
        "koji" | 17 || false
        "koji" | 18 || true // ちゃんとゲッターが動いていることが確認できる
        "koji" | 19 || true
        "koji" | 98 || true
        "koji" | 99 || false

    }

}
