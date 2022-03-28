import java.util.*;

public class Optimal{
    //private static Scanner ki = new Scanner(System.in);
    //private static Scanner kiT = new Scanner(System.in);
    
    public static ArrayList<Integer> InitializeGame(double[][] policyNoAce, double[][] policyAce) {
        int totalScore = 0;
        int epsilon = 1/4;
        ArrayList<Integer> chart = new ArrayList<Integer>();
        
        //spades (S) hearts (H) clubs (C) diamonds (D)
        ArrayList<String> obj = new ArrayList<String>(Arrays.asList
        ("1S", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
        "1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
        "1C", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
        "1D", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD"));
        ArrayList<String> deckCopy = obj;

        //dealer stands at 17
        ArrayList<String> theHand = new ArrayList<String>();
        ArrayList<String> dealerHand = new ArrayList<String>();

        Boolean playAgain = true;
        while (playAgain == true && deckCopy.size() >= 20){
            totalScore = 0;
            theHand.clear();
            dealerHand.clear();

            //2 cards
            //System.out.println("your hand");
            for (int i = 0; i<=1; i++){
                String draw = obj.get(randomCard(deckCopy.size()));
                deckCopy.remove(draw);
                theHand.add(draw);
                //System.out.println(draw + " ");
            }

            //dealer card
            //System.out.println("dealer hand");
            String dealerDraw = obj.get(randomCard(deckCopy.size()));
            deckCopy.remove(dealerDraw);
            dealerHand.add(dealerDraw);
            //System.out.println(dealerDraw + " ");
            
            //drawing cards
            //drawing until more than 12
            while (calculateTotal(theHand) < 12){
                String draw = obj.get(randomCard(deckCopy.size()));
                deckCopy.remove(draw);
                theHand.add(draw);
            }

            //drawing after 12
            String drawOrStand = "";
            while (drawOrStand.equals("draw") && calculateTotal(theHand) <= 21) {
                double num = Math.random();
                if (calculateAce(theHand) == 1) {
                    if (num <= epsilon){
                        if (policyAce[calculateTotal(theHand)-12][getValue(dealerDraw)-1] > 0) {
                            drawOrStand = "stand";
                        } else {
                            String draw = obj.get(randomCard(deckCopy.size()));
                            deckCopy.remove(draw);
                            theHand.add(draw);
                            drawOrStand = "draw";
                        }
                    } else {
                        if (policyAce[calculateTotal(theHand)-12][getValue(dealerDraw)-1] > 0) {
                            String draw = obj.get(randomCard(deckCopy.size()));
                            deckCopy.remove(draw);
                            theHand.add(draw);
                            drawOrStand = "draw";
                        } else {
                            drawOrStand = "stand";
                        }
                    }
                } else {
                    if (num <= epsilon){
                        if (policyNoAce[calculateTotal(theHand)-12][getValue(dealerDraw)-1] > 0) {
                            drawOrStand = "stand";
                        } else {
                            String draw = obj.get(randomCard(deckCopy.size()));
                            deckCopy.remove(draw);
                            theHand.add(draw);
                            drawOrStand = "draw";
                        }
                    } else {
                        if (policyNoAce[calculateTotal(theHand)-12][getValue(dealerDraw)-1] > 0) {
                            String draw = obj.get(randomCard(deckCopy.size()));
                            deckCopy.remove(draw);
                            theHand.add(draw);
                            drawOrStand = "draw";
                        } else {
                            drawOrStand = "stand";
                        }
                    }
                }
            }

            
            chart.add(calculateTotal(theHand));
            chart.add(getValue(dealerDraw));
            chart.add(calculateAce(theHand));

            //dealer drawing
            while (calculateTotal(dealerHand) < 17) {
                dealerDraw = obj.get(randomCard(deckCopy.size()));
                deckCopy.remove(dealerDraw);
                dealerHand.add(dealerDraw);
            }

            //results
            if (calculateTotal(dealerHand) > 21 && calculateTotal(theHand) > 21) {
                totalScore += 0;
            } else if (calculateTotal(dealerHand) < 21 && calculateTotal(theHand) > 21) {
                totalScore -= 1;
            } else if (calculateTotal(dealerHand) > 21 && calculateTotal(theHand) < 21) {
                totalScore += 1;
            } else if (calculateTotal(dealerHand) == 21 && calculateTotal(theHand) < 21){
                totalScore -= 1;
            } else if (calculateTotal(dealerHand) < 21 && calculateTotal(theHand) == 21) {
                totalScore += 1;
            } else if (calculateTotal(dealerHand) == calculateTotal(theHand)){
                totalScore += 0;
            } else if (calculateTotal(dealerHand) > calculateTotal(theHand)){
                totalScore -= 1;
            } else if (calculateTotal(dealerHand) < calculateTotal(theHand)){
                totalScore += 1;
            }

            chart.add(totalScore);

        }
        return chart;
    }

    public static int calculateTotal(ArrayList<String> theHand) {
		int total = 0;
		boolean aceFlag = false;
		for (int i = 0; i < theHand.size(); i++) {
			int value = getValue(theHand.get(i));
			if (value > 10) {
				value = 10;
			} else if ( value == 1) {
				aceFlag = true;
			}
			total += value;
		}
		if (aceFlag && total + 10 <= 21) {
			total += 10;
		}
		return total;
	}

    //1 = has ace, 0 = has no ace
    public static int calculateAce(ArrayList<String> theHand) {
		int total = 0;
		boolean aceFlag = false;
		for (int i = 0; i < theHand.size(); i++) {
			int value = getValue(theHand.get(i));
			if (value > 10) {
				value = 10;
			} else if ( value == 1) {
				aceFlag = true;
			}
			total += value;
		}
		if (aceFlag && total + 10 <= 21) {
			total += 10;

		}
		if (aceFlag == true) {
            return 1;
        }else {
            return 0;
        }
	}

    public static int getValue(String card){
        card = card.substring(0, card.length() - 1);
        if (card.equals("J") || card.equals("Q") || card.equals("K")){
            return 10;
        } else {
            int i = Integer.parseInt(card);
            return (i);
        }
    }

    public static int randomCard(int deckSize){
        int min = 0;
        int max = deckSize-1;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }

    public static String calculateToMove(ArrayList<String> deck, ArrayList<String> myHand){
        double total = calculateTotal(myHand);
        double valid = 0;

        for (int i = 0; i < deck.size(); i++) {
            if ((total + getValue(deck.get(i))) <= 21) {
                valid += 1;
            }
        }
        if ((valid / deck.size()) >= .5) {
            return "Yes";
        } else {
            return "No";
        }
    }
}
