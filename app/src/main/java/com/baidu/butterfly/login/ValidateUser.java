package com.baidu.butterfly.login;

import com.baidu.butterfly.communication.Communication;


public class ValidateUser {

    public static boolean validateNoAuto(String userNameValue, String userPassword){

        String result = Communication.doPost(userNameValue);

        if (result.equals(userPassword)){
            return true;
        }
        else {
            return false;
        }

        //test
       /* if (userNameValue.equals("butterfly") && userPassword.equals("xixihaha")){
            return true;
        }
        else {
            return false;
        }*/
    }
}
