package base.utils.jsonresult;

public class XJsonResultError implements XJsonResult {
	
	private String info = "";
	private String result = "error";
	private boolean success = true;
	
	public XJsonResultError() {}
	
	public XJsonResultError(String info) {
		this.info = info;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
