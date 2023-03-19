package org.example;

import org.example.domain.Trader;
import org.example.domain.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );

        questOne(transactions);
        questTwo(transactions);
        questThree(transactions);
        questFour(transactions);
        questFive(transactions);
        questSix(transactions);
        questSeven(transactions);
        questEight(transactions);

    }
    public static void questOne(List<Transaction> transactions){
        List<Transaction> answerOne = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println("1번");
    }

    public static void questTwo(List<Transaction> transactions){
        List<String> answerTwo = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("2번");
    }

    public static void questThree(List<Transaction> transactions){
        List<String> answerThree = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("3번");
    }

    public static void questFour(List<Transaction> transactions){
        String answerFour = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println("4번");
    }

    public static void questFive(List<Transaction> transactions) {
        boolean answerFive = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("5번");
    }

    public static void questSix(List<Transaction> transactions) {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(transaction -> {
                    System.out.println(transaction.getValue());
                });
        System.out.println("6번");
    }

    public static void questSeven(List<Transaction> transactions) {
        Optional<Integer> answerSeven = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println("7번");
    }

    public static void questEight(List<Transaction> transactions) {
        Optional<Integer> answerEight = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println("8번");
    }
}