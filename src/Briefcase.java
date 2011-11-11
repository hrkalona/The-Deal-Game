/**
 *
 * @author hrkalona
 */
public class Briefcase {
  private double value; //The money contained
  private int number; //The number of the briefcase
  private boolean opened; //Has opened?


    public Briefcase(double value, int number) {

        this.value = value;
        this.number = number;
        this.opened = false;

    }

    //Accesors, Mutators
    public double getValue() {

        return this.value;

    }

    public boolean hasOpened() {

        return this.opened;
        
    }

    public void setOpen() {

        this.opened = true;

    }

    public int getNumber() {

        return this.number;
        
    }

}
