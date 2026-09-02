package nz.ac.auckland.eabs.eric.persistence;

import nz.ac.auckland.eabs.eric.model.Location;
import nz.ac.auckland.eabs.eric.model.Operation;
import nz.ac.auckland.eabs.eric.model.WorkpieceStatus;
import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Dependency-free, recoverable GP baseline repository.
 *
 * A database IP can replace this implementation without changing the tracker.
 */
public final class RecoverableFileWorkpieceRepository
        implements WorkpieceRepository {
    private static final String ROOT = "workpiece.";

    private final Path file;

    public RecoverableFileWorkpieceRepository(Path file) {
        this.file = file.toAbsolutePath().normalize();
    }

    @Override
    public synchronized void save(WorkpieceSnapshot snapshot) throws IOException {
        if (!snapshot.isTerminal()) {
            throw new IllegalArgumentException(
                    "Only completed, rejected or aborted workpieces may be archived");
        }
        Properties properties = load();
        String prefix = prefix(snapshot.getWorkpieceId());
        removePrefix(properties, prefix);
        writeSnapshot(properties, prefix, snapshot);
        storeAtomically(properties);
    }

    @Override
    public synchronized Optional<WorkpieceSnapshot> findById(String workpieceId)
            throws IOException {
        Properties properties = load();
        String prefix = prefix(workpieceId);
        if (!properties.containsKey(prefix + "workpieceId")) {
            return Optional.empty();
        }
        return Optional.of(readSnapshot(properties, prefix));
    }

    @Override
    public synchronized List<WorkpieceSnapshot> findAll() throws IOException {
        Properties properties = load();
        List<String> prefixes = new ArrayList<String>();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(ROOT) && key.endsWith(".workpieceId")) {
                prefixes.add(key.substring(0, key.length() - "workpieceId".length()));
            }
        }
        Collections.sort(prefixes);
        List<WorkpieceSnapshot> snapshots = new ArrayList<WorkpieceSnapshot>();
        for (String prefix : prefixes) {
            snapshots.add(readSnapshot(properties, prefix));
        }
        snapshots.sort(Comparator.comparing(WorkpieceSnapshot::getWorkpieceId));
        return Collections.unmodifiableList(snapshots);
    }

    private Properties load() throws IOException {
        Properties properties = new Properties();
        if (!Files.exists(file) || Files.size(file) == 0) {
            return properties;
        }
        try (InputStream input = Files.newInputStream(file)) {
            properties.load(input);
        }
        return properties;
    }

    private void storeAtomically(Properties properties) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        try (OutputStream output = Files.newOutputStream(temporary)) {
            properties.store(
                    output,
                    "COMPSYS 704 GP completed workpieces " + Instant.now());
        }
        try {
            Files.move(
                    temporary,
                    file,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException unsupported) {
            Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void writeSnapshot(
            Properties target,
            String prefix,
            WorkpieceSnapshot snapshot) {
        set(target, prefix, "workpieceId", snapshot.getWorkpieceId());
        set(target, prefix, "orderId", snapshot.getOrderId());
        set(target, prefix, "batchId", snapshot.getBatchId());
        set(target, prefix, "productId", snapshot.getProductId());
        set(target, prefix, "requiredRecipe", snapshot.getRequiredRecipe());
        set(target, prefix, "location", snapshot.getLocation().name());
        set(target, prefix, "rotaryPosition",
                Integer.toString(snapshot.getRotaryPosition()));
        set(target, prefix, "currentOperation",
                enumName(snapshot.getCurrentOperation()));
        set(target, prefix, "nextOperation",
                enumName(snapshot.getNextOperation()));
        set(target, prefix, "status", snapshot.getStatus().name());
        set(target, prefix, "finalOutcome", snapshot.getFinalOutcome());
        set(target, prefix, "labelPayload", snapshot.getLabelPayload());
        writeEnumList(
                target, prefix + "requiredOperations.",
                snapshot.getRequiredOperations());
        writeEnumList(
                target, prefix + "completedOperations.",
                snapshot.getCompletedOperations());
        writeStringList(
                target, prefix + "actualStations.",
                snapshot.getActualStationsUsed());
        writeStringList(
                target, prefix + "faultHistory.",
                snapshot.getFaultHistory());
        writeStringList(
                target, prefix + "eventHistory.",
                snapshot.getEventHistory());
        for (Map.Entry<Operation, String> timestamp
                : snapshot.getOperationTimestamps().entrySet()) {
            target.setProperty(
                    prefix + "operationTimestamp." + timestamp.getKey().name(),
                    timestamp.getValue());
        }
    }

    private static WorkpieceSnapshot readSnapshot(
            Properties source,
            String prefix) {
        List<Operation> required = readEnumList(
                source, prefix + "requiredOperations.");
        List<Operation> completed = readEnumList(
                source, prefix + "completedOperations.");
        Map<Operation, String> timestamps =
                new EnumMap<Operation, String>(Operation.class);
        for (Operation operation : Operation.values()) {
            String value = source.getProperty(
                    prefix + "operationTimestamp." + operation.name());
            if (value != null) {
                timestamps.put(operation, value);
            }
        }
        return new WorkpieceSnapshot(
                required(source, prefix, "workpieceId"),
                required(source, prefix, "orderId"),
                required(source, prefix, "batchId"),
                required(source, prefix, "productId"),
                required(source, prefix, "requiredRecipe"),
                Location.valueOf(required(source, prefix, "location")),
                Integer.parseInt(required(source, prefix, "rotaryPosition")),
                nullableOperation(source.getProperty(prefix + "currentOperation")),
                nullableOperation(source.getProperty(prefix + "nextOperation")),
                WorkpieceStatus.valueOf(required(source, prefix, "status")),
                required,
                completed,
                readStringList(source, prefix + "actualStations."),
                timestamps,
                readStringList(source, prefix + "faultHistory."),
                readStringList(source, prefix + "eventHistory."),
                source.getProperty(prefix + "finalOutcome", ""),
                source.getProperty(prefix + "labelPayload", ""));
    }

    private static void writeEnumList(
            Properties target,
            String prefix,
            List<Operation> values) {
        target.setProperty(prefix + "count", Integer.toString(values.size()));
        for (int index = 0; index < values.size(); index++) {
            target.setProperty(
                    prefix + index,
                    values.get(index).name());
        }
    }

    private static List<Operation> readEnumList(
            Properties source,
            String prefix) {
        int count = Integer.parseInt(source.getProperty(prefix + "count", "0"));
        List<Operation> values = new ArrayList<Operation>();
        for (int index = 0; index < count; index++) {
            values.add(Operation.valueOf(
                    requiredProperty(source, prefix + index)));
        }
        return values;
    }

    private static void writeStringList(
            Properties target,
            String prefix,
            List<String> values) {
        target.setProperty(prefix + "count", Integer.toString(values.size()));
        for (int index = 0; index < values.size(); index++) {
            target.setProperty(prefix + index, values.get(index));
        }
    }

    private static List<String> readStringList(
            Properties source,
            String prefix) {
        int count = Integer.parseInt(source.getProperty(prefix + "count", "0"));
        List<String> values = new ArrayList<String>();
        for (int index = 0; index < count; index++) {
            values.add(requiredProperty(source, prefix + index));
        }
        return values;
    }

    private static void removePrefix(Properties properties, String prefix) {
        List<String> keys = new ArrayList<String>(
                properties.stringPropertyNames());
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                properties.remove(key);
            }
        }
    }

    private static String prefix(String workpieceId) {
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(
                workpieceId.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return ROOT + encoded + ".";
    }

    private static void set(
            Properties target,
            String prefix,
            String name,
            String value) {
        target.setProperty(prefix + name, value == null ? "" : value);
    }

    private static String enumName(Enum<?> value) {
        return value == null ? "" : value.name();
    }

    private static Operation nullableOperation(String value) {
        return value == null || value.isEmpty()
                ? null
                : Operation.valueOf(value);
    }

    private static String required(
            Properties source,
            String prefix,
            String name) {
        return requiredProperty(source, prefix + name);
    }

    private static String requiredProperty(
            Properties source,
            String key) {
        String value = source.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Archive field is missing: " + key);
        }
        return value;
    }
}
