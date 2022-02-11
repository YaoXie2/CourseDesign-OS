package edu.scau.process;

import edu.scau.device.DeviceManagement;
import edu.scau.device.RequestOfDevice;

/*
 *负责一个时间片，解释并运行一条指令，并且令设备管理器里面的时间--
 */
public class CPU {
    //返回值-1表示发生了进程阻塞，0则正常,deviceOverPid里面放的是这个时间片结束之后可能完成设备调用的进程
    public static int cpu(int pid, DeviceManagement deviceManagement) {

        PCB pcb = PCBManager.getPcbs()[pid];


        int pc = Registers.getPc();
        String instruction = pcb.getInstructions()[pc];
        Registers.setIr(instruction);

        //解释指令
        int instructionType = explain();
        pc++;                  //pc++
        Registers.setPc(pc);

        if (instructionType == -1) {
            return -2;//如果是end指令，则返回值为-2
        } else if (instructionType == 1) {//申请设备
            char device = instruction.charAt(1);
            int workTime = Integer.parseInt(instruction.substring(2));
            //申请设备，然后进程阻塞
            if (requestForDevice(deviceManagement, pid, device, workTime) != -1) {
                return -1;
            }
        }

        return 0;
    }

    //解释指令并执行,返回值表示不同的指令，-1表示end指令，0表示赋值和自增自减指令，1表示申请设备指令，pc++
    private static int explain() {
        String instruction = Registers.getIr();
        int ax = Registers.getAx();
        int pc = Registers.getPc();

        if (instruction.charAt(0) == 'x' && instruction.charAt(1) == '=') {
            String num = instruction.substring(2);
            ax = Integer.parseInt(num);
            Registers.setAx(ax);
            return 0;
        } else if (instruction.equals("x++")) {
            ax++;
            Registers.setAx(ax);

            return 0;
        } else if (instruction.equals("x--")) {
            ax--;
            Registers.setAx(ax);
            return 0;
        } else if (instruction.charAt(0) == '!') {

            return 1;
        } else {

            return -1;//end指令
        }
    }

    //申请设备
    private static int requestForDevice(DeviceManagement deviceManagement, int pid, char device, int workTime) {
        if (device == 'A' || device == 'B' || device == 'C') {
            //申请设备
            RequestOfDevice requestOfDevice = new RequestOfDevice(pid, device, workTime);
            deviceManagement.run(requestOfDevice);

            return 0;
        } else {
            System.out.println("（CPU）设备类型输入错误");
            return -1;
        }
    }
}
