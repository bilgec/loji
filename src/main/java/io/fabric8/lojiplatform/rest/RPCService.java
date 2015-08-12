package io.fabric8.lojiplatform.rest;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.google.gson.Gson;

public class RPCService {

	XmlRpcClient models;

	public RPCService() {
		final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
		try {
			common_config.setServerURL(new URL(String.format(
					"%s/xmlrpc/2/common", Constants.odooUrl)));

			models = new XmlRpcClient() {
				{
					setConfig(new XmlRpcClientConfigImpl() {
						{
							setServerURL(new URL(String.format(
									"%s/xmlrpc/2/object", Constants.odooUrl)));
						}
					});
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// PRODUCT
	// PRODUCT
	// PRODUCT
	public String getProductWithProductId(int productId) throws Exception {
		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"product.product", "read", Arrays.asList(productId)));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result;
			return jsonify(resultList);
		}
	}

	// ORDER
	// ORDER
	// ORDER
	public String getOrderWithOrderId(String orderId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order", "search", Arrays.asList(Arrays.asList(Arrays
						.asList("name", "=", orderId)))));

		Object[] resultSet = (Object[]) result;
		int resultCode = 0;
		if (resultSet.length > 0) {
			resultCode = (int) resultSet[0];
		} else {
			throw new Exception("Arama Sonuç Vermedi");
		}

		Object result2 = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order", "read", Arrays.asList(resultCode)));

		if (result2 instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result2;
			return jsonify(resultList);
		}
	}

	// CUSTOMER
	// CUSTOMER
	// CUSTOMER
	public String getCustomerWithCustomerName(String customerName)
			throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"res.partner", "search", Arrays.asList(Arrays.asList(Arrays
						.asList("name", "=", customerName)))));

		Object[] resultSet = (Object[]) result;
		int resultCode = 0;
		if (resultSet.length > 0) {
			resultCode = (int) resultSet[0];
		} else {
			throw new Exception("Arama Sonuç Vermedi");
		}

		Object result2 = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"res.partner", "read", Arrays.asList(resultCode)));

		if (result2 instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result2;
			return jsonify(resultList);
		}
	}

	public String jsonify(Object input) {
		Gson gson = new Gson();
		String json = gson.toJson(input);
		return json;
	}

}
