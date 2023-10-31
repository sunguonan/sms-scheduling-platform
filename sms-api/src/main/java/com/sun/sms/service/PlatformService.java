package com.sun.sms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.sms.entity.PlatformEntity;

/**
 * 平台表
 *
 * @author IT李老师
 */
public interface PlatformService extends IService<PlatformEntity> {

    PlatformEntity getByAccessKeyId(String accessKeyId);
}
