import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ===================== Composite Pattern =====================
/**
 * Composite pattern base component for menu hierarchy
 * Allows uniform treatment of individual items and menus
 */
abstract class MenuComponent {
    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    public MenuComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }
    public int getChildCount() {
        throw new UnsupportedOperationException();
    }
    public String getName() {
        throw new UnsupportedOperationException();
    }
    public Double getPrice() {
        throw new UnsupportedOperationException();
    }
    public void printMenu(String indent) {
        throw new UnsupportedOperationException();
    }

    // Default without indent
    public void printMenu() {
        printMenu("");
    }
}

// ===================== Composite: Menu (Composite) =====================
class Menu extends MenuComponent {
    private String name;
    private List<MenuComponent> components = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    @Override
    public void add(MenuComponent component){
        components.add(component);
    }
    @Override
    public void remove(MenuComponent component){
        components.remove(component);
    }
    @Override
    public MenuComponent getChild(int index){
        return components.get(index);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getChildCount() {
        return components.size();
    }
    @Override
    public void printMenu(String indent) {
        System.out.println("\n" + indent + "=== " + name + " ===");
        for (MenuComponent c : components) c.printMenu(indent + "  ");
    }
}

// ===================== Composite: MenuItem (Leaf) =====================
abstract class MenuItem extends MenuComponent {
    protected String name;
    protected double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName(){
        return name;
    }
    @Override
    public Double getPrice(){
        return price;
    }
    @Override
    public void printMenu(String indent) {
        System.out.println(indent + "Item: " + name + " — " + price + " EGP");
    }

    public MenuItem getBaseItem() {
        return this;
    }
    public abstract String describe();
}

// ===================== Food / Drink Base Classes =====================
abstract class Food extends MenuItem {
    public Food(String name, double price){
        super(name, price);
    }
}

abstract class Drink extends MenuItem {
    public Drink(String name, double price) {
        super(name, price);
    }
}

// ===================== Product Type Categories =====================
abstract class Pizza extends Food {
    public Pizza(String name, double price) {
        super(name, price);
    }
}

abstract class Burger extends Food {
    public Burger(String name, double price) {
        super(name, price);
    }
}

abstract class Beverage extends Drink {
    public Beverage(String name, double price) {
        super(name, price);
    }
}

// ===================== Decorator Pattern =====================
/**
 * Decorator pattern for adding toppings/add-ons to food items
 * Allows dynamic extension of functionality without subclassing
 */
abstract class FoodDecorator extends Food {
    protected Food food;

    public FoodDecorator(Food food, String addonName, double addonPrice) {
        super(addonName, 0.0); // price of decorator is the addon price
        this.food = food;
        this.price = addonPrice;
    }

    @Override
    public Double getPrice() {
        return food.getPrice() + price;
    }

    @Override
    public void printMenu(String indent) {
        food.printMenu(indent);
        System.out.println(indent + "   + " + name + " — " + price + " EGP");
    }

    @Override
    public String describe() {
        return food.describe() + " + " + name;
    }

    @Override
    public MenuItem getBaseItem() {
        return food.getBaseItem();
    }
}

abstract class DrinkDecorator extends Drink {
    protected Drink drink;

    public DrinkDecorator(Drink drink, String addonName, double addonPrice) {
        super(addonName, 0.0); // price of decorator is the addon price
        this.drink = drink;
        this.price = addonPrice;
    }

    @Override
    public Double getPrice() {
        return drink.getPrice() + price;
    }

    @Override
    public void printMenu(String indent) {
        drink.printMenu(indent);
        System.out.println(indent + "   + " + name + " — " + price + " EGP");
    }

    @Override
    public String describe() {
        return drink.describe() + " + " + name;
    }

