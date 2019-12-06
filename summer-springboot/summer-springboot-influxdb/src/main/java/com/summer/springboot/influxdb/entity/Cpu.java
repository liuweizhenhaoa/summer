package com.summer.springboot.influxdb.entity;
import io.micrometer.core.annotation.Timed;
import lombok.Data;
import lombok.experimental.Accessors;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;


import java.time.Instant;

@Measurement(name = "cpu")
@Data
@Accessors(chain = true)
public class Cpu {
    @TimeColumn
    @Column(name = "time")
    private Instant time;
    @Column(name = "host" , tag = true)
    private String hostname;
    @Column(name = "region", tag = true)
    private String region;
    @Column(name = "idle")
    private Double idle;
    @Column(name = "happydevop")
    private Boolean happydevop;
    @Column(name = "uptimesecs")
    private Long uptimeSecs;
}