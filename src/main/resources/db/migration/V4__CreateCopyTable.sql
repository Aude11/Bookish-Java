CREATE TABLE copy (
	id serial primary key,
	book_id int references book(id)
);