package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物模型
 */
@Data
public class TslModel implements Serializable {

    private TslProfile profile;
    private List<TslServices> services;
    private List<TslProperties> properties;
    private List<TslEvent> events;


}
