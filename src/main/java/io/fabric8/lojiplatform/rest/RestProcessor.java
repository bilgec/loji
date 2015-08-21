package io.fabric8.lojiplatform.rest;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Property;

public class RestProcessor {

	RPCService rpcService = new RPCService();

	public static void main(String args[]) {

		RPCService rpcService = new RPCService();

		System.out.println("Test Baslangic");
		try {
			System.out.println(rpcService.listOrderProduct(19427));
			System.out.println(rpcService.deleteOrderProduct(37637));
			System.out.println(rpcService.listOrderProduct(19427));

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
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getOrder(@Header("orderId") String orderId, @Body String body) {
		try {
			return rpcService.getOrderWithOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getOrderName(@Header("orderName") String orderName,
			@Body String body) {
		try {
			return rpcService.getOrderWithOrderName(orderName);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getCustomer(@Header("customerId") String customerId,
			@Body String body) {
		try {
			return rpcService.getCustomerWithCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getCustomerName(@Header("customerName") String customerName,
			@Body String body) {
		try {
			return rpcService.getCustomerWithCustomerName(customerName);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public int addOrderProduct(@Header("orderId") String orderId,
			@Header("productId") String productId,
			@Header("productQuantity") String productQuantity,
			@Header("price") String price, @Body String body) {
		try {
			return rpcService.addOrderProduct(orderId, productId,
					productQuantity, price);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String deleteOrderProduct(@Header("orderLineId") String orderLineId,
			@Body String body) {
		try {
			return rpcService.deleteOrderProduct(Integer.parseInt(orderLineId))
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String listOrderProduct(@Header("orderId") String orderId,
			@Body String body) {
		try {
			return rpcService.listOrderProduct(Integer.parseInt(orderId))
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String getOrderProduct(@Header("orderLineId") String orderLineId,
			@Body String body) {
		try {
			return rpcService.getOrderProduct(Integer.parseInt(orderLineId))
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}