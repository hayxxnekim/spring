package com.myweb.www;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

//junit 실행 위한 어노테이션(SpringJUnit4ClassRunner 클래스가 도와줌)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ContextConfiguration(classes = {com.myweb.www.config.RootConfig.class})
public class BoardTest {
//db 접속 권한X => 설정
	
	//dao 연결
	@Inject
	private BoardDAO bdao;
	
	@Test
	public void insertBoard() {
		for(int i=0; i<300; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test Title"+i);
			bvo.setWriter("tester");
			//bvo.setWriter("tester"+(int)(Math.random()*30)+1);
			bvo.setContent("Test Content"+i);
			bdao.insert(bvo);
		}
	}
}
