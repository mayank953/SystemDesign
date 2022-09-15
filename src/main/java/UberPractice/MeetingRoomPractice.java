package UberPractice;
/*

Code a system that can schedule meetings in a predefined set of conference rooms.
It should have a method like scheduleMeeting(timeRange) which returns any available room at that time and reserves it or an error if no rooms are available.
3
meeting 1 -> (12345, 34567)
meeting 5 -> (34567, 34987)

meeting 2 -> (12356, 34876)
meeting 3 -> (12387, 34578)

all 3 rooms occupied

meeting 4 -> (12345, 34567)

// basic algo
if all meeting upfront -> sort in ending time and have a priority queue and one by one try adding in the queue if start time of ith meeitng is greater than the ending time of top element in queue

list of meetings scheduled in the increasing order of ending times
when new meeting is to be scheduled, first find the place for the meeting in the schedule list
use to basic algo
if it can be scheduled, insert in the schedule list
else return not possible to schedule
*/


import java.util.*;

class MeetingRoomPractice {

}

class MeetingRoom{
    private final long id;

    public MeetingRoom(long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }
}


class Meeting implements Comparable<Meeting> {
    private final  long id;
    private final long startTime;
    private final long endTime;
    MeetingRoom meetingRoom;

    Meeting( long startTime, long endTime) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
    public MeetingRoom getMeetingRoom(){
        return meetingRoom;
    }
    public long getId(){
        return id;
    }

    @Override
    public int compareTo(Meeting o) {
        return (int) (this.endTime - o.endTime);
    }
}


class ScheduleMeetingService{
    private List<Meeting> meetingList;
    private List<MeetingRoom> meetingRoomList;
    private Map<Long,MeetingRoom> meetingMap;

    public ScheduleMeetingService(List<MeetingRoom> meetingRooms){
        this.meetingRoomList  = meetingRooms;
        this.meetingMap = new HashMap<>();
        for(MeetingRoom meetingRoom: meetingRooms){
            meetingMap.put(meetingRoom.getId(),meetingRoom);
        }
        meetingList = new ArrayList<>();
    }

    public MeetingRoom scheduleMeeting(Meeting meeting){
        return null;
    }




}





