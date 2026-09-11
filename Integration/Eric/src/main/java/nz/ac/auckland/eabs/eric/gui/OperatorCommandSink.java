package nz.ac.auckland.eabs.eric.gui;

/** Implemented by the coordinator adapter, never by an individual plant. */
public interface OperatorCommandSink {
    void submit(OperatorCommand command);
}
