import java.util.Scanner;
abstract class Beverage {
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    private void boilWater() {
        System.out.println("Кипячение воды...");
    }

    private void pourInCup() {
        System.out.println("Наливание в чашку...");
    }

    protected abstract void brew();
    protected abstract void addCondiments();

    protected boolean customerWantsCondiments() {
        return true;
    }
}
class Tea extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание чая...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление лимона...");
    }
}

class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание кофе...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление сахара и молока...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        System.out.print("Хотите добавить сахар и молоко? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("y");
    }
}
class HotChocolate extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Размешивание какао...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление маршмеллоу...");
    }
}
public class Main_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nВыберите напиток для приготовления:");
            System.out.println("1. Чай");
            System.out.println("2. Кофе");
            System.out.println("3. Горячий шоколад");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();

            Beverage beverage = null;

            switch (choice) {
                case 1 -> beverage = new Tea();
                case 2 -> beverage = new Coffee();
                case 3 -> beverage = new HotChocolate();
                case 4 -> {
                    System.out.println("Выход из программы.");
                    running = false;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }

            if (beverage != null) {
                System.out.println("\nПриготовление напитка:");
                beverage.prepareRecipe();
            }
        }

        scanner.close();
    }
}
