package uifactory;

public class UIDemo {
    public static void main(String[] args) {
        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        System.out.println(button.render());
        Checkbox checkbox = factory.createCheckbox();

        GUIFactory winFactory = new WindowsFactory();
        Button winButton = winFactory.createButton();
        System.out.println(winButton.render());
        Checkbox winCheckbox = winFactory.createCheckbox();
    }
}
