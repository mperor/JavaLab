package pl.mperor.lab.java.design.pattern.structural.proxy;

class ServiceImpl implements ExecutableService {

    ServiceImpl() {
        System.out.println("Service has been initialized!");
    }

    @Override
    public void execute() {
        System.out.println("Method has been executed!");
    }
}
