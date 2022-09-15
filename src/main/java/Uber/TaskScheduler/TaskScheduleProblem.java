package Uber.TaskScheduler;

import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class ScheduledTask implements Comparable<ScheduledTask> {

    private final Callable runnable;
    private long scheduledTime;
    private final int taskType;
    private final long duration;
    private final long delay;
    private final TimeUnit timeUnit;

    public ScheduledTask(Callable runnable, long scheduledTime, int taskType,
                         long duration, long delay, TimeUnit timeUnit) {
        this.runnable = runnable;
        this.scheduledTime = scheduledTime;
        this.taskType = taskType;
        this.duration = duration;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public Callable getRunnable() {
        return runnable;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long getDuration() {
        return duration;
    }

    public long getDelay() {
        return delay;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        if (scheduledTime > o.getScheduledTime()) {
            return 1;
        } else if (scheduledTime < o.getScheduledTime()) {
            return -1;
        } else {
            return 0;
        }
//        return (int) (scheduledTime - o.getScheduledTime());
    }
}




class TaskSchedulerService {

    private final PriorityQueue<ScheduledTask> taskQueue;
    private final Lock lock = new ReentrantLock();
    private final Condition newTaskAdded;
    private final ThreadPoolExecutor executor;

    public TaskSchedulerService(int threadPoolSize) {
        newTaskAdded = lock.newCondition();
        taskQueue = new PriorityQueue<>();
//        taskQueue = new PriorityQueue<>(Comparator.comparingLong(ScheduledTask::getScheduledTime));
//        taskQueue = new PriorityQueue<>(Comparator.comparingLong(ScheduledTask::getScheduledTime).reversed());
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        long timeToSleep = 0;
        while (true) {
            lock.lock();
            try {
                while(taskQueue.isEmpty()){
                    newTaskAdded.await();
                }
                while (!taskQueue.isEmpty()){
                    timeToSleep = taskQueue.peek().getScheduledTime() - System.currentTimeMillis();
                    if(timeToSleep <= 0){
                        break;
                    }
                    newTaskAdded.await(timeToSleep, TimeUnit.MILLISECONDS);
                }
                ScheduledTask task = taskQueue.poll();
                long newScheduledTime = 0;
                switch (task.getTaskType()){
                    case 1:
                        //this type of task will be executed only once
                        executor.submit(task.getRunnable());
                        break;
                    case 2:
                        newScheduledTime = System.currentTimeMillis() + task.getTimeUnit().toMillis(task.getDuration());
                        executor.submit(task.getRunnable());
                        task.setScheduledTime(newScheduledTime);
                        taskQueue.add(task);
                        break;
                    case 3:
                        Future<Integer> future = executor.submit(task.getRunnable());
                        int res = future.get(); // will wait for the finish of this task
                        System.out.println("Task Type 3 returned: " + res);
                        newScheduledTime = System.currentTimeMillis() + task.getTimeUnit().toMillis(task.getDelay());
                        task.setScheduledTime(newScheduledTime);
                        taskQueue.add(task);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error occured");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Creates and executes a one-shot action that becomes enabled after the given delay.
     */
    public void schedule(Callable command, long delay, TimeUnit unit) {
        lock.lock();
        try{
            long scheduledTime = System.currentTimeMillis() + unit.toMillis(delay);
            ScheduledTask task = new ScheduledTask(command, scheduledTime, 1, 0, 0, unit);
            taskQueue.add(task);
            newTaskAdded.signal();
        }catch (Exception e){
            System.out.println("some thing wrong in scheduling task type 1");
        }finally {
            lock.unlock();
        }
    }

    /**
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and
     * subsequently with the given period; that is executions will commence after initialDelay then
     * initialDelay+period, then initialDelay + 2 * period, and so on.
     */
    public void scheduleAtFixedRate(Callable command, long initialDelay, long period, TimeUnit unit) {
        lock.lock();
        try{
            long scheduledTime = System.currentTimeMillis() + unit.toMillis(initialDelay);
            ScheduledTask task = new ScheduledTask(command, scheduledTime, 2, period, 0, unit);
            taskQueue.add(task);
            newTaskAdded.signal();
        }catch (Exception e){
            System.out.println("some thing wrong in scheduling task type 2");
        }finally {
            lock.unlock();
        }
    }

    /**
     * Creates and executes a periodic action that becomes enabled first after the given initial delay, and
     * subsequently with the given delay between the termination of one execution and the commencement of the next.
     */
    public void scheduleWithFixedDelay(Callable command, long initialDelay, long delay, TimeUnit unit) {
        lock.lock();
        try{
            long scheduledTime = System.currentTimeMillis() + unit.toMillis(initialDelay);
            ScheduledTask task = new ScheduledTask(command, scheduledTime, 3, 0, delay, unit);
            taskQueue.add(task);
            newTaskAdded.signal();
        }catch (Exception e){
            System.out.println("some thing wrong in scheduling task type 3");
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}



public class TaskScheduleProblem {

    public static void main(String[] args) {
        TaskSchedulerService taskSchedulerService = new TaskSchedulerService(10);

        Callable task1 = getRunnableTask("Task1");
        taskSchedulerService.schedule(task1, 1, TimeUnit.SECONDS);
        Callable task2 = getRunnableTask("Task2");
        taskSchedulerService.scheduleAtFixedRate(task2,1, 2, TimeUnit.SECONDS);
        Callable task3 = getRunnableTask("Task3");
        taskSchedulerService.scheduleWithFixedDelay(task3,1,2,TimeUnit.SECONDS);
        Callable task4 = getRunnableTask("Task4");
        taskSchedulerService.scheduleAtFixedRate(task4,1, 2, TimeUnit.SECONDS);
        taskSchedulerService.start();
    }

    private static Callable getRunnableTask(String s) {
        return () -> {
            System.out.println(s +" started at " + System.currentTimeMillis() / 1000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(s +" ended at " + System.currentTimeMillis() / 1000);
            return 0;
        };
    }
}

