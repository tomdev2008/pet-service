package com.momoplan.pet.framework.bbs.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.framework.bbs.service.CacheKeysConstance;

@Repository
public class NoteSubRepository implements CacheKeysConstance{

	private static Logger logger = LoggerFactory.getLogger(NoteSubRepository.class);
	private static Gson gson = new Gson();

	private RedisPool redisPool= null;
	private MapperOnCache mapperOnCache = null;
	private NoteMapper noteMapper = null;
	private NoteSubMapper noteSubMapper = null;
	
	@Autowired
	public NoteSubRepository(RedisPool redisPool, MapperOnCache mapperOnCache, NoteMapper noteMapper, NoteSubMapper noteSubMapper) {
		super();
		this.redisPool = redisPool;
		this.mapperOnCache = mapperOnCache;
		this.noteMapper = noteMapper;
		this.noteSubMapper = noteSubMapper;
	}

	/**
	 * 回帖
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(NoteSub po)throws Exception{
		//入库并加入缓存
		mapperOnCache.insertSelective(po, po);
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			//增加到 回帖总数列表
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, po.getNoteId());
			String forumId = note.getForumId();
			//forumId 对应栏目的 回帖总数
			jedis.lpush(LIST_NOTE_SUB_TOTALCOUNT+forumId, "n");
			//需求是：最新的回帖排在最下面,【队列】结构
			rpushListNoteSub(jedis,po);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	/**
	 * 需求是：最新的回帖排在最下面,【队列】结构
	 * @param po
	 */
	private void rpushListNoteSub(ShardedJedis jedis,NoteSub po) throws Exception {
		String key = LIST_NOTE_SUB+po.getNoteId();
		try {
			//init key 对应的队列
			if(!jedis.exists(key)||jedis.llen(key)==0){
				initListNoteSub(jedis,po,key);
			}
			NoteSub noteSub = mapperOnCache.selectByPrimaryKey(po.getClass(), po.getId());
			String noteSubJson = gson.toJson(noteSub);
			jedis.lpush(key,noteSubJson);
			logger.debug(key+" lpush "+noteSubJson);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 初始化缓存堆，如果手动清除了缓存，此处可以重新构建
	 * @param jedis
	 * @param po
	 * @param key
	 */
	private void initListNoteSub(ShardedJedis jedis,NoteSub po,String key){
		NoteSubCriteria noteCriteria = new NoteSubCriteria();
		NoteSubCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andNoteIdEqualTo(po.getNoteId());
		noteCriteria.setOrderByClause("ct asc");
		List<NoteSub> noteList = noteSubMapper.selectByExample(noteCriteria);
		logger.info("初始化 回帖 缓存队列 key="+key);
		logger.info("初始化 回帖 缓存队列 noteSubList.size="+noteList.size());
		String[] arr = new String[noteList.size()];
		int i=0;
		for(NoteSub note : noteList){
			String json = gson.toJson(note);
			arr[i++] = json;
			logger.debug(key+" rpush "+json);
		}
		jedis.lpush(key, arr);
		logger.info("初始化 回帖 缓存队列 完成.");
	}	
	
	/**
	 * 指定帖子 总回帖数
	 * @return
	 */
	public Long totalCount(String forumId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			Long total = 0L;
			try{
				total = jedis.llen(LIST_NOTE_SUB_TOTALCOUNT+forumId);
			}catch(Exception e){}
			if(total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("帖子总数 缓存取值 : "+total);
				return total;
			}
			NoteCriteria noteCriteria = new NoteCriteria();
			noteCriteria.createCriteria().andForumIdEqualTo(forumId);
			List<Note> noteList = noteMapper.selectByExample(noteCriteria);
			List<String> noteIds = new ArrayList<String>();
			if(noteList!=null&&noteList.size()>0)
			for(Note note : noteList){
				noteIds.add(note.getId());
			}
			int count = -1;
			if(noteIds.size()>0){
				NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
				noteSubCriteria.createCriteria().andNoteIdIn(noteIds);
				count = noteSubMapper.countByExample(noteSubCriteria);
				logger.debug("初始化 回帖总数 数据库取值 : "+count);
				String[] array = new String[count];
				for(int i=0;i<count;i++){
					array[i] = "n";
				}
				if(array!=null&&array.length>0)
					jedis.lpush(LIST_NOTE_SUB_TOTALCOUNT+forumId, array);
			}
			return Long.valueOf(count);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			redisPool.closeConn(jedis);
		}
		return -1L;
	}
	
}
