package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TslSpecs implements Serializable {
    private int id;
    private String code;
    private List<TslSpecs> specs;
    private String unit;
    private String min;
    private String max;
    private String step;

    private String dataType;
    private String name;
    private String value;

    private int length;

    private Object upValue;
    private Object downValue;
}
