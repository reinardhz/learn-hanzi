package com.reinard.learnhanzi.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reinard.learnhanzi.dao.impl.HanziDaoImpl;
import com.reinard.learnhanzi.json.Hanzi_data;
import com.reinard.learnhanzi.models.HanziData;

import org.springframework.beans.factory.annotation.Autowired;

@Service("hanziService")
public class HanziServiceImpl {
//TODO finish this service
//TODO test this service
	
	private static final Logger logger = Logger.getLogger(HanziServiceImpl.class);
	
	@Autowired
	private HanziDaoImpl hanziDaoImpl;
	
	public Hanzi_data[] selectAll() throws Exception{
		try{
			List<HanziData> allHanzi = hanziDaoImpl.selectAll();
			
			//convert result to json array:
			List<Hanzi_data> hanziJsonList = new ArrayList<>();
			for(HanziData hanzi : allHanzi){
				Hanzi_data hanziJson = new Hanzi_data();
				//hanziJson.setHanzi_id(String.valueOf(hanzi.getHanzi_id()));
				hanziJson.setHanzi(hanzi.getHanzi());
				hanziJson.setCreated_date(hanzi.getCreated_date().toLocaleString());
				hanziJsonList.add(hanziJson);
				
			}
			
			/* //insert data to 2000 (delete this after testing)
			for(int i=1; i<3000; ++i){
				Hanzi_data hanziJson = new Hanzi_data();
				hanziJson.setHanzi("愛");
				hanziJson.setCreated_date(new Timestamp(System.currentTimeMillis()).toLocaleString());
				hanziJsonList.add(hanziJson);
			}*/
			
			Hanzi_data[] result = hanziJsonList.<Hanzi_data>toArray(new Hanzi_data[0]);
			return result;
			
		}catch(Exception e){
			logger.error("Exception when trying to select all from \"hanzi_data\" table", e);
			throw e;
		}
	}
	
}
