package com.homework.Service

import com.homework.JavaService.BaseService

trait StudentService extends BaseService {
    def findName(id: String): String
}
