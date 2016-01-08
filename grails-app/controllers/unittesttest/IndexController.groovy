package unittesttest

class IndexController {

    def index() {
        render "Hello World"
    }

    // 全てのPersonを返す
    def persons() {
        [peresons:Person.list()]
    }

    // 全てのPersonから、全てのHobbyを一つのリストにして返す
    def allHobbies() {
        [hobbies: Person.list().collectMany {it.hobbies}]
    }

    def jsonRequest() {
        String name = request.JSON.name
        name = "${name}!!"
        render(view: "/index/jsonRequest", model: [name:name])
    }

    def domains() {
        List<A>aList = A.list()
        [aList:aList]
    }

    def searchDomains() {
        List<A>aList = A.findAllByName(params.name as String)
        [aList: aList]
    }

    def searchDomains2() {
        List<A>aList = A.findAllByName(params.name as String)
        [aList: aList.findAll{it.b.name == "test"}]
    }
}
