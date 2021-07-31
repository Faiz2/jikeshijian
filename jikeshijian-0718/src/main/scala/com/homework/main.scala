package com.homework

import com.homework.Rpc.{Client, Server}

object main extends App {

    val host = "127.0.0.1"
    val port = 30020
    Server(host, port)

    val client = Client(host, port)
    val ids = "20210000000000" :: "20210123456789" :: "G20210735010082" :: Nil
    client.findNames(ids)

}
