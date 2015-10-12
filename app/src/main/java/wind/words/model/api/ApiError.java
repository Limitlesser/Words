package wind.words.model.api;

/**
 * Created by wind on 2015/9/12.
 */
public class ApiError extends Throwable {

    public int errCode;

    public String errMsg;

    public ApiError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiError(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }

    public ApiError(String detailMessage, Throwable cause, int errCode, String errMsg) {
        super(detailMessage, cause);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
