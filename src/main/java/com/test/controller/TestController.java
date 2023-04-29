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
     * Jump to transaction info page
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/transactions")
    public ModelAndView test2(ModelAndView mv) {
        // Set redirect webpage to transactions (omit .html)
        mv.setViewName("transactions");
        // Place all transaction information query results into the view controller and set the name to list
        mv.addObject("list", transactionsService.selectAll());
        // Return to View Controller
        return mv;
    }

    /**
     * Jump to Rule 1 filter page
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/rule1")
    public ModelAndView rule1(ModelAndView mv) {
        // Set redirect webpage to rule1 (omit .html)
        mv.setViewName("rule1");
        // Place all information query results filtered by rule 1 into the view controller and set the name to list
        mv.addObject("list", accountInfoService.rule1List());
        // Return to View Controller
        return mv;
    }

    /**
     * Jump to Rule 2 filter page
     *
     *
     * @param mv ModelAndView
     * @return mv
     */
    @GetMapping("/rule2")
    public ModelAndView rule2(ModelAndView mv) {
        // Set redirect webpage to rule2 (omit .html)
        mv.setViewName("rule2");
        // Place all information query results filtered by rule 2 into the view controller and set the name to list
        mv.addObject("list", accountInfoService.rule2List());
        // Return to View Controller
        return mv;
    }

    /**
     *
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
     *
     *Export Rule 2 Filter Results
     * @return file
     */
    @SneakyThrows
    @GetMapping("/rule2/export")
    public ResponseEntity<Resource> exportListByRule2() {
        // Export the query list as a. txt file through the export service and obtain the path to the exported file
        String exportPath = accountInfoService.rule2ExportList();
        // Create file information through file path
        File file = new File(exportPath);
        // Construct File Resources
        Resource resource = new UrlResource(file.toURI());

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
