CREATE TABLE IF NOT EXISTS bookorder (
   bookorderid BIGSERIAL PRIMARY KEY,
   notes TEXT NOT NULL,
   timestamp TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS inventoryitem (
    itemid BIGSERIAL PRIMARY KEY,
    bookid TEXT NOT NULL,
    quantity INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS bookorderinventoryitem (
    bookorderid BIGINT NOT NULL,
    itemid BIGINT NOT NULL,
    FOREIGN KEY (bookorderid) REFERENCES bookorder (bookorderid),
    FOREIGN KEY (itemid) REFERENCES inventoryitem (itemid),
    PRIMARY KEY (bookorderid, itemid)
);