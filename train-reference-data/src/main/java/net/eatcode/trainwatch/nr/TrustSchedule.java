package net.eatcode.trainwatch.nr;

import java.io.Serializable;

public class TrustSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    public JsonScheduleV1 JsonScheduleV1;

    public static final class JsonScheduleV1 {
        public final CIF_bank_holiday_running CIF_bank_holiday_running;
        public final String CIF_stp_indicator;
        public final String CIF_train_uid;
        public final String applicable_timetable;
        public final String atoc_code;
        public final New_schedule_segment new_schedule_segment;
        public final String schedule_days_runs;
        public final String schedule_end_date;
        public final Schedule_segment schedule_segment;
        public final String schedule_start_date;
        public final String train_status;
        public final String transaction_type;

        public JsonScheduleV1(CIF_bank_holiday_running CIF_bank_holiday_running, String CIF_stp_indicator,
                String CIF_train_uid, String applicable_timetable, String atoc_code,
                New_schedule_segment new_schedule_segment, String schedule_days_runs, String schedule_end_date,
                Schedule_segment schedule_segment, String schedule_start_date, String train_status,
                String transaction_type) {
            this.CIF_bank_holiday_running = CIF_bank_holiday_running;
            this.CIF_stp_indicator = CIF_stp_indicator;
            this.CIF_train_uid = CIF_train_uid;
            this.applicable_timetable = applicable_timetable;
            this.atoc_code = atoc_code;
            this.new_schedule_segment = new_schedule_segment;
            this.schedule_days_runs = schedule_days_runs;
            this.schedule_end_date = schedule_end_date;
            this.schedule_segment = schedule_segment;
            this.schedule_start_date = schedule_start_date;
            this.train_status = train_status;
            this.transaction_type = transaction_type;
        }

        public static final class CIF_bank_holiday_running {

            public CIF_bank_holiday_running() {
            }
        }

        public static final class New_schedule_segment {
            public final String traction_class;
            public final String uic_code;

            public New_schedule_segment(String traction_class, String uic_code) {
                this.traction_class = traction_class;
                this.uic_code = uic_code;
            }
        }

        public static final class Schedule_segment {
            public final String signalling_id;
            public final String CIF_train_category;
            public final String CIF_headcode;
            public final long CIF_course_indicator;
            public final String CIF_train_service_code;
            public final String CIF_business_sector;
            public final String CIF_power_type;
            public final String CIF_timing_load;
            public final String CIF_speed;
            public final String CIF_operating_characteristics;
            public final String CIF_train_class;
            public final CIF_sleepers CIF_sleepers;
            public final String CIF_reservations;
            public final CIF_connection_indicator CIF_connection_indicator;
            public final String CIF_catering_code;
            public final String CIF_service_branding;
            public final Schedule_location schedule_location[];

            public Schedule_segment(String signalling_id, String CIF_train_category, String CIF_headcode,
                    long CIF_course_indicator, String CIF_train_service_code, String CIF_business_sector,
                    String CIF_power_type, String CIF_timing_load, String CIF_speed,
                    String CIF_operating_characteristics, String CIF_train_class, CIF_sleepers CIF_sleepers,
                    String CIF_reservations, CIF_connection_indicator CIF_connection_indicator,
                    String CIF_catering_code, String CIF_service_branding, Schedule_location[] schedule_location) {
                this.signalling_id = signalling_id;
                this.CIF_train_category = CIF_train_category;
                this.CIF_headcode = CIF_headcode;
                this.CIF_course_indicator = CIF_course_indicator;
                this.CIF_train_service_code = CIF_train_service_code;
                this.CIF_business_sector = CIF_business_sector;
                this.CIF_power_type = CIF_power_type;
                this.CIF_timing_load = CIF_timing_load;
                this.CIF_speed = CIF_speed;
                this.CIF_operating_characteristics = CIF_operating_characteristics;
                this.CIF_train_class = CIF_train_class;
                this.CIF_sleepers = CIF_sleepers;
                this.CIF_reservations = CIF_reservations;
                this.CIF_connection_indicator = CIF_connection_indicator;
                this.CIF_catering_code = CIF_catering_code;
                this.CIF_service_branding = CIF_service_branding;
                this.schedule_location = schedule_location;
            }

            public static final class CIF_sleepers {

                public CIF_sleepers() {
                }
            }

            public static final class CIF_connection_indicator {

                public CIF_connection_indicator() {
                }
            }

            public static final class Schedule_location {
                public final String location_type;
                public final String record_identity;
                public final String tiploc_code;
                public final String departure;
                public final String public_departure;
                public final String platform;
                public final String line;
                public final String pass;
                public final String arrival;
                public final String public_arrival;
                public final String performance_allowance;
                public final String path;
                public final String engineering_allowance;

                public Schedule_location(String location_type, String record_identity, String tiploc_code,
                        String departure, String public_departure, String platform, String line, String pass,
                        String arrival, String public_arrival, String performance_allowance, String path,
                        String engineering_allowance) {
                    this.location_type = location_type;
                    this.record_identity = record_identity;
                    this.tiploc_code = tiploc_code;
                    this.departure = departure;
                    this.public_departure = public_departure;
                    this.platform = platform;
                    this.line = line;
                    this.pass = pass;
                    this.arrival = arrival;
                    this.public_arrival = public_arrival;
                    this.performance_allowance = performance_allowance;
                    this.path = path;
                    this.engineering_allowance = engineering_allowance;
                }
            }
        }
    }
}