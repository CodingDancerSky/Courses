OUTPUTS = $(sort $(wildcard outputs/*.txt))
QUESTIONS = $(patsubst outputs/%.txt,%,$(OUTPUTS))
MAKEFILE_PATH := $(shell readlink $(dir $(lastword $(MAKEFILE_LIST))) -f)
ROOT = $(MAKEFILE_PATH)

all: path $(QUESTIONS)
	rm -rf tmp

%: queries/%.sql
	@echo "checking $@; correct if nothing below ----"
	@psql -A -t -d $(USER) -q -f $< > tmp/$@.txt
	@diff outputs/$@.txt tmp/$@.txt || echo "$@ is wrong"; exit 0
	@echo ""

path:
	@mkdir -p tmp

setup_postgres:
	@echo "creating tables"
	psql -d $(USER) -q -f setup_postgres/create_tables.sql
	@echo "importing data"
	psql -d $(USER) -q -c "COPY yelp_user FROM '$(ROOT)/yelp_data/yelp_user.csv' DELIMITER ',' CSV;"
	psql -d $(USER) -q -c "COPY business FROM '$(ROOT)/yelp_data/business.csv' DELIMITER ',' CSV;"
	psql -d $(USER) -q -c "COPY review FROM '$(ROOT)/yelp_data/review.csv' DELIMITER ',' CSV;"
	psql -d $(USER) -q -c "COPY business_category FROM '$(ROOT)/yelp_data/business_category.csv' DELIMITER ',' CSV;"
	@echo "test, count distinct business categories"
	psql -d $(USER) -q -c "SELECT count(distinct(category)) FROM business_category;"

clean_postgres:
	psql -d $(USER) -q -f setup_postgres/drop_tables.sql

submission:
	tar cvzf $(USER).tar.gz queries

.PHONY: setup_postgres