    @Override
    public MenuItem getBaseItem() {
        return drink.getBaseItem();
    }
}

// ===================== Concrete Decorators =====================
class ExtraCheese extends FoodDecorator {
    public ExtraCheese(Food food) {
        super(food, "Extra Cheese", 10.0);
    }
}

class Sauce extends FoodDecorator {
    public Sauce(Food food) {
        super(food, "Sauce", 5.0);
    }
}

class Syrup extends DrinkDecorator {
    public Syrup(Drink drink) {
        super(drink, "Syrup", 7.0);
    }
}

// ===================== Abstract Factory Pattern =====================
/**
 * Abstract Factory pattern for creating families of related products
 * Ensures products are compatible within each menu family
 */
interface MenuFactory {
    Pizza createPizza(String variant);
    Burger createBurger(String variant);
    Drink createDrink(String variant);
}

// ===================== Vegetarian Product Family =====================
class VegetarianItalianPizza extends Pizza {
    public VegetarianItalianPizza(){
        super("Vegetarian Italian Pizza", 170);
    }
    @Override
    public String describe() {
        return "Vegetarian Italian Pizza";
    }
}

class VegetarianEasternPizza extends Pizza {
    public VegetarianEasternPizza() {
        super("Vegetarian Eastern Pizza", 150);
    }
    @Override
    public String describe() {
        return "Vegetarian Eastern Pizza";
    }
}

class VegClassicBurger extends Burger {
    public VegClassicBurger() {
        super("Veg Classic Burger", 80.75);
    }
    @Override
    public String describe() {
        return "Veg Classic Burger (chickpea & mushroom patty)";
    }
}

class VegCheeseBurger extends Burger {
    public VegCheeseBurger() {
        super("Veg Cheese Burger", 90.00);
    }
    @Override
    public String describe() {
        return "Veg Cheese Burger";
    }
}

class VegSoda extends Drink{
    public VegSoda() {
        super("Sparkling Soda", 20.50);
    }
    @Override
    public String describe() {
        return "Sparkling Soda (330ml)";
    }
}

class VegHerbalTea extends Drink {
    public VegHerbalTea() {
        super("Veg Herbal-Tea", 15.50);
    }
    @Override
    public String describe() {
        return "Veg Herbal-Tea (330ml)";
    }
}

// ===================== Non-Vegetarian Product Family =====================
class ChickenClassicPizza extends Pizza {
    public ChickenClassicPizza() {
        super("Chicken Classic Pizza", 190.00);
    }
    @Override
    public String describe() {
        return "Chicken Classic Pizza";
    }
}

class MeatItalianPizza extends Pizza {
    public MeatItalianPizza() {
        super("Meat Italian Pizza", 200.00);
    }
    @Override
    public String describe() {
        return "Meat Italian Pizza";
    }
}

class ClassicBurger extends Burger {
    public ClassicBurger() {
        super("Classic Burger", 90.00);
    }
    @Override
    public String describe() {
        return "Classic Burger (chickpea & mushroom patty)";
    }
}

class ChickenBurger extends Burger {
    public ChickenBurger() {
        super("Chicken Burger", 100.50);
    }
    @Override
    public String describe() {
        return "Chicken Burger";
    }
}

class ColaDrink extends Drink {
    public ColaDrink() {
        super("Cola Drink", 20.50);
    }
    @Override
    public String describe() {
        return "Cola Drink";
    }
}

class OrangeJuice extends Drink {
    public OrangeJuice() {
        super("Orange Juice", 30.00);
    }
    @Override
    public String describe() {
        return "Orange Juice";
    }
}

// ===================== Kids Menu Product Family =====================
class KidsMiniPizza extends Pizza {
    public KidsMiniPizza() {
        super("Kids Mini Pizza", 65.50);
    }
    @Override
    public String describe() {
        return "Kids Mini Pizza";
    }
}

class KidsMiniBurger extends Burger {
    public KidsMiniBurger() {
        super("Kids Mini Burger", 50.00);
    }
    @Override
    public String describe() {
        return "Kids Mini Burger";
    }
}

class KidsMilkshake extends Drink {
    public KidsMilkshake() {
        super("Kids Milkshake", 60.00);
    }
    @Override
    public String describe() {
        return "Kids Milkshake";
    }
}

// ===================== Concrete Factories =====================
class VegetarianMenuFactory implements MenuFactory {
    public Pizza createPizza(String variant) {
        if (variant == null) variant = "italian";
        switch (variant.toLowerCase()) {
            case "italian": return new VegetarianItalianPizza();
            case "eastern": return new VegetarianEasternPizza();
            default: return new VegetarianItalianPizza(); // default variant
        }
    }
    public Burger createBurger(String variant) {
        if (variant == null) variant = "classic";
        switch (variant.toLowerCase()) {
            case "classic": return new VegClassicBurger();
            case "cheese": return new VegCheeseBurger();
            default: return new VegClassicBurger();
        }
    }
    public Drink createDrink(String variant) {
        if (variant == null) variant = "soda";
        switch (variant.toLowerCase()) {
            case "soda": return new VegSoda();
            case "tea": return new VegHerbalTea();
            default: return new VegSoda();
        }
    }
}

class NonVegMenuFactory implements MenuFactory {
    public Pizza createPizza(String variant) {
        if (variant == null) variant = "chicken";
        switch (variant.toLowerCase()) {
            case "chicken": return new ChickenClassicPizza();
            case "meat": return new MeatItalianPizza();
            default: return new ChickenClassicPizza();
        }
    }
    public Burger createBurger(String variant) {
        if (variant == null) variant = "classic";
        switch (variant.toLowerCase()) {
            case "classic": return new ClassicBurger();
            case "chicken": return new ChickenBurger();
            default: return new ClassicBurger();
        }
    }
    public Drink createDrink(String variant) {
        if (variant == null) variant = "cola";
        switch (variant.toLowerCase()) {
            case "cola": return new ColaDrink();
            case "orange": return new OrangeJuice();
            default: return new ColaDrink();
        }
    }
}

class KidsMenuFactory implements MenuFactory {
    public Pizza createPizza(String variant) {
        return new KidsMiniPizza();
    }
    public Burger createBurger(String variant) {
        return new KidsMiniBurger();
    }
    public Drink createDrink(String variant) {
        return new KidsMilkshake();
    }
}

// ===================== Observer Pattern =====================
/**
 * Observer pattern for order notifications
 * Allows multiple components to react to order events
 */
interface OrderObserver{
    void update(Order order);
}

// ===================== Concrete Observers =====================
class Kitchen implements OrderObserver{
    @Override
    public void update(Order order){
        System.out.println("[Kitchen] New order received: " + order.getDescription());
    }
}

class Waiter implements OrderObserver{
    @Override
    public void update(Order order){
        System.out.println("[Waiter] New order placed: " + order.getDescription());
    }
}

// ===================== Subject Interface =====================
interface OrderSubject{
    void subscribe(OrderObserver observer);
    void unsubscribe(OrderObserver observer);
    void notifyObservers();
}

// ===================== Order Type Enum =====================
enum OrderType {
    DINE_IN, DELIVERY, TAKEAWAY
}

// ===================== Order Class (Subject) =====================
class Order implements OrderSubject {
    private List<OrderObserver> observers = new ArrayList<>();
    private List<MenuItem> items = new ArrayList<>();
    private OrderType orderType = OrderType.DINE_IN;
    private double deliveryFee = 0.0;
    private DiscountStrategy discountStrategy = new NoDiscount();

