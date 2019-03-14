package com.coalvalue.domain.pojo;

import java.time.Duration;
import java.time.LocalDateTime;

public class TraficIndex{

        LocalDateTime occurTime ;
        Duration duration;

        public LocalDateTime getOccurTime() {
            return occurTime;
        }

        public void setOccurTime(LocalDateTime occurTime) {
            this.occurTime = occurTime;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }
