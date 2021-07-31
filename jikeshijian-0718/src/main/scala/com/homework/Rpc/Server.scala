package com.homework.Rpc

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.ipc.RPC
import com.homework.Service.StudentService
import com.homework.Service.Impl.StudentServiceImpl

case class Server(host: String, port: Int) {
    try {
        val server = new RPC.Builder(new Configuration())
            .setBindAddress(host)
            .setPort(port)
            .setProtocol(classOf[StudentService])
            .setInstance(new StudentServiceImpl()).build()
        println("Server Start")
        server.start()
    } catch {
        case ex:Exception => ex.printStackTrace()
    }
}
