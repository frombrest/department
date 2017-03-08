CREATE SCHEMA "TW" DEFAULT CHARACTER SET utf8;

CREATE TABLE "TW"."Department" (
  "id" INT IDENTITY PRIMARY KEY NOT NULL,
  "name" VARCHAR(45)
  );

CREATE TABLE "TW"."Employee" (
  "id" INT IDENTITY PRIMARY KEY NOT NULL,
  "full_name" VARCHAR(45),
  "date_of_birth" DATE,
  "department_id" INT,
  "salary" DOUBLE
  );