package com.example.springinfluxdbexample.util;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "memory")
public class MemoryPoint {

    @Column(name = "time")
    private Instant time;

    @Column(name = "name")
    private String name;

    @Column(name = "free")
    private Long free;

    @Column(name = "used")
    private Long used;

    @Column(name = "buffer")
    private Long buffer;

    @Override
    public String toString() {
        return "MemoryPoint{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", free=" + free +
                ", used=" + used +
                ", buffer=" + buffer +
                '}';
    }
}
