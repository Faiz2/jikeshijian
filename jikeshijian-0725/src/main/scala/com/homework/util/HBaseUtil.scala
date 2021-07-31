package com.homework.util

import org.apache.hadoop.hbase.{HBaseConfiguration, NamespaceDescriptor, NamespaceNotFoundException, TableName}
import org.apache.hadoop.hbase.client.{Admin, ColumnFamilyDescriptorBuilder, Connection, ConnectionFactory, Delete, Get, Put, Scan, Table, TableDescriptorBuilder}
import org.apache.hadoop.hbase.util.Bytes

import scala.util.{Failure, Success, Try}

object HBaseUtil {
    /*
     *创建一个HBase的配置，创建的时候会去加载classpath下的hbase-default.xml和hbase-site.xml两个配置文件
     */
    private val conf = HBaseConfiguration.create()
    //设置Zookeeper的地址和端口来访问HBase
    conf.set("hbase.zookeeper.quorum", "jikehadoop01,jikehadoop02,jikehadoop03")

    //创建操作HBase的入口connection
    private val conn: Connection = ConnectionFactory.createConnection(conf)
    //创建操作HBase表的入口Admin
    private val admin: Admin = conn.getAdmin


    def createNamespace(name: String): Unit = {
        try {
            admin.getNamespaceDescriptor(name)
        } catch {
            case _: NamespaceNotFoundException =>
                admin.createNamespace(NamespaceDescriptor.create(name).build())
            case e => e.printStackTrace()
        }
    }

    /**
     * 获取表
     *
     * @param tableName 表名
     * @return HBase表
     */

    def getTable(tableName: String): Table = {
        val table = Try(conn.getTable(TableName.valueOf(tableName)))
        table.get.close()
        table match {
            case Success(v) => v;
            case Failure(e) => e.printStackTrace()
                null
        }
    }


    /**
     * 创建表
     *
     * @param tableName 表名
     * @param cf        列族
     */
    def createTable(tableName: String, cf: String*): Unit = {
        //创建表
        val tableDesc = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
        tableDesc.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("basic".getBytes).build())
        println(s"Creating table `$tableName`. ")
        Try {
            if (admin.tableExists(TableName.valueOf(tableName))) {
                admin.disableTable(TableName.valueOf(tableName))
                admin.deleteTable(TableName.valueOf(tableName))
            }
            for (item <- cf) {
                //ColumnFamilyDescriptorBuilder 列簇描述生成器
                val info = ColumnFamilyDescriptorBuilder.of(item)
                //添加列簇
                tableDesc.setColumnFamily(info)
            }
            admin.createTable(tableDesc.build())
            admin.close()
            println("Done!")
        } match {
            case Success(_) =>
            case Failure(e) => e.printStackTrace()
        }
    }

    /**
     * 删除数据
     *
     * @param tableName 表名
     * @param rowKey    行键
     */
    def delete(tableName: String, rowKey: String): Unit = {
        val table = conn.getTable(TableName.valueOf(tableName))
        Try {
            val d = new Delete(rowKey.getBytes)
            table.delete(d)
            table.close()
        } match {
            case Success(_) =>
            case Failure(e) => e.printStackTrace()
        }
    }

    /**
     *
     * 往表中存放数据
     *
     * @param tableName 表名
     * @param rowKey    行键
     * @param cf        列族
     * @param qualifier 列限定符
     * @param value     具体的值
     */
    def put(tableName: String, rowKey: String, cf: String, qualifier: String, value: String): Unit = {
        println(s"Put row key $rowKey into $tableName. ")
        val table = conn.getTable(TableName.valueOf(tableName))
        Try {
            // 准备一个row key
            val p = new Put(rowKey.getBytes)
            // 为put操作指定 column qualifier 和 value
            p.addColumn(cf.getBytes, qualifier.getBytes, value.getBytes)
            // 放数据到表中
            table.put(p)
            table.close()
        } match {
            case Success(_) => println("Done!")
            case Failure(e) => e.printStackTrace()
        }
    }

    /**
     * 获得表里面的数据
     *
     * @param tableName 表名
     * @param rowKey    行键
     * @param cf        列族
     * @param qualifier 列限定符
     * @return 获得的数据
     */
    def get(tableName: String, rowKey: String, cf: String, qualifier: String): String = {
        val table = conn.getTable(TableName.valueOf(tableName))
        Try {
            val g = new Get(rowKey.getBytes)
            val result = table.get(g)
            table.close()
            Bytes.toString(result.getValue(cf.getBytes(), qualifier.getBytes()))
        } match {
            case Success(v) => v
            case Failure(e) =>
                e.printStackTrace()
                null
        }
    }

    /**
     * 扫描数据
     *
     * @param tableName 表名
     * @param cf        列族
     * @param qualifier 列限定符
     */
    def scan(tableName: String, cf: String, qualifier: String): Unit = {
        val table = conn.getTable(TableName.valueOf(tableName))
        val scan = new Scan()
        scan.addColumn(cf.getBytes, qualifier.getBytes)
        val scanner = table.getScanner(scan)
        Try {
            val iterator = scanner.iterator()
            while (iterator.hasNext) {
                val next = iterator.next()
                println("Found row: " + next)
                println("Found value: " + Bytes.toString(
                    next.getValue(cf.getBytes, qualifier.getBytes)))
            }
            scanner.close()
            table.close()
        } match {
            case Success(_) =>
            case Failure(e) => e.printStackTrace()
        }

    }
}






