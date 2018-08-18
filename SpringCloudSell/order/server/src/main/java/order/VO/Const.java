package order.VO;

public class Const {
    public enum ResponseCode{

        SUCCESS(0,"SUCCESS"),ERROR(1,"ERROR"),NEED_LOGIN(10,"NEED_LOGIN"),ILLEGAL_ARGUMENT(20,"ILLEGAL_ARGUMENT");

        private int code;
        private String values;

        ResponseCode(int code, String values) {
            this.code = code;
            this.values = values;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }
}
