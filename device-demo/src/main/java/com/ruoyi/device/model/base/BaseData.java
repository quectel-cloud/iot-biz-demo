package com.ruoyi.device.model.base;

public class BaseData {
    /**
     * 消息识别码
     */
    private String ticket;
    /**
     * 产品productKey
     */
    private String productKey;
    /**
     * 设备deviceKey
     */
    private String deviceKey;
    /**
     * 消息类型。ONLINE：上下线、STATUS：状态信息、MATTR：物模型属性、MEVENT：物模型事件
     */
    private String type;
    /**
     * 消息子类型，INFO：物模型事件-信息、WARN：物模型事件-告警、ERROR：物模型事件-告警
     */
    private String subtype;
    /**
     * 消息生成的时间戳，单位精确到毫秒
     */
    private Long createdAt;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
