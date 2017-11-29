package com.ucmo.virtualclassroom.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ucmo.virtualclassroom.model.AcademicWikiForm;
import com.ucmo.virtualclassroom.model.AcademicWikiModel;
import com.ucmo.virtualclassroom.model.Success;
import com.ucmo.virtualclassroom.service.WikiService;
@RestController
public class AcademicWikiController {
	
	@Autowired
	private WikiService service;
	
	@Autowired
	private Environment env;
	
	
	
	@RequestMapping(value = "/classroom/getWikiList", method = RequestMethod.GET)
	public List<AcademicWikiModel>  getWikiList() {
	
	
		return service.getWikiList();
	}
	
	@RequestMapping(value = "/classroom/submitUpload", method = RequestMethod.POST)
	public Success  submitUpload(@ModelAttribute AcademicWikiForm request) {
		Success response = new Success();
		try {
			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Or whatever format fits best your needs.
			String dateStr = sdf.format(date);
			AcademicWikiModel model = new AcademicWikiModel();
			model.setArticleName(request.getArticleName());
			model.setResource(request.getResource());
			model.setStudentName(request.getStudentName());
			model.setDate(dateStr);
			boolean success =service.saveWiki(model);
			response.setSuccess(success);
		} catch (Exception e) {
			response.setSuccess(false);
		}
		ModelAndView mav =null;
		mav=new ModelAndView("academicWikiPage");
		mav.addObject("academicList", service.getWikiList());
		
		mav.addObject("message", "yes");
		return response;
	}
	
	
	public Success  delete(@RequestParam("id") Long id) {
		service.delete(id);
		Success response = new Success();
		response.setSuccess(true);
		return response;
	}
	

}
