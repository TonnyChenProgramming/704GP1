package nz.ac.auckland.eabs.eric.gui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Non-blocking state handoff between SystemJ-facing code and the Java GUI.
 *
 * File/network work does not occur here, and commands are returned only to the
 * coordinator-facing sink.
 */
public final class VisualizationBridge {
    public interface StateListener {
        void onState(DashboardState state);
    }

    private final OperatorCommandSink commandSink;
    private final AtomicReference<DashboardState> latest =
            new AtomicReference<DashboardState>(DashboardState.empty());
    private final List<StateListener> listeners =
            new CopyOnWriteArrayList<StateListener>();

    public VisualizationBridge(OperatorCommandSink commandSink) {
        if (commandSink == null) {
            throw new NullPointerException("commandSink");
        }
        this.commandSink = commandSink;
    }

    public void publish(DashboardState state) {
        if (state == null) {
            throw new NullPointerException("state");
        }
        latest.set(state);
        for (StateListener listener : listeners) {
            listener.onState(state);
        }
    }

    public void addListener(StateListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        listeners.add(listener);
        listener.onState(latest.get());
    }

    public void removeListener(StateListener listener) {
        listeners.remove(listener);
    }

    public void submitOperatorCommand(OperatorCommand command) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        commandSink.submit(command);
    }

    public DashboardState getLatest() {
        return latest.get();
    }
}
