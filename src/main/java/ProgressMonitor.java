import org.apache.log4j.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;

public class ProgressMonitor implements Runnable {

    private static final Logger logger = Logger.getLogger(ProgressMonitor.class);
    private Main main;
    private ScheduledExecutorService scheduledExecutor;
    private CountDownLatch countDownLatch;
    private int countOfAllElements;


    public ProgressMonitor(Main main, ScheduledExecutorService scheduledExecutor, CountDownLatch countDownLatch) {
        this.main = main;
        this.scheduledExecutor = scheduledExecutor;
        this.countDownLatch = countDownLatch;
        this.countOfAllElements = main.getM() * main.getN();
    }

    @Override
    public void run() {

        int countOfOutputElements = main.getCountOfOutputElements();

        if (countOfOutputElements == countOfAllElements)
            scheduledExecutor.shutdown();

        logger.info(countOfOutputElements + "/" + (countOfAllElements - countOfOutputElements));

        if (countOfOutputElements == 0)
            countDownLatch.countDown();
    }
}