    public Order() {}
    public Order(OrderType type) { this.orderType = type; }

    // Strategy pattern for discounts
    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }
    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    // Delivery fee management
    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double fee) { this.deliveryFee = fee; }

    // Observer pattern implementation
    @Override
    public void subscribe(OrderObserver observer) { observers.add(observer); }
    @Override
    public void unsubscribe(OrderObserver observer) { observers.remove(observer); }
    @Override
    public void notifyObservers() {
        for (OrderObserver obs : observers) {
            obs.update(this);
        }
    }

    // Order item management
    public void addItem(MenuItem item) { items.add(item); }
    public List<MenuItem> getItems() { return items; }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.describe()).append(", ");
        }
        if (sb.length() > 2) sb.setLength(sb.length() - 2); // remove trailing comma
        return sb.toString();
    }

    public double calculateRawSubtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // Order type management
    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    // Main order placement method
    public boolean placeOrder(PaymentStrategy paymentStrategy) {
        // Notify observers (kitchen/waiter)
        notifyObservers();

        // Billing handles everything else (discounts, tax, delivery fee, payment)
        double total = Billing.showBill(this);
        return Billing.processPayment(total, paymentStrategy);
    }
}

// ===================== Strategy Pattern: Payment =====================
/**
 * Strategy pattern for payment methods
 * Encapsulates different payment algorithms
 */
interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentDetails();
}

class CashPayment implements PaymentStrategy {
    private double cashGiven;
    public CashPayment(double cashGiven) { this.cashGiven = cashGiven; }
    @Override public boolean pay(double amount) {
        if (cashGiven >= amount) {
            System.out.println("[Payment] Cash accepted. Change: " + (cashGiven - amount) + " EGP");
            return true;
        } else {
            System.out.println("[Payment] Not enough cash provided.");
            return false;
        }
    }
    @Override public String getPaymentDetails() { return "Cash (given: " + cashGiven + " EGP)"; }
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    @Override public boolean pay(double amount) {
        // simulate card processing
        System.out.println("[Payment] Processing credit card ending " + last4(cardNumber) + " for " + amount + " EGP");
        // assume success
        System.out.println("[Payment] Card authorised.");
        return true;
    }
    private String last4(String s) {
        if (s == null || s.length() < 4) return s;
        return s.substring(s.length() - 4);
    }
    @Override public String getPaymentDetails() { return "Credit Card (ending " + last4(cardNumber) + ")"; }
}

class MobileWalletPayment implements PaymentStrategy {
    private String walletId;
    public MobileWalletPayment(String walletId) { this.walletId = walletId; }
    @Override public boolean pay(double amount) {
        System.out.println("[Payment] Charging mobile wallet " + walletId + " for " + amount + " EGP");
        // assume success
        System.out.println("[Payment] Mobile wallet payment successful.");
        return true;
    }
    @Override public String getPaymentDetails() { return "Mobile Wallet (" + walletId + ")"; }
}

// ===================== Strategy Pattern: Discount =====================
interface DiscountStrategy {
    double applyDiscount(Order order);
    String getDescription();
}

// No discount strategy
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(Order order) {
        return 0.0;
    }
    @Override
    public String getDescription() {
        return "No Discount";
    }
}

// Pizza discount strategy
class PizzaDiscount implements DiscountStrategy {
    private double discountPercentage;
    public PizzaDiscount(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    @Override
    public double applyDiscount(Order order) {
        double discount = 0.0;
        for(MenuItem item : order.getItems()) {
            MenuItem base = item.getBaseItem(); // <-- unwrap decorators
            if(base instanceof Pizza) {
                // use base.getPrice() to compute discount on the pizza's base price
                discount += base.getPrice() * (discountPercentage / 100.0);
            }
        }
        return discount;
    }
    @Override
    public String getDescription() {
        return discountPercentage + "% Pizza Discount";
    }
}

// Burger discount strategy
class BurgerDiscount implements DiscountStrategy {
    private double discountPercentage;
    public BurgerDiscount(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    @Override
    public double applyDiscount(Order order) {
        double discount = 0.0;
        for(MenuItem item : order.getItems()) {
            MenuItem base = item.getBaseItem();
            if(base instanceof Burger) {
                discount += base.getPrice() * (discountPercentage / 100.0);
            }
        }
        return discount;
    }
    @Override
    public String getDescription() {
        return discountPercentage + "% Burger Discount";
    }
}

// ===================== Billing System =====================
class Billing {
    private static final double TAX_RATE = 0.14;

