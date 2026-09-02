package nz.ac.auckland.eabs.eric.persistence;

import nz.ac.auckland.eabs.eric.tracking.WorkpieceSnapshot;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** Replaceable persistence boundary for the Group Project baseline. */
public interface WorkpieceRepository {
    void save(WorkpieceSnapshot snapshot) throws IOException;

    Optional<WorkpieceSnapshot> findById(String workpieceId) throws IOException;

    List<WorkpieceSnapshot> findAll() throws IOException;
}
