package com.ruoyi.device.controller;

import com.google.common.collect.Lists;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.device.bean.Device;
import com.ruoyi.device.constant.Constants;
import com.ruoyi.device.constant.RedisKeyConstants;
import com.ruoyi.device.model.tsl.TslModel;
import com.ruoyi.device.model.tsl.TslProperties;
import com.ruoyi.device.model.tsl.TslSpecs;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ruoyi.device.constant.RedisKeyConstants.DEVICE;

/**
 * @program: ruoyi-vue-plus
 * @description:
 * @author: zhh
 * @create: 2021-09-06 16:21
 **/
@RestController
public class DeviceController {

	@Resource
	private RedisCache redisCache;

	/**
	 * 获取设备基础信息
	 */
	@GetMapping("/deviceInfo")
	public AjaxResult getDeviceInfo() {
		//目前写成固定设备，后续开发者可以灵活修改
		String key = DEVICE + "p111r2:202109052000123";
		Device device = redisCache.getCacheObject(key);
		return AjaxResult.success(device);
	}


	/**
	 * 获取设备基础信息
	 */
	@GetMapping("/deviceProperties")
	public AjaxResult getDeviceProperties() {
		//目前写成固定设备，后续开发者可以灵活修改
		String key = DEVICE + "p111r2:202109052000123";
		TslModel tslModel = redisCache.getCacheObject(RedisKeyConstants.TSL + "p111r2");
		Map<String, TslProperties> propertiesMap = redisCache.getCacheMap(RedisKeyConstants.MATTR + "p111r2:202109052000123");
		List<TslProperties> propertiesCfg = tslModel.getProperties().stream().collect(Collectors.toList());
		propertiesCfg.forEach(e -> {
			if (MapUtils.isNotEmpty(propertiesMap)) {
				TslProperties tslProperties = propertiesMap.get(e.getCode());
				if (tslProperties != null) {
					if (Constants.TslDataType.STRUCT.name().equalsIgnoreCase(e.getDataType())) {
						List<TslSpecs> specs = e.getSpecs();
						specs.forEach(s -> {
							TslSpecs tslSpecs = tslProperties.getSpecs().stream().filter(spec -> s.getCode().equals(spec.getCode())).findAny().orElse(null);
							if (tslSpecs != null && ObjectUtils.isNotEmpty(tslSpecs.getUpValue())) {
								s.setUpValue(tslSpecs.getUpValue());
							}
						});
					} else {
						e.setUpValue(tslProperties.getUpValue());
					}
				}
			}
		});
		//排序
		propertiesCfg.sort(Comparator.comparing(TslProperties::getSortNum));


		return AjaxResult.success(propertiesCfg);
	}


}
