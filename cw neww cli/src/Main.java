import java.util.Scanner;

public class Main {
    private static Thread[] vendorThreads = new Thread[5];
    private static Thread[] customerThreads = new Thread[5];
    private static boolean running = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jsonwrite jsonwrite = new Jsonwrite();
        Configuration configuration = null;
        TicketPool ticketPool = null;

        // Prompt for configuration
        System.out.println("Enter 'P' to load previous configuration or 'N' for new configuration:");
        while (true) {
            String option = scanner.nextLine().toUpperCase();
            if (option.equals("N")) {
                // Collect new configuration details
                System.out.println("Enter total tickets available: ");
                int totalTickets = promptPositiveNumber(scanner);

                System.out.println("Enter ticket release rate (in seconds): ");
                int ticketReleaseRate = promptPositiveNumber(scanner);

                System.out.println("Enter customer retrieval rate (in seconds): ");
                int customerRetrievalRate = promptPositiveNumber(scanner);

                System.out.println("Enter maximum ticket capacity for the pool: ");
                int maxTicketCapacity = promptPositiveNumber(scanner);

                // Create new configuration and ticket pool
                configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                ticketPool = new TicketPool(maxTicketCapacity);
                ticketPool.initializeTickets(totalTickets);

                // Save configuration to JSON file
                jsonwrite.writeConfigToJson(configuration);
                break;

            } else if (option.equals("P")) {
                // Load configuration from JSON file
                System.out.println("Loading previous configuration...");
                configuration = jsonwrite.loadConfigFromJson();

                if (configuration != null) {
                    // Initialize ticket pool based on loaded configuration
                    ticketPool = new TicketPool(configuration.getMaxTicketCapacity());
                    ticketPool.initializeTickets(configuration.getTotalTickets());
                    break;
                } else {
                    System.out.println("No previous configuration found. Exiting...");
                    return;
                }
            } else {
                System.out.println("Invalid option. Please Enter 'P' to load previous configuration or 'N' for new configuration :");
            }
        }

        // Main logic for threads
        System.out.println("Enter 1 to start or 2 to stop the program:");
        while (true) {
            int choice = promptPositiveNumber(scanner);

            if (choice == 1) {
                startThreads(configuration, ticketPool);
            } else if (choice == 2) {
                System.out.println("Stopping threads...");
                stopThreads();
                System.out.println("Threads stopped.Exiting program...");
                break;
            } else {
                System.out.println("Invalid input. Please enter 1 to start or 2 to stop:");
            }
        }
        scanner.close();
    }

    private static int promptPositiveNumber(Scanner scanner) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Invalid input.Please enter a positive integer:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }
    }

    private static void startThreads(Configuration configuration, TicketPool ticketPool) {
        running = true;

        // Vendor Threads
        for (int i = 0; i < vendorThreads.length; i++) {
            vendorThreads[i] = createAndStartThread(new Vendor(configuration.getTicketReleaseRate(), ticketPool), "Vendor-" + (i + 1));
        }

        // Customer Threads
        for (int i = 0; i < customerThreads.length; i++) {
            customerThreads[i] = createAndStartThread(new Customer(ticketPool, configuration.getCustomerRetrievalRate()), "Customer-" + (i + 1));
        }
    }

    private static Thread createAndStartThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.start();
        return thread;
    }

    private static void stopThreads() {
        running = false;

        // Interrupt and join all threads
        interruptAndJoinThreads(vendorThreads);
        interruptAndJoinThreads(customerThreads);
    }

    private static void interruptAndJoinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        }

        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) { //
                try {
                    thread.join(); //For each thread that is not null and is still alive, waits for it to finish using
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}






