package com.ruoyi.device.model.tsl;

import java.io.Serializable;
import java.util.List;

public class TslOutputData implements Serializable {
    private List<TslSpecs> specs;
    private String code;
    private String dataType;
    private String name;
    private int id;
    private String $ref;

    public List<TslSpecs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<TslSpecs> specs) {
        this.specs = specs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }
}
