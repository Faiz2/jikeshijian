package com.homework.MapReduce

import com.homework.MapReduce.Entity.FlowBean
import com.homework.MapReduce.Jobs.{PhoneMapper, PhoneReducer}
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat


object main extends App {

    val conf = new JobConf()
    val job = Job.getInstance(conf)
    job.setJobName("PhoneFlowAggregation")
    conf.set("fs.defaultFS","hdfs://jikehadoop01:8020")

    job.setJarByClass(classOf[PhoneMapper])
    job.setJarByClass(classOf[PhoneReducer])

    job.setMapperClass(classOf[PhoneMapper])
    job.setReducerClass(classOf[PhoneReducer])


    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[FlowBean])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[FlowBean])


    FileInputFormat.addInputPath(job, new Path("hdfs://jikehadoop01:8020/user/student/qianpeng/homework/0711/resource/HTTP_20130313143750.dat"))
    FileOutputFormat.setOutputPath(job, new Path("hdfs://jikehadoop01:8020/user/student/qianpeng/homework/0711/result"))

    System.exit(if (job.waitForCompletion(true)) 0 else 1)

}
