package com.homework.MockData

import com.homework.Entity.Student

object Mock {
    private val cache = Map[String, Student](
        "20210123456789" -> Student("20210123456789", "心心"),
        "G20210735010082" -> Student("G20210735010082", "钱鹏")
    )

    def findStudent(id: String): Option[Student] = {
        this.cache.get(id)
    }
}
