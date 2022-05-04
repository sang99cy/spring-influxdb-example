package com.example.springinfluxdbexample.util;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDBExample {
    public static void main(String[] args) {

        InfluxDB connection = InfluxDBFactory.connect("http://localhost:8086",null,null);

        Pong response = connection.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
            return;
        }
        /*Points*/
        Point point = Point.measurement("memory")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("name", "server1")
                .addField("free", 4743656L)
                .addField("used", 1015096L)
                .addField("buffer", 1010467L)
                .build();
        /*Writing Batches*/
//        BatchPoints batchPoints = BatchPoints
//                .database("viettel-bucket")
//                .retentionPolicy("defaultPolicy")
//                .build();
//
//        Point point1 = Point.measurement("memory")
//                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                .addField("name", "server1")
//                .addField("free", 4743656L)
//                .addField("used", 1015096L)
//                .addField("buffer", 1010467L)
//                .build();
//
//        Point point2 = Point.measurement("memory")
//                .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
//                .addField("name", "server1")
//                .addField("free", 4743696L)
//                .addField("used", 1016096L)
//                .addField("buffer", 1008467L)
//                .build();
//
//        batchPoints.point(point1);
//        batchPoints.point(point2);
//        connection.write(batchPoints);

        Query memoryStr = new Query("Select * from memory");
        QueryResult queryResult = connection
                .query(memoryStr, TimeUnit.MILLISECONDS);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<MemoryPoint> memoryPointList = resultMapper
                .toPOJO(queryResult, MemoryPoint.class);
        for (MemoryPoint point1 : memoryPointList){
            System.out.println(point1.toString());
        }
    }
}
