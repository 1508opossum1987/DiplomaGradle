package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.api.page.dto.BlockPageDto;
import org.example.diplomagradle.service.page.PageServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.List;

@Controller
public class MainPageController {
    private final PageServiceInterface pageServiceInterface;

    public MainPageController(PageServiceInterface pageServiceInterface) {
        this.pageServiceInterface = pageServiceInterface;
    }

    @GetMapping("/")
    public String mainPage(@RequestParam(required = false) Integer year,
                           @RequestParam(required = false) Integer month,
                           Model model) {
        YearMonth now = YearMonth.now();
        Integer y = year != null ? year : now.getYear();
        Integer m = month != null ? month : now.getMonthValue();

        List<BlockPageDto> blocks = pageServiceInterface.getMainPageData(y, m);

        model.addAttribute("blocks", blocks);
        model.addAttribute("year", y);
        model.addAttribute("month", m);

        return "index";
    }
}
