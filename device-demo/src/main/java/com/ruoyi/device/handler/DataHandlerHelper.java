package com.ruoyi.device.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.device.bean.Device;
import com.ruoyi.device.constant.Constants;
import com.ruoyi.device.constant.RedisKeyConstants;
import com.ruoyi.device.model.business.DeviceMatterBO;
import com.ruoyi.device.model.tsl.TslModel;
import com.ruoyi.device.model.tsl.TslProperties;
import com.ruoyi.device.model.tsl.TslSpecs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.device.constant.RedisKeyConstants.DEVICE;


@Slf4j
@Component
public class DataHandlerHelper implements DataHandler {

	@Autowired
	private RedisCache redisCache;


	@Override
	public void recordDeviceOnlineStatus(String productKey, String deviceKey, Integer status) {
		String key = DEVICE + productKey + ":" + deviceKey;
		Device device = redisCache.getCacheObject(key);
		if (null == device) {
			device = new Device();
			device.setProductKey(productKey);
			device.setImei(deviceKey);
			device.setOnlineStatus(status);
			redisCache.setCacheObject(key, device);
		} else {
			device.setOnlineStatus(status);
			redisCache.setCacheObject(key, device);
		}

	}

	@Override
	public void handleMattrReport(String data) {
		DeviceMatterBO deviceMatterBO;
		try {
			deviceMatterBO = JSON.parseObject(data, DeviceMatterBO.class);
		} catch (Exception e) {
			log.error("DMP报文数据转型失败！【{}】", data);
			return;
		}
		if (StringUtils.isEmpty(deviceMatterBO.getProductKey())) {
			log.error("监听数据缺少PK【{}】", data);
			return;
		}
		if (StringUtils.isEmpty(deviceMatterBO.getDeviceKey())) {
			log.error("监听数据缺少IMEI【{}】", data);
			return;
		}
		String key = DEVICE + deviceMatterBO.getProductKey() + ":" + deviceMatterBO.getDeviceKey();
		Device device = redisCache.getCacheObject(key);
		if (null == device) {
			log.error("不存在此设备【{}】", deviceMatterBO.getDeviceKey());
			return;
		}
		//获取物模型TSL
		TslModel tslModel = redisCache.getCacheObject(RedisKeyConstants.TSL + deviceMatterBO.getProductKey());
		if (tslModel == null || CollectionUtil.isEmpty(tslModel.getProperties())) {
			log.warn("设备【{}】不存在该产品的物模型，", deviceMatterBO.getDeviceKey());
			return;
		}
		Map<String, Object> resMap = deviceMatterBO.getData().getKv();
		List<TslProperties> dmpPropertiesList = tslModel.getProperties();
		Map<String, TslProperties> tslPropertiesMap = new HashMap<>();
		resMap.forEach((k, v) -> dmpPropertiesList.stream().filter(e -> k.equals(e.getCode())).findAny().ifPresent(dmpProperties -> tslPropertiesMap.put(k, dmpProperties)));
		if (CollectionUtil.isEmpty(tslPropertiesMap)) {
			log.warn("设备【{}】在AEP中不存在该产品的物模型，", deviceMatterBO.getDeviceKey());
			return;
		}
		for (Map.Entry<String, Object> map : resMap.entrySet()) {
			if (ObjectUtils.isNotEmpty(map.getValue())) {
				injectMattrValue(tslPropertiesMap.get(map.getKey()), map.getValue());
			}
		}
		redisCache.setCacheMap(RedisKeyConstants.MATTR + deviceMatterBO.getProductKey() + ":" + deviceMatterBO.getDeviceKey(), tslPropertiesMap);


	}

	@Override
	public void handleMevent(String data) {
		//TODO 处理设备上报的事件

	}

	@Override
	public void handleDeviceLocation(String data) {
		//TODO 处理设备上报的位置信息
	}

	@Override
	public void handleStatus(String data) {
		//TODO 处理设备上报的模组状态信息

	}


	/**
	 * 注入物模型属性值
	 */
	private void injectMattrValue(TslProperties tslProp, Object value) {
		if (tslProp.getDataType().equalsIgnoreCase(Constants.TslDataType.STRUCT.name())) {
			Map map = JSONObject.parseObject(JSON.toJSONString(value), Map.class);
			if (CollectionUtil.isNotEmpty(map)) {
				map.forEach((k, v) -> {
					List<TslSpecs> specs = tslProp.getSpecs();
					if (CollectionUtil.isNotEmpty(specs)) {
						specs.forEach(s -> {
							if (s.getCode().equals(k)) {
								s.setUpValue(v);
							}
						});
					}
				});
			}
		} else {
			tslProp.setUpValue(value);
		}
	}


}
