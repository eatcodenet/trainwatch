package net.eatcode.trainwatch.nr;

import java.io.Serializable;

public class TrustSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    public JsonScheduleV1 JsonScheduleV1;

    public static class JsonScheduleV1 {
        public String CIF_stp_indicator;
        public String CIF_train_uid;
        public String applicable_timetable;
        public String atoc_code;
        public New_schedule_segment new_schedule_segment;
        public String schedule_days_runs;
        public String schedule_end_date;
        public Schedule_segment schedule_segment;
        public String schedule_start_date;
        public String CIF_bank_holiday_running;
        public String train_status;
        public String transaction_type;

        public static class New_schedule_segment {
            public String traction_class;
            public String uic_code;
        }

        public static class Schedule_segment {
            public String signalling_id;
            public String CIF_train_category;
            public String CIF_headcode;
            public long CIF_course_indicator;
            public String CIF_train_service_code;
            public String CIF_business_sector;
            public String CIF_power_type;
            public String CIF_timing_load;
            public String CIF_speed;
            public String CIF_operating_characteristics;
            public String CIF_train_class;
            public String CIF_reservations;
            public String CIF_catering_code;
            public String CIF_service_branding;
            public Schedule_location schedule_location[];

            public static class Schedule_location {
                public String location_type;
                public String record_identity;
                public String tiploc_code;
                public String departure;
                public String public_departure;
                public String platform;
                public String line;
                public String pass;
                public String arrival;
                public String public_arrival;
                public String performance_allowance;
                public String path;
                public String engineering_allowance;
            }
        }
    }
}