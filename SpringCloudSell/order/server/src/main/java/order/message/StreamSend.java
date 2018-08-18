package order.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;

public class StreamSend {
    @Autowired
    StreamClient streamClient;

    public void process(){
        streamClient.output().send(MessageBuilder.withPayload("发送的消息").build());
    }
}
