package com.ruoyi.device.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Device {

	/**
	 * 产品PK
	 */
	private String productKey;
	/**
	 * 设备别名
	 */
	private String deviceName;
	/**
	 * 设备序列号
	 */
	private String deviceSn;
	/**
	 * 设备内置模组IMEI
	 */
	private String imei;
	/**
	 * SIM卡号
	 */
	private String iccid;
	/**
	 * 添加人
	 */
	private Integer createUserId;
	/**
	 * 添加时间（时间戳）
	 */
	private Long tsCreateTime;
	/**
	 * 最后修改时间（时间戳）
	 */
	private Long tsUpdateTime;
	/**
	 * 在线状态(0离线 1在线）
	 */
	private Integer onlineStatus;
	/**
	 * 上线时间（时间戳）
	 */
	private Long tsOnlineTime;
	/**
	 * 模组型号
	 */
	private String modelSpec;
	/**
	 * 模组电量
	 */
	private Integer soc;
	/**
	 * 定位方式。0：GNNS
	 */
	private Integer locateType;
	/**
	 * 最新定位时间（时间戳）
	 */
	private Long tsLocateTime;
	/**
	 * 设备经度。WGS84
	 */
	private BigDecimal wgsLng;
	/**
	 * 设备纬度。WGS84
	 */
	private BigDecimal wgsLat;
	/**
	 * 水平精度因子。0.5-99.99
	 */
	private Float hdop;
	/**
	 * 当前卫星数
	 */
	private Integer satellites;

	/**
	 * 运行状态。1：正常、2：告警、3：故障，4：故障+告警
	 */
	private Integer runningStatus;




}
