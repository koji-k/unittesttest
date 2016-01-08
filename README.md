# Grailsのユニットテストのサンプル

memo:  

テストを実行して以下のようなエラーが出た場合は、単純にクラスアノテーションの@Mockで対象のドメインを指定するだけ。

```
| Failure:  hobby size(unittesttest.IndexControllerSpec)
|  java.lang.IllegalStateException: Method on class [unittesttest.Hobby] was used outside of a Grails application. If running in the context of a test using the mocking API or bootstrap Grails correctly.
        at unittesttest.IndexControllerSpec.hobby size(IndexControllerSpec.groovy:31)

```


また、ドメインのテストで、

```
| Failure:  test something(unittesttest.PersonSpec)
|  groovy.lang.MissingMethodException: No signature of method: unittesttest.Person.addToHobbies() is applicable for argument types: (unittesttest.Hobby) values: [unittesttest.Hobby : (unsaved)]
Possible solutions: getHobbies()
        at unittesttest.PersonSpec.test something(PersonSpec.groovy:25)
| Completed 5 unit tests, 1 failed in 0m 0s
| Tests FAILED  - view reports in /home/koji/IdeaProjects/unittesttest/target/test-reports
grails> 
```

というようなエラーが出る場合、正しくstatic hasManyが指定されているなら、単純に@Mockに今テストしようとしているドメインを追加するだけでOK

つまり、上記2つの例外のうち何れかが発生した場合、Mockにドメインを指定していないだけ。
これはコントローラからでも、ドメインからでも、ドメイン（の特有の機能）を利用する場合は同じ。