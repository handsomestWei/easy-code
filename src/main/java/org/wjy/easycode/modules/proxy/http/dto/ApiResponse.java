package org.wjy.easycode.modules.proxy.http.dto;

import lombok.Data;

/**
 * @author weijiayu
 * @date 2024/10/9 16:50
 */
@Data
public class ApiResponse<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> instanceSuccessApiResponse(T data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(data);
        apiResponse.setCode("0");
        apiResponse.setMsg("");
        return apiResponse;
    }
}
