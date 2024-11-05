-- Drop the table with foreign keys first to avoid foreign key constraint errors

-- Drop the detail_commons table (it references area_based_contents)
DROP TABLE IF EXISTS detail_commons;

-- Drop the area_based_contents table (it references multiple tables including area_codes, content_types, and category_codes)
DROP TABLE IF EXISTS area_based_contents;

-- Drop the sigungu_codes table (it references area_codes)
DROP TABLE IF EXISTS sigungu_codes;

-- Drop the category_codes table (it is referenced by area_based_contents)
DROP TABLE IF EXISTS category_codes;

-- Drop the content_types table (it is referenced by area_based_contents)
DROP TABLE IF EXISTS content_types;

-- Drop the area_codes table (it is referenced by sigungu_codes and area_based_contents)
DROP TABLE IF EXISTS area_codes;
