import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/9/18.
 */
public class GetTokenID {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String url = "https://pay.heepay.com/Phone/SDK/PayInit.aspx";

        String version = "1";//版本号
        String pay_type = "30";//支付类型
        String agent_id = "1664502";//商户ID
        String agent_bill_id = "HYKJ"+SmallTools.getDate("yyyyMMddHHmmss");//商户内部订单编号
        String pay_amt = "0.01";//订单金额
        String return_url = "http://";//支付完成跳转地址
        String notify_url = "http://";//支付完成异步通知地址
        String user_ip = "223.333.33.3";//用户IP地址
        String agent_bill_time = SmallTools.getDate("yyyyMMddHHmmss");//提交单据时间
        String goods_name = URLEncoder.encode("汇元文化衫","GBK");//商品名称
        String goods_num = "3";//产品数量
        String remark = "hyhelper";//备注
        String goods_note = URLEncoder.encode("白色，加长款","GBK");//支付说明
        String sign_type = "MD5";//签名方式
        String sign = "";//签名结果
        String key = "4B05A95416DB4184ACEE4313";//商户密钥
        String meta_option = "[{\"s\":\"Android\",\"n\":\"开发者小助手\","+
                "\"id\":\"com.hyout.devlittlehelper\"},{\"s\":\"IOS\",\"n\":\"开发者小助手\",\"id\":\"com.hyout.devlittlehelper\"}]";//详见开发文档

        //meta_option参数先Base64加密，再URLencode
        meta_option = URLEncoder.encode(new BASE64Encoder().encode(meta_option.getBytes("gb2312")),"gb2312");
        String sign1 = "version="+version+
                "&agent_id="+agent_id+
                "&agent_bill_id="+agent_bill_id+
                "&agent_bill_time="+agent_bill_time+
                "&pay_type="+pay_type+
                "&pay_amt="+pay_amt+
                "&notify_url="+notify_url+
                "&user_ip="+user_ip+
                "&key="+key;

        System.out.println("签名参数："+sign1);

        sign = SmallTools.MD5en(sign1);
        String canshu = "version="+version+
                "&pay_type="+pay_type+
                "&agent_id="+agent_id+
                "&agent_bill_id="+agent_bill_id+
                "&pay_amt="+pay_amt+
                "&return_url="+return_url+
                "&notify_url="+notify_url+
                "&user_ip="+user_ip+
                "&agent_bill_time="+agent_bill_time+
                "&goods_name="+goods_name+
                "&remark="+remark+
                "&sign_type="+sign_type+
                "&meta_option="+meta_option+
                "&goods_num="+goods_num+
                "&goods_note="+goods_note+
                "&sign="+sign;
        System.out.println("请求参数："+canshu);
        String token_id = HttpUtil.sendPost(url,canshu);
        System.out.println("接收到的返回参数："+token_id);
    }


}
