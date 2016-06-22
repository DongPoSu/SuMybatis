package com.su.mybatis.nospring.test;

/**
 * @Author: suzheng
 * @Version:
 * @Package: com.su.mybatis.nospring.test
 * @Company: SIBU_KANGER
 * @Description:
 * @Date: 2016/06/14
 */
public class TestSql {
    public static void main(String[] args) {
        // 销售统计
//        test1();
        // 赠品统计
//        test2();
        // 销售统计 全部月份分组
//        test3();/
        // 赠品统计  全部月份分组
//        test4();
        test5();
    }

    private static void test5() {
        StringBuilder sql = new StringBuilder();
        StringBuilder whereSql = new StringBuilder();
        sql.append("SELECT"+
       " p.brand,"+
       "        p.`name`,"+
//       " SUM(a.purchase_quantity) purchase_quantity,"+
                "CONCAT('\\'',a.order_code),"+
       "        a.price,"+
       "        a.order_status"+
       " FROM(");
        for (int i = 0; i < 63; i++) {
            String index = i < 10 ? "0" + i : i + "";
            whereSql.append(
                    "    SELECT"+
                            "    d.order_code,"+
                            "    d1.product_id,"+
                            "    d1.purchase_quantity,"+
                            "    d1.shipped_quantity,"+
                            "    d.order_status,"+
                            "    d1.price"+
                            "    FROM"+
                            "    doing_order_"+index+" d"+
                            "    JOIN doing_order1_"+index+" d1 ON d1.order_id = d.order_id"+
//                            " AND d1.price != 0"+
                            "   AND d.pay_date >= '2016-05-01 00:00:00'"+
                            "   AND d.pay_date <= '2016-05-31 23:59:59'"
            );
            if (i < 62) {
                whereSql.append(" union all");
            }
        }
        sql.append(whereSql);
       sql.append("        ) a"+
       " JOIN sibu_directsale.product p ON p.product_id = a.product_id"
//       " GROUP BY"+
//       " a.product_id"+
//       " ORDER BY"+
//       " p.`name`"
       );
        System.out.println(sql.toString());
    }

