-- add seed data if the table is empty
WITH check_empty AS (
    SELECT COUNT(*) AS row_count FROM asset.shields
)
INSERT INTO asset.shields (id,version,type,defense_bonus,cost,weight,created_by,created_on,modified_by,modified_on)
SELECT *
FROM (VALUES
          (nextval('asset.shields_id_seq'), 1, 'Small Shield', 1, 40, 8, 'seed', NOW(), 'seed', now()),
          (nextval('asset.shields_id_seq'), 1, 'Medium Shield', 2, 60, 15, 'seed', NOW(), 'seed', now()),
          (nextval('asset.shields_id_seq'), 1, 'Large Shield', 3, 90, 25, 'seed', NOW(), 'seed', now())
     ) AS v (id, version,type,defense_bonus,cost,weight,created_by,created_on,modified_by,modified_on)
WHERE (SELECT row_count FROM check_empty) = 0;
