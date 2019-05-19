DROP TABLE IF EXISTS operation_ride;
DROP TABLE IF EXISTS route_ride;
DROP TABLE IF EXISTS customer_charter_offer;
DROP TABLE IF EXISTS charter_offer_extra;
DROP TABLE IF EXISTS bus_compatible_extra;
DROP TABLE IF EXISTS bus_extra;
DROP TABLE IF EXISTS standby_ride;
DROP TABLE IF EXISTS shift_ride;
DROP TABLE IF EXISTS ride;
DROP TABLE IF EXISTS path_station;
DROP TABLE IF EXISTS removable_extra_unit;
DROP TABLE IF EXISTS charter_offer;
DROP TABLE IF EXISTS start_time;
DROP TABLE IF EXISTS path;
DROP TABLE IF EXISTS driving_licenses;
DROP TABLE IF EXISTS suspended;
DROP TABLE IF EXISTS extra;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS shift;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS station;
DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS bus;
DROP TABLE IF EXISTS customer;



--Level 0


CREATE TABLE bus(
    bus_id SERIAL PRIMARY KEY,
	maintenance_km integer NOT NULL, 
	licence_number varchar(255) UNIQUE, 
	make varchar(255), 
	model varchar(255),
	note varchar(255),
	registration_date date,
	seat_places integer NOT NULL, 
	stand_places integer NOT NULL,
	operation_id integer
);

CREATE TABLE driver(
	driver_id SERIAL PRIMARY KEY,
	address varchar(255),
	birthday date, 
	email varchar(255), 
	employment_type integer, 
	firstname varchar(255),
	lastname varchar(255), 
	job_description varchar(255),
	telephonenumber varchar(255)
);

CREATE TABLE operation(
	operation_id SERIAL PRIMARY KEY,
	operation_date date,
	bus_id integer,
	CONSTRAINT FK_AssignedBus FOREIGN KEY(bus_id) REFERENCES bus(bus_id)
);

CREATE TABLE station(
	station_id SERIAL PRIMARY KEY,
	station_name varchar(255),
	short_name varchar(50)
);

CREATE TABLE route(
	route_id SERIAL PRIMARY KEY,
	route_number integer NOT NULL,
	valid_from date,
	valid_to date,
	variation varchar(255)
);

CREATE TABLE shift(
	shift_id SERIAL PRIMARY KEY
);

CREATE TABLE customer (
  customer_id SERIAL PRIMARY KEY,
  firstname varchar(255) NOT NULL,
  lastname varchar(255) NOT NULL,
  address varchar(255),
  province varchar(255),
  city varchar(255),
  state varchar(255),
  zipcode varchar(255),
  email varchar(255),
  phone varchar(255)
);


CREATE TABLE extra(
  extra_id SERIAL primary KEY,
  type varchar(31) NOT NULL,
  description varchar(255)
);

--Level 1

CREATE TABLE suspended(
	suspended_id SERIAL PRIMARY KEY,
	bus_id integer NOT NULL,
	date_from date,
	date_to date,
	cause varchar(255),
	CONSTRAINT FK_SuspendedBus FOREIGN KEY (bus_id) REFERENCES bus (bus_id)
);

CREATE TABLE driving_licenses(
	driver_id  integer PRIMARY KEY,
	driving_licenses varchar(255)
);

CREATE TABLE path(
	path_id SERIAL PRIMARY KEY,
	path_description varchar(255),
	route_id integer NOT NULL,
	isretour boolean DEFAULT false NOT NULL,
	CONSTRAINT FK_RoutePath FOREIGN KEY (route_id) REFERENCES route (route_id)
);

CREATE TABLE start_time(
	start_time_id SERIAL PRIMARY KEY,
	path_id integer NOT NULL,
	required_capacity integer NOT NULL,
	start_time time without time zone,
	start_time_type integer,
	CONSTRAINT FK_StartTimePath FOREIGN KEY (path_id) REFERENCES path (path_id)
);

