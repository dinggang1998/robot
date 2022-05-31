package com.learn.robot.util;

import com.learn.robot.exception.RobotException;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.enums.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CheckPWDUtil {

    public static String MAX_LENGTH = "20";

    public static String MIN_LENGTH = "8";

    /**
     * 特殊符号集合
     */
    public static String DEFAULT_SPECIAL_CHAR = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    /**
     * 键盘物理位置横向不允许最小的连续个数
     */
    public static String LIMIT_HORIZONTAL_NUM_KEY = "3";

    /**
     * 键盘物理位置斜向不允许最小的连续个数
     */
    public static String LIMIT_SLOPE_NUM_KEY = "3";

    /**
     * 密码口令中字符在逻辑位置上不允许最小的连续个数
     */
    public static String LIMIT_LOGIC_NUM_CHAR = "3";


    /**
     * 密码口令中相同字符不允许最小的连续个数
     */
    public static String LIMIT_NUM_SAME_CHAR = "3";

    /**
     * 键盘横向方向规则
     */
    public static String[] KEYBOARD_HORIZONTAL_ARR = {
            "01234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm",
    };
    /**
     * 键盘斜线方向规则
     */
    public static String[] KEYBOARD_SLOPE_ARR = {
            "1qaz",
            "2wsx",
            "3edc",
            "4rfv",
            "5tgb",
            "6yhn",
            "7ujm",
            "8ik,",
            "9ol.",
            "0p;/",
            "=[;.",
            "-pl,",
            "0okm",
            "9ijn",
            "8uhb",
            "7ygv",
            "6tfc",
            "5rdx",
            "4esz"
    };

    /**
     * @return 符合长度要求 返回true
     * @brief 检测密码中字符长度
     * @param[in] password            密码字符串
     */
    public static boolean checkPasswordLength(String password) {
        boolean flag = false;

        if ("".equals(MAX_LENGTH)) {
            if (password.length() >= Integer.parseInt(MIN_LENGTH)) {
                flag = true;
            }
        } else {
            if (password.length() >= Integer.parseInt(MIN_LENGTH) &&
                    password.length() <= Integer.parseInt(MAX_LENGTH)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @return 包含数字 返回true
     * @brief 检测密码中是否包含数字
     * @param[in] password            密码字符串
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int num_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isDigit(chPass[i])) {
                num_count++;
            }
        }
        if (num_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 包含字母 返回true
     * @brief 检测密码中是否包含字母（不区分大小写）
     * @param[in] password            密码字符串
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLetter(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 包含小写字母 返回true
     * @brief 检测密码中是否包含小写字母
     * @param[in] password            密码字符串
     */
    public static boolean checkContainLowerCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLowerCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 包含大写字母 返回true
     * @brief 检测密码中是否包含大写字母
     * @param[in] password            密码字符串
     */
    public static boolean checkContainUpperCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isUpperCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 包含特殊符号 返回true
     * @brief 检测密码中是否包含特殊符号
     * @param[in] password            密码字符串
     */
    public static boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int special_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (DEFAULT_SPECIAL_CHAR.indexOf(chPass[i]) != -1) {
                special_count++;
            }
        }

        if (special_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 含有横向连续字符串 返回true
     * @brief 键盘规则匹配器 横向连续检测
     * @param[in] password            密码字符串
     */
    public static boolean checkLateralKeyboardSite(String password) {
        String t_password = new String(password);
        //将所有输入字符转为小写
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘横向规则检测
         */
        boolean flag = false;
        int arrLen = KEYBOARD_HORIZONTAL_ARR.length;
        int limit_num = Integer.parseInt(LIMIT_HORIZONTAL_NUM_KEY);

        for (int i = 0; i + limit_num <= n; i++) {
            String str = t_password.substring(i, i + limit_num);
            String distinguishStr = password.substring(i, i + limit_num);

            for (int j = 0; j < arrLen; j++) {
                String configStr = KEYBOARD_HORIZONTAL_ARR[j];
                String revOrderStr = new StringBuffer(KEYBOARD_HORIZONTAL_ARR[j]).reverse().toString();

                //检测包含字母(区分大小写)
                    //考虑 大写键盘匹配的情况
                    String UpperStr = KEYBOARD_HORIZONTAL_ARR[j].toUpperCase();
                    if ((configStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }

            }
        }
        return flag;
    }

    /**
     * @return 含有斜向连续字符串 返回true
     * @brief 键盘规则匹配器 斜向规则检测
     * @param[in] password            密码字符串
     */
    public static boolean checkKeyboardSlantSite(String password) {
        String t_password = new String(password);
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘斜线方向规则检测
         */
        boolean flag = false;
        int arrLen = KEYBOARD_SLOPE_ARR.length;
        int limit_num = Integer.parseInt(LIMIT_SLOPE_NUM_KEY);

        for (int i = 0; i + limit_num <= n; i++) {
            String str = t_password.substring(i, i + limit_num);
            String distinguishStr = password.substring(i, i + limit_num);
            for (int j = 0; j < arrLen; j++) {
                String configStr = KEYBOARD_SLOPE_ARR[j];
                String revOrderStr = new StringBuffer(KEYBOARD_SLOPE_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)

                    //考虑 大写键盘匹配的情况
                    String UpperStr = KEYBOARD_SLOPE_ARR[j].toUpperCase();
                    if ((configStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
            }
        }
        return flag;
    }

    /**
     * @return 含有a-z,z-a连续字符串 返回true
     * @brief 评估a-z,z-a这样的连续字符
     * @param[in] password            密码字符串
     */
    public static boolean checkSequentialChars(String password) {
        String t_password = new String(password);
        boolean flag = false;
        int limit_num = Integer.parseInt(LIMIT_LOGIC_NUM_CHAR);
        int normal_count = 0;
        int reversed_count = 0;

            t_password = t_password.toLowerCase();
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();

        for (int i = 0; i + limit_num <= n; i++) {
            normal_count = 0;
            reversed_count = 0;
            for (int j = 0; j < limit_num - 1; j++) {
                if (pwdCharArr[i + j + 1] - pwdCharArr[i + j] == 1) {
                    normal_count++;
                    if (normal_count == limit_num - 1) {
                        return true;
                    }
                }

                if (pwdCharArr[i + j] - pwdCharArr[i + j + 1] == 1) {
                    reversed_count++;
                    if (reversed_count == limit_num - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @return 含有aaaa, 1111等连续字符串 返回true
     * @brief 评估aaaa, 1111这样的相同连续字符
     * @param[in] password            密码字符串
     */
    public static boolean checkSequentialSameChars(String password) {
        String t_password = new String(password);
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();
        boolean flag = false;
        int limit_num = Integer.parseInt(LIMIT_NUM_SAME_CHAR);
        int count = 0;
        for (int i = 0; i + limit_num <= n; i++) {
            count = 0;
            for (int j = 0; j < limit_num - 1; j++) {
                if (pwdCharArr[i + j] == pwdCharArr[i + j + 1]) {
                    count++;
                    if (count == limit_num - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }


    /**
     * 密码综合性判断(密码包括 数组,小写字母,大写字母,特殊符号中的至少3类)
     *
     * @return
     */
    public static boolean checkPwdCharContent(String password) {
        int res = 0;
        //检测包含小写字母
        boolean lower_flag = checkContainLowerCase(password);
        if (!lower_flag) {
            log.info("密码中未包含小写字母!");
        } else {
            res++;
        }

        //检测包含大写字母
        boolean up_flag = checkContainUpperCase(password);
        if (!up_flag) {
            log.info("密码中未包含大写字母!");
        } else {
            res++;
        }

        //检测是否包含数字
        boolean digit_flag = checkContainDigit(password);
        if (!digit_flag) {
            log.info("密码中未包含数字!");
        } else {
            res++;
        }

        //检测是否包含特殊字符
        boolean special_flag = checkContainSpecialChar(password);
        if (!special_flag) {
            log.info("密码中未包含特殊符号!");
        } else {
            res++;
        }

        if (res >= 3) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param password
     * @param username
     * @return
     * @brief 评估密码与口令相关性检查
     */
    public static boolean checkPwdAndUserName(String password, String username) {
        boolean flag = true;
        if (password.indexOf(username) < 0) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }


    /**
     * @param password
     * @return
     * @brief 评估密码与口令相关性检查
     */
    public static boolean checkPwdFuHao(String password) {
        boolean flag = true;
        String regExp = "^[0-9a-zA-Z!\"#$%&'()*+,-./:;<=>?@\\[\\]\\\\^_`{|}~]*$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(password);
        // 字符串是否与正则表达式相匹配
        flag = matcher.matches();
        return flag;
    }


    /**
     * @return 符合要求 返回true
     * @brief 评估密码中包含的字符类型是否符合要求
     */
    public static void EvalPWD(String password, String username) throws ServiceException {
        boolean flag = false;

        if (password == null || "".equals(password)) {
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_NULL);
        }

        //检测合规
        flag = checkPwdFuHao(password);
        if (!flag) {
            log.info("密码中含有不合规得符号或字符!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_FH_ERROR);
        }

        //检测长度
        flag = checkPasswordLength(password);
        if (!flag) {
            log.info("密码长度不符合要求!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_LENGTH_ERROR);
        }

        //密码综合性判断(密码包括 数组,小写字母,大写字母,特殊符号中的至少3类)
        flag = checkPwdCharContent(password);
        if (!flag) {
            log.info("密码必须包含数字,小写字母,大写字母,特殊符号中至少三类!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_KIND_ERROR);
        }

        //检测键盘横向连续
        flag = checkLateralKeyboardSite(password);
        if (flag) {
            log.info("密码中键盘横向连续!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_HORIZONTAL_ERROR);
        }

        //检测键盘斜向连续
        flag = checkKeyboardSlantSite(password);
        if (flag) {
            log.info("密码中键盘斜向连续!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_SLOPE_ERROR);
        }

        //检测逻辑位置连续
        flag = checkSequentialChars(password);
        if (flag) {
            log.info("密码中逻辑位连续!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_SEQUENTIAL_ERROR);
        }

        //检测相邻字符是否相同
        flag = checkSequentialSameChars(password);
        if (flag) {
            log.info("密码中相邻字符相同!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_SEQUENTIAL_SAME_ERROR);
        }

        //检测用户名密码相关性
        if (StringUtils.isBlank(username)) {
            log.info("缺少用户名!");
            throw RobotException.serviceException(ServiceExceptionEnum.USERNAME_NULL);
        }
        flag = checkPwdAndUserName(password, username);
        if (flag) {
            log.info("密码中含有用户名!");
            throw RobotException.serviceException(ServiceExceptionEnum.PWD_USERNAME_SAME_ERROR);
        }
    }

}
