package com.realdolmen.eindwerk.Interface;

import com.realdolmen.eindwerk.data.BookingInformation;

public interface IBookingInfoLoadListener {
    void onBookingInfoLoadEmpty();
    void onBookingInfoLoadSuccess(BookingInformation bookingInformation);
    void onBookingInfoLoadFailed(String message);
}
