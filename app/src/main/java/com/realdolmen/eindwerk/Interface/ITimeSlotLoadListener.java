package com.realdolmen.eindwerk.Interface;

import com.realdolmen.eindwerk.data.TimeSlot;

import java.util.List;

public interface ITimeSlotLoadListener {
    void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotLisT);
    void onTimeSlotLoadFailed(String message);
    void onTimeSlotLoadEmpty();
}
