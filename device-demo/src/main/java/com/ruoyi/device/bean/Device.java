package com.ruoyi.device.bean;

import lombok.Data;

@Data
public class Device {

	/**
	 * 产品productKey
	 */
	private String productKey;

	/**
	 * IMEI
	 */
	private String imei;

	/**
	 * SIM卡号
	 */
	private String iccid;

	/**
	 * 在线状态(0离线 1在线）
	 */
	private Integer onlineStatus;


	/**
	 * 实时故障标识符。物模型故障事件标识符，多个逗号分隔
	 */
	private String faultCode;

	/**
	 * 实时告警标识符。物模型告警事件标识符，多个逗号分隔
	 */
	private String alarmCode;

}
