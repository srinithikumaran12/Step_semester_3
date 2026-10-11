package oopdesign.class_problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Question 5: Payment Processing for a Shopping System
 *
 * Classes and relationships:
 *   PaymentMethod (interface) <|.. CreditCardPayment, PayPalPayment, BankTransferPayment
 *   Customer 1 ---- 0..* Order
 *   Order 1 ---- 0..* OrderItem ---- 1 Product
 *   Order ..> PaymentMethod   (Order depends only on the interface)
 *
 * Order.pay() depends on the PaymentMethod abstraction, never on a concrete
 * payment class, so a new payment method needs no change to Order. An order
 * is only marked PAID after a successful payment; a failed payment leaves
 * it PENDING.
 */
public class ShoppingPaymentSystem {

    // ---------- Customer and Product ----------

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Product {
        private final String name;
        private final double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        String getName() {
            return name;
        }

        double getPrice() {
            return price;
        }
    }

    static class OrderItem {
        private final Product product;
        private final int quantity;

        OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        double getSubtotal() {
            return product.getPrice() * quantity;
        }
    }

    // ---------- Payment methods ----------

    interface PaymentMethod {
        String getName();

        boolean processPayment(double amount);
    }

    static class CreditCardPayment implements PaymentMethod {
        private final double creditLimit;

        CreditCardPayment(double creditLimit) {
            this.creditLimit = creditLimit;
        }

        @Override
        public String getName() {
            return "Credit Card";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= creditLimit;
        }
    }

    static class PayPalPayment implements PaymentMethod {
        private final double accountBalance;

        PayPalPayment(double accountBalance) {
            this.accountBalance = accountBalance;
        }

        @Override
        public String getName() {
            return "PayPal";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= accountBalance;
        }
    }

    static class BankTransferPayment implements PaymentMethod {
        private final double accountBalance;

        BankTransferPayment(double accountBalance) {
            this.accountBalance = accountBalance;
        }

        @Override
        public String getName() {
            return "Bank Transfer";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= accountBalance;
        }
    }

    // ---------- Order ----------

    enum OrderStatus {
        PENDING("Pending"),
        PAID("Paid");

        private final String label;

        OrderStatus(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }

    static class Order {
        private final Customer customer;
        private final String label;
        private final List<OrderItem> items = new ArrayList<>();
        private OrderStatus status = OrderStatus.PENDING;

        Order(Customer customer, String label) {
            this.customer = customer;
            this.label = label;
            System.out.println("Order created for " + customer.getName() + ".");
        }

        void addItem(Product product, int quantity) {
            if (quantity <= 0) {
                System.out.println("Quantity must be at least 1.");
                return;
            }
            items.add(new OrderItem(product, quantity));
        }

        double getTotal() {
            double total = 0;
            for (OrderItem item : items) {
                total += item.getSubtotal();
            }
            return total;
        }

        OrderStatus getStatus() {
            return status;
        }

        void pay(PaymentMethod method) {
            if (items.isEmpty()) {
                System.out.println("Cannot process payment for an empty order.");
                return;
            }
            if (status == OrderStatus.PAID) {
                System.out.println("Order " + label + " is already paid.");
                return;
            }

            System.out.println("Payment initiated via " + method.getName() + " for Order " + label + ".");

            boolean success = method.processPayment(getTotal()); // polymorphic call
            if (success) {
                status = OrderStatus.PAID;
                System.out.println("Payment for Order " + label + " successful. Order status: "
                        + status.getLabel() + ".");
            } else {
                System.out.println("Payment for Order " + label + " failed. Order status: "
                        + status.getLabel() + ".");
            }
        }
    }

    public static void main(String[] args) {
        Product productA = new Product("Product A", 100.0);
        Product productB = new Product("Product B", 50.0);
        Product productC = new Product("Product C", 500.0);

        // Customer X: 2 x A + 1 x B = 250, pays by credit card (limit 1000) -> success
        Customer customerX = new Customer("Customer X");
        Order orderX = new Order(customerX, "X");
        orderX.addItem(productA, 2);
        orderX.addItem(productB, 1);
        orderX.pay(new CreditCardPayment(1000.0));

        // Customer Y: empty order -> payment refused
        Customer customerY = new Customer("Customer Y");
        Order orderY = new Order(customerY, "Y");
        orderY.pay(new CreditCardPayment(1000.0));

        // Customer Z: 1 x C = 500, PayPal balance only 100 -> payment fails, stays Pending
        Customer customerZ = new Customer("Customer Z");
        Order orderZ = new Order(customerZ, "Z");
        orderZ.addItem(productC, 1);
        orderZ.pay(new PayPalPayment(100.0));
    }
}