    private static void test3() {
        StringBuilder sql = new StringBuilder();
        StringBuilder whereSql = new StringBuilder();

        sql.append(" SELECT"+
        " e.brand,"+
        " e.`name`,"+
        " SUM(e.purchase_quantity) quantity, e.pay_month"+
        " FROM("+
         " SELECT"+
         " p.brand,"+
         " p.`name`,"+
         " c.purchase_quantity,"+
         " c.pay_month"+
         " FROM(");
        for (int i = 0; i < 63; i++) {
            String index = i < 10 ? "0" + i : i + "";
            whereSql.append(
                    " SELECT"+
                            " d1.product_id,"+
                            " SUM(d1.purchase_quantity) purchase_quantity,"+
                            " DATE_FORMAT(d.pay_date, '%Y-%m') AS pay_month"+
                            " FROM"+
                            " doing_order_"+index+" d"+
                            " JOIN doing_order1_"+index+" d1 ON d.order_id = d1.order_id"+
                            " WHERE"+
                            " d1.price != 0"+
                            " AND d.order_s = 1"+
                            " GROUP BY"+
                            " d1.product_id,"+
                            " pay_month");
            if (i < 62) {
                whereSql.append(" union all");
            }
        }
        sql.append(whereSql);

        sql.append( " ) c"+
         " JOIN sibu_directsale.product p ON c.product_id = p.product_id"+
         " WHERE"+
         " NOT EXISTS ("+
         " SELECT"+
         " 1"+
         " FROM"+
         " sibu_directsale.product_connect pc"+
         " WHERE"+
         " p.product_id = pc.product_id"+
         " )"+
         " UNION ALL"+
         " SELECT"+
         " p.brand,"+
         " p.`name`,"+
         " SUM("+
         " c.purchase_quantity * p.connect_product_quantity"+
         " ) AS purchase_quantity,"+
         " c.pay_month"+
         " FROM"+
         " (");
        sql.append(whereSql);

        sql.append( " ) c"+
         " JOIN ("+
         " SELECT"+
         " pc.connect_product_quantity,"+
         " pc.product_id AS box_product_id,"+
         " p.brand,"+
         " p.`name`"+
         " FROM"+
         " sibu_directsale.product_connect pc"+
         " JOIN sibu_directsale.product p ON p.product_id = pc.connect_product_id"+
         " ) p ON c.product_id = p.box_product_id"+
         " GROUP BY"+
         " c.pay_month,"+
         " p.`name`"+
         " ) e"+
        " GROUP BY " +
                " e.pay_month," +
                " e.`name`");
        System.out.println(sql);
    }
    private static void test4() {
        StringBuilder sql = new StringBuilder();
        StringBuilder whereSql = new StringBuilder();

        sql.append(" SELECT"+
                " e.brand,"+
                " e.`name`,"+
                " SUM(e.purchase_quantity) quantity, e.pay_month"+
                " FROM("+
                " SELECT"+
                " p.brand,"+
                " p.`name`,"+
                " c.purchase_quantity,"+
                " c.pay_month"+
                " FROM(");
        for (int i = 0; i < 63; i++) {
            String index = i < 10 ? "0" + i : i + "";
            whereSql.append(
                    " SELECT"+
                            " d1.product_id,"+
                            " SUM(d1.purchase_quantity) purchase_quantity,"+
                            " DATE_FORMAT(d.pay_date, '%Y-%m') AS pay_month"+
                            " FROM"+
                            " doing_order_"+index+" d"+
                            " JOIN doing_order1_"+index+" d1 ON d.order_id = d1.order_id"+
                            " WHERE"+
                            " d1.price = 0"+
                            " AND d.is_pay = 1"+
                            " AND d.pay_date IS NOT NULL"+
                            " GROUP BY"+
                            " d1.product_id,"+
                            " pay_month");
            if (i < 62) {
                whereSql.append(" union all");
            }
        }
        sql.append(whereSql);

        sql.append( " ) c"+
                " JOIN sibu_directsale.product p ON c.product_id = p.product_id"+
                " WHERE"+
                " NOT EXISTS ("+
                " SELECT"+
                " 1"+
                " FROM"+
                " sibu_directsale.product_connect pc"+
                " WHERE"+
                " p.product_id = pc.product_id"+
                " )"+
                " UNION ALL"+
                " SELECT"+
                " p.brand,"+
                " p.`name`,"+
                " SUM("+
                " c.purchase_quantity * p.connect_product_quantity"+
                " ) AS purchase_quantity,"+
                " c.pay_month"+
                " FROM"+
                " (");
        sql.append(whereSql);

        sql.append( " ) c"+
                " JOIN ("+
                " SELECT"+
                " pc.connect_product_quantity,"+
                " pc.product_id AS box_product_id,"+
                " p.brand,"+
                " p.`name`"+
                " FROM"+
                " sibu_directsale.product_connect pc"+
                " JOIN sibu_directsale.product p ON p.product_id = pc.connect_product_id"+
                " ) p ON c.product_id = p.box_product_id"+
                " GROUP BY"+
                " c.pay_month,"+
                " p.`name`"+
                " ) e"+
                " GROUP BY " +
                " e.pay_month," +
                " e.`name`");
        System.out.println(sql);
    }
    private static void test2() {
        StringBuilder sql = new StringBuilder();
        sql.append("select c.brand,c.`name`, SUM(c.purchase_quantity) as purchase_quantity" +
                " FROM (");
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < 63; i++) {
            String index = i < 10 ? "0" + i : i + "";
            where.append(" SELECT a.purchase_quantity, a.shipped_quantity,b.`name`," +
                    " b.brand" +
                    "         FROM" +
                    " (" +
                    "         SELECT" +
                    " d.order_id," +
                    "         d1.product_id," +
                    "         d1.purchase_quantity," +
                    "         d1.shipped_quantity," +
                    "         d.order_status" +
                    " FROM" +
                    " doing_order_" + index + " d," +
                    " doing_order1_" + index + " d1" +
                    " WHERE" +
                    " d.order_id = d1.order_id" +
                    " AND d1.price = 0" +
                    " AND d.is_pay = 1" +
                    " AND d.pay_date >='2016-05-01 00:00:00'" +
                    " AND d.pay_date <='2016-05-31 23:59:59'" +
//                    "AND d.ship_date >='2016-05-01 00:00:00'" +
//                    "AND d.ship_date <='2016-05-31 23:59:59'" +
                    " ) a, sibu_directsale.product b" +
                    " WHERE" +
                    " a.product_id = b.product_id");
            if (i < 62) {
                where.append(" union all");
            }
        }
        sql.append(where);
        sql.append(") c GROUP BY c.`name`");
        System.out.println(sql);
    }


    private static void test1() {

        StringBuilder sql = new StringBuilder();
        sql.append("select c.brand,c.`name`, SUM(c.purchase_quantity) as purchase_quantity" +
                " FROM (");
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < 63; i++) {
            String index = i < 10 ? "0" + i : i + "";
            where.append(" SELECT a.purchase_quantity, a.shipped_quantity,b.`name`," +
                    " b.brand" +
                    "         FROM" +
                    " (" +
                    "         SELECT" +
                    " d.order_id," +
                    "         d1.product_id," +
                    "         d1.purchase_quantity," +
                    "         d1.shipped_quantity," +
                    "         d.order_status" +
                    " FROM" +
                    " doing_order_" + index + " d," +
                    " doing_order1_" + index + " d1" +
                    " WHERE" +
                    " d.order_id = d1.order_id" +
                    " AND d1.price != 0" +
                    " AND d.is_pay = 1" +
                    " AND d.pay_date >='2016-05-01 00:00:00'" +
                    " AND d.pay_date <='2016-05-31 23:59:59'" +
//                    "AND d.ship_date >='2016-05-01 00:00:00'" +
//                    "AND d.ship_date <='2016-05-31 23:59:59'" +
                    " ) a, sibu_directsale.product b" +
                    " WHERE" +
                    " a.product_id = b.product_id");
            if (i < 62) {
                where.append(" union all");
            }
        }
        sql.append(where);
        sql.append(") c GROUP BY c.`name`");
        System.out.println(sql);
    }


}
