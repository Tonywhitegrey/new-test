package com.test.controller;

import com.test.service.AccountInfoService;
import com.test.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

/**
 * TestController
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    /**
     * Inject Account Info Service
     */
    private final AccountInfoService accountInfoService;

    /**
     * Inject Transactions Service
     */
    private final TransactionsService transactionsService;

    /**
     * Forward to account page
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/")
    public ModelAndView home(ModelAndView mv) {
        return index(mv);
    }

    /**
     * Jump to account info page
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/index")
    public ModelAndView index(ModelAndView mv) {
        // Set redirect webpage to index (omit .html)
        mv.setViewName("index");
        // Place all account information query results into the view controller and set the name to list
        mv.addObject("list", accountInfoService.selectAll());
        // Return to View Controller
        return mv;
    }

    /**
     * 跳转到交易信息页面
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/transactions")
    public ModelAndView test2(ModelAndView mv) {
        // 设置跳转网页为 transactions (省略.html)
        mv.setViewName("transactions");
        // 将查询所有交易信息结果放入视图控制器中，设置名称为list
        mv.addObject("list", transactionsService.selectAll());
        // 返回视图控制器
        return mv;
    }

    /**
     * 跳转到规则1筛选页面
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/rule1")
    public ModelAndView rule1(ModelAndView mv) {
        // 设置跳转网页为 rule1 (省略.html)
        mv.setViewName("rule1");
        // 将查询所有根据规则1筛选的结果放入视图控制器中，设置名称为list
        mv.addObject("list", accountInfoService.rule1List());
        // 返回视图控制器
        return mv;
    }

    /**
     * 跳转到规则2筛选页面
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/rule2")
    public ModelAndView rule2(ModelAndView mv) {
        // 设置跳转网页为 rule1 (省略.html)
        mv.setViewName("rule2");
        // 将查询所有根据规则2筛选的结果放入视图控制器中，设置名称为list
        mv.addObject("list", accountInfoService.rule2List());
        // 返回视图控制器
        return mv;
    }

    /**
     * 导出规则1筛选结果
     * Export Rule 1 Filter Results
     *
     * @return file
     */
    @SneakyThrows
    @GetMapping("/rule1/export")
    public ResponseEntity<Resource> exportListByRule1() {
        // Export the query list as a. txt file through the export service and obtain the path to the exported file
        String exportPath = accountInfoService.rule1ExportList();
        // Create file information through file path
        File file = new File(exportPath);
        // Construct File Resources
        Resource resource = new UrlResource(file.toURI());
        // Instantiating HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        // Set response header to force browser to download files
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
        // Encapsulate exported file resources as response entities for browser download
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /**
     * 导出规则2筛选结果
     *
     * @return file
     */
    @SneakyThrows
    @GetMapping("/rule2/export")
    public ResponseEntity<Resource> exportListByRule2() {
        // 通过导出服务将查询列表导出为.txt文件，并获取导出文件的路径
        String exportPath = accountInfoService.rule2ExportList();
        // 通过文件路径创建文件信息
        File file = new File(exportPath);
        // 构造文件资源
        Resource resource = new UrlResource(file.toURI());
        // 实例化http响应头
        HttpHeaders headers = new HttpHeaders();
        // 设置响应头，强制浏览器下载文件
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
        // 将导出文件资源封装为响应实体，供浏览器下载
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
