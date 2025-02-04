package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

record RedLight() implements TrafficLightState {

    @Override
    public void change(TrafficLight trafficLight) {
        trafficLight.setState(new GreenLight());
    }

    @Override
    public void display() {
        System.out.println("🔴 STOP! The light is RED.");
    }
}