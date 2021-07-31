package com.homework.Service.Impl

import com.homework.MockData.Mock
import com.homework.Service.StudentService
import org.apache.hadoop.ipc.ProtocolSignature

class StudentServiceImpl extends StudentService {
    override def findName(id: String): String = Mock.findStudent(id) match {
        case Some(stu) => stu.name
        case _ => null
    }

    override def getProtocolVersion(s: String, l: Long): Long = 1

    override def getProtocolSignature(s: String, l: Long, i: Int): ProtocolSignature =
        new ProtocolSignature(1, null)
}
