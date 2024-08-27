package lazy.demo.image_mngt_file_service.exception;

import lazy.demo.image_mngt_file_service.enums.ErrorEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorEnum errorEnum;

    public CustomException(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

}
