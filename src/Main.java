
import java.util.ArrayList;


public class Main {
    //array to hold 200000000 random numbers
    private static ArrayList<Integer> randomArray = new ArrayList<Integer>();
    //variable that holds the sum of the numbers
    private static int sum = 0;

    //Thread that iterates over the entire array and calculates the sum
    static Thread singleThread = new Thread(new Runnable() {
        @Override
        public void run() {
            for(int i = 0; i < randomArray.size(); i++){
                addToSum(i);
            }
        }
    });

    //Thread that iterates over the first half of the array and adds it to the sum
    static Thread multiThread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            for(int i = 0; i < randomArray.size()/2; i++){
                addToSum(i);
            }
        }
    });
    ////Thread that iterates over the second half of the array and adds it to the sum
    static Thread multiThread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            for(int i = randomArray.size()/2; i < randomArray.size(); i++){
                addToSum(i);
            }
        }
    });

    //synchronized function that allows threads to add to sum at the same time
    private static synchronized void addToSum(int index){
        sum = sum + randomArray.get(index);
    }


    public static void main(String[] args) {
        // populates the array with 200000000 random numbers
        for (int i = 0; i < 200000000; i++) {
            int randomNumber = (int) (Math.random() * (10 - 1 + 1) + 1);
            randomArray.add(randomNumber);
        }

        //timestamp before starting the first thread
        long startTime = System.currentTimeMillis();
        singleThread.start();

        //join is used to ensure that the thread completes before continuing
        try {
            singleThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // timestamp for end of thread
        long endTime = System.currentTimeMillis();
        //calculate total time
        long totalTime = endTime - startTime;
        //display results
        System.out.println("The single thread sum is " + sum);
        System.out.println("Single threading took " + totalTime + " milliseconds");

        // reset sum to 0 for use in the next calculation
        sum = 0;

        //current timestamp and beginning the threads in parallel
        startTime = System.currentTimeMillis();
        multiThread1.start();
        multiThread2.start();

        //join threads, so they finish executing at the same time before moving on
        try {
            multiThread1.join();
            multiThread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //ending time stamp
        endTime = System.currentTimeMillis();
        //calculation for total time
        totalTime = endTime - startTime;
        //display multithreading results
        System.out.println("The multi thread sum is " + sum);
        System.out.println("multi threading took " + totalTime + " milliseconds");
    }
}

