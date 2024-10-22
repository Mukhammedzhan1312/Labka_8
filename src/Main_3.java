import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface IMediator {
    void sendMessage(String message, Colleague sender);
    void registerColleague(Colleague colleague);
    List<Colleague> getColleagues();
}

abstract class Colleague {
    protected IMediator mediator;

    public Colleague(IMediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receiveMessage(String message);
}

class ChatMediator implements IMediator {
    private List<Colleague> colleagues = new ArrayList<>();
    private List<String> messageLog = new ArrayList<>();

    @Override
    public void registerColleague(Colleague colleague) {
        colleagues.add(colleague);
    }

    @Override
    public void sendMessage(String message, Colleague sender) {
        if (!colleagues.contains(sender)) {
            System.out.println("Ошибка: Участник не зарегистрирован в чате.");
            return;
        }
        messageLog.add(sender.getClass().getSimpleName() + ": " + message);
        for (Colleague colleague : colleagues) {
            if (colleague != sender) {
                colleague.receiveMessage(message);
            }
        }
    }

    public void showMessageLog() {
        System.out.println("История сообщений:");
        for (String log : messageLog) {
            System.out.println(log);
        }
    }

    public void removeColleague(Colleague colleague) {
        colleagues.remove(colleague);
        System.out.println("Участник отключен от чата.");
    }

    @Override
    public List<Colleague> getColleagues() {
        return colleagues;
    }
}

class User extends Colleague {
    private String name;

    public User(IMediator mediator, String name) {
        super(mediator);
        this.name = name;
    }

    public void send(String message) {
        System.out.println(name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    public void sendPrivateMessage(String message, User receiver) {
        System.out.println(name + " отправляет приватное сообщение: " + message);
        receiver.receiveMessage("(Приватно) " + message);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println(name + " получил сообщение: " + message);
    }

    public String getName() {
        return name;
    }
}

public class Main_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatMediator chatMediator = new ChatMediator();
        User alice = new User(chatMediator, "Алиса");
        User bob = new User(chatMediator, "Боб");
        User charlie = new User(chatMediator, "Чарли");
        chatMediator.registerColleague(alice);
        chatMediator.registerColleague(bob);
        chatMediator.registerColleague(charlie);
        boolean running = true;

        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Отправить сообщение");
            System.out.println("2. Отправить приватное сообщение");
            System.out.println("3. Показать историю сообщений");
            System.out.println("4. Отключить участника");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя отправителя (Алиса, Боб, Чарли): ");
                    String senderName = scanner.nextLine();
                    User sender = getUserByName(chatMediator, senderName);
                    if (sender != null) {
                        System.out.print("Введите сообщение: ");
                        String message = scanner.nextLine();
                        sender.send(message);
                    } else {
                        System.out.println("Участник не найден.");
                    }
                    break;

                case 2:
                    System.out.print("Введите имя отправителя (Алиса, Боб, Чарли): ");
                    String privateSenderName = scanner.nextLine();
                    User privateSender = getUserByName(chatMediator, privateSenderName);
                    if (privateSender != null) {
                        System.out.print("Введите имя получателя: ");
                        String receiverName = scanner.nextLine();
                        User receiver = getUserByName(chatMediator, receiverName);
                        if (receiver != null) {
                            System.out.print("Введите приватное сообщение: ");
                            String privateMessage = scanner.nextLine();
                            privateSender.sendPrivateMessage(privateMessage, receiver);
                        } else {
                            System.out.println("Получатель не найден.");
                        }
                    } else {
                        System.out.println("Участник не найден.");
                    }
                    break;

                case 3:
                    chatMediator.showMessageLog();
                    break;

                case 4:
                    System.out.print("Введите имя участника для отключения (Алиса, Боб, Чарли): ");
                    String nameToRemove = scanner.nextLine();
                    User userToRemove = getUserByName(chatMediator, nameToRemove);
                    if (userToRemove != null) {
                        chatMediator.removeColleague(userToRemove);
                    } else {
                        System.out.println("Участник не найден.");
                    }
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }

        scanner.close();
        System.out.println("Выход из чата.");
    }

    private static User getUserByName(ChatMediator mediator, String name) {
        for (Colleague colleague : mediator.getColleagues()) {
            if (colleague instanceof User && ((User) colleague).getName().equalsIgnoreCase(name)) {
                return (User) colleague;
            }
        }
        return null;
    }
}