    /**
     * Calculates and displays the complete bill
     * @return total amount for payment processing
     */
    public static double showBill(Order order) {
        DiscountStrategy discountStrategy = order.getDiscountStrategy();
        double subTotal = order.calculateRawSubtotal();
        double discountAmount = discountStrategy.applyDiscount(order);
        double deliveryFee = order.getDeliveryFee();
        double totalBeforeTax = subTotal - discountAmount + deliveryFee;
        double tax = totalBeforeTax * TAX_RATE;
        double total = totalBeforeTax + tax;

        System.out.println("\n=== BILLING ===");
        System.out.println("Order Items: " + order.getDescription());
        System.out.println("Subtotal: " + subTotal + " EGP");
        System.out.println("Discount (" + discountStrategy.getDescription() + "): -" + discountAmount + " EGP");
        if (deliveryFee > 0)
            System.out.println("Delivery Fee: " + deliveryFee + " EGP");
        System.out.println("Total before tax: " + totalBeforeTax + " EGP");
        System.out.println("Tax (14%): +" + tax + " EGP");
        System.out.println("Total: " + total + " EGP");

        return total; // return total so payment can be done later
    }

    /**
     * Processes payment using the selected strategy
     */
    public static boolean processPayment(double total, PaymentStrategy paymentStrategy) {
        boolean paid = paymentStrategy.pay(total);
        if (paid) {
            System.out.println("[Payment] Payment completed successfully via " + paymentStrategy.getPaymentDetails());
        } else {
            System.out.println("[Payment] Payment failed!");
        }
        return paid;
    }
}

// ===================== Builder Pattern =====================
/**
 * Builder pattern for constructing complex menu structures
 * Separates construction from representation
 */
interface MenuBuilder {
    Menu buildMenu();
}

class VegetarianMenuBuilder implements MenuBuilder {
    private MenuFactory factory;

    public VegetarianMenuBuilder(MenuFactory factory) {
        this.factory = factory;
    }

    @Override
    public Menu buildMenu() {
        Menu menu = new Menu("Vegetarian Menu");

        Menu pizzaMenu = new Menu("Pizza");
        pizzaMenu.add(factory.createPizza("italian"));
        pizzaMenu.add(factory.createPizza("eastern"));
        menu.add(pizzaMenu);

        Menu burgerMenu = new Menu("Burger");
        burgerMenu.add(factory.createBurger("classic"));
        burgerMenu.add(factory.createBurger("cheese"));
        menu.add(burgerMenu);

        Menu drinkMenu = new Menu("Drink");
        drinkMenu.add(factory.createDrink("soda"));
        drinkMenu.add(factory.createDrink("tea"));
        menu.add(drinkMenu);

        return menu;
    }
}

class NonVegMenuBuilder implements MenuBuilder {
    private MenuFactory factory;

    public NonVegMenuBuilder(MenuFactory factory) {
        this.factory = factory;
    }

    @Override
    public Menu buildMenu() {
        Menu menu = new Menu("Non-Veg Menu");

        Menu pizzaMenu = new Menu("Pizza");
        pizzaMenu.add(factory.createPizza("chicken"));
        pizzaMenu.add(factory.createPizza("meat"));
        menu.add(pizzaMenu);

        Menu burgerMenu = new Menu("Burger");
        burgerMenu.add(factory.createBurger("classic"));
        burgerMenu.add(factory.createBurger("chicken"));
        menu.add(burgerMenu);

        Menu drinkMenu = new Menu("Drink");
        drinkMenu.add(factory.createDrink("cola"));
        drinkMenu.add(factory.createDrink("orange"));
        menu.add(drinkMenu);

        return menu;
    }
}

class KidsMenuBuilder implements MenuBuilder {
    private MenuFactory factory;

    public KidsMenuBuilder(MenuFactory factory) {
        this.factory = factory;
    }

