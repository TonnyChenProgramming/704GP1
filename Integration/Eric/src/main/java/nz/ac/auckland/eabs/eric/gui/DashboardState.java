package nz.ac.auckland.eabs.eric.gui;

import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Immutable state snapshot sent from SystemJ through the GUI bridge. */
public final class DashboardState {
    private final String orderId;
    private final String productId;
    private final String batchId;
    private final String recipe;
    private final int requested;
    private final int completed;
    private final int rejected;
    private final int inProcess;
    private final int remaining;
    private final boolean safetyPermit;
    private final String hazardReason;
    private final String waitReason;
    private final Map<String, MachineState> machineStates;
    private final Map<Integer, String> rotaryOccupancy;
    private final List<WorkpieceSnapshot> workpieces;

    public DashboardState(
            String orderId,
            String productId,
            String batchId,
            String recipe,
            int requested,
            int completed,
            int rejected,
            int inProcess,
            int remaining,
            boolean safetyPermit,
            String hazardReason,
            String waitReason,
            Map<String, MachineState> machineStates,
            Map<Integer, String> rotaryOccupancy,
            List<WorkpieceSnapshot> workpieces) {
        this.orderId = safe(orderId);
        this.productId = safe(productId);
        this.batchId = safe(batchId);
        this.recipe = safe(recipe);
        this.requested = requested;
        this.completed = completed;
        this.rejected = rejected;
        this.inProcess = inProcess;
        this.remaining = remaining;
        this.safetyPermit = safetyPermit;
        this.hazardReason = safe(hazardReason);
        this.waitReason = safe(waitReason);
        this.machineStates = Collections.unmodifiableMap(
                machineStates == null
                        ? new LinkedHashMap<String, MachineState>()
                        : new LinkedHashMap<String, MachineState>(machineStates));
        this.rotaryOccupancy = Collections.unmodifiableMap(
                rotaryOccupancy == null
                        ? new LinkedHashMap<Integer, String>()
                        : new LinkedHashMap<Integer, String>(rotaryOccupancy));
        this.workpieces = Collections.unmodifiableList(
                workpieces == null
                        ? new ArrayList<WorkpieceSnapshot>()
                        : new ArrayList<WorkpieceSnapshot>(workpieces));
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    public static DashboardState empty() {
        return new DashboardState(
                "", "", "", "", 0, 0, 0, 0, 0,
                false, "No state received", "Waiting for coordinator",
                null, null, null);
    }

    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getBatchId() { return batchId; }
    public String getRecipe() { return recipe; }
    public int getRequested() { return requested; }
    public int getCompleted() { return completed; }
    public int getRejected() { return rejected; }
    public int getInProcess() { return inProcess; }
    public int getRemaining() { return remaining; }
    public boolean isSafetyPermit() { return safetyPermit; }
    public String getHazardReason() { return hazardReason; }
    public String getWaitReason() { return waitReason; }
    public Map<String, MachineState> getMachineStates() { return machineStates; }
    public Map<Integer, String> getRotaryOccupancy() { return rotaryOccupancy; }
    public List<WorkpieceSnapshot> getWorkpieces() { return workpieces; }
}
