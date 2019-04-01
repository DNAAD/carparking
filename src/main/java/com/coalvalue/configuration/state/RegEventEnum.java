package com.coalvalue.configuration.state;

public enum RegEventEnum {
// 连接
CONNECT,
    COMPLETE_CONNECT,

    // 注册
IDENTITY,
IDENTITY_SUCCESS,
REGISTER,
// 注册成功
REGISTER_SUCCESS,
// 注册失败
REGISTER_FAILED,



    LOST_CONNECT,

// 注销
/*UN_REGISTER,
    SYNC;*/
}