    @Override
    public Menu buildMenu() {
        Menu menu = new Menu("Kids Menu");

        Menu pizzaMenu = new Menu("Pizza");
        pizzaMenu.add(factory.createPizza(null));
        menu.add(pizzaMenu);

        Menu burgerMenu = new Menu("Burger");
        burgerMenu.add(factory.createBurger(null));
        menu.add(burgerMenu);

        Menu drinkMenu = new Menu("Drink");
        drinkMenu.add(factory.createDrink(null));
        menu.add(drinkMenu);

        return menu;
    }
}

// ===================== Facade Pattern =====================
/**
 * Facade pattern for simplifying menu creation
 * Provides a unified interface to complex subsystem
 */
class MenuFacade {
    public static Menu createFamilyMenu(String family) {
        MenuFactory factory;
        MenuBuilder builder;

        switch(family.toLowerCase()) {
            case "vegetarian":
                factory = new VegetarianMenuFactory();
                builder = new VegetarianMenuBuilder(factory);
                break;
            case "nonveg":
                factory = new NonVegMenuFactory();
                builder = new NonVegMenuBuilder(factory);
                break;
            case "kids":
                factory = new KidsMenuFactory();
                builder = new KidsMenuBuilder(factory);
                break;
            default:
                throw new IllegalArgumentException("Unknown menu family: " + family);
        }

        return builder.buildMenu();
    }
}

// ===================== Template Method Pattern =====================
/**
 * Template Method pattern for order processing workflow
 * Defines algorithm skeleton with customizable steps
 */
abstract class OrderWorkflowTemplate {
    protected Order order;
    protected PaymentStrategy paymentStrategy;

    public OrderWorkflowTemplate(Order order, PaymentStrategy payment) {
        this.order = order;
        this.paymentStrategy = payment;
    }

    // Template method - defines the algorithm skeleton
    public final void processOrder() {
        displayMenu();
        selectItems();
        beforePayment();
        processPayment();
        notifyObservers();
    }

    protected abstract void displayMenu();
    protected abstract void selectItems();
    protected void beforePayment() {
        // Hook (optional)
    }
    protected void processPayment() {
        double total = Billing.showBill(order);
        Billing.processPayment(total, paymentStrategy);
    }
    protected void notifyObservers() {
        order.notifyObservers();
    }
}

class DineInOrderWorkflow extends OrderWorkflowTemplate {
    private int tableNumber;
    public DineInOrderWorkflow(Order order, PaymentStrategy payment) {
        super(order, payment);
        order.setOrderType(OrderType.DINE_IN);
        this.tableNumber = (int)(Math.random() * 20 + 1); // generate once
    }

    @Override
    protected void displayMenu() {
        System.out.println("[Dine-In] Showing dine-in menu...");
    }

    @Override
    protected void selectItems() {
        System.out.println("[Dine-In] Selecting items for dine-in...");
        // you insert your selection logic here
    }

    @Override
    protected void beforePayment() {
        System.out.println("[Dine-In] Assigning table...");
        System.out.println("Table Number: " + tableNumber); // same number each time
    }
}

class TakeawayOrderWorkflow extends OrderWorkflowTemplate {
    public TakeawayOrderWorkflow(Order order, PaymentStrategy payment) {
        super(order, payment);
        order.setOrderType(OrderType.TAKEAWAY);
    }

    @Override
    protected void displayMenu() {
        System.out.println("[Takeaway] Showing takeaway menu...");
    }

    @Override
    protected void selectItems() {
        System.out.println("[Takeaway] Selecting items for takeaway...");
        // your logic here
    }

    @Override
    protected void beforePayment() {
        System.out.println("[Takeaway] Packing items…");
    }
}

class DeliveryOrderWorkflow extends OrderWorkflowTemplate {
    private String address;
    private String phone;

