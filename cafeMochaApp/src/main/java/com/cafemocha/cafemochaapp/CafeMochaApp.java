package com.cafemocha.cafemochaapp;

import java.io.*;
import java.util.Scanner;

public class CafeMochaApp {

    // File paths
    static final String USER_DATA_PATH = "resources/user.txt";
    static final String ORDERS_DATA_PATH = "resources/orders.txt";
    static final String MENU_DATA_PATH = "resources/menu.txt";
    static final String BILLS_DATA_PATH = "resources/bills.txt";

    public static void main(String[] args) {
        showWelcomeScreen();
        boolean isUserLoggedIn = false;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!isUserLoggedIn) {
                isUserLoggedIn = authenticateUser();
                if (isUserLoggedIn) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed! Please try again.");
                }
            } else {
                displayOptions(scanner);
                isUserLoggedIn = false; // Set to false to require login again after returning
            }
        }
    }

    public static void showWelcomeScreen() {
        // Display ASCII art of a coffee cup with stars
        System.out.println("*****************************************************************************************************************************");
        System.out.println("|                              |     ____                _____   ____       _    __     __     ____    _    _                 |");
        System.out.println("|                              |    / ____|     /\\     |  ____| |  ____|   |  \\/  |  / __ \\   / ____| | |  | |     /\\       |");
        System.out.println("|        Café Mocha App        |   | |         /  \\    | |__    | |__      | \\  / | | |  | | | |      | |__| |    /  \\      |");
        System.out.println("|                              |   | |        / /\\ \\   |  __|   |  __|     | |\\/| | | |  | | | |      |  __  |   / /\\ \\     |");
        System.out.println("********************************   | |____   / ____ \\  | |      | |____    | |  | | | |__| | | |____  | |  | |  / ____ \\    |");
        System.out.println("|        ( (      ) )          |    \\_____| /_/    \\_\\ |_|      |______|   |_|  |_|  \\____/   \\_____| |_|  |_| /_/    \\_\\   |");
        System.out.println("|         ) )    ( (           |                                                                                            |");
        System.out.println("|       ...............        |*********************************************************************************************");
        System.out.println("|       |             |        |                                Your cozy spot for delightful                               |");
        System.out.println("|       \\             /        |*********************************************************************************************");
        System.out.println("|        `-----------'         |                  Cofees    | Bakery Goods    | Soups    | and more                         |");
        System.out.println("|        \\              /      |                                                                                            |");
        System.out.println("|         `------------'       |                  Coffee and treats.                                                        |");
        System.out.println("*****************************************************************************************************************************");
        System.out.println("| Welcome to Café Mocha!       |                                                                                            |");
        System.out.println("|                               |                                                                                            |");
        System.out.println("*****************************************************************************************************************************");
    }

