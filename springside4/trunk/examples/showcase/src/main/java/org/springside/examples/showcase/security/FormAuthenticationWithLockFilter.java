package org.springside.examples.showcase.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class FormAuthenticationWithLockFilter extends FormAuthenticationFilter {

    public static final String                    ACCOUNT_LOCK_KEY_ATTRIBUTE_NAME          = "accountLock";

    public static final String                    REMAIN_LOGIN_ATTEMPTS_KEY_ATTRIBUTE_NAME = "remainLoginAttempts";

    private long                                  maxLoginAttempts                         = 10;

    private ConcurrentHashMap<String, AtomicLong> userLockMap                              = new ConcurrentHashMap<String, AtomicLong>();

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);

        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }

        if (checkIfAccountLocked(request)) {
            return onLoginFailure(token, new ExcessiveAttemptsException(), request, response);
        } else {
            if (!doLogin(request, response, token)) {
                resetAccountLock(getUsername(request));
                return false;
            }
            return true;
        }
    }

    private boolean checkIfAccountLocked(ServletRequest request) {
        String username = getUsername(request);
        if (userLockMap.get((String) username) != null) {
            long loginAttempts = userLockMap.get((String) username).get();
            request.setAttribute(REMAIN_LOGIN_ATTEMPTS_KEY_ATTRIBUTE_NAME, maxLoginAttempts - loginAttempts);

            if (loginAttempts >= maxLoginAttempts) {
                return true;
            }
        }
        return false;
    }

    private boolean doLogin(ServletRequest request, ServletResponse response, AuthenticationToken token)
            throws Exception {
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (IncorrectCredentialsException e) {
            increaseAccountLockAttempts(request);
            checkIfAccountLocked(request);

            return onLoginFailure(token, e, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    private void increaseAccountLockAttempts(ServletRequest request) {
        AtomicLong initValue = new AtomicLong(0);
        AtomicLong lockNums = userLockMap.putIfAbsent(getUsername(request), new AtomicLong(0));
        if (lockNums == null) {
            lockNums = initValue;
        }
        lockNums.getAndIncrement();
        userLockMap.put(getUsername(request), lockNums);
    }

    private void resetAccountLock(String username) {
        userLockMap.put(username, new AtomicLong(0));
    }

    public ConcurrentHashMap<String, AtomicLong> getUserLockMap() {
        return userLockMap;
    }

    public void setMaxLoginAttempts(long maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
    }
}
