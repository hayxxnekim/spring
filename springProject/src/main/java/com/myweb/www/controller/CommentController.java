package com.myweb.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comment/*")
@Slf4j
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	@PostMapping(value = "/post", consumes = "application/json",
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo) {
		log.info(">>> cvo > "+cvo);
		/*
		 * int isOk = csv.post(cvo); return isOk>0 ? new ResponseEntity<String>("1",
		 * HttpStatus.OK) : new ResponseEntity<String>("0",
		 * HttpStatus.INTERNAL_SERVER_ERROR);
		 */
		return csv.post(cvo) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//page 값도 같이 가져옴
	@GetMapping(value = "/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> spread(@PathVariable("bno") long bno,
			@PathVariable("page") int page) {
		log.info(">>> bno / page > "+bno+"   "+page);
		//5 : qty(5개씩 보여줌)
		PagingVO pgvo = new PagingVO(page, 5);
		return new ResponseEntity<PagingHandler>(
				csv.getList(bno, pgvo), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{cno}")
	public ResponseEntity<String> delete(@PathVariable("cno")long cno) {
		return csv.delete(cno) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//consumes : 들어오는 값, produces : 나가는 값(것)
	@PutMapping(value="/{cno}", consumes = "application/json",
			produces = MediaType.TEXT_PLAIN_VALUE) 
	public ResponseEntity<String> edit(@PathVariable("cno") long cno, @RequestBody CommentVO cvo) {
		return csv.modify(cvo) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
