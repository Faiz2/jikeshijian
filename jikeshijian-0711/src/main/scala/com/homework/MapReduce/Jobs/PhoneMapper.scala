package com.homework.MapReduce.Jobs

import com.homework.MapReduce.Entity.FlowBean
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.io.Text

import java.io.IOException

case class PhoneMapper() extends Mapper[Object, Text, Text, FlowBean] {
    override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, FlowBean]#Context): Unit =  {
        try {
            val line = value.toString.split("\t").tail
            val phone = line.head
            val upFlow = line(7)
            val downFlow = line(8)
            context.write(new Text(phone), FlowBean(upFlow.toLong, downFlow.toLong))
        } catch {
            case e: InterruptedException => e.printStackTrace()
            case e: IOException => e.printStackTrace()
        }
    }
}
