CREATE TABLE yelp_user (
uid char(22),
name text,
fans integer,
PRIMARY KEY (uid)
);

CREATE TABLE business (
bid char(22),
name text,
city text,
state varchar(3),
PRIMARY KEY (bid)
);

CREATE TABLE review (
bid char(22),
uid char(22),
stars real,
date Date,
votes_useful integer,
votes_funny integer,
votes_cool integer,
FOREIGN KEY (bid) REFERENCES business,
FOREIGN KEY (uid) REFERENCES yelp_user
);

CREATE TABLE business_category (
bid char(22),
category text,
FOREIGN KEY (bid) REFERENCES business
);


