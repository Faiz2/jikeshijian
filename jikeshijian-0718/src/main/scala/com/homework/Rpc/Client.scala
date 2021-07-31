package com.homework.Rpc

import org.apache.hadoop.ipc.RPC
import com.homework.Service.StudentService
import org.apache.hadoop.conf.Configuration

import java.net.InetSocketAddress

case class Client(host: String, port: Int) {
    private val proxy = RPC.getProxy(classOf[StudentService],
        1,
        new InetSocketAddress(host, port),
        new Configuration())

    def findNames(ids: List[String]): Unit = {
        ids.foreach(id => {
            val name = this.proxy.findName(id)
            println(s"""Number: $id \t Name: $name""")
        })
    }
}
