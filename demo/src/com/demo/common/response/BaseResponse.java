package com.demo.common.response;

/**
 * 基础返回对象类
 * 
 * @author dhf 1815 at 2013-11-24
 * @param <T> 根据所设置的范型返回对象
 */
public class BaseResponse<T>
{
    /**
     * 返回码信息
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String message;

    private boolean success;

    /**
     * 返回对象
     */
    private T resultInfo;

    public String getReturnCode()
    {
        return returnCode;
    }

    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getResultInfo()
    {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo)
    {
        this.resultInfo = resultInfo;
    }

   

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
	@Override
	public String toString() {
		return "BaseResponse [returnCode=" + returnCode + ", message="
				+ message + ", success=" + success 
				+ ", resultInfo=" + resultInfo + "]";
	}


}
