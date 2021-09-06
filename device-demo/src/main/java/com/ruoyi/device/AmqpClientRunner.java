package com.ruoyi.device;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.device.bean.Device;
import com.ruoyi.device.model.tsl.TslModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static com.ruoyi.device.constant.RedisKeyConstants.DEVICE;
import static com.ruoyi.device.constant.RedisKeyConstants.TSL;

/**
 * AMQP客户端
 * <p>监听 设备管理平台推送的AMQP消息</p>
 */
@Slf4j
@Component
public class AmqpClientRunner implements ApplicationRunner {

	@Resource
	private DataRouter dataRouter;

	@Resource
	private RedisCache redisCache;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//初始化设备信息
		Device device = new Device();
		device.setImei("202109052000123");
		device.setProductKey("p111r2");
		device.setDeviceName("智能饲喂机");
		device.setOnlineStatus(0);
		device.setTsCreateTime(System.currentTimeMillis());
		device.setRunningStatus(0);
		device.setDeviceSn("QWERTYUIOP20210906");
		device.setModelSpec("EC-200");
		device.setIccid("123321123512521");
		device.setLocateTime(DateUtil.formatDateTime(new Date()));
		device.setLocateType(0);
		device.setSoc(100);
		device.setHdop(1.93f);
		device.setSatellites(6);
		device.setWgsLng(new BigDecimal("117.127414"));
		device.setWgsLat(new BigDecimal("31.82613"));
		String key = DEVICE + "p111r2:202109052000123";
		redisCache.setCacheObject(key, device);

		//初始化TSL信息
		File file = ResourceUtils.getFile("classpath:tsl/p111r2.json");
		String tsl = FileUtils.readString(file, "UTF-8");
		TslModel tslModel = JSON.parseObject(tsl, TslModel.class);
		redisCache.setCacheObject(TSL + "p111r2", tslModel);


		String accessKey = "24b9rucZxRLuBTfVk1KmLiDD";
		String accessSecret = "6A5zVhQhjxMkD9oXAqoPCmSfRFPGkeaFBMBohiRZ";
		String url = "amqps://iot-amqp.quectel.com:5671/quec-open", queueName = "a.000000004280.farm";
		long timestamp = System.currentTimeMillis();
		String username = String.format("ver=1&auth_mode=accessKey&sign_method=sha256&access_key=%s&timestamp=%s", accessKey, timestamp);
		String password = DigestUtils.sha256Hex(username + accessSecret).toLowerCase();
		try {
			CountDownLatch latch = new CountDownLatch(1);
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUri(url);
			factory.setUsername(username);
			factory.setPassword(password);
			factory.useSslProtocol();
			factory.setConnectionTimeout(60000);
			try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel()) {
				channel.basicQos(1);
				// DeliverCallback
				DeliverCallback deliverCallback = (consumerTag, delivery) -> {
					try {
						String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
						// handle
						System.out.println("Received message: '" + message + "', timestamp: " + System.currentTimeMillis());
						dataRouter.router(message);
					} finally {
						channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					}
				};
				// acutoAck true/false
				channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
					System.out.println("The consumer is cancelled");
					latch.countDown();
				});
				latch.await();
			}
		} catch (Exception e) {
			log.error("AMQP消息监听异常", e);
		}
	}
}
