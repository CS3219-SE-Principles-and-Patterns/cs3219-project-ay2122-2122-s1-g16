package org.cs3219.project.peerprep.common.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class CommonPage<T> {

    public static <T> CommonPage<T> getCommonPage(Page<T> pageInfo) {
        CommonPage<T> commonPage = new CommonPage<>();
        commonPage.setPageNum(pageInfo.getCurrent());
        commonPage.setPageSize(pageInfo.getSize());
        commonPage.setRecords(pageInfo.getRecords());
        return commonPage;
    }

    private Long pageNum;

    private Long pageSize;

    private List<T> records;
}
