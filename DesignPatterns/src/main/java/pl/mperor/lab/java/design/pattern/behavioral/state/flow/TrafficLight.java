package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

class TrafficLight {

    private TrafficLightState state;

    TrafficLight() {
        this.state = new RedLight();
    }

    void setState(TrafficLightState state) {
        this.state = state;
    }

    void change() {
        state.change(this);
    }

    void display() {
        state.display();
    }
}
