package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.FileVO;

public interface FileDAO {

	int insertFile(FileVO fvo);

	List<FileVO> getFileList(long bno);

	int removeFile(String uuid);

	int fileC(long bno);

	int removeFileAll(long bno);

	List<FileVO> selectListAllFiles();

}