package remotecontrol;

public class RemoteControl {
    private Command command;

    public RemoteControl(Command command){
        this.command=command;
    }
    public void setCommand(Command command){
        this.command=command;
    }

    public void pressButton(){
        command.execute();
    }

    public static void main(String[] args) {
        Light light = new Light();
        Fan fan = new Fan();

        RemoteControl remote =
                new RemoteControl(new LightOnCommand(light));

        remote.pressButton();

        remote.setCommand(new FanStartCommand(fan));
        remote.pressButton();

    }
}
