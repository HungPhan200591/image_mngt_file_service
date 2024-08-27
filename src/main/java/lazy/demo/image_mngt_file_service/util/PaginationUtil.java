package lazy.demo.image_mngt_file_service.util;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    @Value("${pagination.page-no}")
    private static int pageNo;

    @Value("${pagination.page-size}")
    private static int pageSize;

    @Value("${pagination.sort-by}")
    private static String sortBy;


    public static Pageable createPageable(int pageNo, int pageSize, String sortBy) {
        if (pageNo == 0) {
            pageNo = PaginationUtil.pageNo;
        }

        if (pageSize == 0) {
            pageSize = PaginationUtil.pageSize;
        }

        if (StringUtils.isEmpty(sortBy)) {
            sortBy = PaginationUtil.sortBy;
        }
        return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
    }

    public static Pageable createPageable() {
        return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
    }

    public static Pageable createPageable(int pageNo, int pageSize) {
        return PageRequest.of(pageNo - 1, pageSize);
    }
}
