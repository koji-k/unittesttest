package unittesttest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(IndexController)
@Mock([Person, Hobby, A, B, C])
class IndexControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "hello world" () {
        // 単純にテキストをrenderしている場合
        setup:
        controller.index()

        expect:
        response.text == "Hello World"
    }

    void "ベーシック"() {
        // ここに注目！IndexコントローラではPersonのリストを取得しているので、ここで事前にデータを生成しておく必要がある。つまり、saveメソッドを実行しないといけない。
        // しかし、Personクラスの全てのプロパティに正しい値を格納してsaveを実行しないといけないので、とても面倒くさい。
        // Personがもっているドメインのプロパティがまた別のドメインを持っていたら。。。
        // 今回のように単純にPersonのlistを取得したり、そもそもテスト対象外のプロパティがある場合など、特に問題がない場合はsaveメソッドにvalidate:falseを指定することで、
        // constraints違反であったとしてもPersonインスタンスが生成、保存される。

        // ただし、省略できないものとして、今回のPersonはageプロパティのGetterを用意しているが、もしそこが単純に「age+1」となっている場合、
        // 以下のようにvalidateをfalseにしても、ageがnullなので、ageのゲッターの実行結果が「null+1」となってしまう。
        // 右のような例外が発生する ---> org.springframework.beans.InvalidPropertyException: Invalid property 'age' of bean class [unittesttest.Person]: Getter for property 'age' threw exception; nested exception is java.lang.reflect.InvocationTargetException
        // それは対策のしようがないので、素直にnew Person(age:1)の用に適当な値を突っ込む。

        given:
        new Person().save(validate:false) // validate:falseがあるので、プロパティに値がなくても大丈夫！
        def model = controller.persons()

        expect:
        model.peresons.size() == 1
    }

    void "別ドメインを利用する部分のテスト"() {
        // 特にコレと言った特筆すべき部分なし！
        given:
        new Person().save(validate:false).addToHobbies(new Hobby()).addToHobbies(new Hobby())

        def model = controller.allHobbies()

        expect:
        model.hobbies.size() == 2
    }

    void "jsonRequest"() {
        setup:
        request.json = [name:'koji']
        controller.jsonRequest()

        expect:
        view == "/index/jsonRequest"
        model.name == "koji!!"
        response.text == ""
    }

    void "domains"() {
        setup:
        new A().save(validate:false)
        new A().save(validate:false)
        new A().save(validate:false)
        def model = controller.domains()

        expect:
        model.aList.size() == 3
    }

    void "search domains"() {
        setup:
        new A(name:"a").save(validate:false)
        new A(name:"b").save(validate:false)
        new A(name:"a").save(validate:false)
        params.name = "a"
        def model = controller.searchDomains()

        expect:
        model.aList.size() == 2
    }

    void "search domains2" () {
        setup:
        new A(name:"a", b: new B(name:"b1")).save(validate:false)
        new A(name:"b", b: new B(name:"b2")).save(validate:false)
        new A(name:"a", b: new B(name:"test")).save(validate:false)
        params.name = "a"
        def model = controller.searchDomains2()

        expect:
        model.aList.size() == 1
    }


}
