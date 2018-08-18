package order.util;

import java.util.Random;

public class KeyUtil {

    public static synchronized String genUniqueKey() {
        //加个6位随机数
        Random random = new Random();
        random.nextInt(900000);
        return String.valueOf(random);
    }
}
