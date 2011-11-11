/**
 *
 * @author hrkalona
 */
public class Level {
  protected int level; //The level
  protected int briefcases; //Briefcases to be removed on this level

  public Level(int level, int briefcases) {

      this.level = level;
      this.briefcases = briefcases;

  }

  //Accesors, Mutators
  public int getLevel() {

      return this.level;

  }

  public int getBriefcases() {

      return this.briefcases;

  }
}
