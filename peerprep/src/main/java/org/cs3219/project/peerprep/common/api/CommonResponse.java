package org.cs3219.project.peerprep.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> create(T data) {
        return new CommonResponse<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), data);
    }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> CommonResponse<T> fail(int code, String message) {
        return new CommonResponse<>(code, message, null);
    }

    public static <T> CommonResponse<T> fail(HttpStatus httpStatus) {
        return fail(httpStatus.value(), httpStatus.getReasonPhrase());
    }
}
