package org.compsys704;

public class MachineCommand {

    public final ControllerCommandType type;
    public final String workpieceId;
    public final int[] operationData;

    public MachineCommand(
        ControllerCommandType type,
        String workpieceId,
        int[] operationData
    ) {
        this.type = type;
        this.workpieceId = workpieceId;
        this.operationData = operationData;
    }
}