package pl.mperor.lab.java.design.pattern.structural.adapter;

public class DigitalToAnalogSignalAdapter implements AnalogSignal {

    private final DigitalSignal digitalSignal;

    public DigitalToAnalogSignalAdapter(DigitalSignal digitalSignal) {
        this.digitalSignal = digitalSignal;
    }

    @Override
    public void transmitAnalog() {
        digitalSignal.transmitDigital();
    }
}
