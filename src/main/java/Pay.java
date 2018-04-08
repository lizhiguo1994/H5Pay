import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/8/15.
 *
 * 声明：由于该Demo支持多种支付类型，所以参数可能多于某一种支付类型文档中所存在的参数
 *       如仅需一种支付类型，可自行对照文档删除无关参数
 *
 */
@WebServlet(name = "Servlet")
public class Pay extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String S = request.getQueryString();
        System.out.println(S);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //请求地址
        String url = "https://pay.heepay.com/Payment/Index.aspx";

        /**
         * 支付类型选择
         * is_phone、is_frame、pay_type控制所有支付类型
         *
         * 微信PC扫码   is_phone=0 is_frame=0 pay_type=30
         * 微信H5       is_phone=1 is_frame=0 pay_type=30
         * 微信公众号   is_phone=1 is_frame=1 pay_type=30
         *
         * 支付宝PC扫码 is_phone=0 is_frame=0 pay_type=22
         * 支付宝H5     is_phone=1 is_frame=0 pay_type=22
         *
         * QQ PC扫码    is_phone=0 is_frame=0 pay_type=31
         * QQ H5        is_phone=1 is_frame=0 pay_type=31
         *
         * 京东PC扫码   is_phone=0 is_frame=0 pay_type=33
         *
         * 其他卡类支付 is_phone=0 is_frame=0 pay_type=参照文档
         */

        //请求参数
        String version = "1";//版本号
        String is_phone = "1";//是否是手机端
        String is_frame = "0";//是否是公众号
        String pay_type = "31";//支付类型
        String agent_id = "1664502";//商户ID
        String agent_bill_id = SmallTools.getDate("yyyyMMddHHmmss")+"Hykj";//商户内部订单编号
        String pay_amt = "0.01";//订单金额
        String return_url = "http://www.baidu.com";//支付完成跳转地址
        String notify_url = "http://xxx.com/H5Pay/ReceiveNotify?asd=123";//支付完成异步通知地址
        String user_ip = "132.326.23.56";//用户IP地址
        String agent_bill_time = SmallTools.getDate("yyyyMMddHHmmss");//提交单据时间
        String goods_name = URLEncoder.encode("汇元文化衫","GBK");//商品名称
        String goods_num = "1";//产品数量
        String remark = "520";//备注
        String goods_note = URLEncoder.encode("白色","GBK");//支付说明
        String sign_type = "MD5";//签名方式
        String sign = "";//签名结果
        String key = "4B05A95416DB4184ACEE4313";//商户密钥
        String meta_option = "{\"s\":\"WAP\",\"n\":\"京东官网\",\"id\":\"https://m.jd.com\"}";//详见开发文档

        //meta_option参数先Base64加密，再URLencode
        //meta_option = URLEncoder.encode(new BASE64Encoder().encode(meta_option.getBytes("gb2312")),"UTF-8");
        meta_option = URLEncoder.encode(meta_option,"GBK");
        String sign1 = "version="+version+
                "&agent_id="+agent_id+
                "&agent_bill_id="+agent_bill_id+
                "&agent_bill_time="+agent_bill_time+
                "&pay_type="+pay_type+
                "&pay_amt="+pay_amt+
                "&notify_url="+notify_url+
                "&return_url="+return_url+
                "&user_ip="+user_ip+
                "&key="+key;

        System.out.println("签名参数："+sign1);
        //对签名参数进行MD5加密得到sign
        sign = SmallTools.MD5en(sign1);
        //拼接请求参数
        String parameter = "version="+version+
                "&pay_type="+pay_type+
                "&agent_id="+agent_id+
                "&agent_bill_id="+agent_bill_id+
                "&pay_amt="+pay_amt+
                "&return_url="+return_url+
                "&notify_url="+notify_url+
                "&is_phone="+is_phone+
                "&is_frame="+is_frame+
                "&user_ip="+user_ip+
                "&agent_bill_time="+agent_bill_time+
                "&goods_name="+goods_name+
                "&remark="+remark+
                "&sign_type="+sign_type+
                "&goods_num="+goods_num+
                "&goods_note="+goods_note+
                "&meta_option="+meta_option+
                "&sign="+sign;
        System.out.println("请求参数："+parameter);
        //携带请求参数并重定向跳转下单地址
        response.sendRedirect(url+"?"+parameter);
    }
}
