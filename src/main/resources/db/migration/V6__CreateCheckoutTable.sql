CREATE TABLE checkout (
    checkoutID int NOT NULL PRIMARY KEY,
    memberID int REFERENCES member(id),
    copyID int  REFERENCES copy(id),
    checkoutDate date
);