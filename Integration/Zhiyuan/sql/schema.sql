-- IP Persistence Layer — SQLite schema
-- COMPSYS 704 Project 1, Group 7 — Zhiyuan Fu (Individual Project)
-- Matches IP Conceptual Design Report, Appendix Tables 1-6.
-- NOTE: rotary_table_position, next_operation, completed_operations,
-- actual_stations_used, fault_history were removed from the original
-- Milestone-1 draft schema after review (see proposal check-in on
-- BottleEvents design). label_data is kept but nullable and unused
-- until the Labeller sub-team confirms its field contract.

PRAGMA foreign_keys = ON;

-- Table 1: Recipes
CREATE TABLE Recipes (
    recipe_id           INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id          VARCHAR(64) NOT NULL,
    liquid_a_proportion DECIMAL(5,4) NOT NULL,
    liquid_b_proportion DECIMAL(5,4) NOT NULL,
    bottle_type         VARCHAR(64) NOT NULL,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (liquid_a_proportion + liquid_b_proportion <= 1.0001)
);

-- Table 2: Batches
CREATE TABLE Batches (
    batch_id             INTEGER PRIMARY KEY AUTOINCREMENT,
    recipe_id            INTEGER NOT NULL REFERENCES Recipes(recipe_id),
    product_id           VARCHAR(64) NOT NULL,
    status                VARCHAR(16) NOT NULL DEFAULT 'PENDING'
                          CHECK (status IN ('PENDING','RUNNING','COMPLETED','FAULT')),
    start_timestamp       DATETIME,
    completion_timestamp  DATETIME,
    created_at            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table 3: Orders
CREATE TABLE Orders (
    order_id            INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_po         VARCHAR(64) NOT NULL UNIQUE,
    customer_id         VARCHAR(64) NOT NULL,
    product_id          VARCHAR(64) NOT NULL,
    quantity            INTEGER NOT NULL CHECK (quantity > 0),
    status               VARCHAR(16) NOT NULL DEFAULT 'PENDING'
                          CHECK (status IN ('PENDING','ADMITTED','COMPLETED','REJECTED')),
    completed_quantity   INTEGER NOT NULL DEFAULT 0,
    created_at           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at         DATETIME
);

-- Table 4: OrderBatches -- junction table, many-to-many Orders / Batches
-- admitted_quantity tracks in-flight (admitted-but-not-yet-complete) bottles,
-- separately from completed_quantity, so bottle-to-order attribution at
-- admission time doesn't over-attribute concurrent bottles to the same
-- order (see POS_BatchManager_Spec.md, Section 4, and Dao.nextUnfulfilledOrderForBatch).
CREATE TABLE OrderBatches (
    order_id            INTEGER NOT NULL REFERENCES Orders(order_id),
    batch_id            INTEGER NOT NULL REFERENCES Batches(batch_id),
    allocated_quantity  INTEGER NOT NULL CHECK (allocated_quantity > 0),
    admitted_quantity   INTEGER NOT NULL DEFAULT 0,
    completed_quantity  INTEGER NOT NULL DEFAULT 0,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (order_id, batch_id)
);

-- Table 5: BottleEvents (persistent, append-only digital-twin history)
CREATE TABLE BottleEvents (
    event_id                     INTEGER PRIMARY KEY AUTOINCREMENT,
    bottle_id                    VARCHAR(64) NOT NULL,   -- IP-minted, stable across runs
    workpiece_id                 VARCHAR(64) NOT NULL,   -- GP run-local Tracker id
    order_id                     INTEGER REFERENCES Orders(order_id),
    batch_id                     INTEGER NOT NULL REFERENCES Batches(batch_id),
    product_id                   VARCHAR(64),
    recipe_id                    INTEGER REFERENCES Recipes(recipe_id),
    location                     VARCHAR(32),
    status                        VARCHAR(16) NOT NULL
                                  CHECK (status IN ('DONE','FAULT','ABORTED')),
    operation_start_timestamp     DATETIME,
    operation_completion_timestamp DATETIME,
    event_timestamp               DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    label_data                    TEXT  -- JSON, NULL until Labeller contract is confirmed
);
CREATE INDEX idx_bottleevents_bottle ON BottleEvents(bottle_id);
CREATE INDEX idx_bottleevents_batch  ON BottleEvents(batch_id);

-- Table 6: Faults
CREATE TABLE Faults (
    fault_id            INTEGER PRIMARY KEY AUTOINCREMENT,
    bottle_id           VARCHAR(64) NOT NULL,
    batch_id            INTEGER NOT NULL REFERENCES Batches(batch_id),
    device_name         VARCHAR(64) NOT NULL,
    fault_type          VARCHAR(64),
    fault_reason         TEXT,
    fault_timestamp      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_timestamp   DATETIME,
    resolution           TEXT,
    status                VARCHAR(16) NOT NULL DEFAULT 'OPEN'
                          CHECK (status IN ('OPEN','RESOLVED'))
);
CREATE INDEX idx_faults_bottle ON Faults(bottle_id);
