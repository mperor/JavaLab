package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

record YellowLight() implements TrafficLightState {

    @Override
    public void change(TrafficLight trafficLight) {
        trafficLight.setState(new RedLight());
    }

    @Override
    public void display() {
        System.out.println("🟡 Caution! The light is YELLOW.");
    }
}