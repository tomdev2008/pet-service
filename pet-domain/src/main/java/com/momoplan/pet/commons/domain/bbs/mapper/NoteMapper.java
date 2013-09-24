package com.momoplan.pet.commons.domain.bbs.mapper;

import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
* NoteMapper
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-24 11:11:36
*/
public interface NoteMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(NoteCriteria noteCriteria);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(NoteCriteria noteCriteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Note note);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Note note);

    /**
     * 根据条件查询记录集
     */
    List<Note> selectByExampleWithBLOBs(NoteCriteria noteCriteria);

    /**
     * 根据条件查询记录集
     */
    List<Note> selectByExample(NoteCriteria noteCriteria);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("note") Note note, @Param("noteCriteria") NoteCriteria noteCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExampleWithBLOBs(@Param("note") Note note, @Param("noteCriteria") NoteCriteria noteCriteria);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("note") Note note, @Param("noteCriteria") NoteCriteria noteCriteria);
}