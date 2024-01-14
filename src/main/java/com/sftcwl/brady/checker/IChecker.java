package com.sftcwl.brady.checker;

import java.util.List;

/**
 * @author : Brady (brady@sfmail.sf-express.com)
 * @version : 1.0
 * @className : IChecker
 * @description :
 * @date : 2024/1/13 2:55 下午
 **/
public interface IChecker<T> {

    /**
     * 字段必填性校验
     * @param row
     * @return
     */
    T rowCheck(T row);

    /**
     * 业务校验
     * @param rows
     * @return
     */
    List<T> bizCheck(List<T> rows);

}
