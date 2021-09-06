package com.ruoyi.device.model.business;


import com.ruoyi.device.model.base.BaseData;
import com.ruoyi.device.model.base.BaseDetailData;

/**
 * 物模型事件-上报
 */
public class DeviceMeventBO extends BaseData {

    private Data data = new Data();

    public static class Data extends BaseDetailData {
        /**
         * 事件的标识符
         */
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
