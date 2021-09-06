package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TslServices implements Serializable {
    private String code;
    private String name;
    private String subType;
    private int id;
    private String type;
    private String desc;
    private List<TslOutputData> outputData;
}
