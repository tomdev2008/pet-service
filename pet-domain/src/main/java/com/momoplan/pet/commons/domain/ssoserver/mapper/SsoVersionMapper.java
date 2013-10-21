package com.momoplan.pet.commons.domain.ssoserver.mapper;

import com.momoplan.pet.commons.domain.ssoserver.po.SsoVersion;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoVersionCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* SsoVersionMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 17:10:57
*/
public interface SsoVersionMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(SsoVersionCriteria ssoVersionCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SsoVersionCriteria ssoVersionCriteria);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(SsoVersion ssoVersion);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(SsoVersion ssoVersion);

    /**
     * 根据条件查询记录集
     */
    List<SsoVersion> selectByExample(SsoVersionCriteria ssoVersionCriteria);

    /**
     * 根据主键查询记录
     */
    SsoVersion selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("ssoVersion") SsoVersion ssoVersion, @Param("ssoVersionCriteria") SsoVersionCriteria ssoVersionCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("ssoVersion") SsoVersion ssoVersion, @Param("ssoVersionCriteria") SsoVersionCriteria ssoVersionCriteria);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(SsoVersion ssoVersion);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SsoVersion ssoVersion);
}