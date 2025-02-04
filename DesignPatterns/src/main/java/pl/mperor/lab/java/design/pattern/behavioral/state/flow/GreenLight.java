package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

record GreenLight() implements TrafficLightState {

    @Override
    public void change(TrafficLight trafficLight) {
        trafficLight.setState(new YellowLight());
    }

    @Override
    public void display() {
        System.out.println("🟢 GO! The light is GREEN.");
    }
}