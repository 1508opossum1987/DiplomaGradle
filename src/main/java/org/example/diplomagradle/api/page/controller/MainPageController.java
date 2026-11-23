package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.api.page.dto.BlockPageDto;
import org.example.diplomagradle.security.AccessCheckerInterface;
import org.example.diplomagradle.service.page.PageServiceInterface;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.List;

@Controller
public class MainPageController {
    private final PageServiceInterface pageServiceInterface;

    private final AccessCheckerInterface accessCheckerInterface;

    public MainPageController(PageServiceInterface pageServiceInterface,
                              AccessCheckerInterface accessCheckerInterface) {
        this.pageServiceInterface = pageServiceInterface;
        this.accessCheckerInterface = accessCheckerInterface;
    }

    @GetMapping("/")
    public String mainPage(@RequestParam(required = false) Integer year,
                           @RequestParam(required = false) Integer month,
                           Model model, Authentication authentication) {
        YearMonth now = YearMonth.now();
        Integer y = year != null ? year : now.getYear();
        Integer m = month != null ? month : now.getMonthValue();

        List<BlockPageDto> blocks = pageServiceInterface.getMainPageData(y, m).stream()
                .filter(block -> accessCheckerInterface.canViewBlock(authentication, block.id()))
                .toList();
        model.addAttribute("blocks", blocks);
        model.addAttribute("year", y);
        model.addAttribute("month", m);

        return "index";
    }
}
