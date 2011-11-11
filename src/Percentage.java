/**
 *
 * @author hrkalona
 */
public class Percentage extends Level {
  private int percentage; //The percentage of the offer

    public Percentage(int level, int briefcases, int percentage) {

        super(level, briefcases);
        this.percentage = percentage;

    }

    //Accesors, Mutators
    public int getPercentage() {

        return this.percentage;

    }

}
