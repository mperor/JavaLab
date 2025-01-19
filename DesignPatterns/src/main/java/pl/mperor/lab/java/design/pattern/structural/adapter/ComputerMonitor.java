package pl.mperor.lab.java.design.pattern.structural.adapter;

public record ComputerMonitor() {

    void display(AnalogSignal analogSignal) {
        System.out.println("🖥️ Computer monitor displaying ...");
        analogSignal.transmitAnalog();
    }
}
