package pl.mperor.lab.java.design.pattern.structural.facade;

public class CPU {

    public void freeze() {
        System.out.println("CPU is freezing");
    }

    public void jump(long position) {
        System.out.println("CPU jumped to " + position);
    }

    public void execute() {
        System.out.println("CPU is executing");
    }
}
