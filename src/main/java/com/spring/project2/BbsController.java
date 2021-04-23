package com.spring.project2;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BbsController {
	
	@Autowired
	BbsDAO dao;
	
    //게시글 등록
	@ResponseBody  //데이터를 보내고 난 후 ajax success문을 수행하기 위해 다시 보냄
	@RequestMapping("bbsinsert")
	public void insert(BbsVO bbsVO){
		dao.create(bbsVO);
	}
	
	//게시글 하나 불러오기
	@RequestMapping("bbscontent")
	public void select(BbsVO bbsVO, Model model) throws Exception {
		BbsVO bbsvo = dao.read(bbsVO);
		model.addAttribute("bbsVO", bbsvo );	
	}
	
	//게시글 개수 세기
	@RequestMapping("bbscount")
	@ResponseBody
	public int selectall() throws Exception {
		return dao.readall();
		
	}
	
	//페이징
	@RequestMapping("pagedata")
	@ResponseBody
	public List<BbsVO> selectpage(int page) throws Exception {
		int start = page*8-7; //선택한 페이지에 넣을 첫번째 데이터의 넘버 (행넘버)
		int end = page*8;  //선택한 페이지에 넣을 마지막 데이터의 넘버
		ArrayList<Object> pagelist = new ArrayList<Object>();
		pagelist.add(start);
		pagelist.add(end);
		List<BbsVO> list = dao.readpage(pagelist);
		return list;
		
	}
	
	//bbs 게시글 수정시 기존 데이터를 read
	@RequestMapping("bbsupdate")   
	public void set1(BbsVO bbsVO, Model model ) throws Exception {
		BbsVO bbsvo = dao.read(bbsVO);
		model.addAttribute("bbsVO", bbsvo );
	}
	
	//bbs 게시글 수정 후 데이터 처리
	@RequestMapping("bbsupdate2")   
	@ResponseBody
	public void set2(BbsVO bbsVO ) throws Exception {
		System.out.println(bbsVO.bcontent);
		System.out.println(bbsVO.btitle);
		System.out.println(bbsVO.bid);
		dao.update(bbsVO);
	}
	
	//게시글 삭제
	@RequestMapping("bbsdelete")
	public String delete(BbsVO bbsVO ) throws Exception {
		dao.delete(bbsVO);
		return "redirect:bbsall.jsp";  //뷰가 아닌 bbsall.jsp로 보내기위해 redirect. 그냥 return 스트링은 view를 보여줌 
	}
}