package net.eatcode.trains.darwin;

import java.util.Objects;

class TrustTrainMovement {

    Header header;
    Body body;

    TrustTrainMovement(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return body.toString();
    }

    static class Header {
        String msg_type;
        String source_dev_id;
        String user_id;
        String original_data_source;
        String msg_queue_timestamp;
        String source_system_id;
    }

    static class Body {
        String event_type;
        String gbtt_timestamp;
        String original_loc_stanox;
        String planned_timestamp;
        String timetable_variation;
        String original_loc_timestamp;
        String current_train_id;
        String delay_monitoring_point;
        String next_report_run_time;
        String reporting_stanox;
        String actual_timestamp;
        String correction_ind;
        String event_source;
        String platform;
        String division_code;
        String train_terminated;
        String train_id;
        String offroute_ind;
        String variation_status;
        String train_service_code;
        String toc_id;
        String loc_stanox;
        String auto_expected;
        String direction_ind;
        String route;
        String planned_event_type;
        String next_report_stanox;
        String line_ind;

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