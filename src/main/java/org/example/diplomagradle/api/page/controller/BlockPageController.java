package org.example.diplomagradle.api.page.controller;

import org.example.diplomagradle.api.page.dto.BlockPageDto;
import org.example.diplomagradle.service.page.PageServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@Controller
public class BlockPageController {
    private final PageServiceInterface pageServiceInterface;

    public BlockPageController(PageServiceInterface pageServiceInterface) {
        this.pageServiceInterface = pageServiceInterface;
    }

    @GetMapping("/block/{blockId}")
    @PreAuthorize("@accessChecker.canViewBlock(authentication, #blockId)")
    public String blockPage(@PathVariable Long blockId,
                            @RequestParam(required = false) Integer year,
                            @RequestParam(required = false) Integer month,
                            Model model) {
        YearMonth now = YearMonth.now();
        Integer y = year != null ? year : now.getYear();
        Integer m = month != null ? month : now.getMonthValue();

        BlockPageDto block = pageServiceInterface.getBlockPageData(blockId, y, m);

        model.addAttribute("block", block);
        model.addAttribute("year", y);
        model.addAttribute("month", m);

        return "block";
    }
}
