package pl.mperor.lab.java.design.pattern.structural.facade;

public class ComputerFacade implements Computer {

    private final static long BOOT_ADDRESS = 0x0;
    private final static long BOOT_SECTOR = 0xF;
    private final static int SECTOR_SIZE = 16;

    private final CPU processor;
    private final Memory ram;
    private final HardDrive hd;

    public ComputerFacade() {
        this.processor = new CPU();
        this.ram = new Memory();
        this.hd = new HardDrive();
    }

    @Override
    public void start() {
        processor.freeze();
        byte[] data = hd.read(BOOT_SECTOR, SECTOR_SIZE);
        ram.load(BOOT_ADDRESS, data);
        processor.jump(BOOT_ADDRESS);
        processor.execute();
    }
}
