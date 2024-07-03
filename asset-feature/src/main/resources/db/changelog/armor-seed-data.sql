-- add seed data if the table is empty
WITH check_empty AS (
    SELECT COUNT(*) AS row_count FROM asset.armor
)
INSERT INTO asset.armor (id,version,type,damage_resistance,cost,weight,created_by,created_on,modified_by,modified_on)
SELECT *
FROM (VALUES
          (nextval('asset.armor_id_seq'), 1, 'Cloth Armor', 1, 150, 12, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Leather Armor', 2, 340, 20, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Light Scale', 3, 610, 49, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Mail', 4, 645, 58, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Steel Laminate', 5, 1360, 64, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Plate', 6, 4040, 90, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Flak Jacket', 7, 500, 20, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Ballistic Vest', 8, 400, 2, 'seed', NOW(), 'seed', now()),
          (nextval('asset.armor_id_seq'), 1, 'Tactical Vest', 12, 900, 9, 'seed', NOW(), 'seed', now())
     ) AS v (id, version,type,damage_resistance,cost,weight,created_by,created_on,modified_by,modified_on)
WHERE (SELECT row_count FROM check_empty) = 0;
