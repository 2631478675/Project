package order.message;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class) //自定义接口的名字
public class StreamReceiver {
    @StreamListener("message")
    public void process(Object message){ //形参是接收到的消息
      //todo
    }
}
