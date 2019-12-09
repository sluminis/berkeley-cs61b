public class ShowDog extends Dog{
    ShowDog() {
        super();
    }

    @Override
    public String bark() {
        return "ShowDog bark~~";
    }

    @Override
    public String toString() {
        return "A Dog";
    }
}
