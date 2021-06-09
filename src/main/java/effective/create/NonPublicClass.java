package effective.create;

class NonPublicClass {
    @Override
    public String toString() {
        return "NonPublicClass: " + super.hashCode();
    }

    public static NonPublicClass getInstance() {
        return new NonPublicClass();
    }
}