    public DeliveryOrderWorkflow(Order order, PaymentStrategy payment, String address, String phone) {
        super(order, payment);
        this.address = address;
        this.phone = phone;

        order.setOrderType(OrderType.DELIVERY);
    }

    @Override
    protected void displayMenu() {
        System.out.println("[Delivery] Showing delivery menu...");
    }

    @Override
    protected void selectItems() {
        System.out.println("[Delivery] Selecting items for delivery...");
    }

    @Override
    protected void beforePayment() {
        System.out.println("[Delivery] Address: " + address);
        System.out.println("[Delivery] Phone: " + phone);
        System.out.println("[Delivery] Estimated Time: 30–45 minutes.");
        order.setDeliveryFee(20.0);
    }
}

// ===================== Discount Selection Logic =====================
class DiscountSelector {
    /**
     * Automatically selects the best discount strategy for an order
     * Implements policy: choose strategy with highest absolute discount
     */
    public static DiscountStrategy selectBestDiscount(Order order) {
        // Candidate policies (add or change as business rules evolve)
        List<DiscountStrategy> candidates = new ArrayList<>();
        candidates.add(new NoDiscount());
        candidates.add(new PizzaDiscount(10));   // 10% off pizzas
        candidates.add(new BurgerDiscount(15));  // 15% off burgers

        // Evaluate each candidate and choose the one with the maximum discount value
        double bestAmount = -1.0;
        DiscountStrategy best = new NoDiscount();
        for (DiscountStrategy s : candidates) {
            double amount = 0.0;
            try {
                amount = s.applyDiscount(order);
            } catch (Exception ex) {
                // safety: make sure a bad strategy doesn't break selection
                amount = 0.0;
            }
            if (amount > bestAmount) {
                bestAmount = amount;
                best = s;
            }
        }

        // If bestAmount is zero, returns NoDiscount (or the first zero one) — that's fine.
        return best;
    }
}

// ===================== Client Code =====================
public class RestaurantSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant Ordering System!");

        // 1. Select Menu Family
        System.out.println("Please choose a menu family:");
        System.out.println("1. Vegetarian");
        System.out.println("2. Non-Veg");
        System.out.println("3. Kids");
        System.out.print("Enter choice (1-3): ");
        int familyChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String family = switch (familyChoice) {
            case 1 -> "vegetarian";
            case 2 -> "nonveg";
            case 3 -> "kids";
            default -> {
                System.out.println("Invalid choice. Defaulting to Vegetarian.");
                yield "vegetarian";
            }
        };

        // 2. Get the menu using Facade
        Menu menu = MenuFacade.createFamilyMenu(family);

        System.out.println("\n=== " + family.toUpperCase() + " MENU ===");
        menu.printMenu();

        // 3. Create order
        Order order = new Order();

        // Subscribe observers
        order.subscribe(new Kitchen());
        order.subscribe(new Waiter());

        boolean addingItems = true;
        while (addingItems) {
            System.out.println("\nChoose item type to add:");
            System.out.println("1. Pizza");
            System.out.println("2. Burger");
            System.out.println("3. Drink");
            System.out.println("0. Finish order");
            System.out.print("Enter choice: ");
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (typeChoice == 0) {
                addingItems = false;
                break;
            }

            MenuComponent typeMenu = switch (typeChoice) {
                case 1 -> menu.getChild(0); // Pizza
                case 2 -> menu.getChild(1); // Burger
                case 3 -> menu.getChild(2); // Drink
                default -> null;
            };

            if (typeMenu != null) {
                System.out.println("\nAvailable items:");
                for (int i = 0; i < typeMenu.getChildCount(); i++) {
                    MenuComponent item = typeMenu.getChild(i);
                    System.out.println((i + 1) + ". " + item.getName() + " — " + item.getPrice() + " EGP");
                }
                System.out.print("Select item number: ");
                int itemChoice = scanner.nextInt() - 1;
                scanner.nextLine();

                if (itemChoice >= 0 && itemChoice < typeMenu.getChildCount()) {
                    MenuItem selectedItem = (MenuItem) typeMenu.getChild(itemChoice);

                    // Ask for add-ons if applicable (Decorator pattern)
                    if (selectedItem instanceof Food) {
                        System.out.print("Add Extra Cheese? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y"))
                            selectedItem = new ExtraCheese((Food) selectedItem);

                        System.out.print("Add Sauce? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y"))
                            selectedItem = new Sauce((Food) selectedItem);

                    } else if (selectedItem instanceof Drink) {
                        System.out.print("Add Syrup? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y"))
                            selectedItem = new Syrup((Drink) selectedItem);
                    }

