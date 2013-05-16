import java.util.Random;

public class Maman14 {
  // initializing static variables
  public static final Random random = new Random();
  public static final int maxHeapSize = 10000;
  public static int systemTime = 0;
  public static int jobsCounter = 0;
  
  public static void main( String[] args ) {
    // Using max-heap as out priority queue
    Heap queue = new Heap( maxHeapSize );
    
    // Sample input
    queue.insert(3, new Job("N",3,11));
    queue.insert(4, new Job("E",4,30));
    queue.insert(6, new Job("T",6,15));
    queue.insert(7, new Job("A",7,21));
    queue.insert(8, new Job("F",8,25));
    queue.insert(5, new Job("H",5,18));
    
    // extracting jobs from the queue
    while ( ! queue.isEmpty() ) {
      Job job = (Job)queue.extractMax();
      int clockTicks = job.time;
      
      // processing job
      while ( clockTicks-- != 0 ) {

        // will add random job to queue for 3% of the clock ticks
        if ( random.nextInt( 100 ) < 3 ) {
          Job randomJob = new Job();
          // setting the time offset for calculation the stats right later
          randomJob.setTimeOffset( -systemTime + -clockTicks);
          queue.insert( randomJob.priority, randomJob );
        }
      }
      
      // printing job stats
      System.out.println(    "" +
          ++jobsCounter   + " " +
          job.name        + " " + 
          job.priority    + " " +
          job.time        + " " +
          ( systemTime + job.getTimeOffset() )
      );
      
      // incrementing the overall system time
      systemTime += job.time;
    }
  }
}