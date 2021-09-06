package com.ruoyi.device.model.tsl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TslEvent implements Serializable {
    private List<TslOutputData> outputData;
    private String code;
    private String name;
    private String subType;
    private int id;
    private String type;
    private String desc;

    //------------拓展物模型事件-----start
    private Boolean enableEmailNotice;
    private Boolean enableSmsNotice;
    private Boolean enableVoiceNotice;
    //异常解除的上报值
    private String valueForClear;
    private String codeForClear;
    //异常解除是否需要发送通知
    private Boolean enableNoticeForClear;
    //------------拓展物模型事件------end
}
