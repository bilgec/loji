package io.fabric8.lojiplatform.rest;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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

	public String jsonify(Object input) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(input);
		return json;
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

	// TODO
	public int createProduct() throws Exception {
		Integer id = (Integer) models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"product.product", "create", Arrays.asList(new HashMap() {
					{
						put("name", "ENISTEST PROD5");
						put("purchase_ok", true);
						put("sale_ok", true);
						put("list_price", 13);
						put("description", "aadesssccc");
						put("ean13", "12345");
						put("categ_id", 616);
						put("route_ids",
								Arrays.asList(Arrays.asList(4, 1, 0),
										Arrays.asList(4, 5, 0)));

					}
				})));
		return id;
	}

	public Boolean deleteProduct(int productId) throws Exception {
		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"product.product", "unlink",
				Arrays.asList(Arrays.asList(productId))));

		if (result instanceof Boolean) {
			// odoo, ID bulunamasa da true donuyor
			// yani sadece varolan birseyi silemeyince hata donuyor
			return (Boolean) result;
		} else {
			throw new Exception("API Hatasi" + result);
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

	public String getCustomerWithCustomerId(String customerId) throws Exception {
		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"res.partner", "read",
				Arrays.asList(Integer.parseInt(customerId))));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result;
			return jsonify(resultList);
		}
	}

	// TODO
	public int createCustomer() throws Exception {

		HashMap<?, ?> newCustomer = new HashMap<Object, Object>() {
			{
				put("name", "ENIS CUSTOMER NEW");
				put("parent_id", "");
				put("city", "ISTANBUL");
				put("mobile", "05553332211");
				put("phone", "02163332211");
				put("email", "enis@enis.com");
				put("street", "adres bolum1");
				put("street2", "adres bolum2");
				put("customer", false);
				put("company_id", "");
				put("is_company", true);
				put("type", "delivery");
				put("vat", "");

			}
		};

		Integer id = (Integer) models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"res.partner", "create", Arrays.asList(newCustomer)));

		return id;
	}

	public Boolean deleteCustomer(int customerId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"res.partner", "unlink",
				Arrays.asList(Arrays.asList(customerId))));

		if (result instanceof Boolean) {
			// odoo, ID bulunamasa da true donuyor
			// yani sadece varolan birseyi silemeyince hata donuyor
			return (Boolean) result;
		} else {
			throw new Exception("API Hatasi" + result);
		}
	}

	// ORDER
	// ORDER
	// ORDER
	public String getOrderWithOrderId(String orderId) throws Exception {

		Object result = models
				.execute("execute_kw", Arrays.asList(Constants.odooDb,
						Constants.odooUid, Constants.odooPass, "sale.order",
						"read", Arrays.asList(Integer.parseInt(orderId))));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result;
			return jsonify(resultList);
		}
	}

	public String getOrderWithOrderName(String orderName) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order", "search", Arrays.asList(Arrays.asList(Arrays
						.asList("name", "=", orderName)))));

		Object[] resultSet = (Object[]) result;
		int resultCode = 0;
		if (resultSet.length > 0) {
			resultCode = (int) resultSet[0];
			// uniq oldugunu varsayip ilkini cekiyoruz
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

	// TODO
	public int createOrder() throws Exception {

		HashMap<?, ?> newOrder = new HashMap<Object, Object>() {
			{
				put("name", "ENISORDER002");
				put("partner_id", 37880);
				put("partner_invoice_id", 37880);
				put("partner_shipping_id", 37880);
				put("state", "draft");
				put("user_id", 7);
				put("note", "ekstranot");
				put("picking_policy", "one");
				put("company_id", "");
				put("date_order", "2015-08-20T10:10:10+05:00");
				put("warehouse_id", 4);// dermoeczane
				put("origin", "ORG001");
				put("campaign_code", "");
				put("channel_name", "dermoeczane");
				put("payment_method", "KREDIKARTI");
				put("bank_details", "GARANTI");
				put("bank_provision_id", "zzzz00011");
				put("bank_payment_id", "12345");

			}
		};

		Integer id = (Integer) models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order", "create", Arrays.asList(newOrder)));

		return id;
	}

	public int addOrderProduct(final String orderId, final String productId,
			final String productQuantity, final String price) throws Exception {

		HashMap<?, ?> newOrder = new HashMap<Object, Object>() {
			{
				put("order_id", Integer.parseInt(orderId));
				put("product_id", Integer.parseInt(productId));
				put("product_uom_qty", Integer.parseInt(productQuantity));
				put("tax_id", Arrays.asList(6, 0, Arrays.asList(10)));
				put("price_unit", Double.parseDouble(price));

			}
		};

		Integer id = (Integer) models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order.line", "create", Arrays.asList(newOrder)));

		return id;
	}

	public Boolean deleteOrderProduct(int orderLineId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order.line", "unlink",
				Arrays.asList(Arrays.asList(orderLineId))));

		if (result instanceof Boolean) {
			// odoo, ID bulunamasa da true donuyor
			// yani sadece varolan birseyi silemeyince hata donuyor
			return (Boolean) result;
		} else {
			throw new Exception("API Hatasi" + result);
		}
	}

	public String listOrderProduct(int orderId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order.line", "search", Arrays.asList(Arrays.asList(Arrays
						.asList("order_id", "=", orderId)))));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			Object result2 = models.execute("execute_kw", Arrays.asList(
					Constants.odooDb, Constants.odooUid, Constants.odooPass,
					"sale.order.line", "read", Arrays.asList(result)));

			if (result2 instanceof Boolean) {
				throw new Exception("Kayıt bulunamadı");
			} else {
				return jsonify(result2);
			}
		}
	}

	public String getOrderProduct(int saleOrderLineId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"sale.order.line", "read", Arrays.asList(saleOrderLineId)));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result;
			return jsonify(resultList);
		}

	}

	// STOCK
	// STOCK
	// STOCK
	// TODO
	public String getStock(int productId) throws Exception {

		Object result = models.execute("execute_kw", Arrays.asList(
				Constants.odooDb, Constants.odooUid, Constants.odooPass,
				"stock.quant", "read", Arrays.asList(productId)));

		if (result instanceof Boolean) {
			throw new Exception("Kayıt bulunamadı");
		} else {
			HashMap<Object, Object> resultList = (HashMap<Object, Object>) result;
			return jsonify(resultList);
		}
	}

}
