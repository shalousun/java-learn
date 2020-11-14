package com.yusun.rpc.protocal.dubbo.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author yu 2020/11/13.
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
