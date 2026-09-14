package com.g7.ip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Thin JDBC connection helper.
 *
 * This class is intentionally the ONLY place in the IP codebase that
 * knows the JDBC URL / driver. Everything else (DAO, Assembler,
 * DeviationDetector) goes through {@link Dao}, never through a raw
 * Connection directly, so that swapping SQLite for another engine
 * later only touches this file.
 */
public final class Db {

    private Db() {}

    /**
     * Opens (and if necessary creates + initialises) the SQLite database
     * at the given path, applying schema.sql if the file is new.
     */
    public static Connection open(String dbPath, String schemaSqlPath) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not on classpath", e);
        }
        boolean isNew = !Files.exists(Paths.get(dbPath));
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        try (Statement s = conn.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");
        }
        if (isNew && schemaSqlPath != null) {
            initSchema(conn, schemaSqlPath);
        }
        return conn;
    }

    private static void initSchema(Connection conn, String schemaSqlPath) throws SQLException {
        try {
            String sql = new String(Files.readAllBytes(Paths.get(schemaSqlPath)));
            try (Statement s = conn.createStatement()) {
                for (String stmt : sql.split(";")) {
                    String trimmed = stmt.trim();
                    if (!trimmed.isEmpty()) {
                        s.execute(trimmed);
                    }
                }
            }
        } catch (java.io.IOException e) {
            throw new SQLException("Could not read schema file: " + schemaSqlPath, e);
        }
    }
}
