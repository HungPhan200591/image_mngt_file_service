package lazy.demo.image_mngt_file_service.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PaginationUtil {

    private static final int pageNo = 1;
    private static final int pageSize = 10;
    private static final String sortBy = "uploaded_at";


    public static Pageable createPageable(Integer pageNo, Integer pageSize, String sortBy) {
        if (Objects.isNull(pageNo) || pageNo == 0) {
            pageNo = PaginationUtil.pageNo;
        }

        if (Objects.isNull(pageSize) || pageSize == 0) {
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
