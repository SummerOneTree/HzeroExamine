package org.hzero.todo.api.controller.v1;

import org.hzero.todo.domain.entity.Company;
import org.hzero.todo.app.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date 2020/9/2 19:20
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * 查询所有的Company信息
     * @return List<Company>
     */
    @GetMapping("/list")
    public List<Company> list() {
        return companyService.getAllCompany();
    }

}
