package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;

/**
 * 物模型profile
 */
@Data
public class TslProfile implements Serializable {
    private String productKey;
    private String version;
    private long tsUpdateTime;
    private String tsl_version;
}
