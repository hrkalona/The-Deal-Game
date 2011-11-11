import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author hrkalona
 */
public class Banker {
  private ArrayList<Percentage> level_briefcases_percentage;  //The levels of the game, including offer percentages
  private Random generator; //A random number generator
  private String converted_offer; //The offer
  private Briefcase[] briefcases_content; //All the briefcases of the game
  

    public Banker(Briefcase[] briefcases_content) {

        generator = new Random(System.currentTimeMillis());

        level_briefcases_percentage = new ArrayList<Percentage> (9);
        level_briefcases_percentage.add(new Percentage(1, 6, 10));
        level_briefcases_percentage.add(new Percentage(2, 5, 20));
        level_briefcases_percentage.add(new Percentage(3, 4, 30));
        level_briefcases_percentage.add(new Percentage(4, 3, 40));
        level_briefcases_percentage.add(new Percentage(5, 2, 50));
        level_briefcases_percentage.add(new Percentage(6, 1, 60));
        level_briefcases_percentage.add(new Percentage(7, 1, 70));
        level_briefcases_percentage.add(new Percentage(8, 1, 80));
        level_briefcases_percentage.add(new Percentage(9, 1, 90));

        this.briefcases_content = briefcases_content;
                
    }

    /**
     * The method, Offer, creates an offer for the game.
     * @return A string containing the offer
     */
    public String Offer() {
        
        int result = generator.nextInt(100);

        //Exchange or Money
        if(result < 7) {
            converted_offer = "exchange";  
        }
        else {
            moneyConverter(calculateOffer()); 
        }

        if(!level_briefcases_percentage.isEmpty()) {
            level_briefcases_percentage.remove(0);
        }
        
        return converted_offer;

    }

    /*
     * The method, calculateOffer, is the actual algorithm that calculates the Money offer.
     */
    private int calculateOffer() {

        double percent = (level_briefcases_percentage.get(0).getPercentage() + generator.nextInt(15) + generator.nextDouble()) / 100 / level_briefcases_percentage.get(0).getBriefcases();
        int offer = (int)(calculateAverage() * percent);
        
        return offer;

    }

    /*
     * The method, calculateAverage, calculates the average of the remaining money.
     */
    private double calculateAverage() {
      double sum = 0, count = 0;

        for(int i = 0; i < briefcases_content.length; i++) {
            if(!briefcases_content[i].hasOpened()) {
                sum += briefcases_content[i].getValue();
                count++;
            }
        }

        return (sum / count);

    }

     /*
     * The method, moneyConverter, converts the money offer with dots every 3 digits.
     */
    private void moneyConverter(int offer) {

        String temp = "" + offer;
        converted_offer = "";

        int number_of_dots = temp.length() / 3;

        if(temp.length() % 3 == 0) {
            number_of_dots--;
        }

        if(number_of_dots > 0) {
            int i = temp.length() - 1;

            for(int counter = 0; number_of_dots > 0; i--) {
                converted_offer = temp.charAt(i) + converted_offer;
                counter++;
                if(counter == 3) {
                    converted_offer = "." + converted_offer;
                    counter = 0;
                    number_of_dots--;
                }
            }

            while(i >= 0) {
                converted_offer = temp.charAt(i) + converted_offer;
                i--;
            } 

        }
        else {
            converted_offer = temp;
        }

    } 

}
