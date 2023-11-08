package com.otavia.stepcapsule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.otavia.stepcapsule.model.domain.Step;
import com.otavia.stepcapsule.service.StepService;
import com.otavia.stepcapsule.mapper.StepMapper;
import org.springframework.stereotype.Service;

/**
* @author huchenkun
* @description 针对表【step(步步信息)】的数据库操作Service实现
* @createDate 2023-10-28 23:08:09
*/
@Service
public class StepServiceImpl extends ServiceImpl<StepMapper, Step>
    implements StepService{

}




