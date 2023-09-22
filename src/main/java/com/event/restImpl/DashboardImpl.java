package com.event.restImpl;

import com.event.rest.DashboardRest;
import com.event.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardImpl implements DashboardRest {

    private final DashboardService dashboardService;

    public DashboardImpl(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
    return dashboardService.getCount();

    }
}
