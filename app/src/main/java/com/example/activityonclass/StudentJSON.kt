//package com.example.activityonclass
//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.builtins.ListSerializer
//import kotlinx.serialization.json.JsonArray
//import kotlinx.serialization.json.JsonElement
//import kotlinx.serialization.json.JsonTransformingSerializer
//@Serializable
//data class Student(
//    @SerialName("id")
//    val name: String,
//    val tClass: String,
//    val date: String,
//    val sex: String,
//
//)
//object StudentListSerializer : JsonTransformingSerializer<List<Student>>(ListSerializer(Student.serializer())) {
//    // If response is not an array, then it is a single object that should be wrapped into the array
//    override fun transformDeserialize(element: JsonElement): JsonElement =
//        if (element !is JsonArray) JsonArray(listOf(element)) else element
//}
//@Serializable
//data class StudentList(
//    @Serializable(with = StudentListSerializer::class)
//    val list: ArrayList<Student>
//)



