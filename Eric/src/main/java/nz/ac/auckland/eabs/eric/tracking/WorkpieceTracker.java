package nz.ac.auckland.eabs.eric.tracking;

import nz.ac.auckland.eabs.eric.model.FinishingJob;
import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.MachineReport;
import nz.ac.auckland.eabs.eric.model.MachineState;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;
import nz.ac.auckland.eabs.eric.persistence.WorkpieceRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Authoritative owner of active Group Project bottle twins.
 *
 * Commands express intent. Only correlated controller results and named sensor
 * evidence are allowed to change completed operations or physical location.
 */
public final class WorkpieceTracker {
    private final Map<String, WorkpieceTwin> active =
            new LinkedHashMap<String, WorkpieceTwin>();
    private final WorkpieceRepository repository;

    public WorkpieceTracker(WorkpieceRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public synchronized WorkpieceSnapshot createTwin(
            String workpieceId,
            String orderId,
            String batchId,
            String productId,
            String requiredRecipe,
            List<Operation> requiredOperations) {
        requireText(workpieceId, "workpieceId");
        if (active.containsKey(workpieceId)) {
            throw new IllegalArgumentException(
                    "Duplicate active workpiece " + workpieceId);
        }
        WorkpieceTwin twin = new WorkpieceTwin(
                workpieceId,
                requireText(orderId, "orderId"),
                requireText(batchId, "batchId"),
                requireText(productId, "productId"),
                requireText(requiredRecipe, "requiredRecipe"),
                requiredOperations);
        active.put(workpieceId, twin);
        return twin.snapshot();
    }

    public synchronized void confirmLocation(
            String workpieceId,
            Location location,
            int rotaryPosition,
            String evidence) {
        requireActive(workpieceId).confirmLocation(
                Objects.requireNonNull(location, "location"),
                rotaryPosition,
                requireText(evidence, "evidence"));
    }

    public synchronized void dispatch(FinishingJob job) {
        Objects.requireNonNull(job, "job");
        requireActive(job.getWorkpieceId()).dispatch(job);
    }

    public synchronized void acceptReport(MachineReport report) {
        Objects.requireNonNull(report, "report");
        if (report.getState() == MachineState.READY
                || report.getState() == MachineState.OFFLINE) {
            throw new IllegalArgumentException(
                    "Availability reports belong to the coordinator, not a twin");
        }
        requireActive(report.getWorkpieceId()).acceptReport(report);
    }

    public synchronized void clearFaultForRetry(
            String workpieceId,
            String evidence) {
        requireActive(workpieceId).clearFaultForRetry(
                requireText(evidence, "evidence"));
    }

    public synchronized WorkpieceSnapshot completeAndArchive(
            String workpieceId,
            String labelPayload) throws IOException {
        WorkpieceTwin twin = requireActive(workpieceId);
        twin.complete(requireText(labelPayload, "labelPayload"));
        return archiveAndRemove(twin);
    }

    public synchronized WorkpieceSnapshot rejectAndArchive(
            String workpieceId,
            String reason) throws IOException {
        WorkpieceTwin twin = requireActive(workpieceId);
        twin.terminate(WorkpieceStatus.REJECTED, requireText(reason, "reason"));
        return archiveAndRemove(twin);
    }

    public synchronized WorkpieceSnapshot abortAndArchive(
            String workpieceId,
            String reason) throws IOException {
        WorkpieceTwin twin = requireActive(workpieceId);
        twin.terminate(WorkpieceStatus.ABORTED, requireText(reason, "reason"));
        return archiveAndRemove(twin);
    }

    public synchronized Optional<WorkpieceSnapshot> findActive(
            String workpieceId) {
        WorkpieceTwin twin = active.get(workpieceId);
        return twin == null
                ? Optional.empty()
                : Optional.of(twin.snapshot());
    }

    public synchronized Optional<WorkpieceSnapshot> findArchived(
            String workpieceId) throws IOException {
        return repository.findById(workpieceId);
    }

    public synchronized List<WorkpieceSnapshot> activeSnapshots() {
        List<WorkpieceSnapshot> snapshots =
                new ArrayList<WorkpieceSnapshot>();
        for (WorkpieceTwin twin : active.values()) {
            snapshots.add(twin.snapshot());
        }
        return Collections.unmodifiableList(snapshots);
    }

    private WorkpieceSnapshot archiveAndRemove(WorkpieceTwin twin)
            throws IOException {
        WorkpieceSnapshot snapshot = twin.snapshot();
        repository.save(snapshot);
        active.remove(twin.workpieceId);
        return snapshot;
    }

    private WorkpieceTwin requireActive(String workpieceId) {
        WorkpieceTwin twin = active.get(workpieceId);
        if (twin == null) {
            throw new IllegalArgumentException(
                    "Unknown active workpiece " + workpieceId);
        }
        return twin;
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    private static final class WorkpieceTwin {
        private final String workpieceId;
        private final String orderId;
        private final String batchId;
        private final String productId;
        private final String requiredRecipe;
        private final List<Operation> requiredOperations;
        private final List<Operation> completedOperations =
                new ArrayList<Operation>();
        private final List<String> actualStationsUsed =
                new ArrayList<String>();
        private final Map<Operation, String> operationTimestamps =
                new EnumMap<Operation, String>(Operation.class);
        private final List<String> faultHistory = new ArrayList<String>();
        private final List<String> eventHistory = new ArrayList<String>();

        private Location location = Location.CREATED;
        private int rotaryPosition;
        private Operation currentOperation;
        private WorkpieceStatus status = WorkpieceStatus.CREATED;
        private String currentJobId = "";
        private String currentMachineId = "";
        private String finalOutcome = "";
        private String labelPayload = "";

        private WorkpieceTwin(
                String workpieceId,
                String orderId,
                String batchId,
                String productId,
                String requiredRecipe,
                List<Operation> requiredOperations) {
            Objects.requireNonNull(requiredOperations, "requiredOperations");
            if (requiredOperations.isEmpty()) {
                throw new IllegalArgumentException(
                        "A workpiece requires at least one operation");
            }
            this.workpieceId = workpieceId;
            this.orderId = orderId;
            this.batchId = batchId;
            this.productId = productId;
            this.requiredRecipe = requiredRecipe;
            this.requiredOperations = Collections.unmodifiableList(
                    new ArrayList<Operation>(requiredOperations));
            this.eventHistory.add(event("CREATED", requiredRecipe));
        }

        private void confirmLocation(
                Location confirmedLocation,
                int confirmedRotaryPosition,
                String evidence) {
            requireNonTerminal();
            if (confirmedRotaryPosition < 0 || confirmedRotaryPosition > 6) {
                throw new IllegalArgumentException(
                        "rotaryPosition must be 0 (off table) or 1..6");
            }
            boolean rotaryLocation =
                    confirmedLocation.name().startsWith("ROTARY_P");
            if (rotaryLocation && confirmedRotaryPosition == 0) {
                throw new IllegalArgumentException(
                        "A rotary location requires position 1..6");
            }
            if (!rotaryLocation && confirmedRotaryPosition != 0) {
                throw new IllegalArgumentException(
                        "An off-table location requires rotaryPosition 0");
            }
            location = confirmedLocation;
            rotaryPosition = confirmedRotaryPosition;
            eventHistory.add(event(
                    "LOCATION_CONFIRMED",
                    confirmedLocation + " evidence=" + evidence));
        }

        private void dispatch(FinishingJob job) {
            requireNonTerminal();
            if (status == WorkpieceStatus.FAULTED) {
                throw new IllegalStateException(
                        "Clear or terminate the workpiece fault before retry");
            }
            if (!currentJobId.isEmpty()) {
                throw new IllegalStateException(
                        "Workpiece already has active job " + currentJobId);
            }
            Operation expected = nextOperation();
            if (expected != job.getOperation()) {
                throw new IllegalArgumentException(
                        "Expected " + expected + " but received "
                                + job.getOperation());
            }
            currentJobId = job.getJobId();
            currentMachineId = job.getMachineId();
            currentOperation = job.getOperation();
            status = WorkpieceStatus.IN_PROCESS;
            eventHistory.add(event(
                    "COMMAND_DISPATCHED",
                    currentJobId + " " + currentMachineId + " "
                            + currentOperation));
        }

        private void acceptReport(MachineReport report) {
            validateCorrelation(report);
            if (report.getState() == MachineState.BUSY) {
                status = WorkpieceStatus.IN_PROCESS;
                eventHistory.add(event("BUSY_CONFIRMED", report.getMachineId()));
                return;
            }
            if (report.getState() == MachineState.DONE) {
                completedOperations.add(currentOperation);
                actualStationsUsed.add(currentMachineId);
                operationTimestamps.put(
                        currentOperation,
                        report.getReportedAt());
                eventHistory.add(event(
                        "OPERATION_CONFIRMED",
                        currentOperation + " evidence=" + report.getEvidence()));
                clearCurrentJob();
                status = WorkpieceStatus.WAITING;
                return;
            }
            if (report.getState() == MachineState.FAULT) {
                String fault = event(
                        "FAULT",
                        currentMachineId + " " + report.getDetail());
                faultHistory.add(fault);
                eventHistory.add(fault);
                status = WorkpieceStatus.FAULTED;
                return;
            }
            throw new IllegalArgumentException(
                    "Unsupported workpiece report state " + report.getState());
        }

        private void clearFaultForRetry(String evidence) {
            if (status != WorkpieceStatus.FAULTED) {
                throw new IllegalStateException(
                        "Workpiece is not faulted");
            }
            eventHistory.add(event("FAULT_CLEARED", evidence));
            clearCurrentJob();
            status = WorkpieceStatus.WAITING;
        }

        private void complete(String finalLabelPayload) {
            requireNonTerminal();
            if (!completedOperations.containsAll(requiredOperations)) {
                throw new IllegalStateException(
                        "Cannot complete before every required operation is confirmed");
            }
            labelPayload = finalLabelPayload;
            finalOutcome = "COMPLETED";
            status = WorkpieceStatus.COMPLETED;
            location = Location.ARCHIVED;
            rotaryPosition = 0;
            eventHistory.add(event("COMPLETED", finalLabelPayload));
        }

        private void terminate(
                WorkpieceStatus terminalStatus,
                String reason) {
            requireNonTerminal();
            if (terminalStatus != WorkpieceStatus.REJECTED
                    && terminalStatus != WorkpieceStatus.ABORTED) {
                throw new IllegalArgumentException(
                        "Unsupported terminal status " + terminalStatus);
            }
            status = terminalStatus;
            finalOutcome = reason;
            location = Location.REJECT_COLLECTION;
            rotaryPosition = 0;
            faultHistory.add(event(terminalStatus.name(), reason));
            eventHistory.add(event(terminalStatus.name(), reason));
            clearCurrentJob();
        }

        private void validateCorrelation(MachineReport report) {
            if (currentJobId.isEmpty()) {
                throw new IllegalStateException(
                        "No active job for " + workpieceId);
            }
            if (!workpieceId.equals(report.getWorkpieceId())
                    || !currentJobId.equals(report.getJobId())
                    || !currentMachineId.equals(report.getMachineId())) {
                throw new IllegalArgumentException(
                        "Stale or mismatched report for " + workpieceId);
            }
        }

        private Operation nextOperation() {
            for (Operation operation : requiredOperations) {
                if (!completedOperations.contains(operation)) {
                    return operation;
                }
            }
            return null;
        }

        private void clearCurrentJob() {
            currentJobId = "";
            currentMachineId = "";
            currentOperation = null;
        }

        private void requireNonTerminal() {
            if (status == WorkpieceStatus.COMPLETED
                    || status == WorkpieceStatus.REJECTED
                    || status == WorkpieceStatus.ABORTED) {
                throw new IllegalStateException(
                        "Workpiece is already terminal: " + status);
            }
        }

        private WorkpieceSnapshot snapshot() {
            return new WorkpieceSnapshot(
                    workpieceId,
                    orderId,
                    batchId,
                    productId,
                    requiredRecipe,
                    location,
                    rotaryPosition,
                    currentOperation,
                    nextOperation(),
                    status,
                    requiredOperations,
                    completedOperations,
                    actualStationsUsed,
                    operationTimestamps,
                    faultHistory,
                    eventHistory,
                    finalOutcome,
                    labelPayload);
        }

        private static String event(String type, String detail) {
            return Instant.now().toString() + " " + type + " " + detail;
        }
    }
}
