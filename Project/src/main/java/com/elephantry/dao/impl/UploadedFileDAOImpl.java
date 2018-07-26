package com.elephantry.dao.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.elephantry.dao.AbstractGenericDAO;
import com.elephantry.dao.UploadedFileDAO;
import com.elephantry.entity.UploadedFile;

@Repository
@Transactional
public class UploadedFileDAOImpl extends AbstractGenericDAO<UploadedFile, Integer> implements UploadedFileDAO{

}
