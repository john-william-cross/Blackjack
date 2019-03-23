package edu.dmacc.codedsm.blackjack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BlackJack {

    public static void main(String[] args) {
        try {
            File file = new File("blackjack_log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter pw = new PrintWriter(file);
            Map<String, List<Integer>> deck = new HashMap<>();
            deck.put("Clubs", createCards());
            deck.put("Diamonds", createCards());
            deck.put("Spades", createCards());
            deck.put("Hearts", createCards());

            Scanner scanner = new Scanner(System.in);
            ArrayList<Card> myHand = new ArrayList<Card>();
            ArrayList<Card> dealerHand = new ArrayList<Card>();

            Card dealerFirst = dealCard(deck);
            Card dealerSecond = dealCard(deck);
            Card firstCard = dealCard(deck);
            Card secondCard = dealCard(deck);

            myHand.add(firstCard);
            myHand.add(secondCard);
            dealerHand.add(dealerSecond);
            dealerHand.add(dealerFirst);

            printDealerFirst(dealerHand);
            int dealerSum = dealerFirst.value + dealerSecond.value;
            if (dealerFirst.value > 10) {
                int newDealerFirst = 10;
                dealerSum = newDealerFirst + dealerSecond.value;
            }
            if (dealerSecond.value > 10) {
                int newDealerSecond = 10;
                dealerSum = dealerFirst.value + newDealerSecond;
            }
            if (dealerFirst.value > 10 && dealerSecond.value > 10) {
                dealerSum = 20;
            }

            System.out.println("Player's cards are: ");
            printCurrentHand(myHand);
            int sum = firstCard.value + secondCard.value;
            if (firstCard.value > 10) {
                int newFirstCard = 10;
                sum = newFirstCard + secondCard.value;
            }
            if (secondCard.value > 10) {
                int newSecondCard = 10;
                sum = firstCard.value + newSecondCard;
            }
            if (firstCard.value > 10 && secondCard.value > 10) {
                sum = 20;
            }

            boolean isPlaying = true;
            while (isPlaying) {
                System.out.println("\nPress 1 to hit or 2 to stand.");
                String hitOrStand = scanner.next();
                if (hitOrStand.equals("1")) {
                    sum = getSum(deck, myHand, sum);
                    System.out.println("\nPlayer hand is: ");
                    printCurrentHand(myHand);
                    if (sum > 21) {
                        System.out.format("\nPlayer's hand was %d.", sum);
                        pw.format("\nPlayer's hand was %d", sum);
                        System.out.println();
                        System.out.format("\nPlayer busts. ");
                        pw.format("\nPlayer busts. ");
                        System.out.println("\nDealer's hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("%s wins!", "Dealer");
                        pw.format("%s wins!", "Dealer");
                        isPlaying = false;
                    }
                } else if (hitOrStand.equals("2")) {
                    System.out.println();
                    while (dealerSum < 17) {
                        dealerSum = getSum(deck, dealerHand, dealerSum);
                    }
                    if (dealerSum == 21) {
                        System.out.println("Dealer hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("Dealer's hand was %d", dealerSum);
                        System.out.println(" ");
                        System.out.format("%s wins!", "Dealer");
                    } else if (dealerSum == sum) {
                        System.out.println("Dealer hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("Dealer's hand was %d", dealerSum);
                        System.out.println();
                        System.out.println("\nPlayer hand is: ");
                        printCurrentHand(myHand);
                        System.out.format("\nPlayer's hand was %d", sum);
                        System.out.println();
                        System.out.println("\nIt's a tie!");
                    } else if (dealerSum > 21) {
                        System.out.println("\nPlayer hand is: ");
                        printCurrentHand(myHand);
                        System.out.format("\nPlayer's hand was %d", sum);
                        System.out.println();
                        System.out.println("\nDealer hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("Dealer's hand was %d", dealerSum);
                        System.out.println();
                        System.out.format("\nDealer busts! %s wins!", "Player");
                    } else if (dealerSum > sum) {
                        System.out.println("Dealer hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("Dealer's hand was %d", dealerSum);
                        System.out.println();
                        System.out.println("\nPlayer hand is: ");
                        printCurrentHand(myHand);
                        System.out.format("\nPlayer's hand was %d", sum);
                        System.out.println();
                        System.out.format("\n%s wins!", "Dealer");
                    } else if (sum > 21) {
                        System.out.println("Player's hand is: ");
                        printCurrentHand(myHand);
                        System.out.format("\nPlayer's hand was %d", sum);
                        System.out.format("\nPlayer busts. ");
                        System.out.println(" ");
                        System.out.format("%s wins!", "Dealer");
                    } else if (dealerSum < sum) {
                        System.out.println("Dealer hand is: ");
                        printDealerHand(dealerHand);
                        System.out.format("Dealer's hand was %d", dealerSum);
                        System.out.println();
                        System.out.println("\nPlayer hand is: ");
                        printCurrentHand(myHand);
                        System.out.format("\nPlayer's hand was %d", sum);
                        System.out.println();
                        System.out.format("\n%s wins!", "Player");
                    }
                    isPlaying = false;
                } else {
                    System.out.println("Invalid Input.");
                }
            }

            pw.close();
            System.out.println("\nDone");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getSum(Map<String, List<Integer>> deck, ArrayList<Card> myHand, int sum) {
        Card hit = dealCard(deck);
        myHand.add(hit);
        if (hit.value > 10) {
            int newHitValue = 10;
            sum = sum + newHitValue;
        } else {
            sum = sum + hit.value;
        }
        return sum;
    }

    public static List<Integer> createCards() {
        List<Integer> cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            cards.add(i);
        }
        return cards;
    }

    public static Card dealCard(Map<String, List<Integer>> deck) {
        Card nextCard = DeckRandomizer.chooseRandomCards(deck, 1).get(0);
        deck.get(nextCard.suit).remove(nextCard.value);
        return nextCard;
    }

    public static void printCurrentHand(List<Card> myHand) {
        for (int i = 0; i < myHand.size(); i++) {
            Card card = myHand.get(i);
            System.out.print(card.suit + " - " + card.value);
            if (i + 1 < myHand.size()) {
                System.out.print(", ");
            }
        }
    }

    public static void printDealerFirst(List<Card> dealerFirst) {
        Card card = dealerFirst.get(0);
        System.out.println("Dealer's first card is: ");
        System.out.println(card.suit + " - " + card.value);
        System.out.println();
    }

    public static void printDealerHand(List<Card> dealerHand) {
        for (int i = 0; i < dealerHand.size(); i++) {
            Card card = dealerHand.get(i);
            System.out.print(card.suit + " - " + card.value);
            if (i + 1 < dealerHand.size()) {
                System.out.print(", ");
            } else {
                System.out.println(" ");
            }
        }
    }

}

