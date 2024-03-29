package cn.lifecode.recordaccount.dto.bill;

import cn.lifecode.recordaccount.entity.DayRecordAccount;
import cn.lifecode.recordaccount.entity.bill.YearBillDetail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 账单首页初始化信息
 *
 * @author luolin
 * @date 2021-01-21 23:29:49
 */
@Data
public class QueryBillInfoResponse implements Serializable {
    /**
     * 支出
     */
    @NotNull
    private double expense;
    /**
     * 收入
     */
    @NotNull
    private double income;

    /**
     * 返回的条数（注意是内层的总条数，也就是没有处理过的条数）
     */
    @NotNull
    private int total;

    /**
     * 年账单
     */
    private YearBillDetail yearBillDetail;
    /**
     * 月账单
     */
    private List<DayRecordAccount> monthBillDetailList;
    /**
     * 时间段账单
     */
    private List<DayRecordAccount> periodBillDetailList;
}