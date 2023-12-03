import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;

class MenuItem {
    String name;
    int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    
    public String toString() {
        return name + " - $" + price;
    }
}

class Order {
    int orderNumber;
    MenuItem menuItem;
    int preparationTime;

    public Order(int orderNumber, MenuItem menuItem, int preparationTime) {
        this.orderNumber = orderNumber;
        this.menuItem = menuItem;
        this.preparationTime = preparationTime;
    }
}

class Restaurant {
    LinkedList<MenuItem> menu;
    Queue<Order> orderQueue;
    Stack<Order> orderPreparationStack;
    int orderNumberCounter;
    double totalBill;

    public Restaurant() {
        menu = new LinkedList<>();
        orderQueue = new LinkedList<>();
        orderPreparationStack = new Stack<>();
        orderNumberCounter = 1;
        totalBill = 0;
        // Mawa we are adding Breakfast items
        menu.add(new MenuItem("Dosa", 20));
        menu.add(new MenuItem("Idli", 20));
        menu.add(new MenuItem("Chapathi", 25));
        menu.add(new MenuItem("Poori", 22));
        menu.add(new MenuItem("Uttappam", 25));
        menu.add(new MenuItem("Parotta", 30));
        menu.add(new MenuItem("Upma", 30));
        // Mawa we are adding Lunch items
        menu.add(new MenuItem("Meals", 50));
        menu.add(new MenuItem("Biryani", 60));
        menu.add(new MenuItem("Chicken Tikka", 70));
        menu.add(new MenuItem("Fried Rice", 40));
        menu.add(new MenuItem("Gobi Rice", 40));
        menu.add(new MenuItem("Khichdi", 50));
        menu.add(new MenuItem("Dal", 30));
        // Mawa we are adding Dinner items
        menu.add(new MenuItem("Meals", 50));
        menu.add(new MenuItem("Chicken Kabab", 100));
        menu.add(new MenuItem("Parotta", 40));
        menu.add(new MenuItem("Tandoori Chicken", 120));
        menu.add(new MenuItem("Vegetable Rice", 35));
    }

       public void displayMenu() {
        System.out.println("Menu:");
        displayMealItems("Breakfast", "Lunch", "Dinner");
    }

    private void displayMealItems(String breakfastLabel, String lunchLabel, String dinnerLabel) {
        System.out.println(breakfastLabel);

        boolean lunchDisplayed = false;
        boolean dinnerDisplayed = false;

        for (MenuItem item : menu) {
            if (item.name.equals("Meals") && !lunchDisplayed) {
                System.out.println("\n" + lunchLabel);
                lunchDisplayed = true;
            } else if (item.name.equals("Meals") && item.price == 50&& !dinnerDisplayed) {
                System.out.println("\n" + dinnerLabel);
                dinnerDisplayed = true;
            }

            System.out.println(item.name + " - " + item.price);
        }
    }


    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the item name to order:");
        String itemName = scanner.nextLine().trim();

        orderItem(itemName);
    }

    private void orderItem(String itemName) {
        MenuItem menuItem = findMenuItem(itemName);
        if (menuItem != null) {
            Order order = new Order(orderNumberCounter++, menuItem, getOrderPreparationTime(itemName));
            orderQueue.add(order);
            totalBill += menuItem.price;
            System.out.println("Thank you for placing your order!");
        } else {
            System.out.println("Item not found in the menu: " + itemName);
        }
    }

     public void displayOrderStatus() {
        System.out.println("Order Queue:");
        for (Order order : orderQueue) {
            System.out.println("Ur order will be placed to u in 5mins Sir");
            System.out.println("Order " + order.orderNumber + ": " + order.menuItem.name);
        }

        System.out.println("\nOrder Preparation Stack:");
        for (Order order : orderPreparationStack) {
            System.out.println("Ur order will be placed to u in 5mins Sir");
            System.out.println("Order " + order.orderNumber + ": " + order.menuItem.name +
                    " (Preparation Time: " + order.preparationTime + " minutes)");
        }
    }

    private int getOrderPreparationTime(String itemName) {
        // preparation time of 5 minutes for all items
        return 5;
    }

    public void cancelOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the order number to cancel:");
        int orderNumber = scanner.nextInt();

        boolean orderFound = false;
        Queue<Order> tempQueue = new LinkedList<>();

        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order.orderNumber == orderNumber) {
                System.out.println("Order canceled: " + order.menuItem.name);
                orderFound = true;
            } else {
                tempQueue.add(order);
            }
        }

        while (!tempQueue.isEmpty()) {
            orderQueue.add(tempQueue.poll());
        }

        if (!orderFound) {
            System.out.println("Order not found with the given order number.");
        }
    }

    public void thankYouMessage() {
        System.out.println("Thank you for visiting our restaurant! Have a nice day.");
        System.out.println("Total Bill: " + totalBill + " rupees");
    }

    private MenuItem findMenuItem(String itemName) {
        for (MenuItem item : menu) {
            if (item.name.equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Customer Menu:");
            System.out.println("1. Display Menu");
            System.out.println("2. Place an Order");
            System.out.println("3. Check Order Status");
            System.out.println("4. Cancel Order");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    restaurant.displayMenu();
                    break;
                case 2:
                    restaurant.takeOrder();
                    break;
                case 3:
                    restaurant.displayOrderStatus();
                    break;
                case 4:
                    restaurant.cancelOrder();
                    break;
                case 5:
                    restaurant.thankYouMessage();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice!=5);
}
}
