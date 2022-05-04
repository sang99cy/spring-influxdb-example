package com.example.springinfluxdbexample.util;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.List;

public class InfluxDB2Example {

    @Measurement(name = "mem")
    public static class Mem {
        @Column(tag = true)
        String host;
        @Column
        Double used_percent;
        @Column(timestamp = true)
        Instant time;
    }
    public static void main(final String[] args) {

        // You can generate an API token from the "API Tokens Tab" in the UI
        String token = "w1S6T71uFD4LnEC_s8ClJLg4KwD4mhQ8r5anyPbmYSCX-ogprFfIKxo6jgKR6wvOz84nAredPoyUUmi__AIMqA==";
        String bucket = "viettel-bucket";
        String org = "cyber";

        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());


        /*Option 1: Use InfluxDB Line Protocol to write data*/
//        String data = "mem ,host=host1 used_percent=23.43234543";
//
//        WriteApiBlocking writeApi = client.getWriteApiBlocking();
//        writeApi.writeRecord(bucket, org, WritePrecision.NS, data);

        /*Option 2: Use a Data Point to write data*/
//        Point point = Point
//                .measurement("mem")
//                .addTag("host", "host1")
//                .addField("used_percent", 23.43234543)
//                .time(Instant.now(), WritePrecision.NS);
//
//        WriteApiBlocking writeApi1 = client.getWriteApiBlocking();
//        writeApi1.writePoint(bucket, org, point);

        /*Option 3: Use POJO and corresponding class to write data*/
//        Mem mem = new Mem();
//        mem.host = "host1";
//        mem.used_percent = 23.43234543;
//        mem.time = Instant.now();
//
//        WriteApiBlocking writeApi3 = client.getWriteApiBlocking();
//        writeApi3.writeRecord(bucket, org, WritePrecision.NS, String.valueOf(mem));


        String query = "from(bucket: \"viettel-bucket\") |> range(start: -1h)";
        List<FluxTable> tables = client.getQueryApi().query(query, org);

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(record.toString());
            }
        }

        client.close();

    }
}
