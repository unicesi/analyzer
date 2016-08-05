CREATE DATABASE mapek
  WITH OWNER = qoscare
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE mapek
  IS 'This database stores de information required by the knowledge base component in the MAPE-K loop for self adaptation.
SLO requirements';


-- Table: c3predicate

-- DROP TABLE c3predicate;

CREATE TABLE c3predicate
(
  day numeric NOT NULL,
  txmin numeric NOT NULL,
  CONSTRAINT "PK_day" PRIMARY KEY (day , txmin )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE c3predicate
  OWNER TO qoscare;

