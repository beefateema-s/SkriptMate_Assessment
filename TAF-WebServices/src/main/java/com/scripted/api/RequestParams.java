package com.scripted.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class RequestParams {

    public String uri, path, pathChange;
    public String contenttype;
    public String proxy;
    public int port;
    public JSONObject jsonBody;
    public JSONArray jsonArrBody;
    public String RestMethodType;
    public Map<String, String> formParams;
    public String genJsonResponsePath = "src/main/resources/GenRocket/Output/";
    public String apiJsonRequestPath = "src/main/resources/WebServices/APIJsonRequest/";
    public String apiURLEncodedRequestPath = "src/main/resources/WebServices/APIURLEncodedRequest/";
    public static Logger LOGGER = Logger.getLogger(RequestParams.class);
    GetProp getPropertiesInMap = new GetProp();
    TreeMap<String, Object> propMap = new TreeMap<>();

    public String getRestMethodType() {
        return RestMethodType;
    }

    public void setRestMethodType(String restMethodType) {
        RestMethodType = restMethodType;
    }

    public JSONObject getJsonbody() {
        return jsonBody;
    }

    public void setJsonbody(String fileName) {
        try {
            String requestFilePath = GetProp.getFilePath(apiJsonRequestPath + fileName + ".txt");
            String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
            jsonBody = new JSONObject(content);
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            Assert.fail("Error while set the Json body : " + e);
            e.printStackTrace();
        }
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public void setFormParams(String fileName, Map<String, String> dynamicBodyValues) {
        try {
            String requestFilePath = GetProp.getFilePath(apiURLEncodedRequestPath + fileName + ".txt");
            formParams = HashMapFromTextFile(requestFilePath, dynamicBodyValues);
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            Assert.fail("Error while set the Json body : " + e);
            e.printStackTrace();
        }
    }


    public void setGenJsonbody(String fileName) {
        try {
            String requestFilePath = GetProp.getFilePath(genJsonResponsePath + fileName + ".json");
            String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
            jsonArrBody = new JSONArray(content);
            jsonBody = jsonArrBody.getJSONObject(0);
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            e.printStackTrace();
        }
    }

    public String getPath() {

        return path;
    }

    public static Map<String, String> HashMapFromTextFile(String filePath, Map<String, String> dynamicBodyValues) {
        Map<String, String> requestBodyMap = new HashMap<String, String>();
        BufferedReader br = null;

        try {

            // create file object
            File file = new File(filePath);

            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split("=");

                // first part is name, second is number
                String key = parts[0].trim();
                String value = parts[1].trim();

                // put name, number in HashMap if they are
                // not empty
                if (!key.equals("") && !value.equals(""))
                    requestBodyMap.put(key, value);
            }
            if (!dynamicBodyValues.isEmpty()) {
                for (Map.Entry<String, String> bodyValues : dynamicBodyValues.entrySet()) {
                    for (Map.Entry<String, String> requestBodyValues : requestBodyMap.entrySet())
                        if (bodyValues.getKey().equals(requestBodyValues.getKey())) {
                            requestBodyMap.replace(bodyValues.getKey(), bodyValues.getValue());
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }

        return requestBodyMap;
    }

    /**
     * @param uri
     * @param contentType
     * @param mediaType
     * @param basePath
     * @param baseURI
     * @param body
     * @param header
     * @param proxy
     * @param host
     * @param port
     * @param scheme
     * @param proxy_URI
     * @param iSrelaxedHTTPSValidation
     * @param protocol
     * @param file
     * @param password
     * @param path
     * @param keystore
     * @param headersMap
     */
    public RequestParams(String uri, String contentType, String proxy, int port, String body, String restMethodType) {
        super();
        this.uri = uri;
        this.contenttype = contentType;
        this.proxy = proxy;
        this.port = port;
        //this.body = body;
        this.RestMethodType = restMethodType;
    }

    public String getproxy() {
        return proxy;
    }

    public void setproxyAndPort(String proxy, int port) {
        this.proxy = proxy;
        this.port = port;
    }

    public int getport() {
        return port;
    }

    public void setport(int port) {
        this.port = port;
    }

    public String geturi() {
        return uri;
    }

    public void seturi(String uri) {
        this.uri = uri;
    }

    public String getcontenttype() {
        return contenttype;
    }

    public void setcontenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void updateUrlPath(String strPropFileName, Map<String, String> dynamicBodyValues) {
        try {
            propMap = getPropertiesInMap.getPropValues("src/main/resources/WebServices/Properties/" + strPropFileName + ".properties");
            boolean isPropMapEmpty = propMap.isEmpty();
            if (!isPropMapEmpty) {
                for (Map.Entry<String, Object> reqParams : propMap.entrySet()) {
                    if (reqParams.getKey().equals("path")) {
                        pathChange = reqParams.getValue().toString();
                        if (!dynamicBodyValues.isEmpty()) {
                            for (Map.Entry<String, String> bodyValues : dynamicBodyValues.entrySet()) {
                                if (reqParams.getValue().toString().contains(bodyValues.getKey())) {
                                    pathChange = pathChange.replace("[" + bodyValues.getKey() + "]", bodyValues.getValue());
                                    path = new String(pathChange);
                                    System.out.println("path: " + path);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
            Assert.fail("Error while set the Url path : " + e);
            e.printStackTrace();
        }
    }

    public RequestParams() {
    }

}
