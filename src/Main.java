import java.util.Scanner;
interface ICommand {
    void execute();
    void undo();
}
class Light {
    public void on() {
        System.out.println("Свет включен.");
    }

    public void off() {
        System.out.println("Свет выключен.");
    }
}
class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
class Television {
    public void on() {
        System.out.println("Телевизор включен.");
    }

    public void off() {
        System.out.println("Телевизор выключен.");
    }
}
class TelevisionOnCommand implements ICommand {
    private Television tv;

    public TelevisionOnCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.on();
    }

    @Override
    public void undo() {
        tv.off();
    }
}

class TelevisionOffCommand implements ICommand {
    private Television tv;

    public TelevisionOffCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.off();
    }

    @Override
    public void undo() {
        tv.on();
    }
}
class RemoteControl {
    private ICommand onCommand;
    private ICommand offCommand;

    public void setCommands(ICommand onCommand, ICommand offCommand) {
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }

    public void pressOnButton() {
        if (onCommand != null) {
            onCommand.execute();
        } else {
            System.out.println("Команда не назначена.");
        }
    }

    public void pressOffButton() {
        if (offCommand != null) {
            offCommand.execute();
        } else {
            System.out.println("Команда не назначена.");
        }
    }

    public void pressUndoButton() {
        if (onCommand != null) {
            onCommand.undo();
        } else {
            System.out.println("Команда не назначена.");
        }
    }
}

class AirConditioner {
    public void on() {
        System.out.println("Кондиционер включен.");
    }

    public void off() {
        System.out.println("Кондиционер выключен.");
    }

    public void setEnergySavingMode() {
        System.out.println("Кондиционер работает в режиме экономии энергии.");
    }
}
class AirConditionerOnCommand implements ICommand {
    private AirConditioner ac;

    public AirConditionerOnCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.on();
    }

    @Override
    public void undo() {
        ac.off();
    }
}

class AirConditionerOffCommand implements ICommand {
    private AirConditioner ac;

    public AirConditionerOffCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.off();
    }

    @Override
    public void undo() {
        ac.on();
    }
}
public class Main {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        Television tv = new Television();
        AirConditioner ac = new AirConditioner();
        ICommand lightOn = new LightOnCommand(livingRoomLight);
        ICommand lightOff = new LightOffCommand(livingRoomLight);
        ICommand tvOn = new TelevisionOnCommand(tv);
        ICommand tvOff = new TelevisionOffCommand(tv);
        ICommand acOn = new AirConditionerOnCommand(ac);
        ICommand acOff = new AirConditionerOffCommand(ac);
        RemoteControl remote = new RemoteControl();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите устройство для управления:");
            System.out.println("1. Свет");
            System.out.println("2. Телевизор");
            System.out.println("3. Кондиционер");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    remote.setCommands(lightOn, lightOff);
                    System.out.println("Управление светом:");
                    executeCommands(remote, scanner);
                }
                case 2 -> {
                    remote.setCommands(tvOn, tvOff);
                    System.out.println("Управление телевизором:");
                    executeCommands(remote, scanner);
                }
                case 3 -> {
                    remote.setCommands(acOn, acOff);
                    System.out.println("Управление кондиционером:");
                    executeCommands(remote, scanner);
                }
                case 4 -> {
                    System.out.println("Выход из программы.");
                    running = false;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        scanner.close();
    }
    private static void executeCommands(RemoteControl remote, Scanner scanner) {
        System.out.println("1. Включить");
        System.out.println("2. Выключить");
        System.out.println("3. Отменить действие");
        System.out.println("4. Назад");
        int commandChoice = scanner.nextInt();
        switch (commandChoice) {
            case 1 -> remote.pressOnButton();
            case 2 -> remote.pressOffButton();
            case 3 -> remote.pressUndoButton();
            case 4 -> System.out.println("Возврат в главное меню.");
            default -> System.out.println("Неверный выбор команды.");
        }
    }
}
