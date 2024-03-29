package cn.lifecode.recordaccount.controller;

import cn.lifecode.frameworkcore.bean.Request;
import cn.lifecode.frameworkcore.bean.Response;
import cn.lifecode.recordaccount.dto.bill.*;
import cn.lifecode.recordaccount.service.bill.BillService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 账单页面
 *
 * @author luolin
 * @date 2021-01-21 22:41:14
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    /**
     * 获取账单信息
     * 获取顶部信息根据传入的参数：返回
     * A.月支出/月收入、B.年收入/年支出、C.时间段-收入/支出；
     * AC --> 每日支出/每日收入、B-->返回每月收入支出
     *
     * @param request
     * @return
     */
    @PostMapping("/queryBillInfo")
    public Response<QueryBillInfoResponse> queryBillInfo(@RequestBody Request<QueryBillInfoRequest> request) {
        return billService.queryBillInfo(request);
    }

    /**
     * 查询 月支出/月收入 列表
     *
     * @return 柱状图所需要数据列表
     */
    @PostMapping("/queryMonthIncomeExpenseList")
    public Response<QueryMonthIncomeExpenseListResponse> queryMonthIncomeExpenseList(
            @RequestBody Request<QueryMonthIncomeExpenseListRequest> request) {
        return billService.queryMonthIncomeExpenseList(request);
    }

    /**
     * 查询年账单折线图数据
     *
     * @param request 请求参数
     * @return 每月的收入和支出list
     */
    @PostMapping("/queryYearBrokeLineList")
    public Response<QueryYearBrokeLineListResponse> queryYearBrokeLineList(
            @RequestBody Request<QueryYearBrokeLineListRequest> request) {
        return billService.queryYearBrokeLineList(request);
    }

    /**
     * 查询账单导出
     *
     * @param request 请求参数
     * @return 返回账单信息
     */
    @PostMapping("/billExport")
    public Response<BillExportResponse> billExport(@Valid @RequestBody Request<BillExportRequest> request) {
        BillExportResponse response = billService.billExportQueryRecordAccount(request.getBody());
        return Response.success(response);
    }
}
