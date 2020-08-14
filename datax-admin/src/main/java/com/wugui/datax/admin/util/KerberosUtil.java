package com.wugui.datax.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.*;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;

@Slf4j
@Component
public class KerberosUtil {

    /**
     * 使用Subject方式登录到HDFS服务器
     * <p>暂时只支持KERBEROS方式</p>
     * @throws IOException
     */
    public static void loginForSubject(String username,String password)throws LoginException {

        String krb5ConfigFilePath = null;
        String jaasConfigFilePath = null;


        try {

            krb5ConfigFilePath = KerberosUtil.class.getResource("/fangzhou_krb5.conf").getPath();
            log.info("fangzhou_krb5 real path => {}" ,krb5ConfigFilePath);
            jaasConfigFilePath =   KerberosUtil.class.getResource("/jaas.conf").getPath();
            log.info("jaas conf real path => {}" ,jaasConfigFilePath);

            // 设置krb5
            System.setProperty("java.security.krb5.conf", krb5ConfigFilePath);
            // 设置jaas文件路径
            System.setProperty("java.security.auth.login.config", jaasConfigFilePath);

            LoginContext loginContext = kinit(username,password);
            UserGroupInformation.loginUserFromSubject(loginContext.getSubject());
        }catch (Exception e) {
            throw new LoginException(" kerberos login failed:"+e.getMessage());

        }

    }

    /**
     * kerberos认证
     *
     * @return LoginContext
     * @Throws LoginException
     */
    private static LoginContext kinit(final String userName, final String password) throws LoginException {
        LoginContext loginContext = new LoginContext(KerberosUtil.class.getSimpleName(), new CallbackHandler() {
            @Override
            public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                for (Callback c : callbacks) {
                    if (c instanceof NameCallback) {
                        ((NameCallback) c).setName(userName);
                    }
                    if (c instanceof PasswordCallback) {
                        ((PasswordCallback) c).setPassword(password.toCharArray());
                    }
                }
            }
        });
        loginContext.login();
        return loginContext;
    }

}
