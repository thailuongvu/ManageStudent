package com.example.activityonclass


import io.realm.kotlin.types.RealmObject

class Person : RealmObject,java.io.Serializable {
    var name: String = "Foo"
    var classt :String=""

}
class Dog : RealmObject {
    var name: String =""

    var age: Int = 0
}

