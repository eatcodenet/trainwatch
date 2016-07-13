package net.eatcode.trainwatch.nr;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;

public class TrainMovementTest {

    @Test
    public void shouldCreateTrainMovementsFromJson() {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        TrustMovementMessage[] data = gson.fromJson(
                "[{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231540000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231510000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"87301\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"A\",\"division_code\":\"84\",\"train_terminated\":\"false\",\"train_id\":\"872M48MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"24673405\",\"toc_id\":\"84\",\"loc_stanox\":\"87301\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"87271\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231540000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231570000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"87261\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 8\",\"division_code\":\"84\",\"train_terminated\":\"false\",\"train_id\":\"872G45MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"24673505\",\"toc_id\":\"84\",\"loc_stanox\":\"87261\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"87271\",\"line_ind\":\"S\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231420000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231450000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"3\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"872H97MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"24787000\",\"toc_id\":\"88\",\"loc_stanox\":\"87622\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"87624\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231240000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231210000\",\"timetable_variation\":\"7\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"87801\",\"actual_timestamp\":\"1458231600000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 6\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"871P39MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"24786000\",\"toc_id\":\"88\",\"loc_stanox\":\"87801\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"87703\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231360000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231360000\",\"timetable_variation\":\"3\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"87920\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 5\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"871B39MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"22721000\",\"toc_id\":\"88\",\"loc_stanox\":\"87920\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"87927\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231600000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231570000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 2\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"872D35MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"24743000\",\"toc_id\":\"88\",\"loc_stanox\":\"88246\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"88248\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231300000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231330000\",\"timetable_variation\":\"4\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"5\",\"reporting_stanox\":\"48013\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"21\",\"train_terminated\":\"false\",\"train_id\":\"482S21MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"21897002\",\"toc_id\":\"21\",\"loc_stanox\":\"48013\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"48016\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231480000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"6\",\"reporting_stanox\":\"25389\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"23\",\"train_terminated\":\"false\",\"train_id\":\"252B52MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"11803920\",\"toc_id\":\"23\",\"loc_stanox\":\"25389\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"18498\",\"line_ind\":\"H\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231540000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231510000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"50423\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"33\",\"train_terminated\":\"false\",\"train_id\":\"502W29MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"21381001\",\"toc_id\":\"33\",\"loc_stanox\":\"50423\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"50431\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231544000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231540000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231540000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 2\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"542F02MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"21724000\",\"toc_id\":\"88\",\"loc_stanox\":\"53303\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"3\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"53301\",\"line_ind\":\"H\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231545000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231360000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231360000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"32000\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"14\",\"division_code\":\"20\",\"train_terminated\":\"false\",\"train_id\":\"321S75MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"12246820\",\"toc_id\":\"20\",\"loc_stanox\":\"32000\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"3\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"33087\",\"line_ind\":\"D\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"SKKF\",\"user_id\":\"#QLP2156\",\"original_data_source\":\"SDR\",\"msg_queue_timestamp\":\"1458231545000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231480000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"\",\"reporting_stanox\":\"07224\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"MANUAL\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"60\",\"train_terminated\":\"true\",\"train_id\":\"072A46MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"13568015\",\"toc_id\":\"60\",\"loc_stanox\":\"07224\",\"auto_expected\":\"false\",\"direction_ind\":\"\",\"route\":\"0\",\"planned_event_type\":\"DESTINATION\",\"next_report_stanox\":\"\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231545000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458233610000\",\"timetable_variation\":\"33\",\"original_loc_timestamp\":\"\",\"current_train_id\":null,\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"33\",\"reporting_stanox\":\"37011\",\"actual_timestamp\":\"1458231600000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":\"DLY\",\"platform\":\"\",\"division_code\":\"00\",\"train_terminated\":\"false\",\"train_id\":\"49423LCE17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"54606070\",\"toc_id\":\"00\",\"loc_stanox\":\"37011\",\"auto_expected\":\"true\",\"direction_ind\":\"\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"37003\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231545000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231540000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231540000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"64\",\"train_terminated\":\"false\",\"train_id\":\"382W37MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"12305012\",\"toc_id\":\"64\",\"loc_stanox\":\"38153\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"38151\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231545000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231600000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"88\",\"train_terminated\":\"false\",\"train_id\":\"542F78MQ17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"21724000\",\"toc_id\":\"88\",\"loc_stanox\":\"53227\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"53223\",\"line_ind\":\"S\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231600000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231600000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"65611\",\"actual_timestamp\":\"1458231600000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"29\",\"train_terminated\":\"false\",\"train_id\":\"652C21MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"12259320\",\"toc_id\":\"29\",\"loc_stanox\":\"65611\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"65610\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"29\",\"train_terminated\":\"false\",\"train_id\":\"652R63MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"22272000\",\"toc_id\":\"29\",\"loc_stanox\":\"65625\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"65630\",\"line_ind\":\"D\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231450000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"28\",\"train_terminated\":\"false\",\"train_id\":\"631F45MP17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"22150000\",\"toc_id\":\"28\",\"loc_stanox\":\"57435\",\"auto_expected\":\"true\",\"direction_ind\":\"\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"57403\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231600000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231630000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231600000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 1\",\"division_code\":\"27\",\"train_terminated\":\"false\",\"train_id\":\"771M68MN17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"22333000\",\"toc_id\":\"27\",\"loc_stanox\":\"57712\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"57606\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231570000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"4\",\"reporting_stanox\":\"23221\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"61\",\"train_terminated\":\"false\",\"train_id\":\"161Y88MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"21700001\",\"toc_id\":\"61\",\"loc_stanox\":\"23221\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"23421\",\"line_ind\":\"M\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231600000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231570000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 2\",\"division_code\":\"23\",\"train_terminated\":\"false\",\"train_id\":\"162W81MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"11835920\",\"toc_id\":\"23\",\"loc_stanox\":\"17112\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"17117\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231420000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231420000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"4\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 1\",\"division_code\":\"23\",\"train_terminated\":\"false\",\"train_id\":\"172D67MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"11821020\",\"toc_id\":\"23\",\"loc_stanox\":\"17021\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"17019\",\"line_ind\":\"F\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231480000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1458231600000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 1\",\"division_code\":\"80\",\"train_terminated\":\"false\",\"train_id\":\"891P50MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"24604004\",\"toc_id\":\"80\",\"loc_stanox\":\"89489\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"89501\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"3\",\"reporting_stanox\":\"89566\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"80\",\"train_terminated\":\"false\",\"train_id\":\"891J471S17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"24647005\",\"toc_id\":\"80\",\"loc_stanox\":\"89566\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"3\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"89556\",\"line_ind\":\"U\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231360000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231360000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"13154\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"56\",\"train_terminated\":\"false\",\"train_id\":\"122I06MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"11798001\",\"toc_id\":\"56\",\"loc_stanox\":\"13154\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"13152\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"VDPR\",\"user_id\":\"#QLI6024\",\"original_data_source\":\"SDR\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1458231420000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231390000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"01008\",\"actual_timestamp\":\"1458231360000\",\"correction_ind\":\"false\",\"event_source\":\"MANUAL\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"60\",\"train_terminated\":\"false\",\"train_id\":\"012H64MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"23542003\",\"toc_id\":\"60\",\"loc_stanox\":\"01008\",\"auto_expected\":\"false\",\"direction_ind\":\"\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"01001\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"VDPR\",\"user_id\":\"#QLI6024\",\"original_data_source\":\"SDR\",\"msg_queue_timestamp\":\"1458231546000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231480000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231510000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"10\",\"reporting_stanox\":\"01008\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"MANUAL\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"60\",\"train_terminated\":\"false\",\"train_id\":\"012H64MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"23542003\",\"toc_id\":\"60\",\"loc_stanox\":\"01008\",\"auto_expected\":\"false\",\"direction_ind\":\"\",\"route\":\"\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"01001\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231547000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231420000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231450000\",\"timetable_variation\":\"1\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"5\",\"reporting_stanox\":\"51347\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 1\",\"division_code\":\"79\",\"train_terminated\":\"false\",\"train_id\":\"522R04MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"27936004\",\"toc_id\":\"79\",\"loc_stanox\":\"51347\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"2\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"51344\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231547000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458232140000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458232140000\",\"timetable_variation\":\"11\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"77301\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 4\",\"division_code\":\"25\",\"train_terminated\":\"false\",\"train_id\":\"775C85MS17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"25460001\",\"toc_id\":\"25\",\"loc_stanox\":\"77301\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"77303\",\"line_ind\":\"R\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"VXPM\",\"user_id\":\"#QDP0241\",\"original_data_source\":\"SDR\",\"msg_queue_timestamp\":\"1458231547000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458234240000\",\"timetable_variation\":\"46\",\"original_loc_timestamp\":\"\",\"current_train_id\":null,\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"2\",\"reporting_stanox\":\"85101\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"MANUAL\",\"train_file_address\":\"EJS\",\"platform\":\"\",\"division_code\":\"00\",\"train_terminated\":\"false\",\"train_id\":\"85653ECS17\",\"offroute_ind\":\"false\",\"variation_status\":\"EARLY\",\"train_service_code\":\"59406441\",\"toc_id\":\"00\",\"loc_stanox\":\"85101\",\"auto_expected\":\"false\",\"direction_ind\":\"\",\"route\":\"\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"85099\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231547000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"1458231480000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231480000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"25701\",\"actual_timestamp\":\"1458231480000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 1\",\"division_code\":\"23\",\"train_terminated\":\"false\",\"train_id\":\"551Y40MQ17\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"21865000\",\"toc_id\":\"23\",\"loc_stanox\":\"25701\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"1\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"25635\",\"line_ind\":\"M\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1458231547000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"DEPARTURE\",\"gbtt_timestamp\":\"\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1458231450000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"83441\",\"actual_timestamp\":\"1458231540000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\"\",\"division_code\":\"25\",\"train_terminated\":\"false\",\"train_id\":\"832T22MR17\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"25482001\",\"toc_id\":\"25\",\"loc_stanox\":\"83441\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"3\",\"planned_event_type\":\"DEPARTURE\",\"next_report_stanox\":\"83435\",\"line_ind\":\"E\"}}]",
                TrustMovementMessage[].class);
        TrustMovementMessage tm = data[0];
        System.out.println(data.length);
        System.out.println(tm.header.msg_queue_timestamp);
        System.out.println(tm.header.msg_type);
        System.out.println(tm.body.actual_timestamp);
        System.out.println(tm.body.loc_stanox);
        System.out.println(tm.body.timetable_variation);
        System.out.println(tm.body.train_id);
        System.out.println(tm.body.train_service_code);
        System.out.println(tm.body.train_terminated);
    }
}
