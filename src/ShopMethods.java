import java.util.Scanner;

public class ShopMethods {
    private int stage = 1;
    Repository r = new Repository();
    Scanner scanner = new Scanner(System.in);
    int currentShoeId;
    int currentCustomerId;
    int currentOrderId = 0;
    boolean firstOrderItem;

    public void logInMethod() throws InterruptedException {
        boolean loggedIn = false;
        while (true) {
            String name;
            String password;
            System.out.println("Namn:");
            name = scanner.nextLine();
            System.out.println("Password:");
            password = scanner.nextLine();
            currentCustomerId = r.loggIn(name, password);
            if (currentCustomerId != -1) {
                shoppingForShoes();
            } else {
                System.out.println("Fel användarnamn eller lösenord. Försök att logga in igen");
            }
        }
    }

    public void shoppingForShoes() throws InterruptedException {
        r.showAllShoeModells();
        String brand;
        String colour;
        int size;
        System.out.println("Vilken skomärke vill du köpa?");
        scanner.nextLine();
        brand = scanner.nextLine();
        System.out.println("Vilken färg?");
        colour = scanner.nextLine();
        System.out.println("Vilken Storlek?");
        size = scanner.nextInt();
        currentShoeId = r.shoeFound(brand, colour, size);
        chooseShoe();
    }

    public void chooseShoe() throws InterruptedException {
            if (currentShoeId != -1) {
                if (currentOrderId == 0) {
                    currentOrderId = r.callAddToCart(currentCustomerId, 0, currentShoeId);
                } else {
                    r.callAddToCart(currentCustomerId, currentOrderId, currentShoeId);
                }
                System.out.println("Varan las till i beställningen!");
            } else {

                System.out.println("Fel uppstod och varan kunde inte läggas in :/ ");
            }
            continueOrView();
    }
    public void continueOrView() throws InterruptedException {
            System.out.println("Vill du fortsätta handla eller se din beställning?" +
                    "\nOm du vill fortsätta handla tryck 1 om du vill se din beställning tryck 2");
            int input = scanner.nextInt();
            if (input == 1) {
                shoppingForShoes();
            } else if (input == 2) {
                r.viewOrder(currentOrderId);
                System.out.println("För att fortsätta handla tryck 1");
                if (scanner.nextInt() == 1){
                    shoppingForShoes();
                }
            }

    }
}