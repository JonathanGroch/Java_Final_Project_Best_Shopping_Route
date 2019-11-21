drop database if exists StoreNavigator;
create database StoreNavigator;

use StoreNavigator;

drop table if exists Store;
drop table if exists Category;
drop table if exists Product;
drop table if exists Location;

create table Store(
	storeID int unsigned auto_increment,
    storeName varchar(25) unique not null,
    primary key(storeID)
);
create table Category(
	storeID int unsigned,
    categoryID int unsigned unique auto_increment,
    categoryName varchar(25) not null,
    foreign key(storeID) references Store(storeID),
    primary key(categoryID)
);
create table Location( # Waypoint is in format name of store, number, then underscore and product name . EX: 'Meijer1_Apples' For Meijer waypoint 1.
	waypoint varchar(40) not null,
    x float(2) not null, #Coordinates assume bottom-left corner of store map is (0,0);
    y float(2) not null,
    primary key(waypoint)
);

create table Product(
	categoryID int unsigned auto_increment,
    productID int unsigned,
    productName varchar(25) not null,
    waypoint varchar(40) not null,
    foreign key(categoryID) references Category(categoryID),
    foreign key(waypoint) references Location(waypoint),
    primary key(productID)
);
insert into Store value(
	1, 'Meijer'
);
insert into Store value(
	2, 'Menards'
);
insert into Store value(
	3, 'Target'
);

