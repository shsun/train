package base.utils;

import javax.servlet.http.HttpSession;

import com.demo.sys.LoginUserEntry;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class XSessionUtil {

    public static void login(LoginUserEntry userEntry) {
        getSession().setAttribute("SessionUser", userEntry);
    }

    public static void logout() {
        getSession().removeAttribute("SessionUser");
        getSession().invalidate();
    }

    public static LoginUserEntry getLoginUser() {
        return (LoginUserEntry) getSession().getAttribute("SessionUser");
    }

    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }
}
