package com.paramount.shopping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paramount.shopping.domian.TbSeckillOrder;
import com.paramount.shopping.domian.TbUser;
import com.paramount.shopping.domian.response.Result;
import com.paramount.shopping.service.SeckillOrderService;
import com.paramount.shopping.service.WeixinPayService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	private WeixinPayService weixinPayService;

	@Autowired
	private SeckillOrderService seckillOrderService;
	
	@RequestMapping("/createNative")
	public Map createNative(){
		//1.获取当前登录用户
		String username =((TbUser)SecurityUtils.getSubject().getPrincipal()).getName();
		//2.提取秒杀订单（从缓存 ）
		TbSeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(username);
		//3.调用微信支付接口
		if(seckillOrder!=null){
			return weixinPayService.createNative(seckillOrder.getId()+"", (long)(seckillOrder.getMoney().doubleValue()*100)+"");		
		}else{
			return new HashMap<>();
		}		
	}

	
	@RequestMapping("/queryPayStatus")
	public Result queryPayStatus(String out_trade_no){
		
		//1.获取当前登录用户
        String username =((TbUser)SecurityUtils.getSubject().getPrincipal()).getName();
				
		Result result=null;
		int x=0;
		while(true){
			
			Map<String,String> map = weixinPayService.queryPayStatus(out_trade_no);//调用查询
			if(map==null){
				result=new Result(false, "支付发生错误");
				break;
			}
			if(map.get("trade_state").equals("SUCCESS")){//支付成功
				result=new Result(true, "支付成功");				
				//保存订单
				seckillOrderService.saveOrderFromRedisToDb(username, Long.valueOf(out_trade_no) ,map.get("transaction_id"));
				break;
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			x++;
			if(x>=100){	
								
				result=new Result(false, "二维码超时");
				
				// 关闭支付
				Map<String,String> payResult = weixinPayService.closePay(out_trade_no);
				if(payResult!=null &&  "FAIL".equals( payResult.get("return_code"))){
					if("ORDERPAID".equals(payResult.get("err_code"))){
						result=new Result(true, "支付成功");				
						//保存订单
						seckillOrderService.saveOrderFromRedisToDb(username, Long.valueOf(out_trade_no) ,map.get("transaction_id"));
					}					
				}
				
				//删除订单
				if(result.isSuccess()==false){
					seckillOrderService.deleteOrderFromRedis(username, Long.valueOf(out_trade_no));
				}
				break;				
			}
			
		}
		return result;
	}
	
	
}
