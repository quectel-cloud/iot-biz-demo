package com.ruoyi.device.model.business;

import com.ruoyi.device.model.base.BaseData;

/**
 * 状态信息数据格式
 */
public class DeviceStatusBO extends BaseData {

    /**
     * 数据详情
     */
    private Data data = new Data();

    public static class Data{
        /**
         * 数据包Id
         */
        private Integer packetId;

        /**
         * 0 - 下线(offline)， 1 - 上线(online)，2 - 重新连接(reonline)
         */
        private Integer value;

        private StatusData kv = new StatusData();

        public Integer getPacketId() {
            return packetId;
        }

        public void setPacketId(Integer packetId) {
            this.packetId = packetId;
        }

        public StatusData getKv() {
            return kv;
        }

        public void setKv(StatusData kv) {
            this.kv = kv;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public static class StatusData {
            /**
             * 电池电量
             */
            private String battery;
            /**
             * 电池电压
             */
            private String voltage;
            /**
             * 信号强度
             */
            private String signal_strength;
            /**
             * 剩余内存
             */
            private String memory_free;
            /**
             * 参考信号接收功率
             */
            private String rsrp;
            /**
             * LTE参考信号接收质量
             */
            private String rsrq;
            /**
             * 信号与干扰加噪声比
             */
            private String snr;
            /**
             * 模组型号
             */
            private String type;
            /**
             * 模组版本
             */
            private String version;
            /**
             * MCU版本
             */
            private String mcu_version;
            /**
             * 基站id
             */
            private String cell_id;
            /**
             * iccid
             */
            private String icc_id;

            /**
             * 移动国家代码
             */
            private String mcc;

            /**
             * 移动网络代码
             */
            private String mnc;
            /**
             * 位置区代码
             */
            private String lac;
            /**
             * 手机号
             */
            private String phone_num;
            /**
             * sim卡号
             */
            private String sim_num;

            public String getBattery() {
                return battery;
            }

            public void setBattery(String battery) {
                this.battery = battery;
            }

            public String getVoltage() {
                return voltage;
            }

            public void setVoltage(String voltage) {
                this.voltage = voltage;
            }

            public String getSignal_strength() {
                return signal_strength;
            }

            public void setSignal_strength(String signal_strength) {
                this.signal_strength = signal_strength;
            }

            public String getMemory_free() {
                return memory_free;
            }

            public void setMemory_free(String memory_free) {
                this.memory_free = memory_free;
            }

            public String getRsrp() {
                return rsrp;
            }

            public void setRsrp(String rsrp) {
                this.rsrp = rsrp;
            }

            public String getRsrq() {
                return rsrq;
            }

            public void setRsrq(String rsrq) {
                this.rsrq = rsrq;
            }

            public String getSnr() {
                return snr;
            }

            public void setSnr(String snr) {
                this.snr = snr;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getMcu_version() {
                return mcu_version;
            }

            public void setMcu_version(String mcu_version) {
                this.mcu_version = mcu_version;
            }

            public String getCell_id() {
                return cell_id;
            }

            public void setCell_id(String cell_id) {
                this.cell_id = cell_id;
            }

            public String getIcc_id() {
                return icc_id;
            }

            public void setIcc_id(String icc_id) {
                this.icc_id = icc_id;
            }

            public String getMcc() {
                return mcc;
            }

            public void setMcc(String mcc) {
                this.mcc = mcc;
            }

            public String getMnc() {
                return mnc;
            }

            public void setMnc(String mnc) {
                this.mnc = mnc;
            }

            public String getLac() {
                return lac;
            }

            public void setLac(String lac) {
                this.lac = lac;
            }

            public String getPhone_num() {
                return phone_num;
            }

            public void setPhone_num(String phone_num) {
                this.phone_num = phone_num;
            }

            public String getSim_num() {
                return sim_num;
            }

            public void setSim_num(String sim_num) {
                this.sim_num = sim_num;
            }
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
