package com.shankephone.mqtt.demo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MQTTSubTest {

	public static void main(String[] args) {
		String[] topic = {"$queue/server2"};
		int[] qos = {2};
		String broker = "tcp://testmqtt.shankephone.com:1883";
		String clientId = "subtest2";
		/*MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence("D:\\mqtt1");*/
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
			sampleClient.setCallback(new MqttCallback() {

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("clientId" + clientId + "," + topic + "," + message);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
				}

				@Override
				public void connectionLost(Throwable cause) {
				}
			});
			sampleClient.subscribe(topic, qos);
			// sampleClient.disconnect();
			// System.out.println("Disconnected");
			// System.exit(0);
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
