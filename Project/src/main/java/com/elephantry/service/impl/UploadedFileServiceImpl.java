package com.elephantry.service.impl;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephantry.dao.UploadedFileDAO;
import com.elephantry.entity.UploadedFile;
import com.elephantry.service.UploadedFileService;
@Service
public class UploadedFileServiceImpl implements UploadedFileService{
	@Autowired
	private UploadedFileDAO uploadedFileDAO;

	@Override
	public Integer save(UploadedFile uploadedFile) {
		Integer result = 0;
		try {
			result = uploadedFileDAO.save(uploadedFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
