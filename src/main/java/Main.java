import org.apache.log4j.Logger;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private volatile int countOfOutputElements = 0;
    private int m;
    private int n;

    public int getCountOfOutputElements() {
        return countOfOutputElements;
    }
    public int getM() {
        return m;
    }
    public int getN() {
        return n;
    }

    public Main(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        int m = 0;
        int n = 0;

        do {
            try {
                System.out.print("Enter M: ");
                m = Integer.parseInt(sc.next());
                if (m < 2)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                logger.error("Input error. Try again. Note: valid only natural number more than 1");
            }
        } while (m < 2);

        do {
            try {
                System.out.print("Enter N: ");
                n = Integer.parseInt(sc.next());
                if (n < 2)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                logger.error("Input error. Try again. Note: valid only natural number more than 1");
            }
        } while (n < 2);

        Main main = new Main(m, n);
        main.outputElements();

    }

    public void outputElements() throws InterruptedException {

        final int DELAY = 60000 / (m * n - 1);
        int[][] array = fillArray(m, n);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(new ProgressMonitor(this, scheduledExecutor, countDownLatch),
                0, 10000, TimeUnit.MILLISECONDS);

        countDownLatch.await();

        for (int line = 0; line < m; line++) {
            if (line % 2 == 0) {
                for (int column = 0; column < n; column++) {
                    logger.info("[" + array[line][column] + "]");
                    countOfOutputElements++;
                    sleep(DELAY);
                }
            }
            else {
                for (int column = n - 1; column >= 0; column--) {
                    logger.info("[" + array[line][column] + "]");
                    countOfOutputElements++;
                    sleep(DELAY);
                }
            }
        }

    }

    private int[][] fillArray(int m, int n) {
        int[][] array = new int[m][n];
        int number = 1;
        for (int line = 0; line < m; line++) {
            for (int column = 0; column < n; column++) {
                array[line][column] = number++;
            }
        }
        return array;
    }

}


