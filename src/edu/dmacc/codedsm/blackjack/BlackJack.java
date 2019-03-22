
package edu.dmacc.codedsm.blackjack;


import java.util.*;

public class BlackJack {


    public static void main(String[] args) {
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
        dealerHand.add(dealerFirst);
        myHand.add(secondCard);
        dealerHand.add(dealerSecond);

        printDealerFirst(dealerHand);

        int dealerSum = dealerFirst.value + dealerSecond.value;

        System.out.print("Player's cards are: ");
        printCurrentHand(myHand);

        int sum = firstCard.value + secondCard.value;

        boolean isPlaying = true;
        while (isPlaying) {
            System.out.println("\nPress 1 to hit or 2 to stand.");
            String hitOrStand = scanner.next();
            if (hitOrStand.equals("1")) {
                Card hit = dealCard(deck);
                sum = sum + hit.value;
                myHand.add(hit);
                System.out.println();
                System.out.println("Player's cards are: ");
                printCurrentHand(myHand);
                if (sum > 21) {
                    System.out.println();
                    System.out.format("\nPlayer's hand was %d.", sum);
                    System.out.println(" Player busts. Dealer wins!");
                    isPlaying = false;
                }

            } else if (hitOrStand.equals("2")) {
                System.out.println();
                while (dealerSum < 17) {
                    Card dealerHit = dealCard(deck);
                    dealerHand.add(dealerHit);
                    dealerSum = dealerSum + dealerHit.value;
                }
                if (dealerSum == 21) {
                    System.out.println("Dealer's cards are: ");
                    printDealerHand(dealerHand);
                    System.out.format("Dealer's hand was %d", dealerSum);
                    System.out.println("Dealer hit 21! Dealer wins!");
                } else if (dealerSum > 16 && dealerSum > sum && dealerSum < 21) {
                    System.out.print("Dealer's cards are: ");
                    printDealerHand(dealerHand);
                    System.out.format("Dealer's hand was %d", dealerSum);
                    System.out.println();
                    System.out.println();
                    System.out.print("Player's hand is: ");
                    printCurrentHand(myHand);
                    System.out.println();
                    System.out.format("Player's hand was %d. ", sum);
                    System.out.println("Dealer wins!");
                } else if (dealerSum == sum) {
                    System.out.print("Dealer's cards are: ");
                    printDealerHand(dealerHand);
                    System.out.format("Dealer's hand was %d", dealerSum);
                    System.out.print("\nPlayer's cards are: ");
                    printCurrentHand(myHand);
                    System.out.println();
                    System.out.format("Player's hand was %d", sum);
                    System.out.println("\nIt's a tie!");
                } else {
                    System.out.print("Dealer's cards are: ");
                    printDealerHand(dealerHand);
                    System.out.format("Dealer's hand was %d. ", dealerSum);
                    System.out.println();
                    System.out.print("\nPlayer's cards are: ");
                    printCurrentHand(myHand);
                    System.out.format("\nPlayer's hand was %d. ", sum);
                    System.out.println("\nPlayer wins!");
                }
                isPlaying = false;
            } else {
                System.out.println("\nInvalid Input.");
            }
        }
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
        System.out.println();
        Card card = dealerFirst.get(0);
        System.out.println("Dealer's first card is" + " " + card.suit + " - " + card.value);
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
