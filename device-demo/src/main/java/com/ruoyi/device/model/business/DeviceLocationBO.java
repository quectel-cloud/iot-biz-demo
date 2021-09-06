package com.ruoyi.device.model.business;

import com.ruoyi.device.model.base.BaseData;

import java.math.BigDecimal;

/**
 * 设备定位数据格式
 */
public class DeviceLocationBO extends BaseData {

    /**
     * 数据详情
     */
    private Data data = new Data();

    public static class Data {
        /**
         * 数据包Id
         */
        private Integer packetId;
        /**
         * GNSS/LBS
         */
        private String type;
        /**
         * 	BD(GGA/GSA/GSV...中国)/GL（俄国）/GP（美国）/GA（欧洲）/GN(GP+BD联合)
         */
        private  String subType;

        private LocationData kv = new LocationData();

        public static class LocationData {
            /**
             * 当前卫星数
             */
            private Integer satellites;
            /**
             * 纬度
             */
            private BigDecimal lat;
            /**
             * 纬度半球
             */
            private String latType;
            /**
             * 经度
             */
            private BigDecimal lng;
            /**
             * 经度半球
             */
            private String lngType;
            /**
             * 水平精度因子
             */
            private Float hdop;
            /**
             * 定位时间
             */
            private Long locateTime;

            public Integer getSatellites() {
                return satellites;
            }

            public void setSatellites(Integer satellites) {
                this.satellites = satellites;
            }

            public BigDecimal getLat() {
                return lat;
            }

            public void setLat(BigDecimal lat) {
                this.lat = lat;
            }

            public String getLatType() {
                return latType;
            }

            public void setLatType(String latType) {
                this.latType = latType;
            }

            public BigDecimal getLng() {
                return lng;
            }

            public void setLng(BigDecimal lng) {
                this.lng = lng;
            }

            public String getLngType() {
                return lngType;
            }

            public void setLngType(String lngType) {
                this.lngType = lngType;
            }

            public Float getHdop() {
                return hdop;
            }

            public void setHdop(Float hdop) {
                this.hdop = hdop;
            }

            public Long getLocateTime() {
                return locateTime;
            }

            public void setLocateTime(Long locateTime) {
                this.locateTime = locateTime;
            }
        }

        public Integer getPacketId() {
            return packetId;
        }

        public void setPacketId(Integer packetId) {
            this.packetId = packetId;
        }

        public LocationData getKv() {
            return kv;
        }

        public void setKv(LocationData kv) {
            this.kv = kv;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
