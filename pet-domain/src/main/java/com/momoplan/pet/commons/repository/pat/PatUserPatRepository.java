package com.momoplan.pet.commons.repository.pat;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
/**
 * 用户的赞操作
 * @author liangc
 */
public class PatUserPatRepository implements CacheKeysConstance{
	
	private static Logger logger = LoggerFactory.getLogger(PatUserPatRepository.class);
	
	private StorePool storePool= null;
	
	private MapperOnCache mapperOnCache = null;
	private Gson gson = MyGson.getInstance();
	private PatUserPatMapper patUserPatMapper = null;
	
	@Autowired
	public PatUserPatRepository(StorePool storePool, MapperOnCache mapperOnCache, PatUserPatMapper patUserPatMapper) {
		super();
		this.storePool = storePool;
		this.mapperOnCache = mapperOnCache;
		this.patUserPatMapper = patUserPatMapper;
	}
	
	public String getCacheKey(PatUserPat po){
		//用户ID+来源ID，可以确定一个 pat,缓存KEY可以做成 key=title:userid:srcid:patid
		String userId = po.getUserid();//用户ID
		String srcId = po.getSrcId();//来源ID
		String patId = po.getId();//赞的ID
		String key = USER_PAT+userId+":"+srcId+":"+patId;
		return key;
	}
	
	/**
	 * 返回的是 赞列表 ，一个 json 列表
	 * @param srcId
	 * @return
	 * @throws Exception
	 */
	public List<String> getPatUserListBySrcId(String srcId)throws Exception{
		PatUserPat keyParam = new PatUserPat();
		keyParam.setSrcId(srcId);
		keyParam.setUserid("");
		keyParam.setId("");
		String key = getCacheKey(keyParam);
		try{
			Set<String> keys = storePool.keys(key);
			if(keys!=null&&keys.size()>0){
				String[] keyArr = keys.toArray(new String[keys.size()]);
				List<String> list = storePool.get(keyArr);
			}
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}
		return null;
	}
	
	/**
	 * 发动态
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(PatUserPat po)throws Exception{
		mapperOnCache.insertSelective(po, po.getId());
		String key = getCacheKey(po);
		try{
			String val = gson.toJson(po);
			logger.debug("赞:key="+key+" ; val="+val);
			storePool.set(key, val);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}
	}
	
	public void delete(String id) throws Exception{
		PatUserPat po = mapperOnCache.selectByPrimaryKey(PatUserPat.class, id);
		mapperOnCache.deleteByPrimaryKey(PatUserPat.class, id);
		//用户ID+来源ID，可以确定一个 pat,缓存KEY可以做成 key=title:userid:srcid:patid
		String key = getCacheKey(po);
		try{
			logger.debug("删除赞:key="+key);
			storePool.del(key);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("delete",e);
		}
	}
	
	
}
