package com.homework.Entity

case class Student(id: String, name: String) extends Serializable {
    def this() = this("", "")
}
