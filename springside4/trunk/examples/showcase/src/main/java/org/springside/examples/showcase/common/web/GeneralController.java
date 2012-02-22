package org.springside.examples.showcase.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/web")
public class GeneralController {

	@RequestMapping(value = "{domain}/{page}", method = RequestMethod.GET)
	public String delete(@PathVariable("domain") String domain, @PathVariable("page") String page) {
		System.out.println(domain + " / " + page);
		return domain + "/" + page;
	}

}
