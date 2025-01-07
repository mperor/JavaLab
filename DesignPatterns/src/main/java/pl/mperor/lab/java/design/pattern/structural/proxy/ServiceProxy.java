package pl.mperor.lab.java.design.pattern.structural.proxy;

class ServiceProxy implements ExecutableService {

    private ExecutableService service;

    @Override
    public void execute() {
        System.out.println("Before executing service method!");
        lazyLoadingService();
        service.execute();
        System.out.println("After executing service method!");
    }

    private void lazyLoadingService() {
        if (service == null) {
            service = new ServiceImpl();
        }
    }
}
