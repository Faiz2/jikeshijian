package com.homework.MapReduce.Entity

import org.apache.hadoop.io.Writable
import java.io.{DataInput, DataOutput}

object FlowBean {
    def apply(upFlow: Long, downFlow: Long) = new FlowBean(upFlow, downFlow)
}

case class FlowBean(
                   var upFlow: Long,
                   var downFlow: Long,
                   var sumFlow: Long
                   ) extends Writable {

    // 必须要重载无参构造,不然无法序列化
    def this() = this(0, 0, 0)

    def this(upFlow: Long, downFlow: Long) = this(upFlow, downFlow, upFlow + downFlow)

    override def write(dataOutput: DataOutput): Unit = {
        dataOutput.writeLong(upFlow)
        dataOutput.writeLong(downFlow)
        dataOutput.writeLong(sumFlow)
    }

    override def readFields(dataInput: DataInput): Unit = {
        upFlow = dataInput.readLong
        downFlow = dataInput.readLong
        sumFlow = dataInput.readLong
    }

    override def toString: String = {
        s"$upFlow\t$downFlow\t$sumFlow"
    }
}
