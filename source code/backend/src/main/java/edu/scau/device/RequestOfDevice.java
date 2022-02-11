package edu.scau.device;

/***
 * @author *00
 * @time 21.09.24
 * 可以理解为一个结构体，结构体中有PID，设备种类，占用设备时间
 */
public class RequestOfDevice {
    private char deviceRequest;//请求分配的设备类型
    private int requestTime;//需要占用设备的时间
    private int PID;//进程唯一标识符

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public char getDeviceRequest() {
        return deviceRequest;
    }

    public void setDeviceRequest(char deviceRequest) {
        this.deviceRequest = deviceRequest;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public RequestOfDevice(int PID,char deviceRequest, int requestTime)
    {
        this.PID=PID;
        this.requestTime=requestTime;
        this.deviceRequest=deviceRequest;
    }

}
