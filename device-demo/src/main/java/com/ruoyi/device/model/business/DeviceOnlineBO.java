package com.ruoyi.device.model.business;

import com.ruoyi.device.model.base.BaseData;

/**
 * 上下线事件数据格式
 */
public class DeviceOnlineBO extends BaseData {

    /**
     * 数据详情
     */
    private Data data = new Data();

    public static class Data {
        /**
         * 设备状态。0：下线、1：上线、2：重新连接
         */
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
