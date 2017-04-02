package com.reinard.learnhanzi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.json.HanziJson;
import com.reinard.learnhanzi.models.HanziData;

import org.springframework.beans.factory.annotation.Autowired;

@Service("hanziService")
public class HanziServiceImpl {
//TODO finish this service
//TODO test this service
	
	private static final Logger logger = Logger.getLogger(HanziServiceImpl.class);
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	public HanziJson[] selectAll() throws Exception{
		try{
			List<HanziData> allHanzi = hanziDaoImpl.selectAll();
			
			//convert result to json array:
			List<HanziJson> hanziJsonList = new ArrayList<>();
			for(HanziData hanzi : allHanzi){
				HanziJson hanziJson = new HanziJson();
				hanziJson.setHanzi_id(String.valueOf(hanzi.getHanzi_id()));
				hanziJson.setHanzi(hanzi.getHanzi());
				hanziJsonList.add(hanziJson);
			}
			HanziJson[] result = hanziJsonList.<HanziJson>toArray(new HanziJson[0]);
			return result;
			
		}catch(Exception e){
			logger.error("Exception when trying to select all from \"hanzi_data\" table", e);
			throw e;
		}
	}
	
}
