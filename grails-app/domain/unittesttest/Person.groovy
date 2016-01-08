package unittesttest

class Person {

    String name
    Integer age
    Title title

    static hasMany = [hobbies:Hobby]


    static constraints = {
        name size: 1..5, blank:false
        age range: 19..99
    }

    Integer getAge() {
        age != null ? age+1 : 0 // テストとは関係ないけど、ageはnullの可能性があるので。
    }

    String writeHobbies() {
        hobbies.collect{it.name}.join(",")
    }
}
