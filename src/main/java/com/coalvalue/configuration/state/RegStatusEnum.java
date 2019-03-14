package com.coalvalue.configuration.state;

public enum RegStatusEnum {
 
// 未连接
UNCONNECTED,
// 已连接
CONNECTED,
    IDENTITING,
    IDENTITED,

    // 注册中
REGISTERING,
// 已注册
REGISTERED,
    SYNCING,
    SYNCED
    ;
}