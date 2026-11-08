package computerbuilder;

class ComputerDirector {

    public Computer buildGamingComputer() {
        return new Computer.Builder("Intel i9", 32)
                .storage(2048)
                .wifi(true)
                .build();
    }

    public Computer buildOfficeComputer() {
        return new Computer.Builder("Intel i5", 8)
                .storage(256)
                .wifi(true)
                .build();
    }
}