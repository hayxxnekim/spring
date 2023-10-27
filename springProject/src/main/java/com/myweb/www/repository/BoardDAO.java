package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pagingVO);

	BoardVO getDetail(long bno);

	int update(BoardVO bvo);

	//두개의 파라미터를 인식하게 하기 위해선 어노테이션 param 필수
	void readCount(@Param("bno") long bno, @Param("cnt") int cnt);

	int remove(long bno);

	int getTotalCount(PagingVO pagingVO);

	long selectOneBno();

	int fcmt();

	int ccmt();

	int updateCommentCount();

	int updateFileCount();

}
