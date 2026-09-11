package org.compsys704;

public class MachineReport {

    public final ControllerReportType type;
    public final String workpieceId;
    public final String faultReason;

    public MachineReport(
        ControllerReportType type,
        String workpieceId,
        String faultReason
    ) {
        this.type = type;
        this.workpieceId = workpieceId;
        this.faultReason = faultReason;
    }
}