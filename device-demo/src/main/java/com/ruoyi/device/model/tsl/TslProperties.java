package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TslProperties implements Serializable{

    private List<TslSpecs> specs;
    private String code;
    private String dataType;
    private String name;
    private String subType;
    private int id;
    private String type;
    private String desc;

    private Object upValue;
    private Object downValue;

    //-------------拓展属性----start
    private Integer sortNum=0;//排序
    private String icon;
    private String unit;
    private Boolean display;
    private Boolean enableReport;//启用报表
    //-------------拓展属性----end

    //允许控制：true-可以控制 false-不可以控制
    private Boolean enableControl;



}
