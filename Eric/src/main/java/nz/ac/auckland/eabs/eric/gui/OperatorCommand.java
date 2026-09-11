package nz.ac.auckland.eabs.eric.gui;

/** Operator intent forwarded to the shared Production Coordinator. */
public final class OperatorCommand {
    private final OperatorCommandType type;
    private final String target;
    private final String value;

    public OperatorCommand(
            OperatorCommandType type,
            String target,
            String value) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
        this.target = target == null ? "" : target;
        this.value = value == null ? "" : value;
    }

    public OperatorCommandType getType() { return type; }
    public String getTarget() { return target; }
    public String getValue() { return value; }
}
