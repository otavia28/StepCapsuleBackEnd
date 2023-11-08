package com.otavia.stepcapsule.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 步步信息
 * @TableName step
 */
@TableName(value ="step")
@Data
public class Step implements Serializable {
    /**
     * 步步编号
     */
    @TableId(type = IdType.AUTO)
    private Integer stepId;

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 步步创建时间
     */
    private Date stepCreateTime;

    /**
     * 工作实际完成时间
     */
    private String realTime;

    /**
     * 天气
     */
    private String weather;

    /**
     * 类型
     */
    private Object stepType;

    /**
     * 内容主体
     */
    private String mainContent;

    /**
     * 描述
     */
    private String description;

    /**
     * 心得
     */
    private String thought;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
