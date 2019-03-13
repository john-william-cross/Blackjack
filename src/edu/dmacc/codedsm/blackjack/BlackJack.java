package edu.dmacc.codedsm.blackjack;

import java.util.*;

public class BlackJack {

    public static void main(String[] args) {
        Map<String, List<Integer>> deck = new HashMap<>(); //creates a map called deck; the key will be a string(we will key by suit)
        deck.put("Clubs", createCards());
        deck.put("Diamonds", createCards());
        deck.put("Spades", createCards());                 //creates the keys (using the four suits); creates the values by inserting the createCards lists
        deck.put("Hearts", createCards());


        Scanner scanner = new Scanner(System.in);          //creates a scanner to take input
        ArrayList<Card> myHand = new ArrayList<Card>();    //creates a new ArrayList called myHand that holds cards drawn

        Card firstCard = dealCard(deck);                   //creates the firstCard by calling the dealCard method and passing in the deck
        Card secondCard = dealCard(deck);                  //creates the second card

        myHand.add(firstCard);                             //adds the first card to the myHand ArrayList
        myHand.add(secondCard);                            //adds the second card to the myHand ArrayList
        printCurrentHand(myHand);                          //calls the printCurrentHand method, which prints the first two cards (loops through all the cards in the hand)
        int sum = firstCard.value + secondCard.value;      //creates a variable to store the sum of cards (sum will always include sum of first two cards dealt)


        boolean isPlaying = true;                          //create boolean isPlaying; sets to true
        while(isPlaying) {                                 //while loop that executes while isPlaying = true

            System.out.println("\nPress 1 to hit or 2 to stand.");   //prompts player to hit or stand
            String hitOrStand = scanner.next();                      //stores the players input
            if(hitOrStand.equals("1")) {                             //creates if statement to execute if the player chooses to hit
                Card hit = dealCard(deck);                           //deals a card (I call that card "hit" because it is the card dealt when you hit)
                sum = sum + hit.value;                               //updates the sum by adding the value of the hit card whenever player hits
                myHand.add(hit);                                     //adds the card called "hit" to the "myHand" array list
                printCurrentHand(myHand);                            //calls the printCurrentHand method, passes in myHand; this prints all the cards in the hand so far including all cards dealt after a hit

            } else if(hitOrStand.equals("2")) {                      //creates else if to execute if player enters 2 which says "stand"
                System.out.format("\nPlayer's hand was %d", sum);    //prints out the sum of the player's hand
                isPlaying = false;                                   //sets isPlaying to false; ends the program
            } else {                                                 //executes code below whenever a player inputs something other than "1" or "2"
                System.out.println("\nInvalid Input.");              //which in turn tells user their input is invalid. If this line is reached, the while loop begins again
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
        Card nextCard = DeckRandomizer.chooseRandomCards(deck, 1).get(0); // randomly selects a card from the deck and returns a List of Cards so we need to do .get(0) to get the Card object itself stored in the nextCard variable
        deck.get(nextCard.suit).remove(nextCard.value); // this removes a card form the deck by first getting the key using the suit of the card, then removes based on value of card since the card value == index in the List of Integers
        return nextCard;
    }

    public static void printCurrentHand(List<Card> myHand) {
        System.out.println();

        for(int i = 0; i < myHand.size(); i++) { // for each card in myHand, get the card at that index (i) and print the card value
            Card card = myHand.get(i);
            System.out.print(card.suit + " - " + card.value);

            if (i + 1 < myHand.size()) { // add a comma if it's not that last card in myHand
                System.out.print(", ");
            }
        }
    }
}

