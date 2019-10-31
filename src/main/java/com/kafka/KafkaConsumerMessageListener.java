package com.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.listener.MessageListener;

public class KafkaConsumerMessageListener implements MessageListener<String,Object> {

    private Logger logger = Logger.getLogger(KafkaConsumerMessageListener.class.getName());

    public KafkaConsumerMessageListener(){

    }

    /**
     * 消息接收-LOG日志处理
     * @param record
     */
    @Override
    public void onMessage(ConsumerRecord<String, Object> record) {
        logger.info("=============kafka消息订阅=============");

        String topic = record.topic();
        String key = record.key();
        Object value = record.value();
        long offset = record.offset();
        int partition = record.partition();

        /*if (ConstantKafka.KAFKA_TOPIC1.equals(topic)){
            doSaveLogs(value.toString());
        }*/

        Object o = record.value();
        logger.info(o.toString());

        logger.info("-------------topic:"+topic);
        logger.info("-------------value:"+value);
        logger.info("-------------key:"+key);
        logger.info("-------------offset:"+offset);
        logger.info("-------------partition:"+partition);
        logger.info("=============kafka消息订阅=============");
    }

}
