package com.myweb.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class PagingVO {
	private int pageNo;
	private int qty;
	//검색 처리용
	private String type;
	private String keyword;
	
	//기본 생성자는 가장 위에 작성
	public PagingVO() {
		this(1, 10);
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}

	//limit 시작, qty : 시작 페이지 번호 구하기
	//pageNo 1 2 3 4 
	// 0, 10 / 10,10 / 20,10 ...
	public int getPageStart() {
		return (this.pageNo-1)*qty;
	}
	
	//타입의 형태를 여러가지 형태로 복합적인 검색을 하기 위해서
	//타입의 키워드 t, c, w, tc,cw, tcw
	//복합 타입을 배열로 나누기 위해 사용
	public String[] getTypeToArray() {
		//타입이 null일 경우 빈 배열 리턴, 아닐 경우 한글자씩 배열에 저장
		return this.type == null ? new String[] {} : this.type.split("");
		
	}
}
