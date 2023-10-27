package com.myweb.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.FileHandler;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
public class BoardController {
	//폴더명 : board	/ mapping : board
	//mapping => /board/register
	//목적지 => /board/register	
	
	//BoardService 연결
	
	@Inject
	private BoardService bsv;
	
	//한 곳에서만 써면 굳이 안써도 됨(등록, 삭제 사용)
	@Inject
	private FileHandler fh;
	
	@GetMapping("/register")
	public String register() {
		log.info(">>> start >>>");
		return "/board/register";
	}
	
	//위와 동일
	//void : 같은 곳으로 보내기
	//@GetMapping("/register")
	//public void register() {
	//log.info(">>> start >>>");
	//}
	
	//컨트롤러=>뷰, 뷰=>컨트롤러, ServletConfiguration.java 설정 //OK!
	
	//name="files" : register.jsp의 name files
	@PostMapping("/register")
	//bvo도 @RequestParam으로 받아도 OK, 파라미터 값을 하나씩 받아야 됨
	public String register(BoardVO bvo, RedirectAttributes reAttr, 
			@RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo > files "+bvo+"   "+files);
		List<FileVO> flist = null;
//		List<FileVO> flist = new ArrayList<>();
		//file upload handler 생성
		if(files[0].getSize()>0) {
			//파일 존재하는 경우
			flist = fh.uploadFiles(files);
		}
		
		int isUp = bsv.register(new BoardDTO(bvo, flist));
		reAttr.addFlashAttribute("isUp", isUp);
		return "redirect:/board/list";
	}
	
//	@GetMapping("/list")
//	public String list(Model model) {
//		List<BoardVO> list = bsv.getList();
//		model.addAttribute("list", list);
//		//서비스에서 리턴 값 설정
//		//model.addAttribute("list", bsv.getList());
//		return "/board/list";
//	}
	
	//paging 추가
	@GetMapping("/list")
	public String list(Model model, PagingVO pagingVO) {
		log.info(">>> pagingVO > "+pagingVO);
		
		//파일 개수, 댓글 개수 업데이트
		int isOk = bsv.fcmt();
		isOk = bsv.ccmt();
		
		List<BoardVO> list = bsv.getList(pagingVO);
		model.addAttribute("list", list);
		//서비스에서 리턴 값 설정
		//model.addAttribute("list", bsv.getList());
		
		//페이징 처리
		//총 페이지 갯수 totalCount(검색 포함)
		int totalCount = bsv.getTotalCount(pagingVO);
		PagingHandler ph = new PagingHandler(pagingVO, totalCount);
		model.addAttribute("ph", ph);
		return "/board/list";
	}
	
	//void : 스프링은 jsp에서 컨트롤러로, 컨트롤러에서 jsp로 이동함
	//ServletConfiguration에서 prefix, suffix처리 해서 이동
	@GetMapping({"/detail", "/modify"})
	public void detail(Model model, @RequestParam("bno")long bno) {
		//BoardVO bvo = bsv.getDetail(bno);
		//model.addAttribute("BoardVO", bvo);
		
		//fileVer
		//BoardDTO bdto = bsv.getDetailFile(bno);
		//btdo : 게시글 정보 && 파일 정보 
		
		//!
		model.addAttribute("bdto", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo, RedirectAttributes reAttr,
			@RequestParam(name="files", required = false)MultipartFile[] files) {		
		//파일 처리
		List<FileVO> flist = null;
		if(files[0].getSize()>0) {
			flist = fh.uploadFiles(files); 
		}
		//BoardDTO bdto = new BoardDTO(bvo, flist);
		int isOk = bsv.modifyFiles(new BoardDTO(bvo, flist));
		
		//detail.jsp로 bno 값을 보냄
		//addAttribute : 계속 남아있음
		reAttr.addAttribute("bno", bvo.getBno());
		//addFlashAttribute : 쓸모가 다하면 사라짐
		reAttr.addFlashAttribute("isMod", isOk);
		return "redirect:/board/detail";
		//파라미터로 bno 전송
		//return "redirect:/board/detail?bno="+bvo.getBno();
		
		//리다이렉트 : 게시글 수정 후, 수정된 detail 페이지를 보여주기 위해, bno 값을 가지고 getmapping으로 이동
		//리다이렉트는 데이터를 못가져가기 때문에 parameter로 bno를 보냄
		
		//OK! 그냥 리턴 시 detail 값이 없기 때문에, redirect를 통해 bno 값을 가지고 getmapping으로 이동
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam("bno")long bno, RedirectAttributes reAttr) {
		//여기서 안하고 서비스에서 하기
		//int isOk = bsv.cmtC(bno);
		//isOk = bsv.fileC(bno);
		
		//!
		int isDel = bsv.remove(bno);
		reAttr.addFlashAttribute("isDel", isDel);
		return "redirect:/board/list";
		
	}
	
	@DeleteMapping(value = "/file/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid) {
		return bsv.removeFile(uuid) > 0? 
				new ResponseEntity<String>("1", HttpStatus.OK) : 
					new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	
}
