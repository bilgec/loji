package io.fabric8.lojiplatform.rest;

import org.apache.camel.Body;
import org.apache.camel.Header;

public class RestProcessor {

	RPCService rpcService = new RPCService();

	public static void main(String args[]) {

		RPCService rpcService = new RPCService();

		System.out.println("Test Baslangic");
		try {
			RestProcessor restPro = new RestProcessor();
			System.out.println(rpcService.getOrderWithOrderId("22801"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Test Bitis");
	}

	public String getProduct(@Header("productId") String productId,
			@Body String body) {

		try {
			int productIdInteger = Integer.parseInt(productId);
			return rpcService.getProductWithProductId(productIdInteger);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getOrder(@Header("orderId") String orderId, @Body String body) {

		try {
			return rpcService.getOrderWithOrderId(orderId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getCustomer(@Header("customerName") String customerName,
			@Body String body) {

		try {
			return rpcService.getCustomerWithCustomerName(customerName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

}