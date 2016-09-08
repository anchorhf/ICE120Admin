package com.anke.ice.util;

public class WhereClauseUtility {
	/// <summary>
    /// 增加 字符串类型等于的 where 语句
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddStringEqual(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if ((valueStr != null) && (valueStr != "") && (!valueStr.equals("-1")) && (!valueStr.equals("--请选择--")) && (valueStr.length()!=0))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" = '").append(valueStr).append("' ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" = '").append(valueStr).append("' ");
        }
    }
    public static void AddStringNotEqual(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if ((valueStr != null) && (valueStr.length()!=0))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" <> '").append(valueStr).append("' ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" <> '").append(valueStr).append("' ");
        }
    }
    /// <summary>
    /// 增加 字符串类型 相似 的 where 语句， 
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddStringLike(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if ((valueStr != null) && (valueStr.length()!=0) && (!valueStr.equals("--请选择--")))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" LIKE '%").append(valueStr).append("%' ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" LIKE '%").append(valueStr).append("%' ");
        }
    }
    /// <summary>
    /// Boolean相等
    /// </summary>
    /// <param name="colunmName">@param
    /// <param name="valueBool">@param
    /// <param name="sbWhereClause">@param
    public static void AddBoolEqual(String colunmName, Boolean valueBool, StringBuilder sbWhereClause)
    {
        if (sbWhereClause.length() > 0)
            sbWhereClause.append(" AND ").append(colunmName).append(" = '").append(valueBool).append("' ");
        else
            sbWhereClause.append(" WHERE ").append(colunmName).append(" = '").append(valueBool).append("' ");
    }

    /// <summary>
    /// 增加 字符串类型 相似 的 where or 语句 <--add tanhuan 
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddStringLikeOr(String columnName1, String valueStr1, String columName2, String valueStr2, StringBuilder sbWhereClause)
    {
        if ((valueStr1 != null) && (valueStr1 != "") && (valueStr2.length()>0))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ( ").append(columnName1).append(" LIKE '%").append(valueStr1).append("%' ").append(" OR ").append(columName2).append(" LIKE '%").append(valueStr2).append("%' ").append(") ");
            else
                sbWhereClause.append(" WHERE ( ").append(columnName1).append(" LIKE '%").append(valueStr1).append("%' ").append(" OR ").append(columName2).append(" LIKE '%").append(valueStr2).append("%' ").append(") ");
        }
    }

    /// <summary>
    /// 增加 时间类型大于等于的 where 语句
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddDateTimeGreaterThan(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if (sbWhereClause.length() > 0)
            sbWhereClause.append(" AND ").append(columnName).append(" >= ").append("to_date('"+valueStr+"','yyyy-mm-dd hh24:mi:ss')").append(" ");
        else
            sbWhereClause.append(" WHERE ").append(columnName).append(" >= ").append("to_date('"+valueStr+"','yyyy-mm-dd hh24:mi:ss')").append(" ");
    }

	/// <summary>
    /// 增加 时间类型小于的 where 语句
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddDateTimeLessThan(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if (sbWhereClause.length() > 0)
            sbWhereClause.append(" AND ").append(columnName).append(" < ").append("to_date('"+valueStr+"','yyyy-mm-dd hh24:mi:ss')").append(" ");
        else
            sbWhereClause.append(" WHERE ").append(columnName).append(" < ").append("to_date('"+valueStr+"','yyyy-mm-dd hh24:mi:ss')").append(" ");
    }

    /// <summary>
    /// 整型等于
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddIntEqual(String columnName, int valueStr, StringBuilder sbWhereClause)
    {
        if (valueStr >= 0)
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" = ").append(String.valueOf(valueStr)).append(" ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" = ").append(String.valueOf(valueStr)).append(" ");
        }
    }
    /// <summary>
    /// 
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="selectQuery"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddNotInSelectQuery(String columnName, String selectQuery, StringBuilder sbWhereClause)
    {
        if ((selectQuery != null) && (selectQuery != "" && selectQuery != "''") && (selectQuery != "'-1'" && selectQuery != "-1") && (selectQuery != "'--请选择--'" && selectQuery != "--请选择--"))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" NOT IN (").append(selectQuery).append(") ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" NOT IN (").append(selectQuery).append(") ");
        }
    }
    public static void AddStringNotInSelectQuery(String columnName, String selectQuery, StringBuilder sbWhereClause)
    {
        if ((selectQuery != null) && (selectQuery != "" && selectQuery != "''") && (selectQuery != "'-1'" && selectQuery != "-1") && (!selectQuery.equals("'--请选择--'")) && (!selectQuery.equals("--请选择--")))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" NOT IN ('").append(selectQuery).append("') ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" NOT IN ('").append(selectQuery).append("') ");
        }
    }
    public static void AddInSelectQuery(String columnName, String selectQuery, StringBuilder sbWhereClause)
    {
        if ((selectQuery != null) && (selectQuery != "" && selectQuery != "''") && (selectQuery != "'-1'" && selectQuery != "-1") && (!selectQuery.equals("'--请选择--'")) && (!selectQuery.equals("--请选择--")))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" IN (").append(selectQuery).append(") ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" IN (").append(selectQuery).append(") ");
        }
    }
    public static void AddStringInSelectQuery(String columnName, String selectQuery, StringBuilder sbWhereClause)
    {
        if ((selectQuery != null) && (selectQuery != "" && selectQuery != "''") && (selectQuery != "'-1'" && selectQuery != "-1") && (!selectQuery.equals("'--请选择--'")) && (!selectQuery.equals("--请选择--")))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" IN ('").append(selectQuery).append("') ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" IN ('").append(selectQuery).append("') ");
        }
    }
    
    /// <summary>
    /// 增加 字符串类型 相似 的 where 语句， 
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void OrStringLike(String columnName, String valueStr, StringBuilder sbWhereClause)
    {
        if ((valueStr != null) && (valueStr.length()!=0))
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" OR ").append(columnName).append(" LIKE '%").append(valueStr).append("%' ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" LIKE '%").append(valueStr).append("%' ");
        }
    }
    /// <summary>
    /// 整型等于
    /// </summary>
    /// <param name="columnName"></param>
    /// <param name="valueStr"></param>
    /// <param name="sbWhereClause"></param>
    public static void AddDoubleEqual(String columnName, double valueStr, StringBuilder sbWhereClause)
    {
        if (valueStr >= 0)
        {
            if (sbWhereClause.length() > 0)
                sbWhereClause.append(" AND ").append(columnName).append(" = ").append(String.valueOf(valueStr)).append(" ");
            else
                sbWhereClause.append(" WHERE ").append(columnName).append(" = ").append(String.valueOf(valueStr)).append(" ");
        }
    }
}
