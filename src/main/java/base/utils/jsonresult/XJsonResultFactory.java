package base.utils.jsonresult;

import java.util.List;

public class XJsonResultFactory {

	public static<T> XJsonResult extgrid(List<T> invdata) {
		return new XExtGrid<T>(invdata);
	}
	public static<T> XJsonResult extgrid(List<T> invdata, int total) {
		return new XExtGrid<T>(invdata, total);
	}
	
	public static<T> XJsonResult success(T bean) {
		return new XJsonResultSuccess<T>(bean);
	}
	
	public static<T> XJsonResult success() {
		return new XJsonResultSuccess<T>();
	}
	
	public static<T> XJsonResult error(String info) {
		return new XJsonResultError(info);
	}
}
