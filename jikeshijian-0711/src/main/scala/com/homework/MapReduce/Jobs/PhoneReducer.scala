package com.homework.MapReduce.Jobs

import com.homework.MapReduce.Entity.FlowBean
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.io.Text

import java.io.IOException
import java.lang
import scala.collection.JavaConverters._

case class PhoneReducer() extends Reducer[Text, FlowBean, Text, FlowBean] {
  override def reduce(key: Text, values: lang.Iterable[FlowBean], context: Reducer[Text, FlowBean, Text, FlowBean]#Context): Unit = {
      try {
          val it = values.iterator().asScala.toList
          val sumUpFlow = it.map(_.upFlow).sum
          val sumDownFlow = it.map(_.downFlow).sum
          context.write(key, FlowBean(sumUpFlow, sumDownFlow))
      } catch {
          case e: InterruptedException => e.printStackTrace()
          case e: IOException => e.printStackTrace()
      }
  }
}
