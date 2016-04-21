package net.eatcode.trainwatch.movement.trust;

/*
 *  auto generated from sample JSON message
 */
public class TrustMovementMessage {

    public Header header;
    public Body body;

    public TrustMovementMessage(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public TrustMovementMessage() {
        this.header = new Header();
        this.body = new Body();
    }

    @Override
    public String toString() {
        return body.toString();
    }

    public static class Header {
        public String msg_type;
        public String msg_queue_timestamp;
    }

    public static class Body {
        public String actual_timestamp;
        public String auto_expected;
        public String correction_ind;
        public String creation_timestamp;
        public String current_train_id;
        public String d1266_record_number;
        public String delay_monitoring_point;
        public String direction_ind;
        public String division_code;
        public String event_source;
        public String event_type;
        public String gbtt_timestamp;
        public String line_ind;
        public String loc_stanox;
        public String next_report_run_time;
        public String next_report_stanox;
        public String offroute_ind;
        public String origin_dep_timestamp;
        public String original_loc_stanox;
        public String original_loc_timestamp;
        public String planned_event_type;
        public String planned_timestamp;
        public String platform;
        public String train_uid;
        public String reporting_stanox;
        public String route;
        public String sched_origin_stanox;
        public String schedule_end_date;
        public String schedule_source;
        public String schedule_start_date;
        public String schedule_type;
        public String schedule_wtt_id;
        public String timetable_variation;
        public String toc_id;
        public String tp_origin_stanox;
        public String tp_origin_timestamp;
        public String train_call_mode;
        public String train_call_type;
        public String train_file_address;
        public String train_id;
        public String train_service_code;
        public String train_terminated;
        public String variation_status;

        @Override
        public String toString() {
            return "Body [actual_timestamp=" + actual_timestamp + ", auto_expected=" + auto_expected
                    + ", correction_ind=" + correction_ind + ", creation_timestamp=" + creation_timestamp
                    + ", current_train_id=" + current_train_id + ", d1266_record_number=" + d1266_record_number
                    + ", delay_monitoring_point=" + delay_monitoring_point + ", direction_ind=" + direction_ind
                    + ", division_code=" + division_code + ", event_source=" + event_source + ", event_type="
                    + event_type + ", gbtt_timestamp=" + gbtt_timestamp + ", line_ind=" + line_ind + ", loc_stanox="
                    + loc_stanox + ", next_report_run_time=" + next_report_run_time + ", next_report_stanox="
                    + next_report_stanox + ", offroute_ind=" + offroute_ind + ", origin_dep_timestamp="
                    + origin_dep_timestamp + ", original_loc_stanox=" + original_loc_stanox
                    + ", original_loc_timestamp=" + original_loc_timestamp + ", planned_event_type="
                    + planned_event_type + ", planned_timestamp=" + planned_timestamp + ", platform=" + platform
                    + ", train_uid=" + train_uid + ", reporting_stanox=" + reporting_stanox + ", route=" + route
                    + ", sched_origin_stanox=" + sched_origin_stanox + ", schedule_end_date=" + schedule_end_date
                    + ", schedule_source=" + schedule_source + ", schedule_start_date=" + schedule_start_date
                    + ", schedule_type=" + schedule_type + ", schedule_wtt_id=" + schedule_wtt_id
                    + ", timetable_variation=" + timetable_variation + ", toc_id=" + toc_id + ", tp_origin_stanox="
                    + tp_origin_stanox + ", tp_origin_timestamp=" + tp_origin_timestamp + ", train_call_mode="
                    + train_call_mode + ", train_call_type=" + train_call_type + ", train_file_address="
                    + train_file_address + ", train_id=" + train_id + ", train_service_code=" + train_service_code
                    + ", train_terminated=" + train_terminated + ", variation_status=" + variation_status + "]";
        }

    }

    public boolean isActivation() {
        return header.msg_type.equals("0001");
    }
}
