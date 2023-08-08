package com.wanted.preonboarding.common.service.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class KrDateService implements DateService {
    private static final String ZONE_ID = "Asia/Seoul";

    @Override
    public LocalDate getDate() {
        return LocalDate.now(ZoneId.of(ZONE_ID));
    }

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID));
    }
}
