package net.eatcode.trainwatch.movement;

public class TrustTrainMovement {

    public Header header;
    public Body body;

    TrustTrainMovement(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return body.toString();
    }

    public static class Header {
        public String msg_type;
        public String source_dev_id;
        public String user_id;
        public String original_data_source;
        public String msg_queue_timestamp;
        public String source_system_id;
    }

    public static class Body {
        public String event_type;
        public String gbtt_timestamp;
        public String original_loc_stanox;
        public String planned_timestamp;
        public String timetable_variation;
        public String original_loc_timestamp;
        public String current_train_id;
        public String delay_monitoring_point;
        public String next_report_run_time;
        public String reporting_stanox;
        public String actual_timestamp;
        public String correction_ind;
        public String event_source;
        public String platform;
        public String division_code;
        public String train_terminated;
        public String train_id;
        public String offroute_ind;
        public String variation_status;
        public String train_service_code;
        public String toc_id;
        public String loc_stanox;
        public String auto_expected;
        public String direction_ind;
        public String route;
        public String planned_event_type;
        public String next_report_stanox;
        public String line_ind;

        @Override
        public String toString() {
            return "Body{" +
                    "event_type='" + event_type + '\'' +
                    ", gbtt_timestamp='" + gbtt_timestamp + '\'' +
                    ", original_loc_stanox='" + original_loc_stanox + '\'' +
                    ", planned_timestamp='" + planned_timestamp + '\'' +
                    ", timetable_variation='" + timetable_variation + '\'' +
                    ", original_loc_timestamp='" + original_loc_timestamp + '\'' +
                    ", current_train_id='" + current_train_id + '\'' +
                    ", delay_monitoring_point='" + delay_monitoring_point + '\'' +
                    ", next_report_run_time='" + next_report_run_time + '\'' +
                    ", reporting_stanox='" + reporting_stanox + '\'' +
                    ", actual_timestamp='" + actual_timestamp + '\'' +
                    ", correction_ind='" + correction_ind + '\'' +
                    ", event_source='" + event_source + '\'' +
                    ", platform='" + platform + '\'' +
                    ", division_code='" + division_code + '\'' +
                    ", train_terminated='" + train_terminated + '\'' +
                    ", train_id='" + train_id + '\'' +
                    ", offroute_ind='" + offroute_ind + '\'' +
                    ", variation_status='" + variation_status + '\'' +
                    ", train_service_code='" + train_service_code + '\'' +
                    ", toc_id='" + toc_id + '\'' +
                    ", loc_stanox='" + loc_stanox + '\'' +
                    ", auto_expected='" + auto_expected + '\'' +
                    ", direction_ind='" + direction_ind + '\'' +
                    ", route='" + route + '\'' +
                    ", planned_event_type='" + planned_event_type + '\'' +
                    ", next_report_stanox='" + next_report_stanox + '\'' +
                    ", line_ind='" + line_ind + '\'' +
                    '}';
        }
    }
}
