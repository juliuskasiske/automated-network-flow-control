drop database if exists timetable;
create database if not exists timetable;
use timetable;

create table if not exists job (
	jobId varchar(50) primary key,
    position int not null references milepost(milepostId)
);

create table if not exists trackWarrant (
	trackWarrantId int auto_increment primary key,
    jobId varchar(50) not null references job(jobId),
    origin varchar(50) not null references milepost(milepostNumber),
    destination varchar(50) not null references milepost(milepostNumber)
);

 create table if not exists itinerary (
	id int auto_increment primary key,
	trackWarrant int not null references trackWarrant(trackWarrantId),
    milepost varchar(50) not null references milepost(milepostId)
 );
 
 create table if not exists milepost (
	milepostNumber varchar(50) primary key
 );
 
 create table if not exists edge (
	edgeId int primary key,
	startNode int not null references milepost(milepostNumber),
    endNode int not null references milepost(milepostNumber),
    numberTracks int not null
);

create table if not exists track (
	trackId int auto_increment primary key,
    edge int not null references edge(edgeId),
    trackLength int not null,
    occupied int references job(jobId)
);

delimiter $$
create trigger checkEdgeConnection
before insert on edge
for each row
begin
  if new.startNode = new.endNode then
    signal sqlstate '45000'
    set message_text = 'The mileposts the edge connects cannot be equal';
  end if;
end$$

create trigger checkTrackNumberOnEdge
before insert on track
for each row
begin
    -- Declare a variable to hold the count of existing tracks for the edge
    declare existingTrackCount int;
    -- Declare a variable to hold the number of tracks allowed for the edge
    declare maxTracksAllowed int;

    -- Find the current number of tracks associated with the edge to be inserted
    select count(*) into existingTrackCount
    from track
    where edge = new.edge;

    -- Get the maximum number of tracks allowed for this edge
    select numberTracks into maxTracksAllowed
    from edge
    where edgeId = new.edge;

    -- Check if adding another track would exceed the maximum allowed
    if existingTrackCount >= maxTracksAllowed then
        -- Prevent the insert operation
        signal sqlstate '45000'
        set message_text = 'This edge cannot support more tracks';
    end if;
end$$

delimiter ;
