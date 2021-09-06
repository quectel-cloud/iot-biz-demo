package com.ruoyi.device.model.base;

import java.util.HashMap;
import java.util.Map;

public class BaseDetailData {

    /**
     * 数据包Id
     */
    private Integer packetId;
    /**
     * 功能标识符和值键值对，当subtype为WRITE、REPORT或READRESP时，该字段存在。
     */
    private Map<String, Object> kv = new HashMap<>();

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public Map<String, Object> getKv() {
        return kv;
    }

    public void setKv(Map<String, Object> kv) {
        this.kv = kv;
    }
}
