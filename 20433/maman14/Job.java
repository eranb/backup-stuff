import java.util.Random;

/**
 * The "Job" represent system job with name, priority and time attributes
 * @version 1
 */

public class Job {
  // initializing static variables
  public static int randomJobsCounter = 0;
  public static final Random random = new Random();
  
  // declaring instance variables
  String name;
  int priority, time, timeOffset;
  
  /**
  * Create new Job.
  * @param name job name.
  * @param priority job priority.
  * @param time job execution time.
  */
  public Job(String name, int priority, int time) {
    this.name = name;
    this.priority = priority;
    this.time = time;
    this.timeOffset = 0;
  }
  
  /**
  * Create new random Job.
  */
  public Job() {
    this.name = "W" + ++randomJobsCounter;
    this.priority = random.nextInt(11);
    this.time = random.nextInt(51);
    this.timeOffset = 0;
  }

  /**
  * Setter for updating job's time-offset.
  * @param offset the actucal offset.
  */
  public void setTimeOffset(int offset) {
    this.timeOffset = offset;
  }

  /**
  * Getter for the time-offset attribute.
  * @return timeOffset job's time-offset.
  */
  public int getTimeOffset() {
    return this.timeOffset;
  }
  
}
