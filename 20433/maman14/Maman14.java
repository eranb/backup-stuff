import java.util.*; // importing the scanner class

public class Maman14 {
  public static int randomJobsCounter = 0;
  public static Random random = new Random();
  
  private static class Job {
    String name;
    int priority;
    int time;

    public Job(String name, int priority, int time) {
      this.name = name;
      this.priority = priority;
      this.time = time;
    }
  }
  
  public static void main( String[] args ) {
    Heap queue = new Heap( 100 );
    int systemTime = 0;
    int jobsCounter = 0;

    queue.insert(1, new Job("job1",1, 30));
    queue.insert(2, new Job("job2",2, 30));
    queue.insert(3, new Job("job3",3, 30));
    queue.insert(4, new Job("job4",4, 30));
    queue.insert(5, new Job("job5",5, 30));
    queue.insert(90, new Job("job90",90, 30));
    queue.insert(6, new Job("job6",6, 30));
    queue.insert(7, new Job("job7",7, 30));
    queue.insert(8, new Job("job8",8, 30));
    queue.insert(9, new Job("job9",9, 30));
    queue.insert(10, new Job("job10",10, 30));

/*
    Scanner scanner = new Scanner( System.in );
    String line = null;

    while(!(line = scanner.nextLine()).isEmpty()) {
      String[] parts = line.split("\\s+");
      String name = parts[0];
      int priority = Integer.parseInt(parts[1]);
      int time = Integer.parseInt(parts[2]);
      queue.insert(priority, new Job(name, priority, time));
      System.out.print("entered: " + Arrays.toString(parts) + "\n");
    }
*/  
    while ( !queue.isEmpty() ) {
      Job job = (Job)queue.extractMax();
      int jobTime = job.time;

      while (jobTime != 0) {
        jobTime--;
      }

      System.out.println( "" + ++jobsCounter + " " + job.name + " " + job.priority + " " + job.time + " " + systemTime );
      systemTime += job.time;
      
      if ( random.nextInt(101) < 30 ) {
        Job randomJob = generateRandomJob();
        queue.insert(randomJob.priority,randomJob);
      }
      
    }
  }

  public static Job generateRandomJob() {
    String name = "W" + ++randomJobsCounter;
    int priority = random.nextInt(11);
    int time = random.nextInt(51);
    return new Job(name,priority,time);
  }

}