package com.realdolmen.eindwerk.Interface;

import com.realdolmen.eindwerk.data.Salon;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSuccess (List<Salon> salonList);
    void onBranchLoadFailed(String message);
}
