package com.learn.robot.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        description = "响应体"
)
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    @ApiModelProperty("响应码")
    private String code;
    @ApiModelProperty("响应消息")
    private String message;
    @ApiModelProperty("响应数据")
    private T data;

    public Response(T data) {
        this.code = "200";
        this.message = "success";
        this.data = data;
    }

    public static Response success() {
        return new Response((Object)null);
    }

    public static Response success(Object data) {
        return new Response(data);
    }

    public static <T> Response.ResponseBuilder<T> builder() {
        return new Response.ResponseBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }


    @Override
    public String toString() {
        return "Response(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public Response() {
    }

    public Response(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class ResponseBuilder<T> {
        private String code;
        private String message;
        private T data;

        ResponseBuilder() {
        }

        public Response.ResponseBuilder<T> code(final String code) {
            this.code = code;
            return this;
        }

        public Response.ResponseBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public Response.ResponseBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public Response<T> build() {
            return new Response(this.code, this.message, this.data);
        }

    }
}