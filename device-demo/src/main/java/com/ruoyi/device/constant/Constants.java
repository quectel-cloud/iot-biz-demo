package com.ruoyi.device.constant;

public class Constants {

	public static enum TslDataType {
		BOOL,
		INT,
		FLOAT,
		DOUBLE,
		ENUM,
		TEXT,
		DATE,
		STRUCT,
		ARRAY;

		private TslDataType() {
		}
	}

	public static class DmpMsgType {
		public static final String DMP_ONLINE = "ONLINE";
		public static final String DMP_MATTR = "MATTR";
		public static final String DMP_MATTR_REPORT = "REPORT";
		public static final String DMP_MATTR_WRITE = "WRITE";
		public static final String DMP_MATTR_READ = "READ";
		public static final String DMP_REQACK = "REQACK";
		public static final String DMP_STATUS = "STATUS";
		public static final String DMP_RAW = "RAW";
		public static final String DMP_RAW_UPLINK = "UPLINK";
		public static final String DMP_RAW_DOWNLINK = "DOWNLINK";
		public static final String DMP_MEVENT = "MEVENT";
		public static final String DMP_MEVENT_INFO = "INFO";
		public static final String DMP_MEVENT_WARN = "WARN";
		public static final String DMP_MEVENT_ERROR = "ERROR";
		public static final String DMP_MSERV = "MSERV";
		public static final String DMP_MSERV_INPUT = "INPUT";
		public static final String DMP_MSERV_OUTPUT = "OUTPUT";
		public static final String DMP_LOCATION = "LOCATION";
		public static final String DMP_LOCATION_REPORT = "INFO-KV";

		public DmpMsgType() {
		}
	}



}
