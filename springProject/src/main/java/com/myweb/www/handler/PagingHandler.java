//231023 수정

package com.myweb.www.handler;

import java.util.List;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//setter 없어도 됨
@Getter
@Setter
@ToString
public class PagingHandler {
	//1~10 / 11~20 / 21~30 ...
	//화면에 보여지는 시작 페이지네이션 번호
	private int startPage;
	private int endPage;
	private int realEndPage;
	//이전, 다음의 존재 여부
	private boolean prev, next;
	//총 글 수
	private int totalCount;
	private PagingVO pgvo;
	
	//231023
	private List<CommentVO> cmtList;
	
	//현재 페이지 값 가져오는 용도 / totalCount DB에서 조회 매개변수로 입력받기
	public PagingHandler(PagingVO pgvo, int totalCount) {
		//pgvo.qty : 한페이지에 보이는 게시글의 수 10개
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		//1~10 / 11~20 / 21~30 ...
		//10, 20 , 30
		//1페이지부터 10페이지까지 어떤 페이지가 선택되도 EndPage는 10
		//1 2 3 4 ... 10 / 10 나머지를 올려(ceil) 1로 만듦 * 10 
		//화면에 표시되어야 하는 페이지 개수(1 2 3 4 5 6 7 8 9 10) => 10개(위의 pgvo.qty와 다름)
		this.endPage = 
				(int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty())*pgvo.getQty();
		
		//1, 11, 21
		this.startPage = this.endPage-9;
		
		//전체 글 수에서 / 한 페이지에 표시되는 게시글 수 pgvo.getQty() => 올림
		//딱 떨어지지 않는다면 한 페이지 추가(올림)
		this.realEndPage = 
				(int)Math.ceil(totalCount/(double)pgvo.getQty());
		
		if(realEndPage<endPage) {
			this.endPage = realEndPage;		
		}
		
		//startPage : 1, 11, 21
		this.prev = this.startPage>1;
		this.next = this.endPage<realEndPage;
	}
	
	//231023 리스트 추가
	public PagingHandler(PagingVO pgvo, int totalCount,
			List<CommentVO> cmtList) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
}
