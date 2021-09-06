package com.ruoyi.device.handler;

public interface DataHandler {

	/**记录设备在线状态  在线状态 0离线 1上线*/
	void recordDeviceOnlineStatus(String productKey,String deviceKey, Integer status);

	/**处理设备上报的物模型属性 */
	void handleMattrReport(String data);

	/**处理设备上报的事件 */
	void handleMevent(String data);

	/**处理设备位置信息上报 */
	void handleDeviceLocation( String data);

	/**处理设备上报状态信息 */
	void handleStatus(String data);
}
