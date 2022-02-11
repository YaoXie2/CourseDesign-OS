package edu.scau.device;

public class Device {
    private char device;
    private boolean flag=false;//标志设备是否被占用
    private RequestOfDevice requestOfDevice;

    public RequestOfDevice getRequestOfDevice() {
        return requestOfDevice;
    }

    public void setRequestOfDevice(RequestOfDevice requestOfDevice) {
        this.requestOfDevice = requestOfDevice;
    }

    public Device(char device)
    {
        this.device=device;
        flag=false;//false表示未被占用
    }
    public char getDevice() {
        return device;
    }

    public void setDevice(char device) {
        this.device = device;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
