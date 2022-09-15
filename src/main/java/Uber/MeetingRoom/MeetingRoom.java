package Uber.MeetingRoom;

import java.security.InvalidParameterException;
import java.util.*;

class MeetingRoom {
    private final long roomId;

    public MeetingRoom(long id) {
        this.roomId = id;
    }

    public long getRoomId() {
        return this.roomId;
    }
}

class Meeting implements Comparable<Meeting> {
    private final long id;
    private MeetingRoom meetingRoom;
    private final long startTimestampMillis;
    private final long endTimestampMillis;

    public Meeting(long startTimestampMillis, long endTimestampMillis) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.startTimestampMillis = startTimestampMillis;
        this.endTimestampMillis = endTimestampMillis;
    }

    public long getStartTimestampMillis() {
        return this.startTimestampMillis;
    }

    public long getEndTimestampMillis() {
        return this.endTimestampMillis;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public void getMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public MeetingRoom getMeetingRoom() {
        return this.meetingRoom;
    }

    @Override
    public int compareTo(Meeting other) {
        return (int) (this.endTimestampMillis - other.getEndTimestampMillis());
    }
}

class ScheduleMeetingService {
    private List<MeetingRoom> meetingRooms;
    private Map<Long, MeetingRoom> meetingRoomsMap;
    private List<Meeting> meetingSchedule;

    public ScheduleMeetingService(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
        this.meetingRoomsMap = new HashMap<>();
        for (MeetingRoom meetingRoom : meetingRooms) {
            meetingRoomsMap.put(meetingRoom.getRoomId(), meetingRoom);
        }
        this.meetingSchedule = new ArrayList<>();
    }

    public MeetingRoom scheduleMeeting(Meeting meeting) {
        // find the position if meeting in the meetingSchedule
        // use binary search
        int meetingScheduleIndex = findMeetingPosition(meeting);
        System.out.println("Meeting index = " + meetingScheduleIndex);
        if (meetingScheduleIndex == -1) {
            meetingSchedule.add(meeting);
        } else {
            meetingSchedule.add(meetingScheduleIndex, meeting);
        }
        // assign room
        MeetingRoom meetingRoom = assignRoomForMeeting(meeting, meetingScheduleIndex);
        if (meetingRoom == null)    meetingSchedule.remove(meetingScheduleIndex);
        return meetingRoom;
    }

    private MeetingRoom assignRoomForMeeting(Meeting meeting, int meetingScheduleIndex) {
        PriorityQueue<Meeting> pq = new PriorityQueue();
        Set<Long> availableMeeingRooms = new HashSet<>();
        for (MeetingRoom meetingRoom : meetingRooms) {
            availableMeeingRooms.add(meetingRoom.getRoomId());
        }
        for (int i = 0; i < meetingScheduleIndex; i++) {
            while (pq.size() > 0 && pq.peek().getEndTimestampMillis() < meetingSchedule.get(i).getStartTimestampMillis()) {
                Meeting cur = pq.poll();
                availableMeeingRooms.add(cur.getMeetingRoom().getRoomId());
            }
            availableMeeingRooms.remove(meetingSchedule.get(i).getMeetingRoom().getRoomId());
        }
        for (int i = meetingScheduleIndex+1; i < meetingSchedule.size() && meetingSchedule.get(i).getStartTimestampMillis() < meeting.getEndTimestampMillis(); i++) {
            while (pq.size() > 0 && pq.peek().getEndTimestampMillis() < meetingSchedule.get(i).getStartTimestampMillis()) {
                Meeting cur = pq.poll();
                availableMeeingRooms.add(cur.getMeetingRoom().getRoomId());
            }
            if (meetingSchedule.get(i).getMeetingRoom() != null) {
                availableMeeingRooms.remove(meetingSchedule.get(i).getMeetingRoom().getRoomId());
            }
        }
        if (availableMeeingRooms.size() > 0) {
            // means room is available
            for (long meetingRoomId : availableMeeingRooms) {
                meeting.setMeetingRoom(meetingRoomsMap.get(meetingRoomId));
                break;
            }
            return meeting.getMeetingRoom();
        } else {
            return null;
        }
    }

    private int findMeetingPosition(Meeting meeting) {
        int low = -1, high = meetingSchedule.size();
        while (high > low + 1) {
            int mid = low + (high - low)/2;
            if (meetingSchedule.get(mid).getEndTimestampMillis() > meeting.getStartTimestampMillis()) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return low;
    }
}

class ScheduleMeetingActivity {

    // singleton in prod and use dependency injection or singleton pattern
    private ScheduleMeetingService scheduleMeetingService;

    public ScheduleMeetingActivity() {
        List<MeetingRoom> meetingRooms = new ArrayList<>();
        meetingRooms.add(new MeetingRoom(1));
        meetingRooms.add(new MeetingRoom(2));
        meetingRooms.add(new MeetingRoom(3));
        this.scheduleMeetingService = new ScheduleMeetingService(meetingRooms);
    }

    public void scheduleMeeting(long meetingStartTimestampMillis, long meetingEndTimestampMillis) {
        // validate the input
        if (!validateInput(meetingStartTimestampMillis, meetingEndTimestampMillis)) throw new InvalidParameterException("");
        // try to schedule meeting
        Meeting meeting = new Meeting(meetingStartTimestampMillis, meetingEndTimestampMillis);
        MeetingRoom meetingRoom = scheduleMeetingService.scheduleMeeting(meeting);
        if (meetingRoom != null) {
            System.out.println("Meeting scheduled: " + meetingRoom.getRoomId());
        } else {
            System.out.println("Meeting cannot be scheduled.");
        }
    }

    /**
     1. meetingStartTimestampMillis <= meetingEndTimestampMillis
     2. meetingStartTimestampMillis >= current system time
     */
    boolean validateInput(long meetingStartTimestampMillis, long meetingEndTimestampMillis) {
        return true;
    }
}

// Main class should be named 'Solution'
class Solution {
    private static ScheduleMeetingActivity scheduleMeetingActivity = new ScheduleMeetingActivity();
    public static void main(String[] args) {
        System.out.println("Hello, World");
        scheduleMeetingActivity.scheduleMeeting(12345, 34567);
        scheduleMeetingActivity.scheduleMeeting(34567, 34987);
        scheduleMeetingActivity.scheduleMeeting(12356, 34876);
        scheduleMeetingActivity.scheduleMeeting(12387, 34578);
        scheduleMeetingActivity.scheduleMeeting(12345, 34567);
    }
}


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
if all meeting upfront -> sort in ending time and have a qirority queue and one by one try adding in the queue if start time of ith meeitng is greater than the ending time of top element in queue

list of meetings scheduled in the increasing order of ending times
when new meeting is to be scheduled, first find the place for the meeting in the schedule list
use to basic algo
if it can be scheduled, insert in the schedule list
else return not possible to schedule
*/
