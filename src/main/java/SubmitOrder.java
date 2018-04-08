import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.spi.SyncResolver;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/11/24.
 */
@WebServlet(name = "SubmitOrder")
public class SubmitOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求地址
        String url = "https://c.heepay.com/quick/pc/index.do";

        String merchantId = "100213"; //商户号	汇付宝提供给商户的ID
        String merchantOrderNo = SmallTools.getDate("yyyyMMddHHmmss");//商户交易号	商户内部的交易ID
        String merchantUserId = "112";//用户号	商户内部的个人用户ID
        String productCode = "HY_B2CEBANKPC";//产品编码	用户签约的产品编码:
        String payAmount = "1.00";//交易金额	单位为元，两位小数
        String requestTime = SmallTools.getDate("yyyyMMddHHmmss");//请求时间	商户请求接口时间 yyyyMMddhhmmss
        String version = "1.0";//版本号	商户请求版本号
        String notifyUrl = "http://hnandtz.iok.la/chuangxinpay/hfb/onlineBank/callback.do";//通知URL	本次交易异步通知URL
        String callBackUrl = "http://hnandtz.iok.la/chuangxinpay/hfb/onlineBank/callback.do";//同步返回URL	本次交易同步返回URL
        String description = "hello";//商品信息	本次交易商品信息
        String clientIp = "124.204.49.130";//用户ip	发起交易用户的ip
        String reqHyTime = System.currentTimeMillis()+"";;//防钓鱼时间	时间戳格式
        String onlineType = "simple";//simple:商户侧选择银行 hard:汇付宝收银台上选择银行
        String bankId = "708";//
        String bankName = "银联";//
        String bankCardType = "SAVING";//SAVING : 储蓄卡支付CREDIT : 信用卡支付
        String key = "77f214b4b5e6e18b15dd97796815d915";//密钥
        String signString = "";//签名串


        //MD5签名参数
        String sign1 = "merchantId="+merchantId+
                "&merchantOrderNo="+merchantOrderNo+
                "&merchantUserId="+merchantUserId+
                "&notifyUrl="+notifyUrl+
                "&payAmount="+payAmount+
                "&productCode="+productCode+
                "&version="+version+
                "&key="+key;

        System.out.println("签名参数："+sign1);
        //对签名参数进行MD5加密得到sign
        signString = SmallTools.MD5en(sign1);
        //拼接请求参数
        String parameter = "merchantId="+merchantId+
                "&merchantOrderNo="+merchantOrderNo+
                "&merchantUserId="+merchantUserId+
                "&productCode="+productCode+
                "&payAmount="+payAmount+
                "&requestTime="+requestTime+
                "&version="+version+
                "&notifyUrl="+notifyUrl+
                "&callBackUrl="+callBackUrl+
                "&description="+description+
                "&clientIp="+clientIp+
                "&signString="+signString+
                "&onlineType="+onlineType+
                "&bankId="+bankId+
                "&bankName="+bankName+
                "&bankCardType="+bankCardType;
        System.out.println("请求参数："+parameter);
        //携带请求参数并重定向跳转下单地址
        String s = HttpUtil.sendPost(url,parameter);
        System.out.println(s);
        //response.sendRedirect(url+"?"+parameter);
    }
}


