import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

// 1. Class representing a Stock
class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    // Simulates market fluctuation: Price changes by -2% to +2%
    public void updatePrice() {
        Random rand = new Random();
        double changePercent = (rand.nextDouble() * 0.04) - 0.02; 
        this.price += this.price * changePercent;
        
        // Ensure price never drops to absolute zero for this logic
        if (this.price < 0.01) this.price = 0.01;
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

// 2. Class representing the User's Portfolio
class Portfolio {
    private double balance;
    // Map stores Symbol -> Quantity (e.g., "AAPL" -> 10 shares)
    private Map<String, Integer> holdings; 

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > balance) {
            System.out.println("❌ Insufficient funds! Cost: $" + String.format("%.2f", cost) + ", Balance: $" + String.format("%.2f", balance));
        } else {
            balance -= cost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("✅ Successfully bought " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void sellStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();
        int currentQuantity = holdings.getOrDefault(symbol, 0);

        if (currentQuantity < quantity) {
            System.out.println("❌ Not enough shares to sell! You own: " + currentQuantity);
        } else {
            double revenue = stock.getPrice() * quantity;
            balance += revenue;
            holdings.put(symbol, currentQuantity - quantity);
            
            // Remove from map if 0 shares left to keep it clean
            if (holdings.get(symbol) == 0) {
                holdings.remove(symbol);
            }
            System.out.println("✅ Successfully sold " + quantity + " shares of " + symbol);
        }
    }

    public double getBalance() { return balance; }
    
    public void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- 💼 YOUR PORTFOLIO ---");
        System.out.printf("Cash Balance: $%.2f%n", balance);
        
        if (holdings.isEmpty()) {
            System.out.println("You do not own any stocks.");
        } else {
            System.out.println("Stocks Owned:");
            System.out.printf("%-10s %-10s %-15s %-15s%n", "Symbol", "Qty", "Current Price", "Total Value");
            double totalPortfolioValue = balance;
            
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                String symbol = entry.getKey();
                int qty = entry.getValue();
                double currentPrice = market.get(symbol).getPrice();
                double value = currentPrice * qty;
                totalPortfolioValue += value;
                
                System.out.printf("%-10s %-10d $%-14.2f $%-14.2f%n", symbol, qty, currentPrice, value);
            }
            System.out.println("----------------------------------------");
            System.out.printf("Net Worth: $%.2f%n", totalPortfolioValue);
        }
    }
}

// 3. Main Class
public class GradeTracker {
    private static Map<String, Stock> market = new HashMap<>();
    private static Portfolio userPortfolio;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize Market Data
        initializeMarket();
        // Initialize User with $10,000
        userPortfolio = new Portfolio(10000.00);

        System.out.println("Welcome to the Wall Street Java Simulator!");
        
        while (true) {
            printMenu();
            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    displayMarket();
                    break;
                case 2:
                    buyStock();
                    break;
                case 3:
                    sellStock();
                    break;
                case 4:
                    userPortfolio.displayPortfolio(market);
                    break;
                case 5:
                    System.out.println("Exiting market... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
            
            // Randomly update market prices after every action to simulate a live market
            updateMarketPrices(); 
        }
    }

    // --- Helper Methods ---

    private static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.00));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 200.00));
        market.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2800.00));
        market.put("AMZN", new Stock("AMZN", "Amazon.com", 3400.00));
    }

    private static void updateMarketPrices() {
        for (Stock s : market.values()) {
            s.updatePrice();
        }
    }

    private static void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. View Market (Stock Prices)");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    private static void displayMarket() {
        System.out.println("\n--- 📈 MARKET PRICES ---");
        System.out.printf("%-10s %-20s %-10s%n", "Symbol", "Company", "Price");
        for (Stock s : market.values()) {
            System.out.printf("%-10s %-20s $%.2f%n", s.getSymbol(), s.getName(), s.getPrice());
        }
    }

    private static void buyStock() {
        System.out.print("Enter Stock Symbol to Buy (e.g., AAPL): ");
        String symbol = scanner.next().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println("❌ Stock not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = getValidIntInput();
        if (qty <= 0) {
            System.out.println("❌ Quantity must be positive.");
            return;
        }

        userPortfolio.buyStock(market.get(symbol), qty);
    }

    private static void sellStock() {
        System.out.print("Enter Stock Symbol to Sell: ");
        String symbol = scanner.next().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println("❌ Stock not found in market.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = getValidIntInput();
        if (qty <= 0) {
            System.out.println("❌ Quantity must be positive.");
            return;
        }

        userPortfolio.sellStock(market.get(symbol), qty);
    }
    
    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}