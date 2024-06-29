-- automatically run by Spring at startup
-- assumes a clean database

CREATE SCHEMA asset;

CREATE TABLE asset.assets (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    version INT NOT NULL ,
    type VARCHAR(255) NOT NULL ,
    damage_resistance INT NOT NULL ,
    cost INT NOT NULL ,
    weight INT NOT NULL ,
    created_on TIMESTAMP NOT NULL ,
    created_by VARCHAR(255) NOT NULL ,
    modified_on TIMESTAMP NOT NULL ,
    modified_by VARCHAR(255) NOT NULL
);

