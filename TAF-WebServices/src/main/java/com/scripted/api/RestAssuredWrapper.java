package com.scripted.api;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.restassured.specification.FilterableRequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RestAssuredWrapper {

    GetProp getPropertiesInMap = new GetProp();
    TreeMap<String, Object> propMap = new TreeMap<>();
    Map<String, Object> ObjMap = new HashMap<>();

    public String filename = null;
    public static Response apiResponse = null;
    public static String url = null;
    public static String uri = null;
    public String path = null;
    public static String queryParam = null;
    public static RequestSpecification restSpec = RestAssured.given();
    public static Logger LOGGER = Logger.getLogger(RestAssuredWrapper.class);
    public String contentType = null;

    public RequestSpecification CreateRequest(RequestParams raWrapper) {
        try {
            propMap = getPropertiesInMap.getPropValues("src/main/resources/WebServices/Properties/" + filename);
            ObjMap = ConvertObjectToMap(raWrapper);
            boolean isPropMapEmpty = propMap.isEmpty();

            if (!isPropMapEmpty) {
                for (Map.Entry<String, Object> stringObjectEntry : propMap.entrySet()) {
                    Map.Entry<String, Object> reqParams = stringObjectEntry;
                    restSpec.baseUri(getBaseURI());

                    if (reqParams.getKey().equals("path")) {
                        path = reqParams.getValue().toString();
                        System.out.println("path in restAssuredWrapper: " + path);
                    }

                    if (reqParams.getKey().equals("proxyAndPort")) {
                        if (!reqParams.getValue().toString().isEmpty()) {
                            String proxyPort = reqParams.getValue().toString();
                            String[] splitproxyPort = proxyPort.split(":");
                            int port = Integer.parseInt(splitproxyPort[1]);
                            restSpec.proxy(splitproxyPort[0], port);
                        }
                    }
                    if (reqParams.getKey().equals("contenttype")) {
                        if (reqParams.getValue().toString().contains("json")) {
                            restSpec.contentType(ContentType.JSON);
                        }
                        if (reqParams.getValue().toString().contains("xml")) {
                            restSpec.contentType(ContentType.XML);
                        }
                        if (reqParams.getValue().toString().contains("x-www-form-urlencoded")) {
                            restSpec.contentType(ContentType.URLENC);
                        }
                        contentType = reqParams.getValue().toString();
                    }
                    if (reqParams.getKey().equals("accept")) {
                        if (reqParams.getValue().toString().contains("json")) {
                            restSpec.accept(ContentType.JSON);
                        }
                        if (reqParams.getValue().toString().contains("xml")) {
                            restSpec.accept(ContentType.XML);
                        }
                    }

                    if (reqParams.getKey().equals("restMethodType")) {
                        if (reqParams.getValue().toString().equals("POST")
                                || reqParams.getValue().toString().equals("PUT")
                                || reqParams.getValue().toString().equals("PATCH")) {
//							restSpec.formParams(raWrapper.getFormParams());
//							restSpec.body(raWrapper.getJsonbody().toString());
                            if (contentType.contains("x-www-form-urlencoded")) {
//                                clearFormParam(restSpec);
                                restSpec.formParams(raWrapper.getFormParams());
                            } else if (contentType.contains("json")) {
                                try {
//                                    clearFormParam(restSpec);
                                    restSpec.body(raWrapper.getJsonbody().toString());
                                } catch (Exception e) {
                                    clearFormParam(restSpec);
                                }
                            }
                        }
                    }
                }
            }
            LOGGER.info("Successfully added headers");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error while trying to create request :" + e);
            Assert.fail("Error while trying to create request" + e);
        }
        return restSpec;
    }

    public void clearFormParam(RequestSpecification requestSpecification) {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) requestSpecification;
        Map<String, String> myMap = new ConcurrentHashMap<>();
        myMap = ((FilterableRequestSpecification) requestSpecification).getFormParams();
        ArrayList<String> keyList = new ArrayList<String>(myMap.keySet());
        List keys = new CopyOnWriteArrayList(keyList);
        Iterator iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            filterableRequestSpecification.removeFormParam(key);
        }
    }

    public void setAPIFileProName(String fileName) {
        this.filename = fileName;
    }

    public void setBaseURI(String uri) {
        this.uri = uri;
    }

    public String getBaseURI() {
        return uri;
    }

    public static Map<String, Object> ConvertObjectToMap(RequestParams raWrapper)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Class<?> pomclass = raWrapper.getClass();
            pomclass = raWrapper.getClass();
            java.lang.reflect.Method[] methods = raWrapper.getClass().getMethods();


            for (java.lang.reflect.Method m : methods) {
                if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {
                    Object value = (Object) m.invoke(raWrapper);
                    map.put(m.getName().substring(3), (Object) value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error while trying to convert object to map :" + e);
            Assert.fail("Error while trying to convert object to map :" + e);
        }
        return map;
    }

    public void sendRequest(String method, RequestSpecification reqSpec) {
        try {
            if (method.contains("Get")) {
                reqSpec.relaxedHTTPSValidation();
                Response response = reqSpec.when().get(path);
                setReponse(response);
            }
            if (method.contains("Post")) {
                reqSpec.relaxedHTTPSValidation();
                Response response = reqSpec.post(path);
                setReponse(response);
            }
            if (method.contains("Put")) {
                reqSpec.relaxedHTTPSValidation();
                Response response = reqSpec.put(path);
                setReponse(response);
            }
            if (method.contains("Patch")) {
                reqSpec.relaxedHTTPSValidation();
                Response response = reqSpec.patch(path);
                setReponse(response);
            }
            if (method.contains("Delete")) {
                reqSpec.relaxedHTTPSValidation();
                Response response = reqSpec.delete(path);
                setReponse(response);
            }
            LOGGER.info("Successfully send the request and got the response");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Fail to send the request, Exception :" + e);
            Assert.fail("Fail to send the request, Exception :" + e);
        }

    }

    public void sendGetRequestWithParams(RequestSpecification reqSpec) {
        try {
            reqSpec.relaxedHTTPSValidation();
            Response response = reqSpec.when().get(uri);
            String responseBody = response.getBody().asString();
            setReponse(response);
            LOGGER.info("Successfully send the request and got the response");
        } catch (Exception e) {
            LOGGER.error("Fail to send the request, Exception :" + e);
            Assert.fail("Fail to send the request, Exception :" + e);
        }
    }

    public Response getResponse() {
        LOGGER.info("get Response : " + apiResponse.asString());
        return this.apiResponse;
    }

    public void setReponse(Response response) {
        this.apiResponse = response;
        LOGGER.info("Set Response : " + apiResponse.asString());
    }

    public void valResponseCode(int respCode) {
        try {
            int statusCode = apiResponse.getStatusCode();
            System.out.println("Response Code : " + statusCode);
            assertEquals(respCode, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Response code, Exception :" + e);
            Assert.fail("Response code, Exception :" + e);
        }
    }

    public static Object fetchJsonPathValue(JSONObject jsonObject, String jsonPath, String jsonKey) {
        int jsonPathLen;
        Object strObjValue = null;
        try {
            JSONArray jsonArr = new JSONArray();
            if (jsonPath.contains(".")) {
                String[] splitJsonObj = jsonPath.split("\\.");
                jsonPathLen = splitJsonObj.length;
                for (int i = 0; i < jsonPathLen; i++) {
                    if (splitJsonObj[i].contains("[")) {
                        String[] splitJsonArr = splitJsonObj[i].split("\\[");
                        jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
                        String jsonarrCount = splitJsonArr[1].replace("]", "");
                        int arrCount = Integer.parseInt(jsonarrCount);
                        jsonObject = jsonArr.getJSONObject(arrCount);
                    } else {
                        jsonObject = jsonObject.getJSONObject(splitJsonObj[i]);
                    }
                }
                strObjValue = jsonObject.get(jsonKey).toString();

            } else {

                if (jsonPath.contains("[")) {
                    String[] splitJsonArr = jsonPath.split("\\[");
                    jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
                    String jsonarrCount = splitJsonArr[1].replace("]", "");
                    int arrCount = Integer.parseInt(jsonarrCount);
                    jsonObject = jsonArr.getJSONObject(arrCount);
                } else {
                    jsonObject = jsonObject.getJSONObject(jsonPath);
                }
                strObjValue = jsonObject.get(jsonKey).toString();
            }

        } catch (Exception e) {
            LOGGER.error("Error in fetching the jsonpath value, Exception :" + e);
            Assert.fail("Error in fetching the jsonpath value, Exception :" + e);


        }
        return strObjValue;
    }


    public void valJsonResponseVal(String jsonPath, Object expValue) {

        try {
            String strVal = null;
            int intVal;
            boolean bVal;
            double dVal;

            JsonPath jsonPathEvaluator = apiResponse.jsonPath();
            Object obj = jsonPathEvaluator.get(jsonPath);
            String getObjType = obj.getClass().getTypeName().toString();
            if (getObjType.contains("String")) {
                strVal = jsonPathEvaluator.get(jsonPath);
                Assert.assertEquals("Mismatch in the response - JsonPath : " + jsonPath, expValue, strVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + strVal);
            }
            if (getObjType.contains("Integer")) {
                intVal = jsonPathEvaluator.get(jsonPath);
                Assert.assertEquals("Mismatch in the response - JsonPath : " + jsonPath, expValue, intVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + intVal);
            }
            if (getObjType.contains("Boolean")) {
                bVal = jsonPathEvaluator.get(jsonPath);
                Assert.assertEquals("Mismatch in the response - JsonPath : " + jsonPath, expValue, bVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + bVal);
            }
            if (getObjType.contains("Double")) {
                dVal = jsonPathEvaluator.get(jsonPath);
                Assert.assertEquals("Mismatch in the response - JsonPath : " + jsonPath, expValue, dVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + dVal);
            }
        } catch (Exception e) {
            LOGGER.error("Error in validating the response, Exception :" + e);
            Assert.fail("Error in validating the response, Exception :" + e);
        }
    }

    public void valXmlResponseVal(String xmlPath, Object expValue) {
        try {
            String strVal = null;
            int intVal;
            boolean bVal;
            double dVal;
            XmlPath xmlPathEvaluator = apiResponse.xmlPath();
            Object obj = xmlPathEvaluator.get(xmlPath);
            String getObjType = obj.getClass().getTypeName().toString();
            if (getObjType.contains("String")) {
                strVal = xmlPathEvaluator.get(xmlPath);
                Assert.assertEquals("Mismatch in the response - XmlPath : ", expValue, strVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + strVal);
            }
            if (getObjType.contains("Integer")) {
                intVal = xmlPathEvaluator.get(xmlPath);
                Assert.assertEquals("Mismatch in the response - XmlPath : ", expValue, intVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + intVal);
            }
            if (getObjType.contains("Boolean")) {
                bVal = xmlPathEvaluator.get(xmlPath);
                Assert.assertEquals("Mismatch in the response - XmlPath : ", expValue, bVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + bVal);
            }
            if (getObjType.contains("Double")) {
                dVal = xmlPathEvaluator.get(xmlPath);
                Assert.assertEquals("Mismatch in the response - XmlPath : ", expValue, dVal);
                LOGGER.info("Expected value " + expValue + " is matching with the actual : " + dVal);
            }
        } catch (Exception e) {
            LOGGER.error("Error in validating the response, Exception :" + e);
            Assert.fail("Error in validating the response, Exception :" + e);
        }
    }

    public void setGetQueryParams(Map<String, String> params) {
        restSpec.params(params);
    }

    public void validateJsonKeyExistence(String jsonPath) {
        try {
            JsonPath jsonPathEvaluator = apiResponse.jsonPath();
            Object obj = jsonPathEvaluator.get(jsonPath);
            if (obj != null) {
                LOGGER.info("element " + jsonPath + " is exist in the responce");
            } else {
                LOGGER.info("element " + jsonPath + " is not exist in the responce");
                Assert.fail("element " + jsonPath + " is not exist in the responce");
            }
        } catch (Exception e) {
            LOGGER.error("Error in while checking existence element:" + e);
            Assert.fail("Error in while checking existence element:" + e);
        }
    }

    public byte[] convertFileToByte() {
        String jsonval = apiResponse.asString();
        byte[] responseval = jsonval.getBytes();
        return responseval;
    }

    public JSONObject convertStringToJson() {
        String jsonval = apiResponse.asString();
        //JsonObject jsonObject = new JsonParser().parse(jsonval).getAsJsonObject();
        JSONObject json = new JSONObject(jsonval);
        return json;
    }

    public boolean valJsonEleNotExists(JSONObject jsonObject, String jsonPath, String jsonKey) {
        int jsonPathLen;
        String errorMessage = null;
        Object strObjValue = null;
        boolean flag = true;
        try {

            JSONArray jsonArr = new JSONArray();
            if (jsonPath.contains(".")) {
                String[] splitJsonObj = jsonPath.split("\\.");
                jsonPathLen = splitJsonObj.length;
                for (int i = 0; i < jsonPathLen; i++) {
                    if (splitJsonObj[i].contains("[")) {
                        String[] splitJsonArr = splitJsonObj[i].split("\\[");
                        jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
                        String jsonarrCount = splitJsonArr[1].replace("]", "");
                        int arrCount = Integer.parseInt(jsonarrCount);
                        jsonObject = jsonArr.getJSONObject(arrCount);
                    } else {
                        jsonObject = jsonObject.getJSONObject(splitJsonObj[i]);
                    }
                }
                strObjValue = jsonObject.get(jsonKey).toString();

            } else {

                if (jsonPath.contains("[")) {
                    String[] splitJsonArr = jsonPath.split("\\[");
                    jsonArr = jsonObject.getJSONArray(splitJsonArr[0]);
                    String jsonarrCount = splitJsonArr[1].replace("]", "");
                    int arrCount = Integer.parseInt(jsonarrCount);
                    jsonObject = jsonArr.getJSONObject(arrCount);
                } else {
                    jsonObject = jsonObject.getJSONObject(jsonPath);
                }
                strObjValue = jsonObject.get(jsonKey).toString();
            }

        } catch (Exception e) {
            //LOGGER.error("Error in fetching the jsonpath value, Exception :" + e);
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            if (errorMessage.contains("not found")) {
                LOGGER.info("Element : " + jsonKey + " is not exist in the response");

            } else {
                flag = false;
            }

        } else {
            flag = false;
        }

        return flag;
    }
}
