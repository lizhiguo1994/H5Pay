import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/9/8.
 */
@WebServlet(name = "ReceiveNotify")
public class ReceiveNotify extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //result=1&agent_id=1234567&jnet_bill_no=B20100225132210&agent_bill_id=20100225132210&
        //接收异步通知参数
        String result = request.getParameter("result");//支付结果，1=成功（没有其他情况，因为之后支付成功才会下发异步通知）
        String agent_id = request.getParameter("agent_id");//商户ID
        String jnet_bill_no = request.getParameter("jnet_bill_no");//汇付宝订单号
        String agent_bill_id = request.getParameter("agent_bill_id");//商户系统订单号
        String pay_type = request.getParameter("pay_type");//支付类型
        String pay_amt = request.getParameter("pay_amt");//实际支付金额
        String remark = request.getParameter("remark");//商户自定义参数，透传参数
        String sign = request.getParameter("sign");//签名

        String key = "4B05A95416DB4184ACEE4313";
        //验签
        String b_sign = "result="+result+
                "&agent_id="+agent_id+
                "&jnet_bill_no="+jnet_bill_no+
                "&agent_bill_id="+agent_bill_id+
                "&pay_type="+pay_type+
                "&pay_amt="+pay_amt+
                "&remark="+remark+
                "&key="+key;
        b_sign = SmallTools.MD5en(b_sign);
        if(b_sign.equals(sign)){
            System.out.println("签名验证成功");
            PrintWriter out = response.getWriter();
            out.print("ok");
            out.flush();
            out.close();
        }else {
            System.out.println("签名验证失败");
            PrintWriter out = response.getWriter();
            out.print("error");
            out.flush();
            out.close();
        }
    }
}
