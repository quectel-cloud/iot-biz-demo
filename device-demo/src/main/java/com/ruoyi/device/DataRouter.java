package com.ruoyi.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.device.handler.DataHandler;
import com.ruoyi.device.model.business.DeviceStatusBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.ruoyi.device.constant.Constants.DmpMsgType.*;

/**
 * 数据路由处理
 */
@Component
@Slf4j
public class DataRouter {


	@Resource
	private DataHandler dataHandler;


	public void router(String data){

		log.info("接收到来自DMP的AMQP推送:{}", data);
		JSONObject jsonObject = JSON.parseObject(data);
		String msgType = jsonObject.getString("type");
		switch (msgType) {
			case DMP_ONLINE:
				recordDeviceOnlineStatus(data);
				break;
			case DMP_MATTR:
				routerMattr(jsonObject.getString("subtype"), data);
				break;
			case DMP_MEVENT:
				dataHandler.handleMevent(data);
				break;
			case DMP_LOCATION:
				routerLocation(jsonObject.getString("subtype"), data);
				break;
			case DMP_STATUS:
				dataHandler.handleStatus(data);
				break;
			default:
				log.error("接收到来自DMP的AMQP推送无法匹配业务类型:{}", data);
		}



	}

	/**
	 * router dmp mattr msg base subtype
	 * @param subtype mattr msg subtype
	 * @param data 定位信息
	 */
	private void routerLocation(String subtype, String data) {
		switch (subtype) {
			case DMP_LOCATION_REPORT:
				dataHandler.handleDeviceLocation(data);
				break;
			default:
				log.error("位置处理无法匹配业务类型:{}", data);
		}
	}

	/**
	 * router dmp mattr msg base subtype
	 * @param subtype mattr msg subtype
	 * @param data 物模型属性信息
	 */
	private void routerMattr(String subtype, String data) {
		switch (subtype) {
			case DMP_MATTR_REPORT:
				dataHandler.handleMattrReport(data);
				break;
			default:
				log.error("属性处理无法匹配业务类型:{}", data);

		}
	}


	/**
	 * device online offline handle
	 * @param data  上下线
	 * @return
	 */
	private void recordDeviceOnlineStatus(String data) {
		DeviceStatusBO deviceStatusBO = JSON.parseObject(data, DeviceStatusBO.class);
		String productKey = deviceStatusBO.getProductKey();
		String deviceKey = deviceStatusBO.getDeviceKey();
		Integer status = deviceStatusBO.getData().getValue();
		dataHandler.recordDeviceOnlineStatus(productKey,deviceKey, status);
	}


}
