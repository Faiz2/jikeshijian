package com.homework.JavaService;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface BaseService extends VersionedProtocol {
    long versionID = 1L;
}