                    order.addItem(selectedItem);
                    System.out.println(selectedItem.getName() + " added to your order.");
                } else {
                    System.out.println("Invalid item choice.");
                }
            }
        }

        // 4. Choose order type
        System.out.println("\nSelect order type:");
        System.out.println("1. Dine-in");
        System.out.println("2. Delivery");
        System.out.println("3. Takeaway");
        System.out.print("Enter choice: ");
        int orderTypeChoice = scanner.nextInt();
        scanner.nextLine();

        switch (orderTypeChoice) {
            case 2 -> order.setOrderType(OrderType.DELIVERY);
            case 3 -> order.setOrderType(OrderType.TAKEAWAY);
            default -> order.setOrderType(OrderType.DINE_IN);
        }

        // 5. Ask for payment method
        System.out.println("\nSelect payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.println("3. Mobile Wallet");
        System.out.print("Enter choice: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        PaymentStrategy payment = null;
        switch (paymentChoice) {
            case 2 -> {
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                payment = new CreditCardPayment(cardNumber);
            }
            case 3 -> {
                System.out.print("Enter wallet ID: ");
                String walletId = scanner.nextLine();
                payment = new MobileWalletPayment(walletId);
            }
            case 1 -> {
                // For cash, we will ask the user after showing the total
                payment = null; // placeholder, will set later
            }
            default -> {
                System.out.println("Invalid choice. Defaulting to Cash.");
                payment = null;
            }
        }

        // 6. Choose correct workflow based on order type (Template Method)
        OrderWorkflowTemplate workflow;
        switch (order.getOrderType()) {
            case DINE_IN -> workflow = new DineInOrderWorkflow(order, payment);
            case DELIVERY -> {
                System.out.print("Enter delivery address: ");
                String address = scanner.nextLine();
                System.out.print("Enter phone number: ");
                String phone = scanner.nextLine();
                workflow = new DeliveryOrderWorkflow(order, payment, address, phone);
            }
            case TAKEAWAY -> workflow = new TakeawayOrderWorkflow(order, payment);
            default -> workflow = new DineInOrderWorkflow(order, payment);
        }

        // 7. Apply delivery fees / hooks
        workflow.beforePayment();

        // 8. Auto-select discount and apply to order (Strategy pattern)
        DiscountStrategy bestDiscount = DiscountSelector.selectBestDiscount(order);
        order.setDiscountStrategy(bestDiscount);
        System.out.println("\n[Auto Discount] Selected: " + bestDiscount.getDescription());
        System.out.println("[Auto Discount] Amount: " + bestDiscount.applyDiscount(order) + " EGP");

        // 9. SHOW BILL BEFORE PAYMENT
        double total = Billing.showBill(order);

        // 10. Handle cash input if cash payment
        if (paymentChoice == 1) {
            double cash;
            while (true) {
                System.out.print("Enter cash amount: ");
                cash = scanner.nextDouble();
                scanner.nextLine();
                if (cash >= total) break;
                System.out.println("Insufficient cash! Total is " + total + " EGP. Please enter enough.");
            }
            payment = new CashPayment(cash);
            // Update workflow's payment strategy
            workflow.paymentStrategy = payment;
        }

        // 11. Process the workflow (Template Method)
        workflow.processOrder();

        System.out.println("\nThank you for your order!");
        scanner.close();
    }
}