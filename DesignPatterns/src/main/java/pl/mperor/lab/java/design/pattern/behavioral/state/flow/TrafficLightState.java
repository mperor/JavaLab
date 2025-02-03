package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

sealed interface TrafficLightState permits RedLight, YellowLight, GreenLight {

    void change(TrafficLight trafficLight);

    void display();
}
