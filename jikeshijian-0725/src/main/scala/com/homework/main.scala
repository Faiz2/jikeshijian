package com.homework

import com.homework.util.HBaseUtil

object main extends App {
    val hbase = HBaseUtil
    val namespace = "qianpeng"
    val tableName = namespace + ":" + "student"
    hbase.createNamespace(namespace)
    hbase.createTable(tableName, "info", "score")
    hbase.put(tableName, "Tom", "info", "student_id", "20210000000001")
    hbase.put(tableName, "Tom", "info", "class", "1")
    hbase.put(tableName, "Tom", "score", "understanding", "75")
    hbase.put(tableName, "Tom", "score", "programming", "82")

    hbase.put(tableName, "QianPeng", "info", "student_id", "G20210735010082")
    hbase.put(tableName, "QianPeng", "info", "class", "1")
    hbase.put(tableName, "QianPeng", "score", "understanding", "75")
    hbase.put(tableName, "QianPeng", "score", "programming", "82")

    println(
        s"""
           |student_id:
           |${hbase.get(tableName, "Tom", "info", "student_id")}\t
           |class:
           |${hbase.get(tableName, "Tom", "info", "class")}
           |""".stripMargin)

    println("==============================================")
    hbase.scan(tableName, "info", "student_id")
    hbase.scan(tableName, "info", "class")
    hbase.scan(tableName, "score", "understanding")
    hbase.scan(tableName, "score", "programming")
    println("==============================================")
    hbase.delete(tableName, "Tom")
    hbase.scan(tableName, "info", "student_id")
    hbase.scan(tableName, "info", "class")
    hbase.scan(tableName, "score", "understanding")
    hbase.scan(tableName, "score", "programming")

}