CREATE TABLE charter_offer(
  charter_offer_id SERIAL primary KEY,
  customer_id integer NOT NULL,
  create_date date NOT NULL,
  description varchar(255),
  place_of_departure varchar(255) NOT NULL,
  place_of_arrival varchar(255) NOT NULL,
  start_date date NOT NULL,
  end_date date NOT NULL,
  start_time time without time zone NOT NULL,
  end_time time without time zone NOT NULL,
  passenger_count integer NOT NULL,
  net_price DOUBLE PRECISION NOT NULL,
  discount DOUBLE PRECISION NOT NULL,
  tax DOUBLE PRECISION NOT NULL,
  CONSTRAINT FK_charter_offer_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE removable_extra_unit (
  removable_extra_unit_id SERIAL primary KEY,
  unit_id integer NOT NULL,
  CONSTRAINT FK_removable_extra_unit_extra FOREIGN KEY (unit_id) REFERENCES extra (extra_id)
);




--Level 2

CREATE TABLE path_station(
	path_station_id SERIAL PRIMARY KEY,
	station_id integer NOT NULL,
	path_id integer NOT NULL,
	position_on_path integer NOT NULL,
	distance_from_previous integer NOT NULL,
	time_from_previous integer NOT NULL,
	CONSTRAINT FK_PathStationStation FOREIGN KEY (station_id) REFERENCES station (station_id),
	CONSTRAINT FK_PathStationPath FOREIGN KEY (path_id) REFERENCES path (path_id)
);

/*
CREATE TABLE ride(
	ride_id SERIAL PRIMARY KEY,
	bus_id integer, 
	operation_id integer,
	--date date,
	start_date_time timestamp without time zone, 
	end_date_time timestamp without time zone, 
	CONSTRAINT FK_RideBus FOREIGN KEY (bus_id) REFERENCES bus (bus_id),
	CONSTRAINT FK_RideOperation FOREIGN KEY (operation_id) REFERENCES operation (operation_id)
);


CREATE TABLE shift_ride(
	shift_id bigint NOT NULL,
	ride_id bigint NOT NULL,
	PRIMARY KEY (shift_id, ride_id),
	CONSTRAINT FK_ShiftRideShift FOREIGN KEY (shift_id) REFERENCES shift (shift_id),
	CONSTRAINT FK_ShiftRideRide FOREIGN KEY (ride_id) REFERENCES ride (ride_id)
);

CREATE TABLE standby_ride(
	standby_ride_id SERIAL PRIMARY KEY,
	bus_id integer NOT NULL,
	operation_id integer NOT NULL,
	start_date_time timestamp without time zone, 
	end_date_time timestamp without time zone,
	CONSTRAINT FK_StandbyRideBus FOREIGN KEY (bus_id) REFERENCES bus (bus_id),
	CONSTRAINT FK_StandbyRideOperation FOREIGN KEY (operation_id) REFERENCES operation (operation_id)
);
*/

CREATE TABLE bus_extra (
  bus_id integer,
  extra_id integer,
  CONSTRAINT PK_bus_extra PRIMARY KEY (bus_id, extra_id),
  CONSTRAINT FK_bus_extra_bus FOREIGN KEY (bus_id) REFERENCES bus(bus_id),
  CONSTRAINT FK_bus_extra_extra FOREIGN KEY (extra_id) REFERENCES extra(extra_id)
);

CREATE TABLE bus_compatible_extra (
 bus_id integer,
 compatible_extra_id integer,
 CONSTRAINT PK_bus_compatible_extra PRIMARY KEY (bus_id, compatible_extra_id),
 CONSTRAINT FK_bus_compatible_extra_bus FOREIGN KEY (bus_id) REFERENCES bus(bus_id),
 CONSTRAINT FK_bus_compatible_extra_extra FOREIGN KEY (compatible_extra_id) REFERENCES extra(extra_id)
);

CREATE TABLE charter_offer_extra (
 charter_offer_id integer,
 extra_id integer,
 CONSTRAINT PK_charter_offer_extra PRIMARY KEY (charter_offer_id, extra_id),
 CONSTRAINT FK_charter_offer_extra_charter_offer FOREIGN KEY (charter_offer_id) REFERENCES charter_offer(charter_offer_id),
 CONSTRAINT FK_charter_offer_extra_extra FOREIGN KEY (extra_id) REFERENCES extra(extra_id)
);

CREATE TABLE customer_charter_offer (
  customer_id integer,
  charter_offer_id integer,
  CONSTRAINT PK_customer_charter_offer PRIMARY KEY (customer_id, charter_offer_id),
  CONSTRAINT FK_customer_charter_offer_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
  CONSTRAINT FK_customer_charter_offer_charter_offer FOREIGN KEY (charter_offer_id) REFERENCES charter_offer(charter_offer_id)

)



--Level 3

--Level 4

CREATE TABLE route_ride(
	route_ride_id SERIAL PRIMARY KEY,
	route_id integer NOT NULL,
	start_time_id integer NOT NULL,
	CONSTRAINT FK_RouteRideRoute FOREIGN KEY (route_id) REFERENCES route (route_id),
	CONSTRAINT FK_RouteRideStartTime FOREIGN KEY (start_time_id) REFERENCES start_time (start_time_id)
);

CREATE TABLE public.operation_ride (
    operation_id integer,
    route_ride_id integer,
	CONSTRAINT PK_OperationRide PRIMARY KEY (operation_id, route_ride_id),
	CONSTRAINT FK_OperationRideOperation FOREIGN KEY (operation_id) REFERENCES operation(operation_id),
	CONSTRAINT FK_OperationRideRide FOREIGN KEY (route_ride_id) REFERENCES route_ride(route_ride_id)
);