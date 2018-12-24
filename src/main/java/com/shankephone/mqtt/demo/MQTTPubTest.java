package com.shankephone.mqtt.demo;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTPubTest {

	public static void main(String[] args) throws InterruptedException {
		String topic = "server2";
		String content = "test";
		int qos = 2;
		String broker = "tcp://testmqtt.shankephone.com:1883";
		String clientId = "pubtest";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(false);
			connOpts.setUserName("mqtt");
			connOpts.setPassword("!QAZxcvfr432".toCharArray());
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts).waitForCompletion();
			System.out.println("Connected");
			for (int i = 0; i < 10000; i++) {
				System.out.println("Publishing message: " + content + i);
				MqttMessage message = new MqttMessage((content + i).getBytes());
				message.setQos(qos);
				sampleClient.publish(topic, message);
				System.out.println("Message published");
				// sampleClient.disconnect();
				// System.out.println("Disconnected");
				// System.exit(0);
				Thread.sleep(1000);
			}
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

}
