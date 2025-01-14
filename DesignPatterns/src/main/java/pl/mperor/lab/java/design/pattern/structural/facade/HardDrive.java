package pl.mperor.lab.java.design.pattern.structural.facade;

public class HardDrive {

    public byte[] read(long lba, int size) {
        System.out.println(size + " bits is reading from " + lba);
        return new byte[size];
    }
}
