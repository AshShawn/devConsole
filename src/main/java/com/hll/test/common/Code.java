package com.hll.test.common;

public enum Code implements ICode {
    /**
     * code,desc
     */
    LACK_OF_PARAM(1, "缺少必填参数"),
    SUCCESS(0, "获取成功"),
    NOT_LOGIN(-999, "未登录"),
    NO_RESULT(2,"查询无结果"),
    CONTINUE_AGAIN(3,"已续费过,无法再次续费"),
    PWD_ERROR(4,"密码或账号错误"),
    VERCODE_ERROR(4,"验证码错误"),
    NO_SIGN(11,"未打卡上班"),
    AL_SIGN(12,"未打卡下班"),
    UN_SIGN(13,"以下班"),
    AL_LOCK(21,"已上锁,无法再次上锁"),
    NO_PERMISSION(-998,"无权限");




    private Code(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    @Override
    public int getCode() {
        return code;
    }


    @Override
    public String getDesc() {
        return desc;
    }


}
