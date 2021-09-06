package com.ruoyi.device.model.business;

import com.ruoyi.device.model.base.BaseData;
import com.ruoyi.device.model.base.BaseDetailData;

import java.util.ArrayList;
import java.util.List;

/**
 * 物模型属性数据格式
 */
public class DeviceMatterBO extends BaseData {

    /**
     * 数据详情
     */
    private Data data = new Data();

    public static class Data extends BaseDetailData {
        /**
         * 读命令的功能标识符集合，当subtype为READ时，该字段存在。
         */
        private List<String> keys = new ArrayList<>();

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
