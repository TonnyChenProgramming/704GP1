package nz.ac.auckland.eabs.zhiyuan.coordinator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The asynchronous DB Worker described in the IP report Section 6/10 (Stage 2): the single
 * thread that ever touches the IP's JDBC Connection/Dao, so that no SystemJ-facing caller --
 * the console reader thread, or the SystemJ tick thread that calls resultReceived() -- ever
 * blocks on a database operation. Callers submit a plain Runnable (the "dbRequest"); the
 * worker runs submitted requests in order on its own thread. There is no separate typed
 * "dbResponseAck" object: every caller in this codebase already knows what it wants done
 * with the outcome (a println, or setting a field a later synchronized call reads), so the
 * Runnable reports its own result inline rather than through a second signal/callback type.
 */
final class DbWorker {
    private final BlockingQueue<Runnable> requests = new LinkedBlockingQueue<Runnable>();

    DbWorker() {
        Thread worker = new Thread(new Runnable() {
            public void run() { runLoop(); }
        }, "ip-db-worker");
        worker.setDaemon(true);
        worker.start();
    }

    void submit(Runnable request) {
        requests.offer(request);
    }

    private void runLoop() {
        while (true) {
            try {
                requests.take().run();
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return;
            } catch (RuntimeException unexpected) {
                System.err.println("[DbWorker] Unexpected error processing a database request: " + unexpected);
            }
        }
    }
}