public static boolean authenticateUser() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Your Username: ");
    String userName = scanner.nextLine();
    System.out.print("Enter Your Password: ");
    String userPassword = scanner.nextLine();

    boolean usernameFound = false;
    boolean passwordCorrect = false;

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DATA_PATH))) {
        String userRecord;
        while ((userRecord = bufferedReader.readLine()) != null) {
            String[] userDetails = userRecord.split(",");
            if (userDetails.length == 2 && userDetails[0].equals(userName)) {
                usernameFound = true;
                if (userDetails[1].equals(userPassword)) {
                    passwordCorrect = true;
                    return true;  
                }
            }
        }
    } catch (IOException ioException) {
        System.out.println("Error reading users file.");
    }

    if (!usernameFound) {
        System.out.println("Error: Username not found.");
    } else if (!passwordCorrect) {
        System.out.println("Error: Incorrect password.");
    }

    return false; 
}


    public static void displayOptions(Scanner scanner) {
        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1.  Add New Order");
            System.out.println("2.  Show Order Information");
            System.out.println("3.  Generate Bill");
            System.out.println("4.  Add New Item to Menu");
            System.out.println("5.  Modify Order");
            System.out.println("6.  Remove Order");
            System.out.println("7.  Logout");
            System.out.println("8.  Help");
            System.out.println("9.  Exit");
            System.out.print("Select an option (1-9): ");

            int selectedOption = scanner.nextInt();
            scanner.nextLine(); 

            switch (selectedOption) {
                case 1 -> createNewOrder();
                case 2 -> showOrderInfo();
                case 3 -> generateBill();
                case 4 -> addMenuItem();
                case 5 -> modifyOrder();
                case 6 -> removeOrder();
                case 7 -> {
                    System.out.println("Successfully logged out. Have a nice day!");
                    return; // Return to the main loop
                }
                case 8 -> showHelp();
                case 9 -> {
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void createNewOrder() {
        displayMenu(); // Display the menu before asking for order items
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Customer Address: ");
        String customerAddress = scanner.nextLine();
        System.out.print("Enter Customer Phone Number: ");
        String customerPhone = scanner.nextLine();
        System.out.print("Enter Order Items (Format: ItemName:Quantity;ItemName:Quantity): ");
        String orderItems = scanner.nextLine();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ORDERS_DATA_PATH, true))) {
            bufferedWriter.write(orderId + "," + customerName + "," + customerAddress + "," + customerPhone + "," + orderItems);
            bufferedWriter.newLine();
            System.out.println("Order successfully added!");
        } catch (IOException ioException) {
            System.out.println("Error processing order data.");
        }
    }

    public static void showOrderInfo() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ORDERS_DATA_PATH))) {
            String orderLine;
            while ((orderLine = bufferedReader.readLine()) != null) {
                String[] orderDetails = orderLine.split(",");
                if (orderDetails.length >= 5) {
                    System.out.println("Order ID: " + orderDetails[0]);
                    System.out.println("Customer Name: " + orderDetails[1]);
                    System.out.println("Customer Address: " + orderDetails[2]);
                    System.out.println("Customer Phone: " + orderDetails[3]);
                    System.out.println("Order Items: " + orderDetails[4]);
                    System.out.println("------------------------");
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error reading order data.");
        }
    }

    public static void generateBill() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        double totalCost = 0;
        boolean orderFound = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(ORDERS_DATA_PATH))) {
            String orderLine;
            while ((orderLine = bufferedReader.readLine()) != null) {
                String[] orderDetails = orderLine.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderId)) {
                    orderFound = true;
                    String[] items = orderDetails[4].split(";");
                    for (String item : items) {
                        String[] itemDetails = item.split(":");
                        if (itemDetails.length == 2) {
                            double itemPrice = fetchItemPrice(itemDetails[0]);
                            totalCost += itemPrice * Integer.parseInt(itemDetails[1]);
                        }
                    }
                    break;
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error reading order data.");
        }

        if (orderFound) {
            System.out.println("Total Bill: $" + totalCost);
            recordBill(orderId, totalCost);
        } else {
            System.out.println("Error: No matching order ID found.");
        }
    }

    public static double fetchItemPrice(String itemName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MENU_DATA_PATH))) {
            String menuLine;
            while ((menuLine = bufferedReader.readLine()) != null) {
                String[] menuDetails = menuLine.split(",");
                if (menuDetails.length >= 3 && menuDetails[0].equalsIgnoreCase(itemName.trim())) {
                    return Double.parseDouble(menuDetails[2].trim());
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error reading menu data.");
        }
        return 0;
    }

    public static void recordBill(String orderId, double totalCost) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(BILLS_DATA_PATH, true))) {
            bufferedWriter.write(orderId + "," + String.format("%.2f", totalCost));
            bufferedWriter.newLine();
        } catch (IOException ioException) {
            System.out.println("Error recording bill data.");
        }
    }

    public static void addMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter Item Description: ");
        String itemDescription = scanner.nextLine();
        System.out.print("Enter Item Price: ");
        double itemPrice = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline character
        System.out.print("Enter Item Category: ");
        String itemCategory = scanner.nextLine();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(MENU_DATA_PATH, true))) {
            bufferedWriter.write(itemName + "," + itemDescription + "," + String.format("%.2f", itemPrice) + "," + itemCategory);
            bufferedWriter.newLine();
            System.out.println("Menu item added successfully!");
        } catch (IOException ioException) {
            System.out.println("Error adding menu item.");
        }
    }

    public static void modifyOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Order ID to modify: ");
        String orderId = scanner.nextLine();

        // Read existing orders
        File ordersFile = new File(ORDERS_DATA_PATH);
        File tempFile = new File("resources/temp_orders.txt");
        boolean orderFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String orderLine;
            while ((orderLine = reader.readLine()) != null) {
                String[] orderDetails = orderLine.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderId)) {
                    orderFound = true;
                    System.out.println("Current Order Details: " + orderLine);
                    System.out.print("Enter new Order Items (Format: ItemName:Quantity;ItemName:Quantity): ");
                    String newOrderItems = scanner.nextLine();
                    writer.write(orderId + "," + orderDetails[1] + "," + orderDetails[2] + "," + orderDetails[3] + "," + newOrderItems);
                } else {
                    writer.write(orderLine);
                }
                writer.newLine();
            }

        } catch (IOException ioException) {
            System.out.println("Error processing order modification.");
        }

        if (orderFound) {
            if (ordersFile.delete() && tempFile.renameTo(ordersFile)) {
                System.out.println("Order modified successfully!");
            } else {
                System.out.println("Error updating the order file.");
            }
        } else {
            System.out.println("Order ID not found.");
        }
    }

    public static void removeOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Order ID to remove: ");
        String orderId = scanner.nextLine();

        // Read existing orders
        File ordersFile = new File(ORDERS_DATA_PATH);
        File tempFile = new File("resources/temp_orders.txt");
        boolean orderFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String orderLine;
            while ((orderLine = reader.readLine()) != null) {
                String[] orderDetails = orderLine.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderId)) {
                    orderFound = true;
                    System.out.println("Removed Order: " + orderLine);
                } else {
                    writer.write(orderLine);
                    writer.newLine();
                }
            }

        } catch (IOException ioException) {
            System.out.println("Error processing order removal.");
        }

        if (orderFound) {
            if (ordersFile.delete() && tempFile.renameTo(ordersFile)) {
                System.out.println("Order removed successfully!");
            } else {
                System.out.println("Error updating the order file.");
            }
        } else {
            System.out.println("Order ID not found.");
        }
    }

    public static void showHelp() {
        System.out.println("\nHelp Menu:");
        System.out.println("1. Add New Order: Create a new order by providing order details and items.");
        System.out.println("2. Show Order Information: View the details of all orders.");
        System.out.println("3. Generate Bill: Calculate and print the bill for a given order ID.");
        System.out.println("4. Add New Item to Menu: Add a new item to the menu with its details.");
        System.out.println("5. Modify Order: Modify the details of an existing order.");
        System.out.println("6. Remove Order: Remove an order from the system.");
        System.out.println("7. Logout: Logout from the system and return to the login screen.");
        System.out.println("8. Help: Show this help menu.");
        System.out.println("9. Exit: Exit the application.");
    }

    public static void displayMenu() {
        System.out.println("Available Menu Items:");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-38s | %-6s | %-10s |\n", "Item Name", "Description", "Price", "Category");
        System.out.println("---------------------------------------------------------------------------------------");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MENU_DATA_PATH))) {
            String menuLine;
            while ((menuLine = bufferedReader.readLine()) != null) {
                String[] menuDetails = menuLine.split(",");
                if (menuDetails.length >= 4) {
                    try {
                        String itemName = menuDetails[0].trim();
                        String itemDescription = menuDetails[1].trim();
                        double itemPrice = Double.parseDouble(menuDetails[2].trim());
                        String itemCategory = menuDetails[3].trim();
                        System.out.printf("| %-20s | %-38s | $%-6.2f | %-10s |\n", itemName, itemDescription, itemPrice, itemCategory);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid price format for item: " + menuDetails[0]);
                    }
                } else {
                    System.out.println("Error: Invalid menu line format: " + menuLine);
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error reading menu data.");
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }
}
