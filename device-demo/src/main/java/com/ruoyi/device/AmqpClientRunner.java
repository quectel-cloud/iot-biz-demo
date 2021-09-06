package com.ruoyi.device;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.device.handler.DataHandler;
import com.ruoyi.device.model.tsl.TslModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

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
	public void run(ApplicationArguments args) throws Exception{


		File file = ResourceUtils.getFile("classpath:tsl/p111r2.json");
		String tsl = FileUtils.readString(file, "UTF-8");
		TslModel tslModel = JSON.parseObject(tsl, TslModel.class);
		redisCache.setCacheObject(TSL+"p111r2",tslModel);


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
			log.error("AMQP消息监听异常",e);
		}
	}
}
