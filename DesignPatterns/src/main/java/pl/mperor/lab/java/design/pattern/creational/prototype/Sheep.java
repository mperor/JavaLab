package pl.mperor.lab.java.design.pattern.creational.prototype;

import java.time.Year;
import java.util.function.Function;

public class Sheep {

    private String name;
    private DNA dna;
    private Year yearOfBirth;
    private Breed breed;

    private Sheep(Sheep sheep) {
        this.name = sheep.name;
        this.dna = sheep.dna;
        this.yearOfBirth = Year.now();
        this.breed = sheep.breed;
    }

    public Sheep(String name, DNA dna, Year yearOfBirth, Breed breed) {
        this.name = name;
        this.dna = dna;
        this.yearOfBirth = yearOfBirth;
        this.breed = breed;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    DNA getDna() {
        return dna;
    }

    Year getYearOfBirth() {
        return yearOfBirth;
    }

    Breed getBreed() {
        return breed;
    }

    public Sheep clone(Function<CloneBuilder, Sheep> cloning) {
        return cloning.apply(new CloneBuilder(new Sheep(this)));
    }

    public class CloneBuilder {
        private Sheep clone;

        public CloneBuilder(Sheep clone) {
            this.clone = clone;
        }

        public CloneBuilder name(String name) {
            clone.name = name;
            return this;
        }

        public Sheep build() {
            return clone;
        }
    }

    enum Breed {
        MERINO,
        SUFFOLK,
        DORSET,
        HAMPSHIRE
    }
}
