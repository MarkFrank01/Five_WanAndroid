package github.markfrank01.five_wanandroid.model.constant;

/**
 * Created by MarkFrank01
 * description :
 */

public class MessageEvent {

    public int code;
    private String mess;
    private String name;

    public MessageEvent(int code, String mess) {
        this.code = code;
        this.mess = mess;
    }

    public MessageEvent(int code, String mess, String name) {
        this.code = code;
        this.mess = mess;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
